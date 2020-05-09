package com.platon.sdk.contracts.wasm;

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

import com.platon.sdk.utlis.NetworkParameters;

public abstract class BaseContractTest {
	protected static final BigInteger GAS_LIMIT = BigInteger.valueOf(4700000);
	protected static final BigInteger GAS_PRICE = BigInteger.valueOf(10000000000L);

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
		address = credentials.getAddress(NetworkParameters.TestNetParams);
		web3jService = new HttpService(nodeUrl);
		web3j = Web3j.build(web3jService);
		transactionManager = new RawTransactionManager(web3j, credentials, chainId);
		gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
	}
}
