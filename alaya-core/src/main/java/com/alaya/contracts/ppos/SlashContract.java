package com.alaya.contracts.ppos;

import com.alaya.abi.solidity.datatypes.BytesType;
import com.alaya.abi.solidity.datatypes.Utf8String;
import com.alaya.abi.solidity.datatypes.generated.Uint32;
import com.alaya.abi.solidity.datatypes.generated.Uint64;
import com.alaya.contracts.ppos.abi.Function;
import com.alaya.contracts.ppos.dto.CallResponse;
import com.alaya.contracts.ppos.dto.TransactionResponse;
import com.alaya.contracts.ppos.dto.common.DuplicateSignType;
import com.alaya.contracts.ppos.dto.common.FunctionType;
import com.alaya.contracts.ppos.exception.EstimateGasException;
import com.alaya.contracts.ppos.exception.NoSupportFunctionType;
import com.alaya.crypto.Credentials;
import com.alaya.parameters.NetworkParameters;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.RemoteCall;
import com.alaya.protocol.core.methods.response.PlatonSendTransaction;
import com.alaya.tx.TransactionManager;
import com.alaya.tx.gas.GasProvider;
import com.alaya.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class SlashContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static SlashContract load(Web3j web3j, long chainId) {
        return new SlashContract( NetworkParameters.getPposContractAddressOfSlash(chainId), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static SlashContract load(Web3j web3j, TransactionManager transactionManager, long chainId) {
        return new SlashContract(NetworkParameters.getPposContractAddressOfSlash(chainId), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static SlashContract load(Web3j web3j, Credentials credentials, long chainId) {
        return new SlashContract(NetworkParameters.getPposContractAddressOfSlash(chainId), chainId, web3j, credentials);
    }

    private SlashContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private SlashContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
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
        Function function = createReportDoubleSignFunction(duplicateSignType, data);
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
        Function function = createReportDoubleSignFunction(duplicateSignType, data);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取举报双签的gasProvider
     *
     * @param data
     * @return
     */
    public GasProvider getReportDoubleSignGasProvider(DuplicateSignType duplicateSignType, String data) throws IOException, EstimateGasException, NoSupportFunctionType {
    	 Function function = createReportDoubleSignFunction(duplicateSignType, data);
    	 return getDefaultGasProvider(function);
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<PlatonSendTransaction> reportDoubleSignReturnTransaction(DuplicateSignType duplicateSignType, String data) {
        Function function = createReportDoubleSignFunction(duplicateSignType, data);
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
        Function function = createReportDoubleSignFunction(duplicateSignType, data);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createReportDoubleSignFunction(DuplicateSignType duplicateSignType, String data) {
        Function function = new Function(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(BigInteger.valueOf(duplicateSignType.getValue())), new Utf8String(data)));
        return function;
    }

    /**
     * 查询节点是否已被举报过多签
     *
     * @param doubleSignType 代表双签类型，1：prepare，2：viewChange
     * @param nodeId         举报的节点Id
     * @param blockNumber    多签的块高
     * @return
     */
    public RemoteCall<CallResponse<String>> checkDoubleSign(DuplicateSignType doubleSignType, String nodeId, BigInteger blockNumber) {
        Function function = new Function(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))
                        , new Uint64(blockNumber)));
        return executeRemoteCallObjectValueReturn(function, String.class);
    }

}
