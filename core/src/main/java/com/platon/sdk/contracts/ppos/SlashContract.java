package com.platon.sdk.contracts.ppos;

import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import com.platon.sdk.contracts.ppos.abi.PlatOnFunction;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ContractAddress;
import com.platon.sdk.contracts.ppos.dto.common.DuplicateSignType;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;

public class SlashContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static SlashContract load(Web3j web3j) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static SlashContract load(Web3j web3j, TransactionManager transactionManager) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static SlashContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    private SlashContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private SlashContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }
    
    private SlashContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<TransactionResponse> reportDoubleSign(DuplicateSignType duplicateSignType, String data) {
        PlatOnFunction function = createReportDoubleSignFunction(duplicateSignType, data, null);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 举报双签
     *
     * @param data        证据的json值
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> reportDoubleSign(DuplicateSignType duplicateSignType, String data, GasProvider gasProvider) {
        PlatOnFunction function = createReportDoubleSignFunction(duplicateSignType, data, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取举报双签的gasProvider
     *
     * @param data
     * @return
     */
    public GasProvider getReportDoubleSignGasProvider(DuplicateSignType duplicateSignType, String data) {
    	 PlatOnFunction function = createReportDoubleSignFunction(duplicateSignType, data, null);
    	 return function.getGasProvider();
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<PlatonSendTransaction> reportDoubleSignReturnTransaction(DuplicateSignType duplicateSignType, String data) {
        PlatOnFunction function = createReportDoubleSignFunction(duplicateSignType, data, null);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 举报双签
     *
     * @param data        证据的json值
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> reportDoubleSignReturnTransaction(DuplicateSignType duplicateSignType, String data, GasProvider gasProvider) {
        PlatOnFunction function = createReportDoubleSignFunction(duplicateSignType, data, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createReportDoubleSignFunction(DuplicateSignType duplicateSignType, String data, GasProvider gasProvider) {          
        PlatOnFunction function = new PlatOnFunction(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(BigInteger.valueOf(duplicateSignType.getValue())), new Utf8String(data)), gasProvider);
        return function;
    }

    /**
     * 查询节点是否已被举报过多签
     *
     * @param doubleSignType 代表双签类型，1：prepare，2：viewChange
     * @param address        举报的节点地址
     * @param blockNumber    多签的块高
     * @return
     */
    public RemoteCall<CallResponse<String>> checkDoubleSign(DuplicateSignType doubleSignType, String address, BigInteger blockNumber) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(address))
                        , new Uint64(blockNumber)));
        return executeRemoteCallObjectValueReturn(function, String.class);
    }

}
