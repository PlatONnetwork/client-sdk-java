package com.alaya.contracts.evm;

import java.math.BigInteger;

import com.alaya.parameters.NetworkParameters;
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

	protected static final long chainId = NetworkParameters.MainNetParams.getChainId();
	protected static final String nodeUrl = "http://192.168.120.150:6789";
	protected static final String privateKey = "0x3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668";

	protected Credentials credentials;
	protected Web3j web3j;
	protected Web3jService web3jService;
	protected TransactionManager transactionManager;
	protected GasProvider gasProvider;
	protected String credentialsAddress;

	@Before
	public void init() {
		credentials = Credentials.create(privateKey);
		credentialsAddress = credentials.getAddress(chainId);
		web3jService = new HttpService(nodeUrl);
		web3j = Web3j.build(web3jService);
		transactionManager = new RawTransactionManager(web3j, credentials, chainId);
		gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
	}
}
