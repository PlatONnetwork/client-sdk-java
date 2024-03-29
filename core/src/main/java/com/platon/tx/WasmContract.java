package com.platon.tx;

import com.platon.abi.wasm.*;
import com.platon.abi.wasm.datatypes.WasmEvent;
import com.platon.abi.wasm.datatypes.WasmEventParameter;
import com.platon.abi.wasm.datatypes.WasmFunction;
import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.Response;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.PlatonCall;
import com.platon.protocol.core.methods.response.PlatonGetCode;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.protocol.exceptions.TransactionException;
import com.platon.rlp.wasm.RLPCodec;
import com.platon.rlp.wasm.RLPList;
import com.platon.rlp.wasm.datatypes.Int;
import com.platon.rlp.wasm.datatypes.Uint;
import com.platon.tx.exceptions.PlatonCallException;
import com.platon.tx.exceptions.PlatonCallTimeoutException;
import com.platon.tx.gas.GasProvider;
import com.platon.utils.Numeric;
import com.platon.utils.Strings;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wasm contract type abstraction for interacting with smart contracts via native Java types.
 */
public abstract class WasmContract extends ManagedTransaction {

	public static final String FUNC_DEPLOY = "deploy";

	protected final String contractBinary;
	protected String contractAddress;
	protected GasProvider gasProvider;
	protected TransactionReceipt transactionReceipt;
	protected Map<String, String> deployedAddresses;
	protected DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;

	protected WasmContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider gasProvider) {
		super(web3j, transactionManager);
		this.contractAddress = contractAddress;
		this.contractBinary = contractBinary;
		this.gasProvider = gasProvider;
	}

	protected WasmContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, GasProvider gasProvider) {
		this(contractBinary, contractAddress, web3j, new RawTransactionManager(web3j, credentials), gasProvider);
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setTransactionReceipt(TransactionReceipt transactionReceipt) {
		this.transactionReceipt = transactionReceipt;
	}

	public String getContractBinary() {
		return contractBinary;
	}

	public void setGasProvider(GasProvider gasProvider) {
		this.gasProvider = gasProvider;
	}

	/**
	 * Check that the contract deployed at the address associated with this smart contract wrapper is in fact the contract you believe it is.
	 *
	 * <p>
	 * This method uses the <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getcode">eth_getCode</a> method to get the contract byte code
	 * and validates it against the byte code stored in this smart contract wrapper.
	 *
	 * @return true if the contract is valid
	 * @throws IOException if unable to connect to web3j node
	 */
	public boolean isValid() throws IOException {
		if (contractAddress.equals("")) {
			throw new UnsupportedOperationException(
					"Contract binary not present, you will need to regenerate your smart contract wrapper with web3j v2.2.0+");
		}

		PlatonGetCode ethGetCode = web3j.platonGetCode(contractAddress, DefaultBlockParameterName.LATEST).send();
		if (ethGetCode.hasError()) {
			return false;
		}

		String code = Numeric.cleanHexPrefix(ethGetCode.getCode());
		// There may be multiple contracts in the Solidity bytecode, hence we only check for a
		// match with a subset
		return !code.isEmpty() && contractBinary.contains(code);
	}

	/**
	 * If this Contract instance was created at deployment, the TransactionReceipt associated with the initial creation will be provided, e.g. via a
	 * <em>deploy</em> method. This will not persist for Contracts instances constructed via a <em>load</em> method.
	 *
	 * @return the TransactionReceipt generated at contract deployment
	 */
	public Optional<TransactionReceipt> getTransactionReceipt() {
		return Optional.ofNullable(transactionReceipt);
	}

	/**
	 * Sets the default block parameter. This use useful if one wants to query historical state of a contract.
	 *
	 * @param defaultBlockParameter the default block parameter
	 */
	public void setDefaultBlockParameter(DefaultBlockParameter defaultBlockParameter) {
		this.defaultBlockParameter = defaultBlockParameter;
	}

	protected <T> T executeCall(WasmFunction function, Class<T> clazz) throws IOException {
		String encodedFunction = WasmFunctionEncoder.encode(function);

		PlatonCall ethCall = web3j
				.platonCall(Transaction.createEthCallTransaction(transactionManager.getFromAddress(), contractAddress, encodedFunction),
						defaultBlockParameter)
				.send();

		//判断底层返回的错误信息是否包含超时信息
		if(ethCall.hasError()){
			Response.Error error = ethCall.getError();
			String message = error.getMessage();
			String lowMessage = !Strings.isBlank(message)? message.toLowerCase() : null;
			//包含timeout则抛超时异常，其他错误则直接抛出runtime异常
			if(!Strings.isBlank(lowMessage)
					&& lowMessage.contains("timeout")){
				throw new PlatonCallTimeoutException(error.getCode(),error.getMessage(),ethCall);
			} else {
				throw new PlatonCallException(error.getCode(),error.getMessage(),ethCall);
			}
		}

		String value = ethCall.getValue();
		if (null != function.getOutputParameterizedType()) {
			return WasmReturnDecoder.decode(value, clazz, function.getOutputParameterizedType());
		} else {
			return WasmReturnDecoder.decode(value, clazz);
		}
	}

	protected <T> RemoteCall<T> executeRemoteCall(WasmFunction function, Class<T> returnType) {
		return new RemoteCall<>(() -> executeCall(function, returnType));
	}

	protected TransactionReceipt executeTransaction(WasmFunction function) throws IOException, TransactionException {
		return executeTransaction(function, BigInteger.ZERO);
	}

	private TransactionReceipt executeTransaction(WasmFunction function, BigInteger vonValue) throws IOException, TransactionException {
		String data = WasmFunctionEncoder.encode(function);
		return executeTransaction(data, vonValue);
	}

	TransactionReceipt executeTransaction(String data, BigInteger vonValue) throws TransactionException, IOException {

		TransactionReceipt receipt = send(contractAddress, data, vonValue, gasProvider.getGasPrice(), gasProvider.getGasLimit());

		if (!receipt.isStatusOK()) {
			throw new TransactionException(String.format("Transaction has failed with status: %s. " + "Gas used: %d. (not-enough gas?)",
					receipt.getStatus(), receipt.getGasUsed()));
		}

		return receipt;
	}

	protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(WasmFunction function) {
		return new RemoteCall<>(() -> executeTransaction(function));
	}

	protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(WasmFunction function, BigInteger vonValue) {
		return new RemoteCall<>(() -> executeTransaction(function, vonValue));
	}

	private static <T extends WasmContract> T create(T contract, String encodedConstructor, BigInteger value)
			throws IOException, TransactionException {
		TransactionReceipt transactionReceipt = contract.executeTransaction(encodedConstructor, value);

		String contractAddress = transactionReceipt.getContractAddress();
		if (contractAddress == null) {
			throw new RuntimeException("Empty contract address returned");
		}
		contract.setContractAddress(contractAddress);
		contract.setTransactionReceipt(transactionReceipt);

		return contract;
	}

	protected static <T extends WasmContract> T deploy(Class<T> type, Web3j web3j, Credentials credentials, GasProvider contractGasProvider, String encodedConstructor, BigInteger value) throws RuntimeException, TransactionException {

		try {
			Constructor<T> constructor = type.getDeclaredConstructor(String.class, Web3j.class, Credentials.class, GasProvider.class);
			constructor.setAccessible(true);

			// we want to use null here to ensure that "to" parameter on message is not populated
			T contract = constructor.newInstance(null, web3j, credentials, contractGasProvider);

			return create(contract, encodedConstructor, value);
		} catch (TransactionException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static <T extends WasmContract> T deploy(Class<T> type, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider, String encodedConstructor, BigInteger value) throws RuntimeException, TransactionException {

		try {
			Constructor<T> constructor = type.getDeclaredConstructor(String.class, Web3j.class, TransactionManager.class, GasProvider.class);
			constructor.setAccessible(true);

			// we want to use null here to ensure that "to" parameter on message is not populated
			T contract = constructor.newInstance(null, web3j, transactionManager, contractGasProvider);
			return create(contract, encodedConstructor, value);
		} catch (TransactionException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T extends WasmContract> RemoteCall<T> deployRemoteCall(Class<T> type, Web3j web3j, Credentials credentials,
			GasProvider contractGasProvider, String encodedConstructor, BigInteger value) {
		return new RemoteCall<>(() -> deploy(type, web3j, credentials, contractGasProvider, encodedConstructor, value));
	}

	public static <T extends WasmContract> RemoteCall<T> deployRemoteCall(Class<T> type, Web3j web3j, Credentials credentials,
			GasProvider contractGasProvider, String encodedConstructor) {
		return new RemoteCall<>(() -> deploy(type, web3j, credentials, contractGasProvider, encodedConstructor, BigInteger.ZERO));
	}

	public static <T extends WasmContract> RemoteCall<T> deployRemoteCall(Class<T> type, Web3j web3j, TransactionManager transactionManager,
			GasProvider contractGasProvider, String encodedConstructor, BigInteger value) {
		return new RemoteCall<>(() -> deploy(type, web3j, transactionManager, contractGasProvider, encodedConstructor, value));
	}

	public static <T extends WasmContract> RemoteCall<T> deployRemoteCall(Class<T> type, Web3j web3j, TransactionManager transactionManager,
			GasProvider contractGasProvider, String encodedConstructor) {
		return new RemoteCall<>(() -> deploy(type, web3j, transactionManager, contractGasProvider, encodedConstructor, BigInteger.ZERO));
	}

	public static WasmEventValues staticExtractEventParameters(WasmEvent event, Log log) {
		String eventSignature = WasmEventEncoder.encode(event);
		List<String> topics = log.getTopics();
		if (null == topics || topics.isEmpty() || !topics.get(0).equals(eventSignature)) {
			return null;
		}

		// The indexed parameters of wasm event
		List<String> indexedValues = new ArrayList<>();
		List<WasmEventParameter> indexedParameters = event.getIndexedParameters();
		if (null != indexedParameters && !indexedParameters.isEmpty()) {
			for (int i = 0; i < indexedParameters.size(); i++) {
				String topicData = topics.get(i + 1);
				WasmEventParameter wasmEventParameter = indexedParameters.get(i);
				Class<?> clazz = wasmEventParameter.getType();

				if (Uint.class.isAssignableFrom(clazz)
						|| Int.class.isAssignableFrom(clazz)) {
					try {
						indexedValues.add(WasmEventDecoder.decodeIndexParameter(topicData, clazz).toString());
					} catch (Exception e) {
						indexedValues.add(topicData);
					}
				} else {
					indexedValues.add(topicData);
				}
			}
		}

		// The unindexed parameters of wasm event
		List<Object> nonIndexedValues = new ArrayList<>();
		List<WasmEventParameter> nonIndexedParameters = event.getNonIndexedParameters();
		if (null != log.getData() && !log.getData().equals("")) {
			RLPList rlpList = RLPCodec.decode(Numeric.hexStringToByteArray(log.getData()), RLPList.class);
			for (int i = 0; i < rlpList.size(); i++) {
				WasmEventParameter wasmEventParameter = nonIndexedParameters.get(i);
				if (wasmEventParameter.getParameterizedType() == null) {
					nonIndexedValues.add(RLPCodec.decode(rlpList.get(i).getEncoded(), wasmEventParameter.getType()));
				} else {
					nonIndexedValues.add(RLPCodec.decodeContainer(rlpList.get(i).getEncoded(), wasmEventParameter.getParameterizedType()));
				}
			}
		}

		return new WasmEventValues(indexedValues, nonIndexedValues);
	}

	protected WasmEventValuesWithLog extractEventParametersWithLog(WasmEvent event, Log log) {
		final WasmEventValues eventValues = staticExtractEventParameters(event, log);
		return (eventValues == null) ? null : new WasmEventValuesWithLog(eventValues, log);
	}

	protected List<WasmEventValuesWithLog> extractEventParametersWithLog(WasmEvent event, TransactionReceipt transactionReceipt) {
		return transactionReceipt.getLogs().stream().map(log -> extractEventParametersWithLog(event, log)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	/**
	 * Subclasses should implement this method to return pre-existing addresses for deployed contracts.
	 *
	 * @param networkId the network id, for example "1" for the main-net, "3" for ropsten, etc.
	 * @return the deployed address of the contract, if known, and null otherwise.
	 */
	protected String getStaticDeployedAddress(String networkId) {
		return null;
	}

	public final void setDeployedAddress(String networkId, String address) {
		if (deployedAddresses == null) {
			deployedAddresses = new HashMap<>();
		}
		deployedAddresses.put(networkId, address);
	}

	public final String getDeployedAddress(String networkId) {
		String addr = null;
		if (deployedAddresses != null) {
			addr = deployedAddresses.get(networkId);
		}
		return addr == null ? getStaticDeployedAddress(networkId) : addr;
	}

	public static class WasmEventValuesWithLog {
		private final WasmEventValues eventValues;
		private final Log log;

		private WasmEventValuesWithLog(WasmEventValues eventValues, Log log) {
			this.eventValues = eventValues;
			this.log = log;
		}

		public List<String> getIndexedValues() {
			return eventValues.getIndexedValues();
		}

		public List<?> getNonIndexedValues() {
			return eventValues.getNonIndexedValues();
		}

		public Log getLog() {
			return log;
		}
	}

}
