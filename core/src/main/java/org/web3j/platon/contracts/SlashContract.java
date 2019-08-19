package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.DoubleSignType;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import rx.Observable;

public class SlashContract extends PlatOnContract {

    /**
     * 查询操作
     *
     * @param web3j
     * @return
     */
    public static SlashContract load(Web3j web3j) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, web3j);
    }

    /**
     * sendRawTransaction 使用默认的gasProvider
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static SlashContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    /**
     * sendRawTransaction 使用自定义的gasProvider
     *
     * @param web3j
     * @param credentials
     * @param contractGasProvider
     * @param chainId
     * @return
     */
    public static SlashContract load(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, String chainId) {
        return new SlashContract(ContractAddress.SLASH_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    private SlashContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private SlashContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials, null);
    }

    private SlashContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials, GasProvider gasProvider) {
        super(contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<BaseResponse> reportDoubleSign(String data) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Utf8String(data)));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取举报双签的gasProvider
     * @param data
     * @return
     */
    public Observable<GasProvider> getReportDoubleSignGasProvider(String data) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                        Arrays.asList(new Utf8String(data))).getGasProvider();
            }
        });
    }

    /**
     * 举报双签
     *
     * @param data 证据的json值
     * @return
     */
    public RemoteCall<PlatonSendTransaction> reportDoubleSignReturnTransaction(String data) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Utf8String(data)));
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(address))
                        , new Uint64(blockNumber)));
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.CHECK_DOUBLESIGN_FUNC_TYPE,
                Arrays.asList(new Uint32(doubleSignType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(address))
                        , new Uint64(blockNumber)));
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
