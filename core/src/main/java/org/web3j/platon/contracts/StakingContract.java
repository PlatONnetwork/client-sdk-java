package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.ProgramVersion;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.bean.UpdateStakingParam;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import rx.Observable;
import rx.functions.Func1;

public class StakingContract extends PlatOnContract {

    public static StakingContract load(Web3j web3j) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, web3j);
    }

    public static StakingContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    /**
     * 查询操作
     *
     * @param contractAddress
     * @param web3j
     */
    private StakingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }
    /**
     * sendRawTransaction，使用默认gasProvider
     *
     * @param contractAddress
     * @param chainId
     * @param web3j
     * @param credentials
     */
    private StakingContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<BaseResponse> staking(StakingParam stakingParam) throws Exception {
        StakingParam tempStakingParam = stakingParam.clone();
        tempStakingParam.setProcessVersion(getProgramVersion().send().data);
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                tempStakingParam.getSubmitInputParameters());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @param gasProvider
     * @return
     * @see StakingParam
     */
    public RemoteCall<BaseResponse> staking(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        StakingParam tempStakingParam = stakingParam.clone();
        tempStakingParam.setProcessVersion(getProgramVersion().send().data);
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                tempStakingParam.getSubmitInputParameters(), gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取质押gasProvider
     *
     * @param stakingParam
     * @return
     */
    public Observable<GasProvider> getStakingGasProvider(StakingParam stakingParam) {
        StakingParam tempStakingParam = stakingParam.clone();
        return Observable.fromCallable(new Callable<ProgramVersion>() {
            @Override
            public ProgramVersion call() throws Exception {
                return getProgramVersion().send().data;
            }
        }).map(new Func1<ProgramVersion, GasProvider>() {
            @Override
            public GasProvider call(ProgramVersion programVersion) {
                tempStakingParam.setProcessVersion(programVersion);
                return new PlatOnFunction(
                        FunctionType.STAKING_FUNC_TYPE,
                        tempStakingParam.getSubmitInputParameters()).getGasProvider();
            }
        });
    }


    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam) throws Exception {
        StakingParam tempStakingParam = stakingParam.clone();
        tempStakingParam.setProcessVersion(getProgramVersion().send().data);
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                tempStakingParam.getSubmitInputParameters());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @param gasProvider
     * @return
     * @see StakingParam
     */
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        StakingParam tempStakingParam = stakingParam.clone();
        tempStakingParam.setProcessVersion(getProgramVersion().send().data);
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                tempStakingParam.getSubmitInputParameters(), gasProvider);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.STAKING_FUNC_TYPE);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<BaseResponse> unStaking(String nodeId) {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<BaseResponse> unStaking(String nodeId, GasProvider gasProvider) {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId))), gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取撤销质押的gasProvider
     *
     * @param nodeId
     * @return
     */
    public Observable<GasProvider> getUnStakingGasProvider(String nodeId) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                        Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId)))).getGasProvider();
            }
        });
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId) {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId      64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @param gasProvider 自定义的gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId, GasProvider gasProvider) {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId))),gasProvider);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getUnStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.WITHDREW_STAKING_FUNC_TYPE);
    }


    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<BaseResponse> updateStakingInfo(UpdateStakingParam updateStakingParam) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @param gasProvider
     * @return
     */
    public RemoteCall<BaseResponse> updateStakingInfo(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters(),gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取更新质押信息GasProvider
     *
     * @param updateStakingParam
     * @return
     */
    public Observable<GasProvider> getUpdateStakingInfoGasProvider(UpdateStakingParam updateStakingParam) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                        updateStakingParam.getSubmitInputParameters()).getGasProvider();
            }
        });
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam) {

        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {

        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters(), gasProvider);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取更新质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getUpdateStakingInfoResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE);
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @return
     */
    public RemoteCall<BaseResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @param gasProvider
     * @return
     */
    public RemoteCall<BaseResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)), gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取增持质押gasProvider
     *
     * @param nodeId
     * @param stakingAmountType
     * @param amount
     * @return
     */
    public Observable<GasProvider> getAddStakingGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                        Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                                new Uint16(stakingAmountType.getValue()),
                                new Uint256(amount))).getGasProvider();
            }
        });
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @return
     */
    public RemoteCall<PlatonSendTransaction> addStakingReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)));
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 增持质押
     *
     * @param nodeId            被质押的节点Id(也叫候选人的节点Id)
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * @param amount            增持的von
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> addStakingReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)), gasProvider);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取增持质押的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getAddStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.ADD_STAKING_FUNC_TYPE);
    }

    /**
     * 获取质押信息
     *
     * @param nodeId
     * @return
     */
    public RemoteCall<BaseResponse<Node>> getStakingInfo(String nodeId) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_STAKINGINFO_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return new RemoteCall<BaseResponse<Node>>(new Callable<BaseResponse<Node>>() {
            @Override
            public BaseResponse<Node> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseObject((String) response.data, Node.class);
                return response;
            }
        });
    }

}
