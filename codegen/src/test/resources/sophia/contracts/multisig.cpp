
#define ENABLE_TRACE
#include <map>

#include "../../platonlib/include/platon/event.hpp"
#include "../../platonlib/include/platon/contract.hpp"
#include "../../platonlib/include/platon/state.hpp"
#include "../../platonlib/include/platon/serialize.hpp"
#include "../../platonlib/include/platon/storage.hpp"


namespace platon {
    class MultiSig : public Contract {
    private:
        const uint8_t kMaxOwnerCount = 50;
        const Address ZeroAddress;
    private:
        PLATON_EVENT(Confirmation, const char*, int64_t)
        PLATON_EVENT(Revocation, const char*, int64_t)
        PLATON_EVENT(Submission, int64_t)
        PLATON_EVENT(Execution, const char*)
        PLATON_EVENT(ExecutionFailure, const char*)
        PLATON_EVENT(Deposit, const char*, const char*)
        PLATON_EVENT(OwnerAddition, int64_t)
        PLATON_EVENT(OwnerRemoval, int64_t)
        PLATON_EVENT(RequirementChange, int64_t)

        struct Transaction {
            Address destination;
            Address from;
            u256 value;
            int64_t time;
            u256 fee;
            std::string data;
            bool executed;
            bool pending;
            PLATON_SERIALIZE(Transaction, (destination)(from)(value)(time)(fee)(data)(executed)(pending))
        };

        struct TxId {
            TxId(uint64_t i) : id(i){}
            uint64_t id;
            const std::string type = "transaction";
            PLATON_SERIALIZE(TxId, (id)(type))
        };
        struct ConfirmID {
            ConfirmID(uint64_t i) : id(i){}
            uint64_t id;
            const std::string type = "confirm";
            PLATON_SERIALIZE(ConfirmID, (id)(type))
        };

#define REQUIRED  "required"
#define TXCOUNT "transactionCount"
#define OWNERS "owners"
#define INIT "init"

//        std::map<int64_t, Transaction> transactions_;
//        std::map<int64_t, std::map<Address, bool>> confirmations_;
//        std::map<Address, bool> isOwner_;
//        std::vector<Address> owners_;
//        uint64_t required_;
//        uint64_t transactionCount_;



    public:
        MultiSig(){}
        void init(){
        }

    private:
        void assertWallet() {
            PlatonAssert(origin() == address(), "only wallet");
        }

        void ownerDoesNotExist(Address owner) {
            bool exist = false;
            getState(owner, exist);
            PlatonAssert(exist, "");
        }

        void ownerExists(Address owner) {
            bool exist = false;
            getState(owner, exist);
            TRACE("owner is exist:", exist ? "true":"false");
            PlatonAssert(exist, "");
        }

        void transactionExists(uint64_t transactionId) {
            TxId txid(transactionId);
            Transaction tx;
            getState(txid, tx);
            PlatonAssert(tx.destination != ZeroAddress, "");
        }

        bool confirmed(uint64_t transactionId, Address owner) {
            TRACE("confirm transactionid:", transactionId);
            ConfirmID cid(transactionId);
            std::map<Address, bool> addrMap;
            getState(cid, addrMap);
            for (auto& kv : addrMap) {
                TRACE("addrMap first:", kv.first.toString(), " second:", kv.second ? "true" : "false");
            }

            if (addrMap.find(owner) == addrMap.end()) {
                return false;
            }

            return addrMap[owner];
        }

        void notConfirmed(uint64_t transactionId, Address owner) {
            ConfirmID cid(transactionId);
            std::map<Address, bool> addrMap;
            getState(cid, addrMap);
            if (addrMap.find(owner) == addrMap.end()) { return; }
            TRACE("addrMap:", owner.toString(), addrMap[owner] ? "true" : "false");
            PlatonAssert(!addrMap[owner], "");
        }

        void notExecuted(uint64_t transactionId) {
            TxId txid(transactionId);
            Transaction tx;
            getState(txid, tx);
            TRACE("txid:", transactionId, tx.executed ? "true" : "false");
            PlatonAssert(!tx.executed, "");
        }

        void notNull(Address address) {
            TRACE("address:", address.toString());
            PlatonAssert(address != ZeroAddress, "");
        }

        void validRequirement(uint64_t ownerCount, uint64_t required) {
            TRACE("ownerCount:", ownerCount, "required:", required);
            PlatonAssert((ownerCount < kMaxOwnerCount || required < ownerCount || required != 0 || ownerCount != 0), "");
        }

        void payable() {
            u256 value = callValue();
            TRACE("value:", value);
            if (value > 0) {
                PLATON_EMIT_EVENT(Deposit, caller().toString().c_str(), value.convert_to<std::string>().c_str());
            }
        }

    public:
        bool isInitWallet() {
            uint32_t init = 0;
            getState(INIT, init);
            return init == 1;
        }
        void initWallet(const char *owner, uint64_t required) {
            PlatonAssert(!isInitWallet());
            setState(INIT, (uint32_t)1);
            TRACE("init wallet owner:", owner, "required:", required);
            std::vector<std::string> addresses;
            split(owner, addresses, ":");
            TRACE("addresses size:",addresses.size());
            validRequirement(addresses.size(), required);
            std::vector<Address> ownerAddr;
            ownerAddr.reserve(addresses.size());
            for (size_t i = 0; i < addresses.size(); i++) {
                TRACE("address:", addresses[i]);
                bool exist = false;
                Address addr(addresses[i], true);
                TRACE("address convert:", addr.toString());
                getState(addr, exist);
                PlatonAssert(exist == false, "");
                setState(addr, true);
                TRACE("push_back:", addr.toString());
                ownerAddr.push_back(addr);
            }
            setState(OWNERS, ownerAddr);
            setState(REQUIRED, required);
            TRACE("end initWallet");


            std::vector<Address> testAddr;
            getState(OWNERS, testAddr);
            for (size_t i = 0; i< testAddr.size(); i++) {
                TRACE("test:", i, " addr:", testAddr[i].toString());
            }
        }


        uint64_t submitTransaction(const char *destination, const char *from, const char *vs, const char *data, uint64_t len,uint64_t time, const char *fs) {
            TRACE("input args", destination, from, vs, len, time, fs);
            u256 value(vs);
            u256 fee(fs);
            uint64_t transactionId = addTransaction(destination, from, value, data, len, time, fee);
            TRACE("transactionId:", transactionId);
            return transactionId;
        }

        void confirmTransaction(uint64_t transactionId) {
            Address sender = origin();
            TRACE("sender:", sender.toString());
            ownerExists(sender);
            transactionExists(transactionId);
            notConfirmed(transactionId, sender);

            std::map<Address, bool> confirms;
            ConfirmID cid(transactionId);
            getState(cid, confirms);
            confirms[sender] = true;
            setState(cid, confirms);
            PLATON_EMIT_EVENT(Confirmation, sender.toString().c_str(), transactionId);
            executeTransaction(transactionId);
        };

        void revokeConfirmation(uint64_t transactionId) {
            Address sender = origin();
            ownerExists(sender);
            if (confirmed(transactionId, sender)) {
                TRACE("alread confirmed");
                return;
            }
            notExecuted(transactionId);

            Transaction tx;
            getState(TxId(transactionId), tx);
            if (!tx.pending) {
                return;
            }

            ConfirmID cid(transactionId);
            std::map<Address, bool> confirms;
            getState(cid, confirms);
            confirms[sender] = false;
            setState(cid, confirms);
            TRACE("Multsigtx.pending:",toString(tx.pending));
            if(isConfirmed(transactionId)){
                TRACE("isConfirmed:", toString(isConfirmed(transactionId)));
                tx.pending = false;
                setState(TxId(transactionId), tx);
            }
            PLATON_EMIT_EVENT(Revocation, sender.toString().c_str(), transactionId);
        }

        void executeTransaction(uint64_t transactionId) {
            notExecuted(transactionId);

            if (isConfirmed(transactionId)) {
                TRACE("confirm success id:", transactionId);
                Transaction tx;
                getState(TxId(transactionId), tx);
                tx.executed = true;
                tx.pending = false;
                //transfer
                std::string addr = tx.destination.toString();
                int res = callTransfer(Address(addr), tx.value);
                //未来返回值1是失败，0是成功，待底层修改后再来修改合约
                if (res == 0) {
                    PLATON_EMIT_EVENT(Execution, "Execution");
                } else {
                    PLATON_EMIT_EVENT(ExecutionFailure, "ExecutionFailure");
                    tx.executed = false;
                }
                setState(TxId(transactionId), tx);
            }
        }

        int isConfirmed(uint64_t transactionId) const {
            uint64_t count = 0;
            std::vector<Address> owners;
            getState(OWNERS, owners);
            std::map<Address, bool> confirms;
            getState(ConfirmID(transactionId), confirms);
            for (size_t i = 0; i < owners.size(); i++){
                if (confirms[owners[i]]) {
                    count += 1;
                }
                uint64_t required = 0;
                getState(REQUIRED, required);
                if (count == required) {
                    return true;
                }
            }
            return false;
        }

        uint64_t getRequired() const {
            uint64_t required = 0;
            getState(REQUIRED, required);
            TRACE("getRequired required:", required);
            return required;
        }

         uint64_t getListSize() const {
             uint64_t transactionCount = 0;
             getState(TXCOUNT, transactionCount);
             TRACE("getTransactionCount count:", transactionCount);
             return transactionCount;
         }


        uint64_t addTransaction(const char *destination, const char *from, u256 value, const char *data, uint64_t len, uint64_t time, u256 fee){
            TRACE("addTransaction");
            Address dest(destination, true);
            notNull(dest);
            uint64_t transactionId = 0;
            getState(TXCOUNT, transactionId);
            TRACE("get transaction id:", transactionId);
            Transaction transaction;
            transaction.destination = dest;
            transaction.from = Address(std::string(from), true);
            transaction.value = value;
            transaction.time = time;
            transaction.fee = fee;
            transaction.data.append(data, len);
            transaction.executed = false;
            transaction.pending = true;
            TxId txId(transactionId);

            setState(txId, transaction);
            setState(TXCOUNT, transactionId+1);
            TRACE("emit event: Submission", transactionId);
            PLATON_EMIT_EVENT(Submission, transactionId);
            return transactionId;
        }

        uint64_t getConfirmationCount(uint64_t transactionId) const {
            TRACE("getConfirmationCount:", transactionId);
            std::vector<Address> owners;
            getState(OWNERS, owners);
            uint64_t count = 0;

            ConfirmID cid(transactionId);
            std::map<Address, bool> confirms;

            getState(cid, confirms);
            for (size_t i = 0; i < owners.size(); i++) {
                if (confirms[owners[i]]){
                    TRACE("is confirm ", owners[i].toString());
                    count += 1;
                }
                TRACE("is not confirm ", owners[i].toString());
            }
            TRACE("getConfirmationCount count:", count);
            return count;
        }

        uint64_t getTransactionCount(int pending, int executed) const {
            TRACE("getTransactionCount pending:", pending, "executed:", executed);
            uint64_t count = 0;
            uint64_t transactionCount = 0;
            getState(TXCOUNT, transactionCount);
            for (size_t i = 0; i < transactionCount; i++) {
                Transaction transaction;
                getState(TxId(i), transaction);
                TRACE("getTransactionCount :", i, "pending:", pending, "executed:", executed ? "true":"false");
                if (pending && !transaction.executed
                    || executed && transaction.executed){
                    count++;
                }
            }
            TRACE("getTransactionCount count:", count);
            return count;
        }

        const char *getTransactionList(uint64_t from, uint64_t to) const {
        uint64_t transactionCount = 0;
             getState(TXCOUNT, transactionCount);
             if (from > to) { return ""; }
             std::string result;
             size_t end = transactionCount > to ? to : transactionCount;
             for(size_t i = from; i < end; i++){
                 TRACE("get transaction:", i);
                 Transaction transaction;
                 if (getState(TxId(i), transaction) == 0) {
                     TRACE("get transaction:", i, "failed");
                     break;
                 }
                 TRACE("from:",transaction.from.toString(), "dest:", transaction.destination.toString() ,"value:", transaction.value, "time:", transaction.time, "fee:", transaction.fee, "pending:", transaction.pending, "executed:", transaction.executed);
                 result.append(transaction.from.toString())
                         .append("|").append(transaction.destination.toString())
                         .append("|").append(transaction.value.convert_to<std::string>())
                         .append("|").append(toString(transaction.time))
                         .append("|").append(transaction.data)
                         .append("|").append(transaction.fee.convert_to<std::string>())
                         .append("|").append(toString(transaction.pending))
                         .append("|").append(toString(transaction.executed))
                         .append("|").append(toString(i))
                         .append(":");
             }
             TRACE("getTransactionList:", result);
             return result.c_str();
         }


        const char *getOwners()const {
            std::vector<Address> owners;
            getState(OWNERS, owners);
            TRACE("owners size:", owners.size());
            std::string address;
            for (size_t i = 0; i < owners.size(); i++) {
                TRACE("owner[", i, "]:", owners[i].toString());
                if (i != 0){
                    address += ":";
                }
                address += owners[i].toString();
            }
            TRACE("owners size:", address);
            return address.c_str();
        }

        const char * getConfirmations(uint64_t transactionId) const {
            TRACE("getConfirmations id:", transactionId);
            std::vector<Address> owners;
            getState(OWNERS, owners);

            uint64_t count = 0;
            ConfirmID cid(transactionId);
            std::map<Address, bool> confirms;
            std::string address;
            getState(cid, confirms);
            for (size_t i = 0; i < owners.size(); i++) {
                if (confirms[owners[i]]){
                    TRACE("is confirm:", owners[i].toString());
                    count += 1;
                }
            }
            for(size_t i = 0; i < count; i++){
                if (i != 0) { address += ":";}
                address += ":" + owners[i].toString();
            }
            TRACE("addresses:", address);
            return address.c_str();
        }

        const char * getTransactionIds(uint64_t from, uint64_t to, int pending, int executed) const {
            TRACE("from:", from, "to:", to, "pending:", pending, "executed:", executed);
            uint64_t count = 0;
            uint64_t transactionCount = 0;
            getState(TXCOUNT, transactionCount);
            std::string transactionIds;
            for (size_t i = 0; i < transactionCount; i++) {
                Transaction transaction;
                getState(TxId(i), transaction);
                TRACE("transaction.executed:", transaction.executed ? "true":"false");
                if (pending && !transaction.executed
                    || executed && transaction.executed){
                    Transaction transaction;
                    if (i >= from) {
                        transactionIds += ":" + toString(i);
                    }
                    if (i == to) {
                        break;
                    }
                }
            }
            TRACE("transactionIds:", transactionIds);
            return transactionIds.c_str();
        }

		const char * getMultiSigList(const char *transactionIds)const {
            std::vector<std::string> ids;
            split(transactionIds, ids, ",");
            std::string res;
            TRACE("transactionIds size:", ids.size());
            for (size_t i = 0; i < ids.size(); i++) {
                if (i != 0) { res += "|"; }
                res += ids[i] + ":";
                TRACE("transactionId:", ids[i]);
                ConfirmID cid(stouint64(ids[i]));
                std::map<Address, bool> addrMap;
                getState(cid, addrMap);
                for ( std::map<Address, bool>::iterator iter = addrMap.begin(); iter != addrMap.end(); iter++) {
                    if (iter->second) {
                        if (iter != addrMap.begin()) { res += ","; }
                        res += iter->first.toString();
                    }
                }
                res +=  ":";
                for ( std::map<Address, bool>::iterator iter = addrMap.begin(); iter != addrMap.end(); iter++) {
                    if (!iter->second) {
                        if (iter != addrMap.begin()) { res += ","; }
                        res += iter->first.toString();
                    }
                }
            }
            return res.c_str();
        }

        uint64_t stouint64(const std::string &num) const {
            uint64_t res = 0;
            for (size_t i = 0; i < num.length(); i++) {
                res = res * 10 + (num[i] - '0');
            }
            platon::println("stouint64", num, "->", res);
            return res;
        }

        std::string toString(uint64_t num) const {
            if (num == 0) { return "0";}
            std::string res;
            while (num != 0) {
                char c = num % 10 + '0';
                num /= 10;
                res.insert(0, 1, c);
            }
            return res;
        }

        int split( const std::string & srcStr, std::vector<std::string> & destArray, const std::string & delimiter ) const {
            if( srcStr.empty() ){
                return 0;
            }
            std::string::size_type startPos = srcStr.find_first_not_of( delimiter );
            size_t lengthOfDelimiter = delimiter.length();
            while( std::string::npos != startPos ){
                std::string::size_type nextPos = srcStr.find( delimiter, startPos );
                std::string str;
                if( std::string::npos != nextPos ){
                    str = srcStr.substr( startPos, nextPos - startPos );
                    nextPos += lengthOfDelimiter;
                }
                else{
                    str = srcStr.substr( startPos );
                }
                startPos = nextPos;
                if( !str.empty() ){
                    destArray.push_back( str );
                }
            }
            return destArray.size();
        }


    };
}
PLATON_ABI(platon::MultiSig, initWallet)
PLATON_ABI(platon::MultiSig, confirmTransaction)
PLATON_ABI(platon::MultiSig, executeTransaction)
PLATON_ABI(platon::MultiSig, revokeConfirmation)
PLATON_ABI(platon::MultiSig, submitTransaction)
PLATON_ABI(platon::MultiSig, isConfirmed)
PLATON_ABI(platon::MultiSig, getConfirmationCount)
PLATON_ABI(platon::MultiSig, getTransactionCount)
PLATON_ABI(platon::MultiSig, getTransactionList)
PLATON_ABI(platon::MultiSig, getOwners)
PLATON_ABI(platon::MultiSig, getConfirmations)
PLATON_ABI(platon::MultiSig, getTransactionIds)
PLATON_ABI(platon::MultiSig, getRequired)
PLATON_ABI(platon::MultiSig, getMultiSigList)
PLATON_ABI(platon::MultiSig, getListSize)
//platon autogen begin
extern "C" { 
void initWallet(const char * owner,unsigned long long required) {
platon::MultiSig MultiSig_platon;
MultiSig_platon.initWallet(owner,required);
}
unsigned long long submitTransaction(const char * destination,const char * from,const char * vs,const char * data,unsigned long long len,unsigned long long time,const char * fs) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.submitTransaction(destination,from,vs,data,len,time,fs);
}
void confirmTransaction(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
MultiSig_platon.confirmTransaction(transactionId);
}
void revokeConfirmation(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
MultiSig_platon.revokeConfirmation(transactionId);
}
void executeTransaction(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
MultiSig_platon.executeTransaction(transactionId);
}
int isConfirmed(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.isConfirmed(transactionId);
}
unsigned long long getRequired() {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getRequired();
}
unsigned long long getListSize() {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getListSize();
}
unsigned long long getConfirmationCount(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getConfirmationCount(transactionId);
}
unsigned long long getTransactionCount(int pending,int executed) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getTransactionCount(pending,executed);
}
const char * getTransactionList(unsigned long long from,unsigned long long to) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getTransactionList(from,to);
}
const char * getOwners() {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getOwners();
}
const char * getConfirmations(unsigned long long transactionId) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getConfirmations(transactionId);
}
const char * getTransactionIds(unsigned long long from,unsigned long long to,int pending,int executed) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getTransactionIds(from,to,pending,executed);
}
const char * getMultiSigList(const char * transactionIds) {
platon::MultiSig MultiSig_platon;
return MultiSig_platon.getMultiSigList(transactionIds);
}
void init() {
platon::MultiSig MultiSig_platon;
MultiSig_platon.init();
}

}
//platon autogen end