package com.platon.sdk.contracts.evm;

import java.math.BigInteger;

import org.junit.Before;
import com.alaya.crypto.Credentials;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.Web3jService;
import com.alaya.protocol.http.HttpService;
import com.alaya.tx.RawTransactionManager;
import com.alaya.tx.TransactionManager;
import com.alaya.tx.gas.ContractGasProvider;
import com.alaya.tx.gas.GasProvider;

public abstract class BaseContractTest {
	protected static final BigInteger GAS_LIMIT = BigInteger.valueOf(4700000);
	protected static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

	protected static final long chainId = 103;
	protected static final String nodeUrl = "http://192.168.120.141:6789";
	protected static final String privateKey = "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b";

	protected Credentials credentials;
	protected String address;
	protected Web3j web3j;
	protected Web3jService web3jService;
	protected TransactionManager transactionManager;
	protected GasProvider gasProvider;

	@Before
	public void init() {
		credentials = Credentials.create(privateKey);
		address = credentials.getAddress(chainId);
		web3jService = new HttpService(nodeUrl);
		web3j = Web3j.build(web3jService);
		transactionManager = new RawTransactionManager(web3j, credentials, chainId);
		gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
	}
}
