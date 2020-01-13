package org.web3j.protocol.core;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Hash;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.AdminProgramVersion;
import org.web3j.protocol.core.methods.response.AdminSchnorrNIZKProve;
import org.web3j.protocol.core.methods.response.DbGetHex;
import org.web3j.protocol.core.methods.response.DbGetString;
import org.web3j.protocol.core.methods.response.DbPutHex;
import org.web3j.protocol.core.methods.response.DbPutString;
import org.web3j.protocol.core.methods.response.DebugEconomicConfig;
import org.web3j.protocol.core.methods.response.NetListening;
import org.web3j.protocol.core.methods.response.NetPeerCount;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.PlatonAccounts;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonBlockNumber;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.core.methods.response.PlatonEstimateGas;
import org.web3j.protocol.core.methods.response.PlatonEvidences;
import org.web3j.protocol.core.methods.response.PlatonFilter;
import org.web3j.protocol.core.methods.response.PlatonGasPrice;
import org.web3j.protocol.core.methods.response.PlatonGetBalance;
import org.web3j.protocol.core.methods.response.PlatonGetBlockTransactionCountByHash;
import org.web3j.protocol.core.methods.response.PlatonGetBlockTransactionCountByNumber;
import org.web3j.protocol.core.methods.response.PlatonGetCode;
import org.web3j.protocol.core.methods.response.PlatonGetStorageAt;
import org.web3j.protocol.core.methods.response.PlatonGetTransactionCount;
import org.web3j.protocol.core.methods.response.PlatonGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.PlatonLog;
import org.web3j.protocol.core.methods.response.PlatonPendingTransactions;
import org.web3j.protocol.core.methods.response.PlatonProtocolVersion;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.core.methods.response.PlatonSign;
import org.web3j.protocol.core.methods.response.PlatonSyncing;
import org.web3j.protocol.core.methods.response.PlatonTransaction;
import org.web3j.protocol.core.methods.response.PlatonUninstallFilter;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.Web3Sha3;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSON;

/**
 * Verify the functionality of the base API
 * 
 * @author lhdeng
 *
 */
public class JsonRpc2_0ApiTest {
	private Logger logger = LoggerFactory.getLogger(JsonRpc2_0ApiTest.class);

	static final BigInteger GAS_LIMIT = BigInteger.valueOf(990000);
	static final BigInteger GAS_PRICE = BigInteger.valueOf(1000000000L);

	private long chainId;
	private String nodeUrl;
	private String privateKey;

	private Credentials credentials;
	private String address;
	private Web3j web3j;
	private Web3jService web3jService;

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
	}

	/**
	 * Returns the current price per gas in Von.
	 * 
	 * Result : {"gasPrice":1000000000,"id":0,"jsonrpc":"2.0","result":"0x3b9aca00"}
	 */
	@Test
	public void getGasPrice() {
		try {
			PlatonGasPrice platonGasPrice = web3j.platonGasPrice().send();
			logger.info("platonGasPrice >>> " + JSON.toJSONString(platonGasPrice));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current client version.
	 * 
	 * Result :
	 * {"id":0,"jsonrpc":"2.0","result":"PlatONnetwork/platon/v0.7.5-unstable-331655ad/linux-amd64/go1.12.9","web3ClientVersion":"PlatONnetwork/platon/v0.7.5-unstable-331655ad/linux-amd64/go1.12.9"}
	 */
	@Test
	public void web3ClientVersion() {
		try {
			Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
			logger.info("web3ClientVersion >>> " + JSON.toJSONString(web3ClientVersion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns Keccak-256 (not the standardized SHA3-256) of the given data.
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","result":"0x0eccfe5d014e8c46d0a4afdd7ba4a75f269473fb60de682768439deadd8e1165"}
	 */
	@Test
	public void web3Sha3() {
		// the data to convert into a SHA3 hash.
		String data = Numeric.toHexString("web3Sha3".getBytes());
		logger.info("data >>> " + data);

		try {
			Web3Sha3 web3Sha3 = web3j.web3Sha3(data).send();
			logger.info("web3Sha3 >>> " + JSON.toJSONString(web3Sha3));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current network id. The network id in: "1": PlatON Mainnet "2": Morden Testnet (deprecated) "3": Ropsten Testnet "4": Rinkeby
	 * Testnet "42": Kovan Testnet
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","netVersion":"1","result":"1"}
	 */
	@Test
	public void netVersion() {
		try {
			NetVersion netVersion = web3j.netVersion().send();
			logger.info("netVersion >>> " + JSON.toJSONString(netVersion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns true if client is actively listening for network connections.
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","listening":true,"result":true}
	 */
	@Test
	public void netListening() {
		try {
			NetListening netListening = web3j.netListening().send();
			logger.info("netListening >>> " + JSON.toJSONString(netListening));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns number of peers currently connected to the client.
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","quantity":0,"result":"0x0"}
	 */
	@Test
	public void netPeerCount() {
		try {
			NetPeerCount netPeerCount = web3j.netPeerCount().send();
			logger.info("netPeerCount >>> " + JSON.toJSONString(netPeerCount));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the current platon protocol version.
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","protocolVersion":"0x3f","result":"0x3f"}
	 */
	@Test
	public void platonProtocolVersion() {
		PlatonProtocolVersion platonProtocolVersion;
		try {
			platonProtocolVersion = web3j.platonProtocolVersion().send();
			logger.info("platonProtocolVersion >>> " + JSON.toJSONString(platonProtocolVersion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns an object with data about the sync status or false. Object|Boolean, An object with sync status data or FALSE, when not syncing:
	 * startingBlock: QUANTITY - The block at which the import started (will only be reset, after the sync reached his head) currentBlock: QUANTITY -
	 * The current block, same as platon_blockNumber highestBlock: QUANTITY - The estimated highest block
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","result":{"syncing":false},"syncing":false}
	 */
	@Test
	public void platonSyncing() {
		try {
			PlatonSyncing platonSyncing = web3j.platonSyncing().send();
			logger.info("platonSyncing >>> " + JSON.toJSONString(platonSyncing));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a list of addresses owned by client.
	 * 
	 * Result :
	 * {"accounts":["0xe17552158f1b6f6e3244d7e9cdd58d530b6423e5","0xb0d6f0e1bd91ff93c85ee858105136a3e6de1ad4"],"id":0,"jsonrpc":"2.0","result":["0xe17552158f1b6f6e3244d7e9cdd58d530b6423e5","0xb0d6f0e1bd91ff93c85ee858105136a3e6de1ad4"]}
	 */
	@Test
	public void platonAccounts() {
		try {
			PlatonAccounts platonAccounts = web3j.platonAccounts().send();
			logger.info("platonAccounts >>> " + JSON.toJSONString(platonAccounts));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of most recent block.
	 * 
	 * Result : {"blockNumber":955492,"id":0,"jsonrpc":"2.0","result":"0xe9464"}
	 */
	@Test
	public void platonBlockNumber() {
		try {
			PlatonBlockNumber platonBlockNumber = web3j.platonBlockNumber().send();
			logger.info("platonBlockNumber >>> " + JSON.toJSONString(platonBlockNumber));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the balance of the account of given address.
	 * 
	 * Result :
	 * {"balance":3533694129556768659166595001485837031654967793481217911164800423565239552,"id":0,"jsonrpc":"2.0","result":"0x1ffffffffffffffffffffffffffffffffffffff20a50a31a82520fceca500"}
	 */
	@Test
	public void platonGetBalance() {
		try {
			PlatonGetBalance platonGetBalance = web3j.platonGetBalance(address, DefaultBlockParameterName.LATEST).send();
			logger.info("platonGetBalance >>> " + JSON.toJSONString(platonGetBalance));

			// integer of the current balance in von.
			BigInteger balanceVon = platonGetBalance.getBalance();
			BigDecimal balanceLat = Convert.fromVon(new BigDecimal(balanceVon), Convert.Unit.LAT);
			logger.info("LAT >>> " + balanceLat.toPlainString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the value from a storage position at a given address.
	 * 
	 * Result : {"data":"0x","id":0,"jsonrpc":"2.0","result":"0x"}
	 */
	@Test
	public void platonGetStorageAt() {
		BigInteger position = BigInteger.valueOf(0L);
		try {
			PlatonGetStorageAt platonGetStorageAt = web3j.platonGetStorageAt(address, position, DefaultBlockParameterName.LATEST).send();
			logger.info("platonGetStorageAt >>> " + JSON.toJSONString(platonGetStorageAt));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of transactions sent from an address.
	 * 
	 * Result : {"id":0,"jsonrpc":"2.0","result":"0x23d","transactionCount":573}
	 */
	@Test
	public void platonGetTransactionCount() {
		try {
			PlatonGetTransactionCount platonGetTransactionCount = web3j.platonGetTransactionCount(address, DefaultBlockParameterName.LATEST).send();
			logger.info("platonGetTransactionCount >>> " + JSON.toJSONString(platonGetTransactionCount));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns information about a block by block number.
	 */
	@Test
	public void platonGetBlockByNumber() {
		/**
		 * QUANTITY|TAG - integer of a block number, or the string "earliest", "latest" or "pending", as in the default block parameter. Boolean - If
		 * true it returns the full transaction objects, if false only the hashes of the transactions.
		 */
		try {
			PlatonBlock platonBlock = web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
			logger.info("platonBlock hash >>>> " + platonBlock.getBlock().getHash());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of transactions in a block from a block matching the given block hash.
	 */
	@Test
	public void platonGetBlockTransactionCountByHash() {
		try {
			String blockHash = "0x9b6b76349f2c9fd9fa9c9dd84ba593b48d9993403baa6de5a4454ec61825add1";
			PlatonGetBlockTransactionCountByHash platonGetBlockTransactionCountByNumber = web3j.platonGetBlockTransactionCountByHash(blockHash)
					.send();
			logger.info("PlatonGetBlockTransactionCountByNumber >>> " + JSON.toJSONString(platonGetBlockTransactionCountByNumber));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of transactions in a block matching the given block number.
	 */
	@Test
	public void platonGetBlockTransactionCountByNumber() {
		try {
			PlatonGetBlockTransactionCountByNumber platonGetBlockTransactionCountByNumber = web3j
					.platonGetBlockTransactionCountByNumber(DefaultBlockParameterName.EARLIEST).send();
			logger.info("PlatonGetBlockTransactionCountByNumber >>> " + JSON.toJSONString(platonGetBlockTransactionCountByNumber));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns code at a given address.
	 */
	@Test
	public void platonGetCode() {
		try {
			PlatonGetCode platonGetCode = web3j.platonGetCode(contractAddress, DefaultBlockParameterName.LATEST).send();
			logger.info("platonGetCode >>> " + JSON.toJSONString(platonGetCode));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The sign method calculates an Ethereum specific signature with: sign(keccak256("\x19Ethereum Signed Message:\n" + len(message) + message))).
	 * 
	 * Note the address to sign with must be unlocked.
	 */
	@Test
	public void platonSign() {
		String data = "hello platon";
		byte[] hash = Hash.sha3(data.getBytes());
		String sha3HashOfDataToSign = Numeric.toHexString(hash);

		try {
			PlatonSign platonSign = web3j.platonSign(address, sha3HashOfDataToSign).send();
			logger.info("platonSign >>> " + JSON.toJSONString(platonSign));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates new message call transaction or a contract creation, if the data field contains code.
	 */
	@Test
	public void platonSendTransaction() {
		// The address the transaction is send from.
		String from = address;

		// (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
		BigInteger nonce = null;
		PlatonGetTransactionCount platonGetTransactionCount;
		try {
			platonGetTransactionCount = web3j.platonGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
			nonce = platonGetTransactionCount.getTransactionCount();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// (optional, default: To-Be-Determined) Integer of the gasPrice used for each paid gas
		BigInteger gasPrice = GAS_PRICE;

		// (optional, default: 90000) Integer of the gas provided for the transaction execution. It will return unused gas.
		BigInteger gasLimit = GAS_LIMIT;

		// (optional when creating new contract) The address the transaction is directed to.
		String to = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";

		// (optional) Integer of the value sent with this transaction
		BigInteger value = BigInteger.valueOf(20000L);

		// The compiled code of a contract OR the hash of the invoked method signature and encoded parameters.
		String data = "";
		Transaction transaction = new Transaction(from, nonce, gasPrice, gasLimit, to, value, data);
		try {
			PlatonSendTransaction platonSendTransaction = web3j.platonSendTransaction(transaction).send();
			logger.info("platonSendTransaction >>> " + JSON.toJSONString(platonSendTransaction));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates new message call transaction or a contract creation for signed transactions.
	 */
	@Test
	public void platonSendRawTransaction() {
		// The address the transaction is send from.
		String from = address;

		// (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
		BigInteger nonce = null;
		PlatonGetTransactionCount platonGetTransactionCount;
		try {
			platonGetTransactionCount = web3j.platonGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
			nonce = platonGetTransactionCount.getTransactionCount();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// (optional, default: To-Be-Determined) Integer of the gasPrice used for each paid gas
		BigInteger gasPrice = GAS_PRICE;

		// (optional, default: 90000) Integer of the gas provided for the transaction execution. It will return unused gas.
		BigInteger gasLimit = GAS_LIMIT;

		// (optional when creating new contract) The address the transaction is directed to.
		String to = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";

		// (optional) Integer of the value sent with this transaction
		BigInteger value = BigInteger.valueOf(20000L);

		// The compiled code of a contract OR the hash of the invoked method signature and encoded parameters.
		String data = "";

		RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, to, value, data);
		byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
		String hexValue = Numeric.toHexString(signedMessage);
		PlatonSendTransaction platonSendTransaction;
		try {
			platonSendTransaction = web3j.platonSendRawTransaction(hexValue).send();
			logger.info("platonSendTransaction >>> " + JSON.toJSONString(platonSendTransaction));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Executes a new message call immediately without creating a transaction on the block chain.
	 */
	@Test
	public void platCall() {
		// The address the transaction is send from.
		String from = address;

		// (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
		BigInteger nonce = null;
		PlatonGetTransactionCount platonGetTransactionCount;
		try {
			platonGetTransactionCount = web3j.platonGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
			nonce = platonGetTransactionCount.getTransactionCount();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// (optional, default: To-Be-Determined) Integer of the gasPrice used for each paid gas
		BigInteger gasPrice = GAS_PRICE;

		// (optional, default: 90000) Integer of the gas provided for the transaction execution. It will return unused gas.
		BigInteger gasLimit = GAS_LIMIT;

		// (optional when creating new contract) The address the transaction is directed to.
		String to = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";

		// (optional) Integer of the value sent with this transaction
		BigInteger value = BigInteger.valueOf(20000L);

		// The compiled code of a contract OR the hash of the invoked method signature and encoded parameters.
		String data = "";
		Transaction transaction = new Transaction(from, nonce, gasPrice, gasLimit, to, value, data);

		try {
			PlatonCall platonCall = web3j.platonCall(transaction, DefaultBlockParameterName.LATEST).send();
			logger.info("platonCall >>> " + JSON.toJSONString(platonCall));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates and returns an estimate of how much gas is necessary to allow the transaction to complete. The transaction will not be added to the
	 * blockchain. Note that the estimate may be significantly more than the amount of gas actually used by the transaction, for a variety of reasons
	 * including EVM mechanics and node performance.
	 */
	@Test
	public void platonEstimateGas() {
		// The address the transaction is send from.
		String from = address;

		// (optional) Integer of a nonce. This allows to overwrite your own pending transactions that use the same nonce.
		BigInteger nonce = null;
		PlatonGetTransactionCount platonGetTransactionCount;
		try {
			platonGetTransactionCount = web3j.platonGetTransactionCount(from, DefaultBlockParameterName.LATEST).send();
			nonce = platonGetTransactionCount.getTransactionCount();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// (optional, default: To-Be-Determined) Integer of the gasPrice used for each paid gas
		BigInteger gasPrice = GAS_PRICE;

		// (optional, default: 90000) Integer of the gas provided for the transaction execution. It will return unused gas.
		BigInteger gasLimit = GAS_LIMIT;

		// (optional when creating new contract) The address the transaction is directed to.
		String to = "0x31ac3dad7fa96b62d58b2be229575db40aa28b2c";

		// (optional) Integer of the value sent with this transaction
		BigInteger value = BigInteger.valueOf(20000L);

		// The compiled code of a contract OR the hash of the invoked method signature and encoded parameters.
		String data = "";
		Transaction transaction = new Transaction(from, nonce, gasPrice, gasLimit, to, value, data);

		try {
			PlatonEstimateGas platonEstimateGas = web3j.platonEstimateGas(transaction).send();
			logger.info("platonEstimateGas >>> " + JSON.toJSONString(platonEstimateGas));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns information about a block by hash.
	 */
	@Test
	public void platonGetBlockByHash() {
		String blockHash = "0x0a0d04b01aa6e04b96eb2343eb4e0d5a3d9dda0c11a41799d1c7b8f4a9a07a02";
		try {
			PlatonBlock platonBlock = web3j.platonGetBlockByHash(blockHash, true).send();
			logger.info("platonBlock hash >>> " + platonBlock.getBlock().getHash());
			logger.info("platonBlock author >>> " + platonBlock.getBlock().getAuthor());
			logger.info("platonBlock number >>> " + platonBlock.getBlock().getNumber());
			logger.info("platonBlock nonce >>> " + platonBlock.getBlock().getNonce());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns information about a transaction by block hash and transaction index position.
	 */
	@Test
	public void platonGetTransactionByBlockHashAndIndex() {
		String blockHash = "0x0a0d04b01aa6e04b96eb2343eb4e0d5a3d9dda0c11a41799d1c7b8f4a9a07a02";
		BigInteger transactionIndex = BigInteger.valueOf(1L);
		try {
			PlatonTransaction platonTransaction = web3j.platonGetTransactionByBlockHashAndIndex(blockHash, transactionIndex).send();
			logger.info("platonTransaction >>> " + JSON.toJSONString(platonTransaction));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the information about a transaction requested by transaction hash.
	 */
	@Test
	public void platonGetTransactionByHash() {
		String transactionHash = "0x8c35272a7c95ab38b9cd44180350ea18925220dc7b34d8e01a0167f5b299afec";
		try {
			PlatonTransaction platonTransaction = web3j.platonGetTransactionByHash(transactionHash).send();
			logger.info("platonTransaction >>> " + JSON.toJSONString(platonTransaction));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns information about a transaction by block number and transaction index position.
	 */
	@Test
	public void platonGetTransactionByBlockNumberAndIndex() {
		long blockNumber = 958697L;
		BigInteger transactionIndex = BigInteger.valueOf(0L);
		try {
			PlatonTransaction platonTransaction = web3j
					.platonGetTransactionByBlockNumberAndIndex(new DefaultBlockParameterNumber(blockNumber), transactionIndex).send();
			logger.info("platonTransaction >>> " + JSON.toJSONString(platonTransaction));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the receipt of a transaction by transaction hash.
	 * 
	 * Note: That the receipt is not available for pending transactions.
	 */
	@Test
	public void platonGetTransactionReceipt() {
		String transactionHash = "0x8c35272a7c95ab38b9cd44180350ea18925220dc7b34d8e01a0167f5b299afec";
		try {
			PlatonGetTransactionReceipt platonGetTransactionReceipt = web3j.platonGetTransactionReceipt(transactionHash).send();
			logger.info("platonGetTransactionReceipt >>> " + JSON.toJSONString(platonGetTransactionReceipt));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a filter object, based on filter options, to notify when the state changes (logs). To check if the state has changed, call
	 * platon_getFilterChanges.
	 */
	@Test
	public void platonNewFilter() {
		org.web3j.protocol.core.methods.request.PlatonFilter platonFilter = new org.web3j.protocol.core.methods.request.PlatonFilter(
				DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
		try {
			PlatonFilter platonFilter_res = web3j.platonNewFilter(platonFilter).send();
			logger.info("platonFilter_res >>> " + JSON.toJSONString(platonFilter_res));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a filter in the node, to notify when a new block arrives. To check if the state has changed, call platon_getFilterChanges.
	 */
	@Test
	public void platonNewBlockFilter() {
		try {
			PlatonFilter platonFilter = web3j.platonNewBlockFilter().send();
			logger.info("platonFilter >>> " + JSON.toJSONString(platonFilter));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates a filter in the node, to notify when new pending transactions arrive. To check if the state has changed, call platon_getFilterChanges.
	 */
	@Test
	public void platonNewPendingTransactionFilter() {
		try {
			PlatonFilter platonFilter = web3j.platonNewPendingTransactionFilter().send();
			logger.info("platonFilter >>> " + JSON.toJSONString(platonFilter));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uninstalls a filter with given id. Should always be called when watch is no longer needed. Additonally Filters timeout when they aren't
	 * requested with platon_getFilterChanges for a period of time.
	 */
	@Test
	public void platonUninstallFilter() {
		BigInteger filterId = new BigInteger("258575391212208728421850232718129168292", 10);
		try {
			PlatonUninstallFilter platonUninstallFilter = web3j.platonUninstallFilter(filterId).send();
			logger.info("platonUninstallFilter >>> " + JSON.toJSONString(platonUninstallFilter));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Polling method for a filter, which returns an array of logs which occurred since last poll.
	 */
	@Test
	public void platonGetFilterChanges() {
		BigInteger filterId = new BigInteger("336685864917132318099501088544437319929", 10);
		try {
			PlatonLog platonLog = web3j.platonGetFilterChanges(filterId).send();
			logger.info("platonLog >>> " + JSON.toJSONString(platonLog));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns an array of all logs matching filter with given id.
	 */
	@Test
	public void platonGetFilterLogs() {
		BigInteger filterId = new BigInteger("336685864917132318099501088544437319929", 10);
		try {
			PlatonLog platonLog = web3j.platonGetFilterLogs(filterId).send();
			logger.info("platonLog >>> " + JSON.toJSONString(platonLog));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns an array of all logs matching a given filter object.
	 */
	@Test
	public void platonGetLogs() {
		org.web3j.protocol.core.methods.request.PlatonFilter platonFilter = new org.web3j.protocol.core.methods.request.PlatonFilter(
				DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST, contractAddress);
		try {
			PlatonLog platonLog = web3j.platonGetLogs(platonFilter).send();
			logger.info("platonLog >>> " + JSON.toJSONString(platonLog));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the pending transactions list.
	 */
	@Test
	public void platonPendingTransactions() {
		try {
			PlatonPendingTransactions platonPendingTransactions = web3j.platonPendingTx().send();
			logger.info("platonPendingTransactions >>> " + JSON.toJSONString(platonPendingTransactions));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores a string in the local database.
	 */
	@Test
	public void dbPutString() {
		String databaseName = "platon";
		String keyName = "key";
		String stringToStore = "hello platon";
		try {
			DbPutString dbPutString = web3j.dbPutString(databaseName, keyName, stringToStore).send();
			logger.info("dbPutString >>> " + JSON.toJSONString(dbPutString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns string from the local database.
	 */
	@Test
	public void dbGetString() {
		String databaseName = "platon";
		String keyName = "key";
		try {
			DbGetString dbGetString = web3j.dbGetString(databaseName, keyName).send();
			logger.info("dbGetString >>> " + JSON.toJSONString(dbGetString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores binary data in the local database.
	 */
	@Test
	public void dbPutHex() {
		String databaseName = "platon";
		String keyName = "key2";
		String dataToStore = "0x68656c6c6f20776f726c64";
		try {
			DbPutHex dbPutHex = web3j.dbPutHex(databaseName, keyName, dataToStore).send();
			logger.info("dbPutHex >>> " + JSON.toJSONString(dbPutHex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns binary data from the local database.
	 */
	@Test
	public void dbGetHex() {
		String databaseName = "platon";
		String keyName = "key2";
		try {
			DbGetHex dbGetHex = web3j.dbGetHex(databaseName, keyName).send();
			logger.info("dbGetHex >>> " + JSON.toJSONString(dbGetHex));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回双签举报数据
	 */
	@Test
	public void platonEvidences() {
		try {
			PlatonEvidences platonEvidences = web3j.platonEvidences().send();
			logger.info("platonEvidences >>> " + JSON.toJSONString(platonEvidences));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取代码版本
	 */
	@Test
	public void getProgramVersion() {
		try {
			AdminProgramVersion adminProgramVersion = web3j.getProgramVersion().send();
			logger.info("adminProgramVersion >>> " + JSON.toJSONString(adminProgramVersion));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取bls的证明
	 */
	@Test
	public void getSchnorrNIZKProve() {
		try {
			AdminSchnorrNIZKProve adminSchnorrNIZKProve = web3j.getSchnorrNIZKProve().send();
			logger.info("adminSchnorrNIZKProve >>> " + JSON.toJSONString(adminSchnorrNIZKProve));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取PlatON参数配置
	 */
	@Test
	public void getEconomicConfig() {
		try {
			DebugEconomicConfig debugEconomicConfig = web3j.getEconomicConfig().send();
			logger.info("debugEconomicConfig >>> " + JSON.toJSONString(debugEconomicConfig));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
