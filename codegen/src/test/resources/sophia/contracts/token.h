#include <platon/platon.hpp>
#include <stdio.h>
#include <string>
namespace token {
#define MAX_SUPPLY "maxSupply"
#define CREATOR "creator"
#define ASSIGN "assign"
    class Token : public platon::Contract {
    public:
        Token(){}
        void init(){}
        void initSupply(platon::Address creator, uint64_t maxSupply){
            setCreator(creator);
            setMaxSupply(maxSupply);
            setAssign(0);
            platon::println("init supply", creator.toString(), maxSupply);
        }
        PLATON_EVENT2(create, const char *, uint64_t)

        PLATON_EVENT2(transfer, const char *, uint64_t)

        void create(platon::Address addr, uint64_t asset) {
            platon::println("create asset", addr.toString(), asset);

            platon::Address caller = platon::caller();
            platon::Address creator = getCreator();
            platon::println("caller:", caller.toString(), "creator:", creator.toString());
            if (caller != creator) {
                platon::println("create asset failed", caller.toString(), creator.toString());
                return;
            }
            uint64_t assign = getAssign();
            uint64_t max = getMaxSupply();
            if (assign + asset > max) {
                platon::println("assign + asset > maxSupply", assign, asset, max);
                return;
            }
            setAssign(assign + asset);
            addAsset(addr, asset);

            PLATON_EMIT_EVENT2(create, addr.toString().c_str(), asset);
            platon::println("create asset success", addr.toString(), asset);
        }


        uint64_t getAsset(platon::Address addr) const {
            std::string asset;
            platon::getState(addr.toString(), asset);

            if (asset.length() == 0) {
                platon::println("get ssset failed reason: account noexist");
                return 0;
            }
            uint64_t res = stouint64(asset);
            platon::println("get asset address:", addr.toString(), "asset:", asset);
            return res;
        }

        void transfer(platon::Address addr, uint64_t asset) {
            platon::Address caller = platon::caller();
            platon::println("transfer", caller.toString(), "to", addr.toString(), "asset", asset);
            uint64_t callerAsset = getAsset(caller);
            if (callerAsset < asset) {
                platon::println("transfer failed asset too little");
                return;
            }
            setAsset(caller, callerAsset-asset);
            addAsset(addr, asset);
            PLATON_EMIT_EVENT2(transfer, addr.toString().c_str(), asset);
            platon::println("transfer success", caller.toString(), addr.toString());
        }


        void setMaxSupply(uint64_t maxSupply){
            platon::setState(MAX_SUPPLY, toString(maxSupply));
        }

        uint64_t getMaxSupply(){
            std::string max;
            platon::getState(MAX_SUPPLY, max);
            return stouint64(max);
        }
        void setAssign(uint64_t assign) {
            platon::println("setAssign:", assign);
            platon::setState(ASSIGN, toString(assign));
        }
        uint64_t getAssign() {
            std::string assign;
            platon::getState(ASSIGN, assign);
            return stouint64(assign);
        }

        void setCreator(platon::Address &addr){
            platon::setState(CREATOR, addr.toString());
        }

        platon::Address getCreator() {
            std::string creator;
            platon::getState(CREATOR, creator);
            platon::Address creatorAddr(creator, true);
            return creatorAddr;
        }

        void setAsset(platon::Address &addr, uint64_t asset) {
            platon::setState(addr.toString(), toString(asset));
        }

        void addAsset(platon::Address &addr, uint64_t asset) {
            uint64_t old = 0;
            old = getAsset(addr);
            setAsset(addr, old + asset);
        }

        void subAsset(platon::Address &addr, uint64_t asset) {
            uint64_t old = 0;
            old = getAsset(addr);
            setAsset(addr, old - asset);
        }

        uint64_t stouint64(const std::string &num) const {
            uint64_t res = 0;
            for (size_t i = 0; i < num.length(); i++) {
                res = res * 10 + (num[i] - '0');
            }
            platon::println("stouint64", num, "->", res);
            return res;
        }


        std::string toString(uint64_t num){
            std::string res;
            while (num != 0) {
                char c = num % 10 + '0';
                num /= 10;
                res.insert(0, 1, c);
            }
            platon::println("toString", num, "->", res);
            return res;
        }
};
}