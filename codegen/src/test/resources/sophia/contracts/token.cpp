#include "token.h"


namespace platon {
    class ACC : public token::Token {
    public:
        ACC(){}
        void init(){
            Address user(std::string("0xa0b21d5bcc6af4dda0579174941160b9eecb6916"), true);
            initSupply(user, 199999);
        }

        void create(const char *addr, uint64_t asset){
            Address user(std::string(addr), true);
            Token::create(user, asset);
        }

        uint64_t getAsset(const char *addr) const {
            Address user(std::string(addr), true);
            return Token::getAsset(user);
        }

        void transfer(const char *addr, uint64_t asset) {
            Address user(std::string(addr), true);
            Token::transfer(user, asset);
        }
    };
}

PLATON_ABI(platon::ACC, create)
PLATON_ABI(platon::ACC, getAsset)
PLATON_ABI(platon::ACC, transfer)
//platon autogen begin
extern "C" { 
void create(const char * addr,unsigned long long asset) {
platon::ACC ACC_platon;
ACC_platon.create(addr,asset);
}
unsigned long long getAsset(const char * addr) {
platon::ACC ACC_platon;
return ACC_platon.getAsset(addr);
}
void transfer(const char * addr,unsigned long long asset) {
platon::ACC ACC_platon;
ACC_platon.transfer(addr,asset);
}
void init() {
platon::ACC ACC_platon;
ACC_platon.init();
}

}
//platon autogen end