#define TESTNET
#include <platon/platon.hpp>
#include <string>
using namespace platon;


CONTRACT ContractEmitEvent : public platon::Contract{
   public:
      PLATON_EVENT0(transfer,std::string,std::string,Address)

      ACTION void init(){}

      ACTION void setNameAndEmitAddress(std::string name, Address address){
           stringstorage.self() = name;
           PLATON_EMIT_EVENT0(transfer,name,name,address);
      }

      CONST std::string getName(){
          return stringstorage.self();
      }

      CONST Address getAddress(){
          return platon_caller();
      }

   private:
      platon::StorageType<"sstorage"_n, std::string> stringstorage;
};

PLATON_DISPATCH(ContractEmitEvent, (init)(setNameAndEmitAddress)(getName)(getAddress))
