pragma solidity ^0.5.13;

/**
 * This is just an example for simulating a transfer.
*/
contract PayWages {
    
    enum EmployeeStatus {
         INVALID,
         VALID
    }
    
    enum ErrCode {
        UNKNOWN,
        SUCCESS,
        FAILED
    }
    
    struct Employee {
        uint id;
        uint8 status;
        string name;
        address account;
    }
    
    event Transfer(address indexed _to, uint _value);
    event AddUser(uint8 _code, address _account);
    
    address public personnel;
    
    address public financer;
    
    address public administrator;
    
    uint internal totalBal;
    
    mapping(address => Employee) internal employeeMap;
    address[] internal keys;
    
    Employee internal tmp;
    
    constructor(address _personnel, address _financer) public payable {
        administrator = msg.sender;
        totalBal = msg.value;
        personnel = _personnel;
        financer = _financer;
    }
    
    function clearEmployee() internal {
        delete tmp.id;
        delete tmp.status;
        delete tmp.name;
        delete tmp.account;
    }
    
    function addEmployee(uint _id, uint8 _status, string memory _name, address _account) public returns(bool) {
        require(msg.sender == personnel, "Only personnel can add Employee.");
        require(_account != address(0), "The account can not be empty.");
        require(employeeMap[_account].status != uint8(EmployeeStatus.VALID));
        
        clearEmployee();
        tmp.id = _id;
        tmp.status = _status;
        tmp.name = _name;
        tmp.account = _account;
        employeeMap[_account] = tmp;
        keys.push(_account);
        
        emit AddUser(uint8(ErrCode.SUCCESS), _account);
        
        return true;
    }
    
    function getEmployee(address _account) view public returns(uint, uint8, string memory, address) {
        return (employeeMap[_account].id, employeeMap[_account].status, employeeMap[_account].name, employeeMap[_account].account);
    }
    
    function receipt() public payable{
        totalBal += msg.value;
        emit Transfer(msg.sender, msg.value);
    }
    
    function getTotalBalance() view public returns(uint) {
        return totalBal;
    }
    
    function getContractBalance() view public returns(uint) {
        return address(this).balance;
    }
    
    function pay(address payable _to, uint256 _amount) public {
        require(msg.sender == financer, "Only financer can pay.");
        require(_amount >= 0, "The amount must be greater than 0.");
        require(address(this).balance >= _amount);
        require(address(_to) != address(0), "The payee address cannot be empty.");
        require(employeeMap[_to].status == uint8(EmployeeStatus.VALID), "Employees are nonexistent or ineffective.");
        
        _to.transfer(_amount);
        totalBal -= _amount;
        emit Transfer(_to, _amount);
    }
    
    function() external payable {
        totalBal += msg.value;
        emit Transfer(address(this), msg.value);
    }
}