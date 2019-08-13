package org.web3j.tx;

import org.web3j.abi.EventValues;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.PlatOnEventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.core.methods.response.PlatonGetCode;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;


/**
 * Solidity contract type abstraction for interacting with smart contracts via native Java types.
 */
@SuppressWarnings("WeakerAccess")
public abstract class PlatOnContract extends ManagedTransaction {

    //https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    /**
     * @see org.web3j.tx.gas.DefaultGasProvider
     * @deprecated ...
     */

    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    public static final String FUNC_DEPLOY = "deploy";
    public static final String RESTRICTING_PLAN_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000001";
    public static final String STAKING_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000002";
    public static final String DELEGATE_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000002";
    public static final String NODE_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000002";
    public static final String SLASH_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000004";
    public static final String PROPOSAL_CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000005";

    protected final String contractBinary;
    protected String contractAddress;
    protected ContractGasProvider gasProvider;
    protected TransactionReceipt transactionReceipt;
    protected DefaultBlockParameter defaultBlockParameter = DefaultBlockParameterName.LATEST;

    protected PlatOnContract(String contractBinary, String contractAddress,
                             Web3j web3j, TransactionManager transactionManager,
                             ContractGasProvider gasProvider) {
        super(web3j, transactionManager);

        this.contractAddress = ensResolver.resolve(contractAddress);

        this.contractBinary = contractBinary;
        this.gasProvider = gasProvider;
    }

    protected PlatOnContract(String contractBinary, String contractAddress,
                             Web3j web3j, Credentials credentials,
                             ContractGasProvider gasProvider) {

        this(contractBinary, contractAddress, web3j,
                new RawTransactionManager(web3j, credentials),
                gasProvider);
    }

    protected PlatOnContract(String contractBinary, String contractAddress, String chainId,
                             Web3j web3j, Credentials credentials,
                             ContractGasProvider gasProvider) {

        this(contractBinary, contractAddress, web3j,
                new RawTransactionManager(web3j, credentials, new Byte(chainId)),
                gasProvider);
    }

    @Deprecated
    protected PlatOnContract(String contractBinary, String contractAddress,
                             Web3j web3j, TransactionManager transactionManager,
                             BigInteger gasPrice, BigInteger gasLimit) {
        this(contractBinary, contractAddress, web3j, transactionManager,
                new StaticGasProvider(gasPrice, gasLimit));
    }

    @Deprecated
    protected PlatOnContract(String contractBinary, String contractAddress,
                             Web3j web3j, Credentials credentials,
                             BigInteger gasPrice, BigInteger gasLimit) {
        this(contractBinary, contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit);
    }

    @Deprecated
    protected PlatOnContract(String contractAddress,
                             Web3j web3j, TransactionManager transactionManager,
                             BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    @Deprecated
    protected PlatOnContract(String contractAddress,
                             Web3j web3j, Credentials credentials,
                             BigInteger gasPrice, BigInteger gasLimit) {
        this("", contractAddress, web3j, new RawTransactionManager(web3j, credentials),
                gasPrice, gasLimit);
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

    public void setGasProvider(ContractGasProvider gasProvider) {
        this.gasProvider = gasProvider;
    }

    /**
     * Should be implemented by sub contract.
     *
     * @param function contract function
     * @return contract type
     */
    protected long getTransactionType(Function function) {
        return 0;
    }

    /**
     * Allow {@code gasPrice} to be set.
     *
     * @param newPrice gas price to use for subsequent transactions
     * @deprecated use ContractGasProvider
     */
    public void setGasPrice(BigInteger newPrice) {
        this.gasProvider = new StaticGasProvider(newPrice, gasProvider.getGasLimit());
    }

    /**
     * Get the current {@code gasPrice} value this contract uses when executing transactions.
     *
     * @return the gas price set on this contract
     * @deprecated use ContractGasProvider
     */
    public BigInteger getGasPrice() {
        return gasProvider.getGasPrice();
    }

    /**
     * Check that the contract deployed at the address associated with this smart contract wrapper
     * is in fact the contract you believe it is.
     *
     * <p>This method uses the
     * <a href="https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getcode">eth_getCode</a> method
     * to get the contract byte code and validates it against the byte code stored in this smart
     * contract wrapper.
     *
     * @return true if the contract is valid
     * @throws IOException if unable to connect to web3j node
     */
    public boolean isValid() throws IOException {
        if (contractAddress.equals("")) {
            throw new UnsupportedOperationException(
                    "Contract binary not present, you will need to regenerate your smart "
                            + "contract wrapper with web3j v2.2.0+");
        }

        PlatonGetCode ethGetCode = web3j
                .platonGetCode(contractAddress, DefaultBlockParameterName.LATEST)
                .send();
        if (ethGetCode.hasError()) {
            return false;
        }

        String code = Numeric.cleanHexPrefix(ethGetCode.getCode());
        // There may be multiple contracts in the Solidity bytecode, hence we only check for a
        // match with a subset
        return !code.isEmpty() && contractBinary.contains(code);
    }

    /**
     * If this Contract instance was created at deployment, the TransactionReceipt associated
     * with the initial creation will be provided, e.g. via a <em>deploy</em> method. This will
     * not persist for Contracts instances constructed via a <em>load</em> method.
     *
     * @return the TransactionReceipt generated at contract deployment
     */
    public Optional<TransactionReceipt> getTransactionReceipt() {
        return Optional.ofNullable(transactionReceipt);
    }

    /**
     * Sets the default block parameter. This use useful if one wants to query
     * historical state of a contract.
     *
     * @param defaultBlockParameter the default block parameter
     */
    public void setDefaultBlockParameter(DefaultBlockParameter defaultBlockParameter) {
        this.defaultBlockParameter = defaultBlockParameter;
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     */
    private List<Type> executeCall(Function function) throws IOException {
        String encodedFunction = PlatOnUtil.invokeEncode(function);
        PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                        transactionManager.getFromAddress(), contractAddress, encodedFunction),
                defaultBlockParameter)
                .send();
        String value = ethCall.getValue();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    protected BaseResponse executePatonCall(Function function) throws IOException {
        String encodedFunction = PlatOnUtil.invokeEncode(function);
        PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                        transactionManager.getFromAddress(), contractAddress, encodedFunction),
                defaultBlockParameter)
                .send();
        String value = ethCall.getValue();
        BaseResponse response = JSONUtil.parseObject(new String(Numeric.hexStringToByteArray(value)), BaseResponse.class);
        return response;
    }

    /**
     * Execute constant function call - i.e. a call that does not change state of the contract
     *
     * @param function to call
     * @return {@link List} of values returned by function call
     */
    private List<Type> executeCall(Function function, BigInteger number) throws IOException {
        String encodedFunction = PlatOnUtil.invokeEncode(function, getTransactionType(function));
        PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                        transactionManager.getFromAddress(), contractAddress, encodedFunction),
                new DefaultBlockParameterNumber(number))
                .send();

        String value = ethCall.getValue();
        return FunctionReturnDecoder.decode(value, function.getOutputParameters());
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type> T executeCallSingleValueReturn(
            Function function) throws IOException {
        List<Type> values = executeCall(function);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type> T executeCallSingleValueReturn(
            Function function, BigInteger number) throws IOException {
        List<Type> values = executeCall(function, number);
        if (!values.isEmpty()) {
            return (T) values.get(0);
        } else {
            return null;
        }
    }


    @SuppressWarnings("unchecked")
    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType) throws IOException {
        T result = executeCallSingleValueReturn(function);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString();  // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: " + value
                            + " to expected type: " + returnType.getSimpleName());
        }
    }

    @SuppressWarnings("unchecked")
    protected <T extends Type, R> R executeCallSingleValueReturn(
            Function function, Class<R> returnType, BigInteger number) throws IOException {
        T result = executeCallSingleValueReturn(function, number);
        if (result == null) {
            throw new ContractCallException("Empty value (0x) returned from contract");
        }

        Object value = result.getValue();
        if (returnType.isAssignableFrom(value.getClass())) {
            return (R) value;
        } else if (result.getClass().equals(Address.class) && returnType.equals(String.class)) {
            return (R) result.toString();  // cast isn't necessary
        } else {
            throw new ContractCallException(
                    "Unable to convert response: " + value
                            + " to expected type: " + returnType.getSimpleName());
        }
    }

    protected List<Type> executeCallMultipleValueReturn(
            Function function) throws IOException {
        return executeCall(function);
    }

    protected TransactionReceipt executeTransaction(
            Function function)
            throws IOException, TransactionException {
        return executeTransaction(function, BigInteger.ZERO);
    }

    protected PlatonSendTransaction executePlatonTransaction(Function function) throws IOException, TransactionException {
        return executePlatonTransaction(function, BigInteger.ZERO);
    }

    private TransactionReceipt executeTransaction(Function function, BigInteger weiValue) throws IOException, TransactionException {
        return executeTransaction(PlatOnUtil.invokeEncode(function, getTransactionType(function)), weiValue, function.getName());
    }

    private BaseResponse executeTransactionWithFunctionType(Function function, BigInteger weiValue) throws IOException, TransactionException {
        return executeTransaction(PlatOnUtil.invokeEncode(function), weiValue, function.getType());
    }

    private PlatonSendTransaction executePlatonTransaction(Function function, BigInteger weiValue) throws IOException, TransactionException {
        return executePlatonTransaction(PlatOnUtil.invokeEncode(function), weiValue, function.getType());
    }

    /**
     * Given the duration required to execute a transaction.
     *
     * @param data     to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws IOException          if the call to the node fails
     * @throws TransactionException if the transaction was not mined while waiting
     */
    TransactionReceipt executeTransaction(
            String data, BigInteger weiValue, String funcName)
            throws TransactionException, IOException {

        TransactionReceipt receipt = send(contractAddress, data, weiValue,
                gasProvider.getGasPrice(funcName),
                gasProvider.getGasLimit(funcName));

        if (!receipt.isStatusOK()) {
            throw new TransactionException(
                    String.format(
                            "Transaction has failed with status: %s. "
                                    + "Gas used: %d. (not-enough gas?)",
                            receipt.getStatus(),
                            receipt.getGasUsed()));
        }

        return receipt;
    }

    /**
     * Given the duration required to execute a transaction.
     *
     * @param data     to send in transaction
     * @param weiValue in Wei to send in transaction
     * @return {@link Optional} containing our transaction receipt
     * @throws IOException          if the call to the node fails
     * @throws TransactionException if the transaction was not mined while waiting
     */
    BaseResponse executeTransaction(
            String data, BigInteger weiValue, int functionType)
            throws TransactionException, IOException {

        TransactionReceipt receipt = send(contractAddress, data, weiValue,
                gasProvider.getGasPrice(functionType),
                gasProvider.getGasLimit(functionType));

        return getResponseFromTransactionReceipt(receipt, functionType);
    }

    BaseResponse getTransactionResult(PlatonSendTransaction ethSendTransaction, int functionType) throws IOException, TransactionException {

        TransactionReceipt receipt = getTransactionReceipt(ethSendTransaction);

        if (!receipt.isStatusOK()) {
            throw new TransactionException(
                    String.format(
                            "Transaction has failed with status: %s. "
                                    + "Gas used: %d. (not-enough gas?)",
                            receipt.getStatus(),
                            receipt.getGasUsed()));
        }
        return getResponseFromTransactionReceipt(receipt, functionType);
    }

    PlatonSendTransaction executePlatonTransaction(
            String data, BigInteger weiValue, int functionType)
            throws TransactionException, IOException {

        return sendPlatonRawTransaction(contractAddress, data, weiValue,
                gasProvider.getGasPrice(functionType),
                gasProvider.getGasLimit(functionType));
    }

    private BaseResponse getResponseFromTransactionReceipt(TransactionReceipt transactionReceipt, int functionType) throws TransactionException {

        Event event = new Event(functionType,
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));

        List<EventValuesWithLog> eventValuesWithLogList = extractEventParametersWithLog(event, transactionReceipt);

        return JSONUtil.parseObject(getResponseFromLog(transactionReceipt, eventValuesWithLogList), BaseResponse.class);
    }

    private String getResponseFromLog(TransactionReceipt transactionReceipt, List<EventValuesWithLog> eventValuesWithLogList) throws TransactionException {

        boolean isEventValuesWithLogEmpty = eventValuesWithLogList == null || eventValuesWithLogList.isEmpty();

        List<Type> nonIndexedValues;

        if (isEventValuesWithLogEmpty || (nonIndexedValues = eventValuesWithLogList.get(0).getNonIndexedValues()) == null || nonIndexedValues.isEmpty()) {
            throw new TransactionException(
                    String.format(
                            "Transaction has failed with status: %s. "
                                    + "Gas used: %d. (not-enough gas?)",
                            transactionReceipt.getStatus(),
                            transactionReceipt.getGasUsed()));
        }

        return (String) nonIndexedValues.get(0).getValue();
    }

    protected <T> RemoteCall<BaseResponse<T>> executePlatonRemoteCallSingleValueReturn(Function function) {
        return new RemoteCall<BaseResponse<T>>(new Callable<BaseResponse<T>>() {
            @Override
            public BaseResponse<T> call() throws Exception {
                return executePatonCall(function);
            }
        });
    }

    protected <T extends Type> RemoteCall<T> executeRemoteCallSingleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function));
    }

    protected <T> RemoteCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType));
    }

    protected <T> RemoteCall<T> executeRemoteCallSingleValueReturn(
            Function function, Class<T> returnType, BigInteger number) {
        return new RemoteCall<>(() -> executeCallSingleValueReturn(function, returnType, number));
    }

    protected RemoteCall<List<Type>> executeRemoteCallMultipleValueReturn(Function function) {
        return new RemoteCall<>(() -> executeCallMultipleValueReturn(function));
    }

    protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(Function function) {
        return new RemoteCall<>(() -> executeTransaction(function));
    }

    protected RemoteCall<PlatonSendTransaction> executeRemoteCallPlatonTransaction(Function function, BigInteger weiValue) {
        return new RemoteCall<>(() -> executePlatonTransaction(function, weiValue));
    }

    protected RemoteCall<PlatonSendTransaction> executeRemoteCallPlatonTransaction(Function function) {
        return new RemoteCall<>(() -> executePlatonTransaction(function, BigInteger.ZERO));
    }

    protected RemoteCall<TransactionReceipt> executeRemoteCallTransaction(
            Function function, BigInteger weiValue) {
        return new RemoteCall<>(() -> executeTransaction(function, weiValue));
    }

    protected RemoteCall<BaseResponse> executeRemoteCallTransactionWithFunctionType(Function function, BigInteger weiValue) {
        return new RemoteCall<>(new Callable<BaseResponse>() {
            @Override
            public BaseResponse call() throws Exception {
                return executeTransactionWithFunctionType(function, weiValue);
            }
        });
    }

    protected RemoteCall<BaseResponse> executeRemoteCallTransactionWithFunctionType(Function function) {
        return new RemoteCall<>(new Callable<BaseResponse>() {
            @Override
            public BaseResponse call() throws Exception {
                return executeTransactionWithFunctionType(function, BigInteger.ZERO);
            }
        });
    }

    protected RemoteCall<BaseResponse> executeRemoteCallTransactionWithFunctionType(PlatonSendTransaction ethSendTransaction, int functionType) {
        return new RemoteCall<>(new Callable<BaseResponse>() {
            @Override
            public BaseResponse call() throws Exception {
                return getTransactionResult(ethSendTransaction, functionType);
            }
        });
    }

    public static EventValues staticExtractEventParameters(
            Event event, Log log) {

        List<String> topics = log.getTopics();
        String encodedEventSignature = PlatOnEventEncoder.encodeWithFunctionType(event);
        if (!topics.get(0).equals(encodedEventSignature)) {
            return null;
        }

        List<Type> indexedValues = new ArrayList<>();
        List<Type> nonIndexedValues = PlatOnUtil.eventDecode(log.getData(), event.getNonIndexedParameters());

        List<TypeReference<Type>> indexedParameters = event.getIndexedParameters();
        for (int i = 0; i < indexedParameters.size(); i++) {
            Type value = FunctionReturnDecoder.decodeIndexedValue(
                    topics.get(i + 1), indexedParameters.get(i));
            indexedValues.add(value);
        }
        return new EventValues(indexedValues, nonIndexedValues);
    }

    protected EventValues extractEventParameters(Event event, Log log) {
        return staticExtractEventParameters(event, log);
    }

    protected List<EventValues> extractEventParameters(
            Event event, TransactionReceipt transactionReceipt) {
        List<EventValues> list = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            EventValues eventValues = extractEventParameters(event, log);
            if (eventValues != null) {
                list.add(eventValues);
            }
        }
        return list;
    }

    protected EventValuesWithLog extractEventParametersWithLog(Event event, Log log) {
        final EventValues eventValues = staticExtractEventParameters(event, log);
        return (eventValues == null) ? null : new EventValuesWithLog(eventValues, log);
    }

    protected List<EventValuesWithLog> extractEventParametersWithLog(
            Event event, TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> list = new ArrayList<>();
        for (Log log : transactionReceipt.getLogs()) {
            EventValuesWithLog eventValuesWithLog = extractEventParametersWithLog(event, log);
            if (eventValuesWithLog != null) {
                list.add(eventValuesWithLog);
            }
        }
        return list;
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
