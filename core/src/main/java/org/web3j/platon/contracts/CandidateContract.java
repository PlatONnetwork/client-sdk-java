package org.web3j.platon.contracts;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
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
import org.web3j.utils.PlatOnUtil;
import org.web3j.utils.TXTypeEnum;
import rx.Observable;
import rx.functions.Func1;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
public class CandidateContract extends PlatOnContract {
    
	public static final String CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000001";
	
	private static final String ABI = "[\t{\t\t\t\"name\":\"CandidateDeposit\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"owner\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"fee\",\t\t\t\t\t\"type\":\"uint64\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"host\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"port\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"extra\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"false\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"CandidateApplyWithdraw\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"withdraw\",\t\t\t\t\t\"type\":\"uint256\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"false\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"CandidateWithdraw\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"false\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"SetCandidateExtra\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t},\t\t\t\t{\t\t\t\t\t\"name\":\"extra\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"false\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"CandidateWithdrawInfos\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"true\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"CandidateDetails\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeId\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"true\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"GetBatchCandidateDetail\",\t\t\t\"inputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"nodeIds\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"true\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"CandidateList\",\t\t\t\"inputs\":[],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"true\",\t\t\t\"type\":\"function\"\t},\t{\t\t\t\"name\":\"VerifiersList\",\t\t\t\"inputs\":[],\t\t\t\"outputs\":[\t\t\t\t{\t\t\t\t\t\"name\":\"\",\t\t\t\t\t\"type\":\"string\"\t\t\t\t}\t\t\t],\t\t\t\"constant\":\"true\",\t\t\t\"type\":\"function\"\t},\t{\"name\":\"CandidateDepositEvent\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"},\t{\"name\":\"CandidateApplyWithdrawEvent\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"},\t{\"name\":\"CandidateWithdrawEvent\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"},\t{\"name\":\"SetCandidateExtraEvent\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"}]";

    public static final String FUNC_CANDIDATEDEPOSIT = "CandidateDeposit";

    public static final String FUNC_CANDIDATEAPPLYWITHDRAW = "CandidateApplyWithdraw";

    public static final String FUNC_CANDIDATEWITHDRAW = "CandidateWithdraw";

    public static final String FUNC_SETCANDIDATEEXTRA = "SetCandidateExtra";

    public static final String FUNC_GETCANDIDATEWITHDRAWINFOS = "GetCandidateWithdrawInfos";

    public static final String FUNC_GETCANDIDATEDETAILS = "GetCandidateDetails";

    public static final String FUNC_GETBATCHCANDIDATEDETAIL = "GetBatchCandidateDetail";

    public static final String FUNC_GETCANDIDATELIST = "GetCandidateList";

    public static final String FUNC_GETVERIFIERSLIST = "GetVerifiersList";

    public static final Event CANDIDATEDEPOSITEVENT_EVENT = new Event("CandidateDepositEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CANDIDATEAPPLYWITHDRAWEVENT_EVENT = new Event("CandidateApplyWithdrawEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event CANDIDATEWITHDRAWEVENT_EVENT = new Event("CandidateWithdrawEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event SETCANDIDATEEXTRAEVENT_EVENT = new Event("SetCandidateExtraEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected CandidateContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CandidateContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CandidateContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CandidateContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> CandidateDeposit(String nodeId, String owner, BigInteger fee, String host, String port, String extra, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CANDIDATEDEPOSIT, 
                Arrays.<Type>asList(new Utf8String(nodeId),
                new Utf8String(owner),
                new org.web3j.abi.datatypes.generated.Uint32(fee),
                new Utf8String(host),
                new Utf8String(port),
                new Utf8String(extra)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static String CandidateDepositData(String nodeId, String owner, BigInteger fee, String host, String port, String extra) {
        final Function function = new Function(
                FUNC_CANDIDATEDEPOSIT,
                Arrays.<Type>asList(new Utf8String(nodeId),
                new Utf8String(owner),
                new org.web3j.abi.datatypes.generated.Uint32(fee),
                new Utf8String(host),
                new Utf8String(port),
                new Utf8String(extra)),
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }

    public static BigInteger CandidateDepositGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String nodeId, String owner, BigInteger fee, String host, String port, String extra) throws IOException {
        String ethEstimateGasData = CandidateDepositData(nodeId, owner, fee, host, port, extra);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> CandidateApplyWithdraw(String nodeId, BigInteger withdraw) {
        final Function function = new Function(
                FUNC_CANDIDATEAPPLYWITHDRAW,
                Arrays.<Type>asList(new Utf8String(nodeId),
                new org.web3j.abi.datatypes.generated.Uint256(withdraw)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String CandidateApplyWithdrawData(String nodeId, BigInteger withdraw) {
        final Function function = new Function(
                FUNC_CANDIDATEAPPLYWITHDRAW,
                Arrays.<Type>asList(new Utf8String(nodeId),
                new org.web3j.abi.datatypes.generated.Uint256(withdraw)),
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }

    public static BigInteger CandidateApplyWithdrawGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String nodeId, BigInteger withdraw) throws IOException {
        String ethEstimateGasData = CandidateApplyWithdrawData(nodeId, withdraw);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> CandidateWithdraw(String nodeId) {
        final Function function = new Function(
                FUNC_CANDIDATEWITHDRAW,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String CandidateWithdrawData(String nodeId) {
        final Function function = new Function(
                FUNC_CANDIDATEWITHDRAW,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }

    public static BigInteger CandidateWithdrawGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String nodeId) throws IOException {
        String ethEstimateGasData = CandidateWithdrawData(nodeId);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<TransactionReceipt> SetCandidateExtra(String nodeId, String extra) {
        final Function function = new Function(
                FUNC_SETCANDIDATEEXTRA,
                Arrays.<Type>asList(new Utf8String(nodeId),
                new Utf8String(extra)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static String SetCandidateExtraData(String nodeId, String extra) {
        final Function function = new Function(
                FUNC_SETCANDIDATEEXTRA,
                Arrays.<Type>asList(new Utf8String(nodeId),
                new Utf8String(extra)),
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }

    public static BigInteger SetCandidateExtraGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String nodeId, String extra) throws IOException {
        String ethEstimateGasData = SetCandidateExtraData(nodeId, extra);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }

    public RemoteCall<String> CandidateWithdrawInfos(String nodeId) {
        final Function function = new Function(FUNC_GETCANDIDATEWITHDRAWINFOS,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> CandidateDetails(String nodeId,BigInteger number) {
        final Function function = new Function(FUNC_GETCANDIDATEDETAILS,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class,number);
    }

    public RemoteCall<String> CandidateDetails(String nodeId) {
        final Function function = new Function(FUNC_GETCANDIDATEDETAILS,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetBatchCandidateDetail(String nodeIds) {
        final Function function = new Function(FUNC_GETBATCHCANDIDATEDETAIL,
                Arrays.<Type>asList(new Utf8String(nodeIds)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> CandidateList() {
        final Function function = new Function(FUNC_GETCANDIDATELIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    
    public RemoteCall<String> CandidateList( BigInteger number) {
        final Function function = new Function(FUNC_GETCANDIDATELIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class, number);
    }

    public RemoteCall<String> VerifiersList() {
        final Function function = new Function(FUNC_GETVERIFIERSLIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    
    public RemoteCall<String> VerifiersList(BigInteger number) {
        final Function function = new Function(FUNC_GETVERIFIERSLIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class, number);
    }

    public List<CandidateDepositEventEventResponse> getCandidateDepositEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CANDIDATEDEPOSITEVENT_EVENT, transactionReceipt);
        ArrayList<CandidateDepositEventEventResponse> responses = new ArrayList<CandidateDepositEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CandidateDepositEventEventResponse typedResponse = new CandidateDepositEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CandidateDepositEventEventResponse> candidateDepositEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CandidateDepositEventEventResponse>() {
            @Override
            public CandidateDepositEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CANDIDATEDEPOSITEVENT_EVENT, log);
                CandidateDepositEventEventResponse typedResponse = new CandidateDepositEventEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CandidateDepositEventEventResponse> candidateDepositEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANDIDATEDEPOSITEVENT_EVENT));
        return candidateDepositEventEventObservable(filter);
    }

    public List<CandidateApplyWithdrawEventEventResponse> getCandidateApplyWithdrawEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CANDIDATEAPPLYWITHDRAWEVENT_EVENT, transactionReceipt);
        ArrayList<CandidateApplyWithdrawEventEventResponse> responses = new ArrayList<CandidateApplyWithdrawEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CandidateApplyWithdrawEventEventResponse typedResponse = new CandidateApplyWithdrawEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CandidateApplyWithdrawEventEventResponse> candidateApplyWithdrawEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CandidateApplyWithdrawEventEventResponse>() {
            @Override
            public CandidateApplyWithdrawEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CANDIDATEAPPLYWITHDRAWEVENT_EVENT, log);
                CandidateApplyWithdrawEventEventResponse typedResponse = new CandidateApplyWithdrawEventEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CandidateApplyWithdrawEventEventResponse> candidateApplyWithdrawEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANDIDATEAPPLYWITHDRAWEVENT_EVENT));
        return candidateApplyWithdrawEventEventObservable(filter);
    }

    public List<CandidateWithdrawEventEventResponse> getCandidateWithdrawEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(CANDIDATEWITHDRAWEVENT_EVENT, transactionReceipt);
        ArrayList<CandidateWithdrawEventEventResponse> responses = new ArrayList<CandidateWithdrawEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            CandidateWithdrawEventEventResponse typedResponse = new CandidateWithdrawEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<CandidateWithdrawEventEventResponse> candidateWithdrawEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, CandidateWithdrawEventEventResponse>() {
            @Override
            public CandidateWithdrawEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(CANDIDATEWITHDRAWEVENT_EVENT, log);
                CandidateWithdrawEventEventResponse typedResponse = new CandidateWithdrawEventEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<CandidateWithdrawEventEventResponse> candidateWithdrawEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CANDIDATEWITHDRAWEVENT_EVENT));
        return candidateWithdrawEventEventObservable(filter);
    }

    public List<SetCandidateExtraEventEventResponse> getSetCandidateExtraEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SETCANDIDATEEXTRAEVENT_EVENT, transactionReceipt);
        ArrayList<SetCandidateExtraEventEventResponse> responses = new ArrayList<SetCandidateExtraEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SetCandidateExtraEventEventResponse typedResponse = new SetCandidateExtraEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetCandidateExtraEventEventResponse> setCandidateExtraEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetCandidateExtraEventEventResponse>() {
            @Override
            public SetCandidateExtraEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SETCANDIDATEEXTRAEVENT_EVENT, log);
                SetCandidateExtraEventEventResponse typedResponse = new SetCandidateExtraEventEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SetCandidateExtraEventEventResponse> setCandidateExtraEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETCANDIDATEEXTRAEVENT_EVENT));
        return setCandidateExtraEventEventObservable(filter);
    }

    @Deprecated
    public static CandidateContract load(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CandidateContract("", CONTRACT_ADDRESS, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CandidateContract load(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CandidateContract("", CONTRACT_ADDRESS, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CandidateContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CandidateContract("", CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static CandidateContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CandidateContract("", CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static String getDeployData(String contractBinary) {
        return PlatOnUtil.deployEncode(contractBinary, ABI);
    }

    public static BigInteger getDeployGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String contractBinary) throws IOException {
        return PlatOnUtil.estimateGasLimit(web3j, estimateGasFrom, estimateGasTo, getDeployData(contractBinary));
    }

    private static long customTransactionType(Function function) {
        switch (function.getName()){
            case "CandidateDeposit": return 1001;
            case "CandidateApplyWithdraw": return 1002;
            case "CandidateWithdraw": return 1003;
        }
        return 1004;
    }

    protected long getTransactionType(Function function) {
        return customTransactionType(function);
    }

    public static class CandidateDepositEventEventResponse {
        public Log log;

        public String param1;
    }

    public static class CandidateApplyWithdrawEventEventResponse {
        public Log log;

        public String param1;
    }

    public static class CandidateWithdrawEventEventResponse {
        public Log log;

        public String param1;
    }

    public static class SetCandidateExtraEventEventResponse {
        public Log log;

        public String param1;
    }
}
