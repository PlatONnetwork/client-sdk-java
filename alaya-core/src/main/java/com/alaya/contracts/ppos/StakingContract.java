package com.alaya.contracts.ppos;

import com.alaya.abi.solidity.datatypes.BytesType;
import com.alaya.abi.solidity.datatypes.generated.Uint16;
import com.alaya.abi.solidity.datatypes.generated.Uint256;
import com.alaya.contracts.ppos.abi.Function;
import com.alaya.contracts.ppos.dto.CallResponse;
import com.alaya.contracts.ppos.dto.TransactionResponse;
import com.alaya.contracts.ppos.dto.common.FunctionType;
import com.alaya.contracts.ppos.dto.enums.StakingAmountType;
import com.alaya.contracts.ppos.dto.req.StakingParam;
import com.alaya.contracts.ppos.dto.req.UpdateStakingParam;
import com.alaya.contracts.ppos.dto.resp.Node;
import com.alaya.contracts.ppos.exception.EstimateGasException;
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

public class StakingContract extends BaseContract {
	
	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static StakingContract load(Web3j web3j, long chainId) {
        return new StakingContract(NetworkParameters.getPposContractAddressOfStaking(chainId), web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static StakingContract load(Web3j web3j, TransactionManager transactionManager,long chainId) {
    	return new StakingContract(NetworkParameters.getPposContractAddressOfStaking(chainId), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static StakingContract load(Web3j web3j, Credentials credentials, long chainId) {
    	return new StakingContract(NetworkParameters.getPposContractAddressOfStaking(chainId), chainId, web3j, credentials);
    }
    
    private StakingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private StakingContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private StakingContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }
    
    /**
     * 获取质押信息
     *
     * @param nodeId
     * @return
     */
    public RemoteCall<CallResponse<Node>> getStakingInfo(String nodeId) {
        Function function = new Function(FunctionType.GET_STAKINGINFO_FUNC_TYPE,
        		Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return executeRemoteCallObjectValueReturn(function, Node.class);
    }
    
    /**
     * 查询当前结算周期的区块奖励
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getPackageReward() {
    	Function function = new Function(FunctionType.GET_PACKAGEREWARD_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }
    
    /**
     * 查询当前结算周期的质押奖励
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getStakingReward() {
    	Function function = new Function(FunctionType.GET_STAKINGREWARD_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }
    
    /**
     * 查询打包区块的平均时间
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getAvgPackTime() {
        Function function = new Function(FunctionType.GET_AVGPACKTIME_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam) {
        Function function = createStakingFunction(stakingParam);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @param gasProvider
     * @return
     * @see StakingParam
     */
    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam, GasProvider gasProvider)  {
        Function function = createStakingFunction(stakingParam);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取质押gasProvider
     *
     * @param stakingParam
     * @return
     */
    public GasProvider getStakingGasProvider(StakingParam stakingParam) throws IOException, EstimateGasException {
        Function function = createStakingFunction(stakingParam);
        return getDefaultGasProvider(function);
    }


    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam)  {
        Function function = createStakingFunction(stakingParam);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @param gasProvider
     * @return
     * @see StakingParam
     */
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam, GasProvider gasProvider) {
        Function function = createStakingFunction(stakingParam);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createStakingFunction(StakingParam stakingParam)  {
        Function function = new Function(
                FunctionType.STAKING_FUNC_TYPE,
                stakingParam.getSubmitInputParameters());
        return function;
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<TransactionResponse> unStaking(String nodeId) {
        Function function = createUnStakingFunction(nodeId);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<TransactionResponse> unStaking(String nodeId, GasProvider gasProvider) {
        Function function = createUnStakingFunction(nodeId);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取撤销质押的gasProvider
     *
     * @param nodeId
     * @return
     */
    public GasProvider getUnStakingGasProvider(String nodeId) throws IOException, EstimateGasException {
        Function function = createUnStakingFunction(nodeId);
        return getDefaultGasProvider(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId) {
        Function function = createUnStakingFunction(nodeId);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId      64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @param gasProvider 自定义的gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unStakingReturnTransaction(String nodeId, GasProvider gasProvider) {
        Function function = createUnStakingFunction(nodeId);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createUnStakingFunction(String nodeId) {
        Function function = new Function(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return function;
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<TransactionResponse> updateStakingInfo(UpdateStakingParam updateStakingParam) {
        Function function = createUpdateStakingFunction(updateStakingParam);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> updateStakingInfo(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        Function function = createUpdateStakingFunction(updateStakingParam);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 获取更新质押信息GasProvider
     *
     * @param updateStakingParam
     * @return
     */
    public GasProvider getUpdateStakingInfoGasProvider(UpdateStakingParam updateStakingParam) throws IOException, EstimateGasException {
        Function function = createUpdateStakingFunction(updateStakingParam);
        return getDefaultGasProvider(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam) {
        Function function = createUpdateStakingFunction(updateStakingParam);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        Function function = createUpdateStakingFunction(updateStakingParam);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createUpdateStakingFunction(UpdateStakingParam updateStakingParam) {
        Function function = new Function(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters());
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
    public RemoteCall<TransactionResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransaction(function);
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
    public RemoteCall<TransactionResponse> addStaking(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取增持质押gasProvider
     *
     * @param nodeId
     * @param stakingAmountType
     * @param amount
     * @return
     */
    public GasProvider getAddStakingGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) throws IOException, EstimateGasException {
        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
        return getDefaultGasProvider(function);
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
        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransactionStep1(function);
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
        Function function = createAddStakingFunction(nodeId, stakingAmountType, amount );
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createAddStakingFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        Function function = new Function(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)));
        return function;
    }
}
