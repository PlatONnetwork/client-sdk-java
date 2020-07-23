#define TESTNET
#include <platon/platon.hpp>
#include <string>
using namespace platon;
using int8_type_array = std::array<int8_t, 2>;
using int16_type_array = std::array<int16_t, 2>;
using uint8_type_array = std::array<uint8_t, 2>;
using uint16_type_array = std::array<uint16_t, 2>;
using int8_type_vector = std::vector<int8_t>;
using int16_type_vector =  std::vector<int16_t>;
using uint8_type_vector = std::vector<uint8_t>;
using uint16_type_vector = std::vector<uint16_t>;
using int8_type_list = std::list<int8_t>;
using int16_type_list =  std::list<int16_t>;
using uint8_type_list = std::list<uint8_t>;
using uint16_type_list = std::list<uint16_t>;



CONTRACT EventTopic : public platon::Contract{
   public:
      PLATON_EVENT3(stringAndAddrAndBoolean,std::string, Address, bool)
      PLATON_EVENT2(intNumber, int8_t, int16_t)
      PLATON_EVENT2(uintNumber, uint8_t, uint16_t)
      PLATON_EVENT2(intArray, int8_type_array, int16_type_array)
      PLATON_EVENT2(uintArray, uint8_type_array, uint16_type_array)
      PLATON_EVENT2(intVector, int8_type_vector, int16_type_vector)
      PLATON_EVENT2(uintVector, uint8_type_vector,  uint16_type_vector)
      PLATON_EVENT2(intList, int8_type_list, int16_type_list)
      PLATON_EVENT2(uintList, uint8_type_list, uint16_type_list)

      ACTION void init(){}

      ACTION void setStringAndAddressAndBoolean(std::string name, Address address, bool b){
           PLATON_EMIT_EVENT3(stringAndAddrAndBoolean,name,address,b);
      }

      ACTION void setIntNumber(int8_t i8, int16_t i16){
          PLATON_EMIT_EVENT2(intNumber,i8,i16);
      }

      ACTION void setUintNumber(uint8_t ui8, uint16_t ui16){
          PLATON_EMIT_EVENT2(uintNumber,ui8,ui16);
      }

      ACTION void setIntArray(std::array<int8_t, 2> i8Array, std::array<int16_t, 2> i16Array){
          PLATON_EMIT_EVENT2(intArray,i8Array,i16Array);
      }

      ACTION void setUintArray(std::array<uint8_t, 2> ui8Array,  std::array<uint16_t, 2> ui16Array){
          PLATON_EMIT_EVENT2(uintArray,ui8Array,ui16Array);
      }

      ACTION void setIntVector(std::vector<int8_t> i8Array, std::vector<int16_t> i16Array){
          PLATON_EMIT_EVENT2(intVector,i8Array,i16Array);
      }

      ACTION void setUintVector(std::vector<uint8_t> ui8Array,  std::vector<uint16_t> ui16Array){
          PLATON_EMIT_EVENT2(uintVector,ui8Array,ui16Array);
      }

      ACTION void setIntList(std::list<int8_t> i8Array, std::list<int16_t> i16Array){
          PLATON_EMIT_EVENT2(intList,i8Array,i16Array);
      }

      ACTION void setUintList(std::list<uint8_t> ui8Array,  std::list<uint16_t> ui16Array){
          PLATON_EMIT_EVENT2(uintList,ui8Array,ui16Array);
      }

   private:
      platon::StorageType<"sstorage"_n, std::string> stringstorage;
};

PLATON_DISPATCH(EventTopic, (init)(setStringAndAddressAndBoolean)(setIntNumber)(setUintNumber)(setIntArray)(setUintArray)(setIntVector)(setUintVector)(setIntList)(setUintList))
