pragma solidity ^0.5.13;

contract SolEvent {

    uint256 public result; 
    string name;
    
    event FundTransfer(address sender, uint result);
    event SetNameEvent(string name1,string name2,address addr);


    function add(uint amount) public {

        result += amount;

        emit FundTransfer(msg.sender, result);
    }
    
    function setNameAndEmitAddress(string memory _name,address _address) public {
           name = _name;
           emit SetNameEvent(name,name,_address);
      }

      function  getName() public view returns(string memory) {
          return name;
      }

      function getAddress() public view returns(address){
          return msg.sender;
      }
    
}