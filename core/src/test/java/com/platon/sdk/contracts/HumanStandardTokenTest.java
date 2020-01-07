package com.platon.sdk.contracts;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import com.platon.sdk.contracts.HumanStandardToken.TransferEventResponse;

import rx.Observable;
import rx.Observer;

/**
 * Test the smart contract wrapper class
 * 
 * @author lhdeng
 *
 */
public class HumanStandardTokenTest extends BaseContractTest {
	private Logger logger = LoggerFactory.getLogger(HumanStandardTokenTest.class);

	// The solidity smart contract 'HumanStandardToken' address
	String contractAddress = "0xd20106f57b2a7c75290ade3631045ec50a26c0aa";
	String toAddress = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";

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
	}

	@Test
	public void transfer() {
		HumanStandardToken humanStandardToken = HumanStandardToken.load(contractAddress, web3j, transactionManager, gasProvider);

		// get balance
		BigInteger balance = null;
		try {
			balance = humanStandardToken.balanceOf(address).send();
			logger.info("Before The address[{}] balance is:{}", address, balance.toString());
		} catch (Exception e) {
			logger.error("Get balance error,address:{}", address, e);
		}

		// transfer
		BigInteger value = BigInteger.valueOf(200000L);
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
			logger.info("After The address[{}] balance is:{}", address, balance_2.toString());
		} catch (Exception e) {
			logger.error("Get balance error,address:{}", address, e);
		}
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
