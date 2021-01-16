package com.platon.contracts.evm;

import com.platon.abi.solidity.EventEncoder;
import com.platon.abi.solidity.TypeReference;
import com.platon.abi.solidity.datatypes.*;
import com.platon.abi.solidity.datatypes.generated.Uint256;
import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.tx.Contract;
import com.platon.tx.TransactionManager;
import com.platon.tx.gas.GasProvider;
import rx.Observable;
import rx.functions.Func1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://github.com/PlatONnetwork/client-sdk-java/releases">platon-web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 0.13.0.5.
 */
public class SolEvent extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506105ec806100206000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80631003e2d21461005c57806317d7de7c1461008a578063336eed241461010d57806338cc4831146101e85780636537214714610232575b600080fd5b6100886004803603602081101561007257600080fd5b8101908080359060200190929190505050610250565b005b6100926102cf565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100d25780820151818401526020810190506100b7565b50505050905090810190601f1680156100ff5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101e66004803603604081101561012357600080fd5b810190808035906020019064010000000081111561014057600080fd5b82018360208201111561015257600080fd5b8035906020019184600183028401116401000000008311171561017457600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610371565b005b6101f0610504565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61023a61050c565b6040518082815260200191505060405180910390f35b8060008082825401925050819055507f97c35397cb6acebd9df368c206404479fc4c80dd6034d1b28943aaf58263187033600054604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390a150565b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103675780601f1061033c57610100808354040283529160200191610367565b820191906000526020600020905b81548152906001019060200180831161034a57829003601f168201915b5050505050905090565b8160019080519060200190610387929190610512565b507f9f78bfd9c32411e18dbe173525d2f463cb010b7daaa9f7dc3ba22f67f4a64dee600180836040518080602001806020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200183810383528681815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561046c5780601f106104415761010080835404028352916020019161046c565b820191906000526020600020905b81548152906001019060200180831161044f57829003601f168201915b50508381038252858181546001816001161561010002031660029004815260200191508054600181600116156101000203166002900480156104ef5780601f106104c4576101008083540402835291602001916104ef565b820191906000526020600020905b8154815290600101906020018083116104d257829003601f168201915b50509550505050505060405180910390a15050565b600033905090565b60005481565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061055357805160ff1916838001178555610581565b82800160010185558215610581579182015b82811115610580578251825591602001919060010190610565565b5b50905061058e9190610592565b5090565b6105b491905b808211156105b0576000816000905550600101610598565b5090565b9056fea265627a7a72315820432bd06394b3029dab718a1b64a33f351fee8beee384ff763ca8f4522ecdedb564736f6c63430005110032";

    public static final String FUNC_ADD = "add";

    public static final String FUNC_GETADDRESS = "getAddress";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_RESULT = "result";

    public static final String FUNC_SETNAMEANDEMITADDRESS = "setNameAndEmitAddress";

    public static final Event FUNDTRANSFER_EVENT = new Event("FundTransfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event SETNAMEEVENT_EVENT = new Event("SetNameEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
    ;

    protected SolEvent(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected SolEvent(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<FundTransferEventResponse> getFundTransferEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(FUNDTRANSFER_EVENT, transactionReceipt);
        ArrayList<FundTransferEventResponse> responses = new ArrayList<FundTransferEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            FundTransferEventResponse typedResponse = new FundTransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<FundTransferEventResponse> fundTransferEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, FundTransferEventResponse>() {
            @Override
            public FundTransferEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(FUNDTRANSFER_EVENT, log);
                FundTransferEventResponse typedResponse = new FundTransferEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<FundTransferEventResponse> fundTransferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FUNDTRANSFER_EVENT));
        return fundTransferEventObservable(filter);
    }

    public List<SetNameEventEventResponse> getSetNameEventEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(SETNAMEEVENT_EVENT, transactionReceipt);
        ArrayList<SetNameEventEventResponse> responses = new ArrayList<SetNameEventEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            SetNameEventEventResponse typedResponse = new SetNameEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.name1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.name2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.addr = (String) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetNameEventEventResponse> setNameEventEventObservable(PlatonFilter filter) {
        return web3j.platonLogObservable(filter).map(new Func1<Log, SetNameEventEventResponse>() {
            @Override
            public SetNameEventEventResponse call(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(SETNAMEEVENT_EVENT, log);
                SetNameEventEventResponse typedResponse = new SetNameEventEventResponse();
                typedResponse.log = log;
                typedResponse.name1 = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.name2 = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.addr = (String) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<SetNameEventEventResponse> setNameEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(SETNAMEEVENT_EVENT));
        return setNameEventEventObservable(filter);
    }

    public RemoteCall<TransactionReceipt> add(BigInteger amount) {
        final Function function = new Function(
                FUNC_ADD, 
                Arrays.<Type>asList(new Uint256(amount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getAddress() {
        final Function function = new Function(FUNC_GETADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getName() {
        final Function function = new Function(FUNC_GETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> result() {
        final Function function = new Function(FUNC_RESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> setNameAndEmitAddress(String _name, String _address) {
        final Function function = new Function(
                FUNC_SETNAMEANDEMITADDRESS, 
                Arrays.<Type>asList(new Utf8String(_name),
                new Address(_address)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SolEvent> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return deployRemoteCall(SolEvent.class, web3j, credentials, contractGasProvider, BINARY,  "");
    }

    public static RemoteCall<SolEvent> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return deployRemoteCall(SolEvent.class, web3j, transactionManager, contractGasProvider, BINARY,  "");
    }

    public static SolEvent load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
        return new SolEvent(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SolEvent load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
        return new SolEvent(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class FundTransferEventResponse {
        public Log log;

        public String sender;

        public BigInteger result;
    }

    public static class SetNameEventEventResponse {
        public Log log;

        public String name1;

        public String name2;

        public String addr;
    }
}
