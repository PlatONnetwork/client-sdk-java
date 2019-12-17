package com.platon.sdk.contracts.ppos;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.crypto.Credentials;
import org.web3j.exceptions.MessageDecodingException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.PlatonCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.sdk.contracts.ppos.abi.PlatOnFunction;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ErrorCode;


/**
 * 内置合约基类
 * @author chendai
 */
public abstract class BaseContract extends ManagedTransaction {

    protected String contractAddress;
    protected TransactionReceipt transactionReceipt;

    protected BaseContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
        this.contractAddress = ensResolver.resolve(contractAddress);
    }

    protected BaseContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        this(contractAddress, web3j, new RawTransactionManager(web3j, credentials, Long.valueOf(chainId)));
    }

    protected BaseContract(String contractAddress, Web3j web3j) {
        this(contractAddress, web3j, new ReadonlyTransactionManager(web3j, contractAddress));
    }

    public String getContractAddress() {
        return contractAddress;
    }
    
    protected <T> RemoteCall<CallResponse<T>> executeRemoteCallObjectValueReturn(PlatOnFunction function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallObjectValueReturn(function, returnType));
    }
    
    protected <T> RemoteCall<CallResponse<List<T>>> executeRemoteCallListValueReturn(PlatOnFunction function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallListValueReturn(function, returnType));
    }
   
    private <T> CallResponse<T> executeCallObjectValueReturn(PlatOnFunction function, Class<T> returnType) throws IOException {
    	PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                		transactionManager.getFromAddress(), contractAddress, function.getEncodeData()),
                DefaultBlockParameterName.LATEST)
                .send();
    	
    	String result = Numeric.cleanHexPrefix(ethCall.getValue());
    	if(result==null || "".equals(result)){
    		  throw new ContractCallException("Empty value (0x) returned from contract");
    	}

    	CallRet callRet = JSONUtil.parseObject(new String(Hex.decode(result)), CallRet.class);
        if (callRet == null) {
        	throw new ContractCallException("Unable to convert response: " + result);
        }
        
        CallResponse<T> callResponse = new CallResponse<T>();
        if (callRet.isStatusOk()) {
        	callResponse.setCode(callRet.getCode());
        	if(BigInteger.class.isAssignableFrom(returnType) ) {
        		callResponse.setData((T)numberDecoder(callRet.getRet()));
        	}else {
        		callResponse.setData(JSONUtil.parseObject(JSONUtil.toJSONString(callRet.getRet()), returnType));
			}
        } else {
        	callResponse.setCode(callRet.getCode());
        	callResponse.setErrMsg(callRet.getRet().toString());
        }
        return callResponse;
    }
    
    private BigInteger numberDecoder(Object number) {
    	if(number instanceof String) {
    		String numberStr = (String)number;
    		return Numeric.decodeQuantity(numberStr);
    	} else if(number instanceof Number ){
    		Number number2 = (Number)number;
    		return BigInteger.valueOf(number2.longValue());
		} else {
			throw new MessageDecodingException("Can not decode number value = " + number);
		}
    }
   
    private <T> CallResponse<List<T>> executeCallListValueReturn(PlatOnFunction function, Class<T> returnType) throws IOException {
    	PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                		transactionManager.getFromAddress(), contractAddress, function.getEncodeData()),
                DefaultBlockParameterName.LATEST)
                .send();
    	
    	String result = Numeric.cleanHexPrefix(ethCall.getValue());
    	if(result==null || "".equals(result)){
    		  throw new ContractCallException("Empty value (0x) returned from contract");
    	}
    	
    	CallRet callRet = JSONUtil.parseObject(new String(Hex.decode(result)), CallRet.class);
        if (callRet == null) {
        	throw new ContractCallException("Unable to convert response: " + result);
        }
        
        CallResponse<List<T>> callResponse = new CallResponse<List<T>>();
        if (callRet.isStatusOk()) {
        	callResponse.setCode(callRet.getCode());
        	callResponse.setData(JSONUtil.parseArray(JSONUtil.toJSONString(callRet.getRet()), returnType));
        } else {
        	callResponse.setCode(callRet.getCode());
        	callResponse.setErrMsg(callRet.getRet().toString());
        }
        return callResponse;
    }
    
    public static class CallRet{
    	@JSONField(name = "Code")
    	private int code;
    	@JSONField(name = "Ret")
    	private Object ret;
    	
        public boolean isStatusOk() {
            return code == ErrorCode.SUCCESS;
        }
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public Object getRet() {
			return ret;
		}
		public void setRet(Object ret) {
			this.ret = ret;
		}
		
		@Override
		public String toString() {
			return "CallRet [code=" + code + ", ret=" + ret + "]";
		}    	
    }
    
    
    protected RemoteCall<PlatonSendTransaction> executeRemoteCallTransactionStep1(PlatOnFunction function) {
    	return new RemoteCall<>(() -> executeTransactionStep1(function, BigInteger.ZERO));
	}
    
    private RemoteCall<TransactionResponse> executeRemoteCallTransactionStep2(PlatonSendTransaction ethSendTransaction) {
        return new RemoteCall<>(() -> executeTransactionStep2(ethSendTransaction));
    }
    
    public RemoteCall<TransactionResponse> getTransactionResponse(PlatonSendTransaction ethSendTransaction) throws IOException, TransactionException {
    	return executeRemoteCallTransactionStep2(ethSendTransaction);
    }
   
    protected RemoteCall<TransactionResponse> executeRemoteCallTransaction(PlatOnFunction function) {
    	return new RemoteCall<>(() -> executeTransaction(function, BigInteger.ZERO));
	}
    
    private TransactionResponse executeTransaction(PlatOnFunction function, BigInteger weiValue)throws TransactionException, IOException {
    	GasProvider gasProvider = function.getGasProvider();

    	TransactionReceipt receipt = send(contractAddress, function.getEncodeData(), weiValue,
             gasProvider.getGasPrice(),
             gasProvider.getGasLimit());

    	return getResponseFromTransactionReceipt(receipt);
    }
    
    private PlatonSendTransaction executeTransactionStep1( PlatOnFunction function, BigInteger weiValue) throws TransactionException, IOException {

        GasProvider gasProvider = function.getGasProvider();

        PlatonSendTransaction sendTransaction =  sendPlatonRawTransaction(contractAddress, function.getEncodeData(), weiValue,
                gasProvider.getGasPrice(),
                gasProvider.getGasLimit());
        
        return sendTransaction;
    }
    
    private TransactionResponse executeTransactionStep2(PlatonSendTransaction ethSendTransaction) throws IOException, TransactionException {

        TransactionReceipt receipt = getTransactionReceipt(ethSendTransaction);
        
        return getResponseFromTransactionReceipt(receipt);
    }
    
    private TransactionResponse getResponseFromTransactionReceipt(TransactionReceipt transactionReceipt) throws TransactionException {
		List<Log> logs = transactionReceipt.getLogs();
        if(logs==null||logs.isEmpty()){
        	 throw new TransactionException("TransactionReceipt logs is empty");
        }

		String logData = logs.get(0).getData();
		if(null == logData || "".equals(logData) ){
			throw new TransactionException("TransactionReceipt log data is empty");
		}

        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);
        
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setCode(statusCode);
        transactionResponse.setTransactionReceipt(transactionReceipt);
        
        return transactionResponse;
	}
}
