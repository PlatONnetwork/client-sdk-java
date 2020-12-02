package com.platon.sdk.contracts.ppos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.platon.sdk.contracts.ppos.abi.Function;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ErrorCode;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.exception.EstimateGasException;
import com.platon.sdk.contracts.ppos.exception.NoSupportFunctionType;
import com.platon.sdk.contracts.ppos.utils.EncoderUtils;
import com.platon.sdk.contracts.ppos.utils.EstimateGasUtil;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.exceptions.MessageDecodingException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
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
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;


/**
 * 内置合约基类
 * @author chendai
 */
public abstract class BaseContract extends ManagedTransaction {

    private static final Logger log = LoggerFactory.getLogger(BaseContract.class);

    protected String contractAddress;
    protected TransactionReceipt transactionReceipt;

    protected BaseContract(String contractAddress,  Web3j web3j, TransactionManager transactionManager) {
        super(web3j, transactionManager);
        this.contractAddress = contractAddress;
    }

    protected BaseContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
        this(contractAddress, web3j, new RawTransactionManager(web3j, credentials, chainId));
    }

    protected BaseContract(String contractAddress, Web3j web3j) {
        this(contractAddress, web3j, new ReadonlyTransactionManager(web3j, contractAddress));
    }

    public String getContractAddress() {
        return contractAddress;
    }
    
    protected <T> RemoteCall<CallResponse<T>> executeRemoteCallObjectValueReturn(Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallObjectValueReturn(function, returnType));
    }
    
    protected <T> RemoteCall<CallResponse<List<T>>> executeRemoteCallListValueReturn(Function function, Class<T> returnType) {
        return new RemoteCall<>(() -> executeCallListValueReturn(function, returnType));
    }
   
    private <T> CallResponse<T> executeCallObjectValueReturn(Function function, Class<T> returnType) throws IOException {
    	PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                		transactionManager.getFromAddress(), contractAddress, EncoderUtils.functionEncoder(function)),
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
   
    private <T> CallResponse<List<T>> executeCallListValueReturn(Function function, Class<T> returnType) throws IOException {
    	PlatonCall ethCall = web3j.platonCall(
                Transaction.createEthCallTransaction(
                		transactionManager.getFromAddress(), contractAddress, EncoderUtils.functionEncoder(function)),
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

        if(callRet.getCode() == ErrorCode.OBJECT_NOT_FOUND){
            callResponse.setCode(ErrorCode.SUCCESS);
            callResponse.setData(Collections.emptyList());
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

    protected RemoteCall<PlatonSendTransaction> executeRemoteCallTransactionStep1(Function function, GasProvider gasProvider) {
        return new RemoteCall<>(() -> executeTransactionStep1(function, BigInteger.ZERO,gasProvider));
    }
    
    protected RemoteCall<PlatonSendTransaction> executeRemoteCallTransactionStep1(Function function) {
    	return new RemoteCall<>(() -> executeTransactionStep1(function, BigInteger.ZERO, getDefaultGasProvider(function)));
	}
    
    private RemoteCall<TransactionResponse> executeRemoteCallTransactionStep2(PlatonSendTransaction ethSendTransaction) {
        return new RemoteCall<>(() -> executeTransactionStep2(ethSendTransaction));
    }
    
    public RemoteCall<TransactionResponse> getTransactionResponse(PlatonSendTransaction ethSendTransaction) throws IOException, TransactionException {
    	return executeRemoteCallTransactionStep2(ethSendTransaction);
    }

    protected RemoteCall<TransactionResponse> executeRemoteCallTransaction(Function function) {
    	return new RemoteCall<>(() -> executeTransaction(function, BigInteger.ZERO, getDefaultGasProvider(function)));
	}

    protected RemoteCall<TransactionResponse> executeRemoteCallTransaction(Function function, GasProvider gasProvider) {
        return new RemoteCall<>(() -> executeTransaction(function, BigInteger.ZERO, gasProvider));
    }


    protected GasProvider getDefaultGasProvider(Function function) throws IOException, EstimateGasException, NoSupportFunctionType {
        /*if(EstimateGasUtil.isSupportLocal(function.getType())){
            return  getDefaultGasProviderLocal(function);
        } else {
            return  getDefaultGasProviderRemote(function);
        }*/
        return  getDefaultGasProviderRemote(function);
    }

    private GasProvider getDefaultGasProviderRemote(Function function) throws IOException, EstimateGasException, NoSupportFunctionType {
        //gasPrice必须首先获得，在estimateGas的时候，治理合约就需要gasPrice。
        //estimateGas的时候，交易的所有参数，除了gasLimit，应该和真正发送时的参数一样。
        BigInteger gasPrice = getDefaultGasPrice(function.getType());
        //必须填gasLimit，否则按区块最大gas来算，这样要求账户余额>=区块最大gas*gasPrice，就容易抛出余额不足的错误
        BigInteger gasLimit = EstimateGasUtil.getGasLimit(function);

        BigInteger nonce = null;

        //String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data
        Transaction transaction = Transaction.createFunctionCallTransaction(transactionManager.getFromAddress(), nonce, gasPrice, gasLimit, contractAddress, EncoderUtils.functionEncoder(function));

        PlatonEstimateGas platonEstimateGas = web3j.platonEstimateGas(transaction).send();
        String result = platonEstimateGas.getResult();
        if(platonEstimateGas.hasError()){
            if(platonEstimateGas.getError().getCode() == ErrorCode.PlatON_Precompiled_Contract_EXEC_FAILED) {
                log.error("estimate gas error, code:={}, message:={}", platonEstimateGas.getError().getCode(), platonEstimateGas.getError().getData());
                Response.Error error = JSON.parseObject(platonEstimateGas.getError().getData(), Response.Error.class);
                //vm执行出错，需要解析出业务错误，并抛出
                throw new EstimateGasException(error.getMessage());
            }else{
                throw new EstimateGasException(platonEstimateGas.getError().getMessage());
            }

        }else{
            gasLimit = Numeric.decodeQuantity(platonEstimateGas.getResult());
        }
        GasProvider gasProvider = new ContractGasProvider(gasPrice, gasLimit);
        return  gasProvider;
    }

    private GasProvider getDefaultGasProviderLocal(Function function) throws IOException, NoSupportFunctionType {
        BigInteger gasLimit = EstimateGasUtil.getGasLimit(function);
        BigInteger gasPrice = getDefaultGasPrice(function.getType());
        GasProvider gasProvider = new ContractGasProvider(gasPrice, gasLimit);
        return  gasProvider;
    }

    /**
     * 获得默认的gasPrice
     *
     * @param type
     * @return
     * @throws IOException
     */
    private BigInteger getDefaultGasPrice(int type) throws IOException {
        switch (type) {
            case FunctionType.SUBMIT_TEXT_FUNC_TYPE:
                return BigInteger.valueOf(1500000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIT_VERSION_FUNC_TYPE:
                return BigInteger.valueOf(2100000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIR_PARAM_FUNCTION_TYPE:
                return BigInteger.valueOf(2000000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIT_CANCEL_FUNC_TYPE:
                return BigInteger.valueOf(3000000).multiply(BigInteger.valueOf(1000000000));
            default:
                return web3j.platonGasPrice().send().getGasPrice();
        }
    }




    private TransactionResponse executeTransaction(Function function, BigInteger weiValue, GasProvider gasProvider)throws TransactionException, IOException {

    	TransactionReceipt receipt = send(contractAddress, EncoderUtils.functionEncoder(function), weiValue,
             gasProvider.getGasPrice(),
             gasProvider.getGasLimit());

    	return getResponseFromTransactionReceipt(receipt);
    }
    
    private PlatonSendTransaction executeTransactionStep1(Function function, BigInteger weiValue, GasProvider gasProvider) throws TransactionException, IOException {

        PlatonSendTransaction sendTransaction =  sendPlatonRawTransaction(contractAddress,  EncoderUtils.functionEncoder(function), weiValue,
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
