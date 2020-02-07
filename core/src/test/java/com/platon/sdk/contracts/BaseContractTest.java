package com.platon.sdk.contracts;

import java.math.BigInteger;

import org.junit.Before;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;

public abstract class BaseContractTest {
	protected static final BigInteger GAS_LIMIT = BigInteger.valueOf(4700000);
	protected static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

	protected static final long chainId = 100L;
	// protected static final String nodeUrl = "http://10.10.8.21:8804";
	protected static final String nodeUrl = "http://10.1.1.1:8801";
	
	// protected static final String privateKey = "11e20dc277fafc4bc008521adda4b79c2a9e403131798c94eacb071005d43532";
	protected static final String privateKey = "3e9516bc43b09dd2754040ad228b9a6c6253c87aa6895318438c7c46002050a6";
	
	protected Credentials credentials;
	protected String address;
	protected Web3j web3j;
	protected Web3jService web3jService;
	protected TransactionManager transactionManager;
	protected GasProvider gasProvider;

	@Before
	public void init() {
		credentials = Credentials.create(privateKey);
		address = credentials.getAddress();
		web3jService = new HttpService(nodeUrl);
		web3j = Web3j.build(web3jService);
		transactionManager = new RawTransactionManager(web3j, credentials, chainId);
		gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
	}
}
