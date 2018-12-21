package org.web3j.codegen;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int32;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class Multisig extends PlatOnContract {
    private static final String ABI = "[{\"name\":\"initWallet\",\"inputs\":[{\"name\":\"owner\",\"type\":\"string\"},{\"name\":\"required\",\"type\":\"uint64\"}],\"outputs\":[],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"submitTransaction\",\"inputs\":[{\"name\":\"destination\",\"type\":\"string\"},{\"name\":\"from\",\"type\":\"string\"},{\"name\":\"vs\",\"type\":\"string\"},{\"name\":\"data\",\"type\":\"string\"},{\"name\":\"len\",\"type\":\"uint64\"},{\"name\":\"time\",\"type\":\"uint64\"},{\"name\":\"fs\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint64\"}],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"confirmTransaction\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"revokeConfirmation\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"executeTransaction\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"isConfirmed\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[{\"name\":\"\",\"type\":\"int32\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getRequired\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint64\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getListSize\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"uint64\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getConfirmationCount\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint64\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getTransactionCount\",\"inputs\":[{\"name\":\"pending\",\"type\":\"int32\"},{\"name\":\"executed\",\"type\":\"int32\"}],\"outputs\":[{\"name\":\"\",\"type\":\"uint64\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getTransactionList\",\"inputs\":[{\"name\":\"from\",\"type\":\"uint64\"},{\"name\":\"to\",\"type\":\"uint64\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getOwners\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getConfirmations\",\"inputs\":[{\"name\":\"transactionId\",\"type\":\"uint64\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getTransactionIds\",\"inputs\":[{\"name\":\"from\",\"type\":\"uint64\"},{\"name\":\"to\",\"type\":\"uint64\"},{\"name\":\"pending\",\"type\":\"int32\"},{\"name\":\"executed\",\"type\":\"int32\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"getMultiSigList\",\"inputs\":[{\"name\":\"transactionIds\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"Confirmation\",\"inputs\":[{\"type\":\"string\"},{\"type\":\"int64\"}],\"type\":\"event\"},{\"name\":\"Revocation\",\"inputs\":[{\"type\":\"string\"},{\"type\":\"int64\"}],\"type\":\"event\"},{\"name\":\"Submission\",\"inputs\":[{\"type\":\"int64\"}],\"type\":\"event\"},{\"name\":\"Execution\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"},{\"name\":\"ExecutionFailure\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"},{\"name\":\"Deposit\",\"inputs\":[{\"type\":\"string\"},{\"type\":\"string\"}],\"type\":\"event\"},{\"name\":\"OwnerAddition\",\"inputs\":[{\"type\":\"int64\"}],\"type\":\"event\"},{\"name\":\"OwnerRemoval\",\"inputs\":[{\"type\":\"int64\"}],\"type\":\"event\"},{\"name\":\"RequirementChange\",\"inputs\":[{\"type\":\"int64\"}],\"type\":\"event\"}]";

    public static final String FUNC_INITWALLET = "initWallet";

    public static final String FUNC_SUBMITTRANSACTION = "submitTransaction";

    public static final String FUNC_CONFIRMTRANSACTION = "confirmTransaction";

    public static final String FUNC_REVOKECONFIRMATION = "revokeConfirmation";

    public static final String FUNC_EXECUTETRANSACTION = "executeTransaction";

    public static final String FUNC_ISCONFIRMED = "isConfirmed";

    public static final String FUNC_GETREQUIRED = "getRequired";

    public static final String FUNC_GETLISTSIZE = "getListSize";

    public static final String FUNC_GETCONFIRMATIONCOUNT = "getConfirmationCount";

    public static final String FUNC_GETTRANSACTIONCOUNT = "getTransactionCount";

    public static final String FUNC_GETTRANSACTIONLIST = "getTransactionList";

    public static final String FUNC_GETOWNERS = "getOwners";

    public static final String FUNC_GETCONFIRMATIONS = "getConfirmations";

    public static final String FUNC_GETTRANSACTIONIDS = "getTransactionIds";

    public static final String FUNC_GETMULTISIGLIST = "getMultiSigList";

    public static final Event CONFIRMATION_EVENT = new Event("Confirmation", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int64>() {}));
    ;

    public static final Event REVOCATION_EVENT = new Event("Revocation", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Int64>() {}));
    ;

    public static final Event SUBMISSION_EVENT = new Event("Submission", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
    ;

    public static final Event EXECUTION_EVENT = new Event("Execution", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event EXECUTIONFAILURE_EVENT = new Event("ExecutionFailure", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event DEPOSIT_EVENT = new Event("Deposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event OWNERADDITION_EVENT = new Event("OwnerAddition", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
    ;

    public static final Event OWNERREMOVAL_EVENT = new Event("OwnerRemoval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
    ;

    public static final Event REQUIREMENTCHANGE_EVENT = new Event("RequirementChange", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
    ;

    @Deprecated
    protected Multisig(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Multisig(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Multisig(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Multisig(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> initWallet(String owner, BigInteger required) {
        final Function function = new Function(
                FUNC_INITWALLET, 
                Arrays.<Type>asList(new Utf8String(owner),
                new Uint64(required)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String initWalletData(String owner, BigInteger required) {
        final Function function = new Function(
                FUNC_INITWALLET, 
                Arrays.<Type>asList(new Utf8String(owner),
                new Uint64(required)),
                Collections.<TypeReference<?>>emptyList());
        return getInvokeData(function);
    }

    public static BigInteger initWalletGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String owner, BigInteger required) throws IOException {
        String ethEstimateGasData = initWalletData(owner, required);
        return estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> submitTransaction(String destination, String from, String vs, String data, BigInteger len, BigInteger time, String fs) {
        final Function function = new Function(
                FUNC_SUBMITTRANSACTION, 
                Arrays.<Type>asList(new Utf8String(destination),
                new Utf8String(from),
                new Utf8String(vs),
                new Utf8String(data),
                new Uint64(len),
                new Uint64(time),
                new Utf8String(fs)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String submitTransactionData(String destination, String from, String vs, String data, BigInteger len, BigInteger time, String fs) {
        final Function function = new Function(
                FUNC_SUBMITTRANSACTION, 
                Arrays.<Type>asList(new Utf8String(destination),
                new Utf8String(from),
                new Utf8String(vs),
                new Utf8String(data),
                new Uint64(len),
                new Uint64(time),
                new Utf8String(fs)),
                Collections.<TypeReference<?>>emptyList());
        return getInvokeData(function);
    }

    public static BigInteger submitTransactionGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String destination, String from, String vs, String data, BigInteger len, BigInteger time, String fs) throws IOException {
        String ethEstimateGasData = submitTransactionData(destination, from, vs, data, len, time, fs);
        return estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> confirmTransaction(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_CONFIRMTRANSACTION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String confirmTransactionData(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_CONFIRMTRANSACTION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return getInvokeData(function);
    }

    public static BigInteger confirmTransactionGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, BigInteger transactionId) throws IOException {
        String ethEstimateGasData = confirmTransactionData(transactionId);
        return estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> revokeConfirmation(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_REVOKECONFIRMATION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String revokeConfirmationData(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_REVOKECONFIRMATION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return getInvokeData(function);
    }

    public static BigInteger revokeConfirmationGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, BigInteger transactionId) throws IOException {
        String ethEstimateGasData = revokeConfirmationData(transactionId);
        return estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> executeTransaction(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_EXECUTETRANSACTION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String executeTransactionData(BigInteger transactionId) {
        final Function function = new Function(
                FUNC_EXECUTETRANSACTION, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Collections.<TypeReference<?>>emptyList());
        return getInvokeData(function);
    }

    public static BigInteger executeTransactionGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, BigInteger transactionId) throws IOException {
        String ethEstimateGasData = executeTransactionData(transactionId);
        return estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<BigInteger> isConfirmed(BigInteger transactionId) {
        final Function function = new Function(FUNC_ISCONFIRMED, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Int32>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getRequired() {
        final Function function = new Function(FUNC_GETREQUIRED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getListSize() {
        final Function function = new Function(FUNC_GETLISTSIZE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getConfirmationCount(BigInteger transactionId) {
        final Function function = new Function(FUNC_GETCONFIRMATIONCOUNT, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> getTransactionCount(BigInteger pending, BigInteger executed) {
        final Function function = new Function(FUNC_GETTRANSACTIONCOUNT, 
                Arrays.<Type>asList(new Int32(pending),
                new Int32(executed)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> getTransactionList(BigInteger from, BigInteger to) {
        final Function function = new Function(FUNC_GETTRANSACTIONLIST, 
                Arrays.<Type>asList(new Uint64(from),
                new Uint64(to)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getOwners() {
        final Function function = new Function(FUNC_GETOWNERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getConfirmations(BigInteger transactionId) {
        final Function function = new Function(FUNC_GETCONFIRMATIONS, 
                Arrays.<Type>asList(new Uint64(transactionId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getTransactionIds(BigInteger from, BigInteger to, BigInteger pending, BigInteger executed) {
        final Function function = new Function(FUNC_GETTRANSACTIONIDS, 
                Arrays.<Type>asList(new Uint64(from),
                new Uint64(to),
                new Int32(pending),
                new Int32(executed)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getMultiSigList(String transactionIds) {
        final Function function = new Function(FUNC_GETMULTISIGLIST, 
                Arrays.<Type>asList(new Utf8String(transactionIds)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<ConfirmationEventResponse> getConfirmationEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CONFIRMATION_EVENT, transactionReceipt);
        ArrayList<ConfirmationEventResponse> responses = new ArrayList<ConfirmationEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ConfirmationEventResponse typedResponse = new ConfirmationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.param2 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ConfirmationEventResponse> confirmationEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ConfirmationEventResponse>() {
            @Override
            public ConfirmationEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CONFIRMATION_EVENT, log);
                ConfirmationEventResponse typedResponse = new ConfirmationEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.param2 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ConfirmationEventResponse> confirmationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONFIRMATION_EVENT));
        return confirmationEventObservable(filter);
    }

    public List<RevocationEventResponse> getRevocationEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REVOCATION_EVENT, transactionReceipt);
        ArrayList<RevocationEventResponse> responses = new ArrayList<RevocationEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RevocationEventResponse typedResponse = new RevocationEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.param2 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RevocationEventResponse> revocationEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RevocationEventResponse>() {
            @Override
            public RevocationEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REVOCATION_EVENT, log);
                RevocationEventResponse typedResponse = new RevocationEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.param2 = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RevocationEventResponse> revocationEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOCATION_EVENT));
        return revocationEventObservable(filter);
    }

    public List<SubmissionEventResponse> getSubmissionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SUBMISSION_EVENT, transactionReceipt);
        ArrayList<SubmissionEventResponse> responses = new ArrayList<SubmissionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SubmissionEventResponse typedResponse = new SubmissionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SubmissionEventResponse> submissionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SubmissionEventResponse>() {
            @Override
            public SubmissionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SUBMISSION_EVENT, log);
                SubmissionEventResponse typedResponse = new SubmissionEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SubmissionEventResponse> submissionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SUBMISSION_EVENT));
        return submissionEventObservable(filter);
    }

    public List<ExecutionEventResponse> getExecutionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EXECUTION_EVENT, transactionReceipt);
        ArrayList<ExecutionEventResponse> responses = new ArrayList<ExecutionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ExecutionEventResponse typedResponse = new ExecutionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ExecutionEventResponse> executionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ExecutionEventResponse>() {
            @Override
            public ExecutionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(EXECUTION_EVENT, log);
                ExecutionEventResponse typedResponse = new ExecutionEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ExecutionEventResponse> executionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTION_EVENT));
        return executionEventObservable(filter);
    }

    public List<ExecutionFailureEventResponse> getExecutionFailureEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(EXECUTIONFAILURE_EVENT, transactionReceipt);
        ArrayList<ExecutionFailureEventResponse> responses = new ArrayList<ExecutionFailureEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            ExecutionFailureEventResponse typedResponse = new ExecutionFailureEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ExecutionFailureEventResponse> executionFailureEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ExecutionFailureEventResponse>() {
            @Override
            public ExecutionFailureEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(EXECUTIONFAILURE_EVENT, log);
                ExecutionFailureEventResponse typedResponse = new ExecutionFailureEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ExecutionFailureEventResponse> executionFailureEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(EXECUTIONFAILURE_EVENT));
        return executionFailureEventObservable(filter);
    }

    public List<DepositEventResponse> getDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(DEPOSIT_EVENT, transactionReceipt);
        ArrayList<DepositEventResponse> responses = new ArrayList<DepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            DepositEventResponse typedResponse = new DepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.param2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<DepositEventResponse> depositEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, DepositEventResponse>() {
            @Override
            public DepositEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(DEPOSIT_EVENT, log);
                DepositEventResponse typedResponse = new DepositEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.param2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<DepositEventResponse> depositEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(DEPOSIT_EVENT));
        return depositEventObservable(filter);
    }

    public List<OwnerAdditionEventResponse> getOwnerAdditionEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERADDITION_EVENT, transactionReceipt);
        ArrayList<OwnerAdditionEventResponse> responses = new ArrayList<OwnerAdditionEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnerAdditionEventResponse typedResponse = new OwnerAdditionEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnerAdditionEventResponse> ownerAdditionEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnerAdditionEventResponse>() {
            @Override
            public OwnerAdditionEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERADDITION_EVENT, log);
                OwnerAdditionEventResponse typedResponse = new OwnerAdditionEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnerAdditionEventResponse> ownerAdditionEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERADDITION_EVENT));
        return ownerAdditionEventObservable(filter);
    }

    public List<OwnerRemovalEventResponse> getOwnerRemovalEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERREMOVAL_EVENT, transactionReceipt);
        ArrayList<OwnerRemovalEventResponse> responses = new ArrayList<OwnerRemovalEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnerRemovalEventResponse typedResponse = new OwnerRemovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<OwnerRemovalEventResponse> ownerRemovalEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, OwnerRemovalEventResponse>() {
            @Override
            public OwnerRemovalEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERREMOVAL_EVENT, log);
                OwnerRemovalEventResponse typedResponse = new OwnerRemovalEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<OwnerRemovalEventResponse> ownerRemovalEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERREMOVAL_EVENT));
        return ownerRemovalEventObservable(filter);
    }

    public List<RequirementChangeEventResponse> getRequirementChangeEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REQUIREMENTCHANGE_EVENT, transactionReceipt);
        ArrayList<RequirementChangeEventResponse> responses = new ArrayList<RequirementChangeEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RequirementChangeEventResponse typedResponse = new RequirementChangeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<RequirementChangeEventResponse> requirementChangeEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, RequirementChangeEventResponse>() {
            @Override
            public RequirementChangeEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(REQUIREMENTCHANGE_EVENT, log);
                RequirementChangeEventResponse typedResponse = new RequirementChangeEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<RequirementChangeEventResponse> requirementChangeEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REQUIREMENTCHANGE_EVENT));
        return requirementChangeEventObservable(filter);
    }

    public static RemoteCall<Multisig> deploy(Web3j web3j, Credentials credentials, String contractBinary, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Multisig.class, web3j, credentials, contractGasProvider, contractBinary, ABI, "");
    }

    @Deprecated
    public static RemoteCall<Multisig> deploy(Web3j web3j, Credentials credentials, String contractBinary, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Multisig.class, web3j, credentials, gasPrice, gasLimit, contractBinary, ABI, "");
    }

    public static RemoteCall<Multisig> deploy(Web3j web3j, TransactionManager transactionManager, String contractBinary, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Multisig.class, web3j, transactionManager, contractGasProvider, contractBinary, ABI, "");
    }

    @Deprecated
    public static RemoteCall<Multisig> deploy(Web3j web3j, TransactionManager transactionManager, String contractBinary, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Multisig.class, web3j, transactionManager, gasPrice, gasLimit, contractBinary, ABI, "");
    }

    @Deprecated
    public static Multisig load(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Multisig(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Multisig load(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Multisig(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Multisig load(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Multisig(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Multisig load(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Multisig(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getDeployData(String contractBinary) {
        return getDeployData(contractBinary, ABI);
    }

    public static BigInteger getDeployGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String contractBinary) throws IOException {
        return getDeployGasLimit(web3j, estimateGasFrom, estimateGasTo, contractBinary, ABI);
    }

    public static class ConfirmationEventResponse {
        public Log log;

        public String param1;

        public BigInteger param2;
    }

    public static class RevocationEventResponse {
        public Log log;

        public String param1;

        public BigInteger param2;
    }

    public static class SubmissionEventResponse {
        public Log log;

        public BigInteger param1;
    }

    public static class ExecutionEventResponse {
        public Log log;

        public String param1;
    }

    public static class ExecutionFailureEventResponse {
        public Log log;

        public String param1;
    }

    public static class DepositEventResponse {
        public Log log;

        public String param1;

        public String param2;
    }

    public static class OwnerAdditionEventResponse {
        public Log log;

        public BigInteger param1;
    }

    public static class OwnerRemovalEventResponse {
        public Log log;

        public BigInteger param1;
    }

    public static class RequirementChangeEventResponse {
        public Log log;

        public BigInteger param1;
    }
}
