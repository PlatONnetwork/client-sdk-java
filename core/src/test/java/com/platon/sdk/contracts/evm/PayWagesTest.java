package com.platon.sdk.contracts.evm;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;

import org.junit.Test;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.alibaba.fastjson.JSON;
import com.platon.sdk.contracts.evm.PayWages.AddUserEventResponse;
import com.platon.sdk.contracts.evm.PayWages.TransferEventResponse;

/**
 * The smart contract is used to test transfers.
 * 
 * @see PayWages PayWages.sol
 * 
 * @author lhdeng
 *
 */
public class PayWagesTest extends BaseContractTest {
	String walletPwd = "123456";
	String financerWallet = PayWagesTest.class.getClassLoader().getResource("wallet/financer.json").getPath();
	String personnelWallet = PayWagesTest.class.getClassLoader().getResource("wallet/personnel.json").getPath();
	String employeeWallet = PayWagesTest.class.getClassLoader().getResource("wallet/employee.json").getPath();

	String contractAddress = "0x6a10e9fd79bba9dc4d92c97dd807f6562d382cb3";
	String financerAddress = "0xa9e7ddbc25fb75bed0d7967c9722a573b4bbdb5c";
	String personnelAddress = "0x85fad5acd0421555453aeb0948c1229ab1fde4fb";
	String employeeAddress = "0xa495ca5aad3e8d3e14255072e713245154fdbf00";

	@Test
	public void deploy() throws Exception {
		PayWages payWages = PayWages.deploy(web3j, transactionManager, gasProvider, BigInteger.valueOf(0L), personnelAddress, financerAddress).send();
		contractAddress = payWages.getContractAddress();

		System.err.println("PayWages contract address >>>> " + contractAddress);
	}

	@Test
	public void transfer() throws Exception {
		// String toAddress = personnelAddress;
		String toAddress = financerAddress;

		// get balance
		PlatonGetBalance balance = web3j.platonGetBalance(toAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("Before transfer,balance >>> " + balance.getBalance().toString());

		// transfer to toAddress
		Transfer transfer = new Transfer(web3j, transactionManager);
		BigDecimal value = BigDecimal.valueOf(200L);
		TransactionReceipt receipt = transfer.sendFunds(toAddress, value, Convert.Unit.LAT, GAS_PRICE, GAS_LIMIT).send();
		System.err.println("transfer status >>> " + receipt.getStatus());
		System.err.println("transfer receipt >>> " + JSON.toJSONString(receipt));

		// get balance
		balance = web3j.platonGetBalance(toAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("After transfer, balance >>> " + balance.getBalance().toString());
	}

	@Test
	public void transferToContract() throws Exception {
		String toAddress = contractAddress;

		// get balance
		PlatonGetBalance balance = web3j.platonGetBalance(toAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("Before transfer,balance >>> " + balance.getBalance().toString());

		// transfer to toAddress
		Transfer transfer = new Transfer(web3j, transactionManager);
		BigDecimal value = BigDecimal.valueOf(500L);
		TransactionReceipt receipt = transfer.sendFunds(toAddress, value, Convert.Unit.LAT, GAS_PRICE, GAS_LIMIT).send();
		System.err.println("transfer status >>> " + receipt.getStatus());
		System.err.println("transfer result >>> " + JSON.toJSONString(receipt));

		// transfer event
		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);
		List<TransferEventResponse> list = payWages.getTransferEvents(receipt);
		System.err.println("to >>> " + list.get(0)._to);
		System.err.println("value >>> " + list.get(0)._value.toString());
		System.err.println("TransferEventResponse >>> " + JSON.toJSONString(list));

		// get balance
		balance = web3j.platonGetBalance(toAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("After transfer, balance >>> " + balance.getBalance().toString());
	}

	@Test
	public void addEmployee() throws Exception {
		// Only personnel can add employee
		Credentials personnel = WalletUtils.loadCredentials(walletPwd, personnelWallet);
		transactionManager = new RawTransactionManager(web3j, personnel, chainId);
		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);
		System.err.println("msg.sender >>>> " + transactionManager.getFromAddress());
		System.err.println("contract personnel address >>> " + payWages.personnel().send());

		// invoke function
		BigInteger id = BigInteger.valueOf(123456L);
		// status:0-invalid,1-valid
		BigInteger status = BigInteger.valueOf(1L);
		String name = "Alice";
		TransactionReceipt transactionReceipt = payWages.addEmployee(id, status, name, employeeAddress).send();
		System.err.println("transactionReceipt status >>>> " + transactionReceipt.getStatus());
		System.err.println("gasUsed >>>> " + transactionReceipt.getGasUsed().toString());
		System.err.println("transactionReceipt >>>> " + JSON.toJSONString(transactionReceipt));

		// get Event
		List<AddUserEventResponse> list = payWages.getAddUserEvents(transactionReceipt);
		System.err.println("code >>> " + list.get(0)._code);
		System.err.println("address >>> " + list.get(0)._account);
		System.err.println("AddUserEventResponse >>> " + JSON.toJSONString(list));
	}

	@Test
	public void getEmployee() throws Exception {
		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);
		Tuple4<BigInteger, BigInteger, String, String> result = payWages.getEmployee(employeeAddress).send();
		System.err.println("employee id >>>> " + result.getValue1());
		System.err.println("employee status >>>> " + result.getValue2());
		System.err.println("employee name >>>> " + result.getValue3());
		System.err.println("employee account >>>> " + result.getValue4());
	}

	@Test
	public void receipt() throws Exception {
		// get balance
		PlatonGetBalance balance = web3j.platonGetBalance(contractAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("Before transfer,balance >>> " + balance.getBalance().toString());

		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);
		BigInteger value = Convert.toVon(BigDecimal.valueOf(10), Convert.Unit.LAT).toBigInteger();
		TransactionReceipt receipt = payWages.receipt(value).send();
		System.err.println("receipt status >>>> " + receipt.getStatus());
		System.err.println("receipt >>>> " + JSON.toJSONString(receipt));

		// get balance
		balance = web3j.platonGetBalance(contractAddress, DefaultBlockParameterName.LATEST).send();
		System.err.println("After transfer,balance >>> " + balance.getBalance().toString());
	}

	@Test
	public void getBalance() throws Exception {
		Credentials financer = WalletUtils.loadCredentials(walletPwd, financerWallet);
		transactionManager = new RawTransactionManager(web3j, financer, chainId);
		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);
		BigInteger balance = payWages.getTotalBalance().send();
		System.err.println("TotalBalance >>>> " + balance);

		balance = payWages.getContractBalance().send();
		System.err.println("ContractBalance >>>> " + balance);
	}

	@Test
	public void pay() throws Exception {
		Credentials financer = WalletUtils.loadCredentials(walletPwd, financerWallet);
		transactionManager = new RawTransactionManager(web3j, financer, chainId);
		PayWages payWages = PayWages.load(contractAddress, web3j, transactionManager, gasProvider);

		BigInteger balance = payWages.getContractBalance().send();
		System.err.println("Before pay, ContractBalance >>>> " + balance);

		BigInteger amout = Convert.toVon(BigDecimal.valueOf(18), Convert.Unit.LAT).toBigInteger();
		TransactionReceipt receipt = payWages.pay(employeeAddress, amout).send();
		System.err.println("pay status >>>> " + receipt.getStatus());
		System.err.println("receipt >>>> " + JSON.toJSONString(receipt));

		balance = payWages.getContractBalance().send();
		System.err.println("After pay, ContractBalance >>>> " + balance);
	}

	@Test
	public void createWallet()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
		WalletUtils.generateLightNewWalletFile(walletPwd, new File(System.getProperty("user.dir") + "/src/test/resources/wallet/"));
	}
}
