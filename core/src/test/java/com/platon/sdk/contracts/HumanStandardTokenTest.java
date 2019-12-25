package com.platon.sdk.contracts;

import java.math.BigInteger;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;
import com.platon.sdk.contracts.HumanStandardToken.TransferEventResponse;

import rx.Observable;
import rx.Observer;

/**
 * Test the smart contract wrapper class
 * 
 * @author lhdeng
 *
 */
public class HumanStandardTokenTest {
	private Logger logger = LoggerFactory.getLogger(HumanStandardTokenTest.class);

	static final BigInteger GAS_LIMIT = BigInteger.valueOf(990000);
	static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

	private long chainId;
	private String nodeUrl;
	private String privateKey;

	private Credentials credentials;
	private String address;
	private Web3j web3j;
	private Web3jService web3jService;
	private TransactionManager transactionManager;
	private GasProvider gasProvider;

	// The solidity smart contract 'HumanStandardToken' address
	String contractAddress = "0xae362a98cec5bb2a3c8d598dbe825c40b5f1fc14";

	@Before
	public void init() {
		chainId = 100L;
		nodeUrl = "http://10.10.8.21:8804";
		privateKey = "11e20dc277fafc4bc008521adda4b79c2a9e403131798c94eacb071005d43532";

		credentials = Credentials.create(privateKey);
		address = credentials.getAddress();
		web3jService = new HttpService(nodeUrl);
		web3j = Web3j.build(web3jService);
		transactionManager = new RawTransactionManager(web3j, credentials, chainId);
		gasProvider = new ContractGasProvider(GAS_PRICE, GAS_LIMIT);
	}

	@Test
	public void deploy() {
		BigInteger initialAmount = BigInteger.valueOf(10000000000000L);
		String tokenName = "My Token";
		BigInteger decimal = BigInteger.valueOf(2L);
		String tokenSymbol = "MT";

		HumanStandardToken humanStandardToken = null;
		try {
			humanStandardToken = HumanStandardToken.deploy(web3j, transactionManager, gasProvider, initialAmount, tokenName, decimal, tokenSymbol)
					.send();
			contractAddress = humanStandardToken.getContractAddress();
			logger.info("Deploy smart contract [HumanStandardToken] success.contract address >>> " + contractAddress);
		} catch (Exception e) {
			logger.error("Deploy smart contract [HumanStandardToken] error: " + e.getMessage(), e);
		}

		Assert.assertTrue(null != humanStandardToken && null != humanStandardToken.getContractAddress());
	}

	@Test
	public void getBalance() {
		HumanStandardToken humanStandardToken = HumanStandardToken.load(contractAddress, web3j, transactionManager, gasProvider);

		BigInteger balance = null;
		try {
			balance = humanStandardToken.balanceOf(address).send();
			logger.info("The address[{}] balance is:{}", address, balance.toString());
		} catch (Exception e) {
			logger.error("Get balance error,address:{}", address, e);
		}

		Assert.assertTrue(null != balance);
	}

	@Test
	public void transfer() {
		HumanStandardToken humanStandardToken = HumanStandardToken.load(contractAddress, web3j, transactionManager, gasProvider);

		// get balance
		BigInteger balance = null;
		try {
			balance = humanStandardToken.balanceOf(address).send();
			logger.info("The address[{}] balance is:{}", address, balance.toString());
		} catch (Exception e) {
			logger.error("Get balance error,address:{}", address, e);
		}
		Assert.assertTrue(null != balance);

		// transfer
		BigInteger value = BigInteger.valueOf(200000L);
		String toAddress = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";
		try {
			TransactionReceipt receipt = humanStandardToken.transfer(toAddress, value).send();

			logger.info("transfer success,transaction hash:{}", receipt.getTransactionHash());

			List<TransferEventResponse> list = humanStandardToken.getTransferEvents(receipt);
			logger.info("from:{}", list.get(0)._from);
			logger.info("to:{}", list.get(0)._to);
			logger.info("value:{}", list.get(0)._value.toString());
		} catch (Exception e) {
			logger.info("transfer error,from:{},to:{},value:{}", address, toAddress, value, e);
		}

		// get balance
		BigInteger balance_2 = null;
		try {
			balance_2 = humanStandardToken.balanceOf(address).send();
		} catch (Exception e) {
			logger.error("Get balance error,address:{}", address, e);
		}

		Assert.assertTrue(balance_2.add(value).equals(balance));
	}

	@Test
	public void eventListening() {
		HumanStandardToken humanStandardToken = HumanStandardToken.load(contractAddress, web3j, transactionManager, gasProvider);
		Observable<TransferEventResponse> observable = humanStandardToken.transferEventObservable(DefaultBlockParameterName.EARLIEST,
				DefaultBlockParameterName.LATEST);

		Observer<TransferEventResponse> observer = new Observer<HumanStandardToken.TransferEventResponse>() {
			@Override
			public void onNext(TransferEventResponse t) {
				logger.info("from:{}", t._from);
				logger.info("to:{}", t._to);
				logger.info("value:{}", t._value);
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onCompleted() {

			}
		};
		observable.subscribe(observer);
	}
}
