package org.web3j.tx;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.WasmFunctionEncoder;
import org.web3j.abi.WasmReturnDecoder;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.WasmFunction;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.core.methods.response.PlatonGetCode;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

/**
 * Wasm contract type abstraction for interacting with smart contracts via native Java types.
 */
@SuppressWarnings("rawtypes")
public abstract class WasmContract extends ManagedTransaction {

	public static final String FUNC_DEPLOY = "deploy";

	protected final String contractBinary;
	protected String contractAddress;
	protected GasProvider gasProvider;
	protected TransactionReceipt transactionReceipt;
	protected Map<String, String> deployedAddresses;
	protected DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;

	protected WasmContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager,
			GasProvider gasProvider) {
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

		String value = ethCall.getValue();
		return WasmReturnDecoder.decode(value, clazz);
	}

	protected <T> RemoteCall<T> executeRemoteCall(WasmFunction function, Class<T> returnType) {
		return new RemoteCall<>(() -> executeCall(function, returnType));
	}

	protected TransactionReceipt executeTransaction(WasmFunction function) throws IOException, TransactionException {
		return executeTransaction(function, BigInteger.ZERO);
	}

	private TransactionReceipt executeTransaction(WasmFunction function, BigInteger weiValue) throws IOException, TransactionException {
		String data = WasmFunctionEncoder.encode(function);
		return executeTransaction(data, weiValue);
	}

	TransactionReceipt executeTransaction(String data, BigInteger weiValue) throws TransactionException, IOException {

		TransactionReceipt receipt = send(contractAddress, data, weiValue, gasProvider.getGasPrice(), gasProvider.getGasLimit());

		if (!receipt.isStatusOK()) {
			throw new TransactionException(String.format("Transaction has failed with status: %s. " + "Gas used: %d. (not-enough gas?)",
					receipt.getStatus(), receipt.getGasUsed()));
		}

		return receipt;
	}

	protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(WasmFunction function) {
		return new RemoteCall<>(() -> executeTransaction(function));
	}

	protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(WasmFunction function, BigInteger weiValue) {
		return new RemoteCall<>(() -> executeTransaction(function, weiValue));
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

	protected static <T extends WasmContract> T deploy(Class<T> type, Web3j web3j, Credentials credentials, GasProvider contractGasProvider,
			String encodedConstructor, BigInteger value) throws RuntimeException, TransactionException {

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

	protected static <T extends WasmContract> T deploy(Class<T> type, Web3j web3j, TransactionManager transactionManager,
			GasProvider contractGasProvider, String encodedConstructor, BigInteger value) throws RuntimeException, TransactionException {

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

	public static EventValues staticExtractEventParameters(Event event, Log log) {

		List<String> topics = log.getTopics();
		String encodedEventSignature = EventEncoder.encode(event);
		if (topics == null || topics.size() == 0 || !topics.get(0).equals(encodedEventSignature)) {
			return null;
		}

		List<Type> indexedValues = new ArrayList<>();
		List<Type> nonIndexedValues = FunctionReturnDecoder.decode(log.getData(), event.getNonIndexedParameters());

		List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
		for (int i = 0; i < indexedParameters.size(); i++) {
			Type value = FunctionReturnDecoder.decodeIndexedValue(topics.get(i + 1), indexedParameters.get(i));
			indexedValues.add(value);
		}
		return new EventValues(indexedValues, nonIndexedValues);
	}

	protected EventValues extractEventParameters(Event event, Log log) {
		return staticExtractEventParameters(event, log);
	}

	protected List<EventValues> extractEventParameters(Event event, TransactionReceipt transactionReceipt) {
		return transactionReceipt.getLogs().stream().map(log -> extractEventParameters(event, log)).filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	protected EventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
		final EventValues eventValues = staticExtractEventParameters(event, log);
		return (eventValues == null) ? null : new EventValuesWithLog(eventValues, log);
	}

	protected List<EventValuesWithLog> extractEventParametersWithLog(Event event, TransactionReceipt transactionReceipt) {
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

	/**
	 * Adds a log field to {@link EventValues}.
	 */
	public static class EventValuesWithLog {
		private final EventValues eventValues;
		private final Log log;

		private EventValuesWithLog(EventValues eventValues, Log log) {
			this.eventValues = eventValues;
			this.log = log;
		}

		public List<Type> getIndexedValues() {
			return eventValues.getIndexedValues();
		}

		public List<Type> getNonIndexedValues() {
			return eventValues.getNonIndexedValues();
		}

		public Log getLog() {
			return log;
		}
	}

}
