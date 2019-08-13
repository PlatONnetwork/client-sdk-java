package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.DoubleSignType;
import org.web3j.platon.FunctionType;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class SlashContract extends PlatOnContract {

    public static SlashContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SlashContract("", SLASH_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static SlashContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SlashContract("", SLASH_CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static SlashContract load(Web3j web3j, ContractGasProvider contractGasProvider) {
        return new SlashContract("", SLASH_CONTRACT_ADDRESS, web3j, contractGasProvider);
    }

    public static SlashContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String chainId) {
        return new SlashContract("", SLASH_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    protected SlashContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    protected SlashContract(String contractBinary, String contractAddress, Web3j web3j, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, new ReadonlyTransactionManager(web3j,contractAddress), gasProvider);
    }

    public SlashContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    public SlashContract(String contractBinary, String contractAddress, String chainId, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<BaseResponse> reportDoubleSign(String data) {
        Function function = new Function(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Utf8String(data))
                , Collections.emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<PlatonSendTransaction> reportDoubleSignReturnTransaction(String data) {
        Function function = new Function(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Utf8String(data))
                , Collections.emptyList());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getReportDoubleSignResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE);
    }

    /**
     * 查询节点是否已被举报过多签
     *
     * @param doubleSignType 代表双签类型，1：prepare，2：viewChange
     * @param address        举报的节点地址
     * @param blockNumber    多签的块高
     * @return
     */
    public RemoteCall<BaseResponse> checkDoubleSign(DoubleSignType doubleSignType, String address, BigInteger blockNumber) {
        Function function = new Function(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(address))
                        , new Uint64(blockNumber))
                , Collections.emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 查询节点是否已被举报过多签
     *
     * @param doubleSignType 代表双签类型，1：prepare，2：viewChange
     * @param address        举报的节点地址
     * @param blockNumber    多签的块高
     * @return
     */
    public RemoteCall<PlatonSendTransaction> checkDoubleSignReturnTransaction(DoubleSignType doubleSignType, String address, BigInteger blockNumber) {
        Function function = new Function(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(address))
                        , new Uint64(blockNumber))
                , Collections.emptyList());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取查询节点是否已被举报过多签的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getCheckDoubleSignResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE);
    }

}
