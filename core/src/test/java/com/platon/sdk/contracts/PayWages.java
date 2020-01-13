package com.platon.sdk.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.PlatonFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>
 * Auto generated code.
 * <p>
 * <strong>Do not modify!</strong>
 * <p>
 * Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>, or the
 * org.web3j.codegen.SolidityFunctionWrapperGenerator in the <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to
 * update.
 *
 * <p>
 * Generated with web3j none.
 */
@SuppressWarnings("rawtypes")
public class PayWages extends Contract {
	private static final String BINARY = "60806040526040516112b03803806112b08339818101604052604081101561002657600080fd5b81019080805190602001909291908051906020019092919050505033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555034600381905550816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555080600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050506111958061011b6000396000f3fe6080604052600436106100865760003560e01c8063c407687611610059578063c4076876146102a4578063cd581cb2146102ff578063e1e6b89814610356578063e590f12c14610360578063f53d0a8e1461047757610086565b806312b58349146100e657806332648e09146101115780636f9fb98a14610222578063bf8764d51461024d575b346003600082825401925050819055503073ffffffffffffffffffffffffffffffffffffffff167f69ca02dd4edd7bf0a4abb9ed3b7af3f14778db5d61921c7dc7cd545266326de2346040518082815260200191505060405180910390a2005b3480156100f257600080fd5b506100fb6104ce565b6040518082815260200191505060405180910390f35b34801561011d57600080fd5b506101606004803603602081101561013457600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506104d8565b604051808581526020018460ff1660ff168152602001806020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001828103825284818151815260200191508051906020019080838360005b838110156101e45780820151818401526020810190506101c9565b50505050905090810190601f1680156102115780820380516001836020036101000a031916815260200191505b509550505050505060405180910390f35b34801561022e57600080fd5b506102376106c2565b6040518082815260200191505060405180910390f35b34801561025957600080fd5b506102626106e1565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156102b057600080fd5b506102fd600480360360408110156102c757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190505050610706565b005b34801561030b57600080fd5b50610314610a1e565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b61035e610a44565b005b34801561036c57600080fd5b5061045d6004803603608081101561038357600080fd5b8101908080359060200190929190803560ff169060200190929190803590602001906401000000008111156103b757600080fd5b8201836020820111156103c957600080fd5b803590602001918460018302840111640100000000831117156103eb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610aa4565b604051808215151515815260200191505060405180910390f35b34801561048357600080fd5b5061048c610f00565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000600354905090565b60008060606000600460008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060000154600460008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff16600460008873ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600201600460008973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060030160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16818054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106ac5780601f10610681576101008083540402835291602001916106ac565b820191906000526020600020905b81548152906001019060200180831161068f57829003601f168201915b5050505050915093509350935093509193509193565b60003073ffffffffffffffffffffffffffffffffffffffff1631905090565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146107c9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4f6e6c792066696e616e6365722063616e207061792e0000000000000000000081525060200191505060405180910390fd5b6000811015610823576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001806111166022913960400191505060405180910390fd5b803073ffffffffffffffffffffffffffffffffffffffff1631101561084757600080fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff1614156108cd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260228152602001806110f46022913960400191505060405180910390fd5b6001808111156108d957fe5b60ff16600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff1660ff1614610985576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260298152602001806111386029913960400191505060405180910390fd5b8173ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051600060405180830381858888f193505050501580156109cb573d6000803e3d6000fd5b508173ffffffffffffffffffffffffffffffffffffffff167f69ca02dd4edd7bf0a4abb9ed3b7af3f14778db5d61921c7dc7cd545266326de2826040518082815260200191505060405180910390a25050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b346003600082825401925050819055503373ffffffffffffffffffffffffffffffffffffffff167f69ca02dd4edd7bf0a4abb9ed3b7af3f14778db5d61921c7dc7cd545266326de2346040518082815260200191505060405180910390a2565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610b68576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f6e6c7920706572736f6e6e656c2063616e2061646420456d706c6f7965652e81525060200191505060405180910390fd5b600073ffffffffffffffffffffffffffffffffffffffff168273ffffffffffffffffffffffffffffffffffffffff161415610c0b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f546865206163636f756e742063616e206e6f7420626520656d7074792e00000081525060200191505060405180910390fd5b600180811115610c1757fe5b60ff16600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900460ff1660ff161415610c7857600080fd5b610c80610f26565b8460066000018190555083600660010160006101000a81548160ff021916908360ff1602179055508260066002019080519060200190610cc1929190610f7f565b5081600660030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506006600460008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082015481600001556001820160009054906101000a900460ff168160010160006101000a81548160ff021916908360ff16021790555060028201816002019080546001816001161561010002031660029004610da6929190610fff565b506003820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff168160030160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555090505060058290806001815401808255809150509060018203906000526020600020016000909192909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550507ff2ad717e1fea86183e49376af8ee2c432ffd3db033a7dc494029cf9f1ca189d460016002811115610ea557fe5b83604051808360ff1660ff1681526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390a160019050949350505050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600660000160009055600660010160006101000a81549060ff021916905560066002016000610f559190611086565b600660030160006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610fc057805160ff1916838001178555610fee565b82800160010185558215610fee579182015b82811115610fed578251825591602001919060010190610fd2565b5b509050610ffb91906110ce565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106110385780548555611075565b8280016001018555821561107557600052602060002091601f016020900482015b82811115611074578254825591600101919060010190611059565b5b50905061108291906110ce565b5090565b50805460018160011615610100020316600290046000825580601f106110ac57506110cb565b601f0160209004906000526020600020908101906110ca91906110ce565b5b50565b6110f091905b808211156110ec5760008160009055506001016110d4565b5090565b9056fe54686520706179656520616464726573732063616e6e6f7420626520656d7074792e54686520616d6f756e74206d7573742062652067726561746572207468616e20302e456d706c6f7965657320617265206e6f6e6578697374656e74206f7220696e6566666563746976652ea265627a7a72315820c703064dfd75608e27c8b0fc4f9a0464ab2c9b0f1c2fce462a83fdd4ba42b4aa64736f6c634300050d0032";

	public static final String FUNC_ADDEMPLOYEE = "addEmployee";

	public static final String FUNC_ADMINISTRATOR = "administrator";

	public static final String FUNC_FINANCER = "financer";

	public static final String FUNC_GETCONTRACTBALANCE = "getContractBalance";

	public static final String FUNC_GETEMPLOYEE = "getEmployee";

	public static final String FUNC_GETTOTALBALANCE = "getTotalBalance";

	public static final String FUNC_PAY = "pay";

	public static final String FUNC_PERSONNEL = "personnel";

	public static final String FUNC_RECEIPT = "receipt";

	public static final Event ADDUSER_EVENT = new Event("AddUser", Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {
	}, new TypeReference<Address>() {
	}));;

	public static final Event TRANSFER_EVENT = new Event("Transfer", Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {
	}, new TypeReference<Uint256>() {
	}));;

	@Deprecated
	protected PayWages(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	protected PayWages(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
	}

	@Deprecated
	protected PayWages(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
		super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	protected PayWages(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
		super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static RemoteCall<PayWages> deploy(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, BigInteger initialWeiValue,
			String _personnel, String _financer) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_personnel), new org.web3j.abi.datatypes.Address(_financer)));
		return deployRemoteCall(PayWages.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
	}

	public static RemoteCall<PayWages> deploy(Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider,
			BigInteger initialWeiValue, String _personnel, String _financer) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_personnel), new org.web3j.abi.datatypes.Address(_financer)));
		return deployRemoteCall(PayWages.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
	}

	@Deprecated
	public static RemoteCall<PayWages> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit,
			BigInteger initialWeiValue, String _personnel, String _financer) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_personnel), new org.web3j.abi.datatypes.Address(_financer)));
		return deployRemoteCall(PayWages.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
	}

	@Deprecated
	public static RemoteCall<PayWages> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit,
			BigInteger initialWeiValue, String _personnel, String _financer) {
		String encodedConstructor = FunctionEncoder.encodeConstructor(
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_personnel), new org.web3j.abi.datatypes.Address(_financer)));
		return deployRemoteCall(PayWages.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
	}

	public List<AddUserEventResponse> getAddUserEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDUSER_EVENT, transactionReceipt);
		ArrayList<AddUserEventResponse> responses = new ArrayList<AddUserEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			AddUserEventResponse typedResponse = new AddUserEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse._code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			typedResponse._account = (String) eventValues.getNonIndexedValues().get(1).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public Observable<AddUserEventResponse> addUserEventObservable(PlatonFilter filter) {
		return web3j.platonLogObservable(filter).map(new Func1<Log, AddUserEventResponse>() {
			@Override
			public AddUserEventResponse call(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDUSER_EVENT, log);
				AddUserEventResponse typedResponse = new AddUserEventResponse();
				typedResponse.log = log;
				typedResponse._code = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
				typedResponse._account = (String) eventValues.getNonIndexedValues().get(1).getValue();
				return typedResponse;
			}
		});
	}

	public Observable<AddUserEventResponse> addUserEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(ADDUSER_EVENT));
		return addUserEventObservable(filter);
	}

	public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
		List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
		ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
		for (Contract.EventValuesWithLog eventValues : valueList) {
			TransferEventResponse typedResponse = new TransferEventResponse();
			typedResponse.log = eventValues.getLog();
			typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
			typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
			responses.add(typedResponse);
		}
		return responses;
	}

	public Observable<TransferEventResponse> transferEventObservable(PlatonFilter filter) {
		return web3j.platonLogObservable(filter).map(new Func1<Log, TransferEventResponse>() {
			@Override
			public TransferEventResponse call(Log log) {
				Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
				TransferEventResponse typedResponse = new TransferEventResponse();
				typedResponse.log = log;
				typedResponse._to = (String) eventValues.getIndexedValues().get(0).getValue();
				typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
				return typedResponse;
			}
		});
	}

	public Observable<TransferEventResponse> transferEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
		PlatonFilter filter = new PlatonFilter(startBlock, endBlock, getContractAddress());
		filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
		return transferEventObservable(filter);
	}

	public RemoteCall<TransactionReceipt> addEmployee(BigInteger _id, BigInteger _status, String _name, String _account) {
		final Function function = new Function(FUNC_ADDEMPLOYEE,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_id), new org.web3j.abi.datatypes.generated.Uint8(_status),
						new org.web3j.abi.datatypes.Utf8String(_name), new org.web3j.abi.datatypes.Address(_account)),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteCall<String> administrator() {
		final Function function = new Function(FUNC_ADMINISTRATOR, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
				}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteCall<String> financer() {
		final Function function = new Function(FUNC_FINANCER, Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
		}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteCall<BigInteger> getContractBalance() {
		final Function function = new Function(FUNC_GETCONTRACTBALANCE, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<Tuple4<BigInteger, BigInteger, String, String>> getEmployee(String _account) {
		final Function function = new Function(FUNC_GETEMPLOYEE, Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_account)),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}, new TypeReference<Uint8>() {
				}, new TypeReference<Utf8String>() {
				}, new TypeReference<Address>() {
				}));
		return new RemoteCall<Tuple4<BigInteger, BigInteger, String, String>>(new Callable<Tuple4<BigInteger, BigInteger, String, String>>() {
			@Override
			public Tuple4<BigInteger, BigInteger, String, String> call() throws Exception {
				List<Type> results = executeCallMultipleValueReturn(function);
				return new Tuple4<BigInteger, BigInteger, String, String>((BigInteger) results.get(0).getValue(),
						(BigInteger) results.get(1).getValue(), (String) results.get(2).getValue(), (String) results.get(3).getValue());
			}
		});
	}

	public RemoteCall<BigInteger> getTotalBalance() {
		final Function function = new Function(FUNC_GETTOTALBALANCE, Arrays.<Type>asList(),
				Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
				}));
		return executeRemoteCallSingleValueReturn(function, BigInteger.class);
	}

	public RemoteCall<TransactionReceipt> pay(String _to, BigInteger _amount) {
		final Function function = new Function(FUNC_PAY,
				Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_to), new org.web3j.abi.datatypes.generated.Uint256(_amount)),
				Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function);
	}

	public RemoteCall<String> personnel() {
		final Function function = new Function(FUNC_PERSONNEL, Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {
		}));
		return executeRemoteCallSingleValueReturn(function, String.class);
	}

	public RemoteCall<TransactionReceipt> receipt(BigInteger weiValue) {
		final Function function = new Function(FUNC_RECEIPT, Arrays.<Type>asList(), Collections.<TypeReference<?>>emptyList());
		return executeRemoteCallTransaction(function, weiValue);
	}

	@Deprecated
	public static PayWages load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
		return new PayWages(contractAddress, web3j, credentials, gasPrice, gasLimit);
	}

	@Deprecated
	public static PayWages load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice,
			BigInteger gasLimit) {
		return new PayWages(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
	}

	public static PayWages load(String contractAddress, Web3j web3j, Credentials credentials, GasProvider contractGasProvider) {
		return new PayWages(contractAddress, web3j, credentials, contractGasProvider);
	}

	public static PayWages load(String contractAddress, Web3j web3j, TransactionManager transactionManager, GasProvider contractGasProvider) {
		return new PayWages(contractAddress, web3j, transactionManager, contractGasProvider);
	}

	public static class AddUserEventResponse {
		public Log log;

		public BigInteger _code;

		public String _account;
	}

	public static class TransferEventResponse {
		public Log log;

		public String _to;

		public BigInteger _value;
	}
}
