package org.web3j.platon.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.Callable;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.bean.UpdateStakingParam;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

public class StakingContract extends PlatOnContract {

    public static StakingContract load(Web3j web3j) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, web3j);
    }

    public static StakingContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }
    
    public static StakingContract load(Web3j web3j, TransactionManager transactionManager) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, web3j, transactionManager);
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
    
    private StakingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<BaseResponse> staking(StakingParam stakingParam) throws Exception {
        PlatOnFunction function = createStakingFunction(stakingParam, null);
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
        PlatOnFunction function = createStakingFunction(stakingParam, gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取质押gasProvider
     *
     * @param stakingParam
     * @return
     */
    public GasProvider getStakingGasProvider(StakingParam stakingParam) throws Exception {
    	PlatOnFunction function = createStakingFunction(stakingParam, null);
    	return function.getGasProvider();
    }
    

    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam) throws Exception {
        PlatOnFunction function = createStakingFunction(stakingParam, null);
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
        PlatOnFunction function = createStakingFunction(stakingParam, gasProvider);
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
    
    private PlatOnFunction createStakingFunction(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        StakingParam tempStakingParam = stakingParam.clone();
        tempStakingParam.setProcessVersion(getProgramVersion().send().data);
        tempStakingParam.setBlsProof(web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve());
        PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                tempStakingParam.getSubmitInputParameters(), gasProvider);
        return function;
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<BaseResponse> unStaking(String nodeId) {
        PlatOnFunction function  = createUnStakingFunction(nodeId, null);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<BaseResponse> unStaking(String nodeId, GasProvider gasProvider) {
        PlatOnFunction function = createUnStakingFunction(nodeId, gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取撤销质押的gasProvider
     *
     * @param nodeId
     * @return
     */
    public GasProvider getUnStakingGasProvider(String nodeId) {
    	PlatOnFunction function = createUnStakingFunction(nodeId, null);
    	return function.getGasProvider();
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId) {
        PlatOnFunction function = createUnStakingFunction(nodeId, null);
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
        PlatOnFunction function = createUnStakingFunction(nodeId, gasProvider);
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

    private PlatOnFunction createUnStakingFunction(String nodeId, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(nodeId))), gasProvider);
        return function;
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<BaseResponse> updateStakingInfo(UpdateStakingParam updateStakingParam) {
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, null);
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
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取更新质押信息GasProvider
     *
     * @param updateStakingParam
     * @return
     */
    public GasProvider getUpdateStakingInfoGasProvider(UpdateStakingParam updateStakingParam) {
    	PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, null);
    	return function.getGasProvider();
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam) {
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, null);        
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, gasProvider);
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

    private PlatOnFunction createUpdateStakingFunction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters(),gasProvider);
        return function;
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, null);
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, gasProvider);
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
    public GasProvider getAddStakingGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, null);
    	return function.getGasProvider();
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, null);
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, gasProvider);        
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
    
    private PlatOnFunction createAddStakingFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {        
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)), gasProvider);
        return function;
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
