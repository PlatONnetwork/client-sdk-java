package org.web3j.platon.contracts;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
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
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;
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
public class TicketContract extends PlatOnContract {
	
	public static final String CONTRACT_ADDRESS = "0x1000000000000000000000000000000000000002";

    private static final String ABI = "[{\"name\":\"VoteTicket\",\"inputs\":[{\"name\":\"count\",\"type\":\"uint32\"},{\"name\":\"price\",\"type\":\"uint256\"},{\"name\":\"nodeId\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"false\",\"type\":\"function\"},{\"name\":\"GetCandidateTicketCount\",\"inputs\":[{\"name\":\"nodeIds\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"GetTicketCountByTxHash\",\"inputs\":[{\"name\":\"txHashs\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"GetCandidateEpoch\",\"inputs\":[{\"name\":\"nodeId\",\"type\":\"string\"}],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"GetPoolRemainder\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"GetTicketPrice\",\"inputs\":[],\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"constant\":\"true\",\"type\":\"function\"},{\"name\":\"VoteTicketEvent\",\"inputs\":[{\"type\":\"string\"}],\"type\":\"event\"}]";

    public static final String FUNC_VOTETICKET = "VoteTicket";

    public static final String FUNC_GETCANDIDATETICKETCOUNT = "GetCandidateTicketCount";

    public static final String FUNC_GETCANDIDATEEPOCH = "GetCandidateEpoch";

    public static final String FUNC_GETPOOLREMAINDER = "GetPoolRemainder";

    public static final String FUNC_GETTICKETPRICE = "GetTicketPrice";

    public static final String FUNC_GETTICKETCOUNTBYTXHASH = "GetTicketCountByTxHash";

    public static final Event VOTETICKETEVENT_EVENT = new Event("VoteTicketEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected TicketContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TicketContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TicketContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TicketContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> VoteTicket(BigInteger count, BigInteger price, String nodeId) {
        final Function function = new Function(
                FUNC_VOTETICKET, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint32(count),
                new org.web3j.abi.datatypes.generated.Uint256(price), 
                new Utf8String(nodeId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function,count.multiply(price));
    }

    public static String VoteTicketData(BigInteger count, BigInteger price, String nodeId) {
        final Function function = new Function(
                FUNC_VOTETICKET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint64(count),
                new org.web3j.abi.datatypes.generated.Uint256(price),
                new Utf8String(nodeId)),
                Collections.<TypeReference<?>>emptyList());
        return PlatOnUtil.invokeEncode(function,customTransactionType(function));
    }
    
    public List<String> VoteTicketIds(int tickets, String txHash) {
    	List<String> result = new ArrayList<>();
    	byte[] hashBytes;
    	byte[] ticketBytes;
    	byte[] hash;
    	for (int i = 0; i < tickets; i++) {
    		hashBytes = Numeric.hexStringToByteArray(txHash);
    		ticketBytes = String.valueOf(i).getBytes();
    		hash = new byte[hashBytes.length+ticketBytes.length];
    		System.arraycopy(hashBytes, 0, hash, 0, hashBytes.length);
    		System.arraycopy(ticketBytes, 0, hash, hashBytes.length, hash.length - hashBytes.length);
    		
    		Digest digest = new SHA3Digest(256);
    	    digest.update(hash, 0, hash.length);
    	    byte[] rsData = new byte[digest.getDigestSize()];
    	    digest.doFinal(rsData, 0);
    		
    	    result.add(Numeric.toHexString(rsData));
    		
		}
    	return result;
    }

    public static BigInteger VoteTicketGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, BigInteger count, BigInteger price, String nodeId) throws IOException {
        String ethEstimateGasData = VoteTicketData(count, price, nodeId);
        return PlatOnUtil.estimateGasLimit(web3j,estimateGasFrom,estimateGasTo,ethEstimateGasData);
    }


    public RemoteCall<String> GetCandidateTicketCount(String nodeIds) {
        final Function function = new Function(FUNC_GETCANDIDATETICKETCOUNT,
                Arrays.<Type>asList(new Utf8String(nodeIds)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetCandidateEpoch(String nodeId) {
        final Function function = new Function(FUNC_GETCANDIDATEEPOCH,
                Arrays.<Type>asList(new Utf8String(nodeId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetPoolRemainder() {
        final Function function = new Function(FUNC_GETPOOLREMAINDER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetTicketPrice() {
        final Function function = new Function(FUNC_GETTICKETPRICE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> GetTicketCountByTxHash(String txHashs) {
        final Function function = new Function(FUNC_GETTICKETCOUNTBYTXHASH,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(txHashs)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<VoteTicketEventEventResponse> getVoteTicketEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(VOTETICKETEVENT_EVENT, transactionReceipt);
        ArrayList<VoteTicketEventEventResponse> responses = new ArrayList<VoteTicketEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            VoteTicketEventEventResponse typedResponse = new VoteTicketEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VoteTicketEventEventResponse> voteTicketEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, VoteTicketEventEventResponse>() {
            @Override
            public VoteTicketEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(VOTETICKETEVENT_EVENT, log);
                VoteTicketEventEventResponse typedResponse = new VoteTicketEventEventResponse();
                typedResponse.log = log;
                typedResponse.param1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<VoteTicketEventEventResponse> voteTicketEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTETICKETEVENT_EVENT));
        return voteTicketEventEventObservable(filter);
    }

    @Deprecated
    public static TicketContract load(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TicketContract("", CONTRACT_ADDRESS, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TicketContract load(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TicketContract("", CONTRACT_ADDRESS, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TicketContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TicketContract("", CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static TicketContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TicketContract("", CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static String getDeployData(String contractBinary) {
        return PlatOnUtil.deployEncode(contractBinary, ABI);
    }

    public static BigInteger getDeployGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String contractBinary) throws IOException {
        return PlatOnUtil.estimateGasLimit(web3j, estimateGasFrom, estimateGasTo, getDeployData(contractBinary));
    }

    private static long customTransactionType(Function function) {
        switch (function.getName()){
            case "VoteTicket": return 1000;
        }
        return 1004;
    }

    protected long getTransactionType(Function function) {
        return customTransactionType(function);
    }

    public static class VoteTicketEventEventResponse {
        public Log log;

        public String param1;
    }
}
