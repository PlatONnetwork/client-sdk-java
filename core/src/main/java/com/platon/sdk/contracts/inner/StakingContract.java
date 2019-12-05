package com.platon.sdk.contracts.inner;

import java.math.BigInteger;
import java.util.Arrays;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.bean.Node;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import com.platon.sdk.contracts.inner.dto.CallResponse;
import com.platon.sdk.contracts.inner.dto.TransactionResponse;
import com.platon.sdk.contracts.inner.dto.enums.StakingAmountType;
import com.platon.sdk.contracts.inner.dto.param.StakingParam;
import com.platon.sdk.contracts.inner.dto.param.UpdateStakingParam;

public class StakingContract extends BaseContract {
	
	/**
	 *  加载合约，ReadonlyTransactionManager管理
	 * @param web3j
	 * @return 
	 */
    public static StakingContract load(Web3j web3j) {
        return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约，自定义TransactionManager管理
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static StakingContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, web3j, transactionManager);
    }

    /**
     * 加载合约，RawTransactionManager管理
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static StakingContract load(Web3j web3j, Credentials credentials, String chainId) {
    	return new StakingContract(ContractAddress.STAKING_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }
    
    private StakingContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private StakingContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_STAKINGINFO_FUNC_TYPE, 
        		Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))));
        return executeRemoteCallObjectValueReturn(function, Node.class);
    }
    
    /**
     * 查询当前结算周期的区块奖励
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getPackageReward() {
    	PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PACKAGEREWARD_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }
    
    /**
     * 查询当前结算周期的质押奖励
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getStakingReward() {
    	PlatOnFunction function = new PlatOnFunction(FunctionType.GET_STAKINGREWARD_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }
    
    /**
     * 查询打包区块的平均时间
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getAvgPackTime() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_AVGPACKTIME_FUNC_TYPE);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }

    /**
     * 发起质押
     *
     * @param stakingParam
     * @return
     * @see StakingParam
     */
    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam) throws Exception {
        PlatOnFunction function = createStakingFunction(stakingParam, null);
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
    public RemoteCall<TransactionResponse> staking(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createStakingFunction(stakingParam, gasProvider);
        return executeRemoteCallTransaction(function);
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
    public RemoteCall<PlatonSendTransaction> stakingReturnTransaction(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createStakingFunction(stakingParam, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 获取质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<TransactionResponse> getStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionStep2(ethSendTransaction);
    }

    private PlatOnFunction createStakingFunction(StakingParam stakingParam, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = new PlatOnFunction(
                FunctionType.STAKING_FUNC_TYPE,
                stakingParam.getSubmitInputParameters(), gasProvider);
        return function;
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<TransactionResponse> unStaking(String nodeId) {
        PlatOnFunction function = createUnStakingFunction(nodeId, null);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 撤销质押
     *
     * @param nodeId 64bytes 被质押的节点Id(也叫候选人的节点Id)
     * @return
     */
    public RemoteCall<TransactionResponse> unStaking(String nodeId, GasProvider gasProvider) {
        PlatOnFunction function = createUnStakingFunction(nodeId, gasProvider);
        return executeRemoteCallTransaction(function);
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
        PlatOnFunction function = createUnStakingFunction(nodeId, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 获取质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<TransactionResponse> getUnStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionStep2(ethSendTransaction);
    }

    private PlatOnFunction createUnStakingFunction(String nodeId, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId))), gasProvider);
        return function;
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<TransactionResponse> updateStakingInfo(UpdateStakingParam updateStakingParam) {
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, null);
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
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, gasProvider);
        return executeRemoteCallTransaction(function);
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
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 更新质押信息
     *
     * @param updateStakingParam
     * @return
     */
    public RemoteCall<PlatonSendTransaction> updateStakingInfoReturnTransaction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        PlatOnFunction function = createUpdateStakingFunction(updateStakingParam, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 获取更新质押结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<TransactionResponse> getUpdateStakingInfoResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionStep2(ethSendTransaction);
    }

    private PlatOnFunction createUpdateStakingFunction(UpdateStakingParam updateStakingParam, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE,
                updateStakingParam.getSubmitInputParameters(), gasProvider);
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, null);
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, gasProvider);
        return executeRemoteCallTransaction(function);
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
        PlatOnFunction function = createAddStakingFunction(nodeId, stakingAmountType, amount, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 获取增持质押的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<TransactionResponse> getAddStakingResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionStep2(ethSendTransaction);
    }

    private PlatOnFunction createAddStakingFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.ADD_STAKING_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(nodeId)),
                        new Uint16(stakingAmountType.getValue()),
                        new Uint256(amount)), gasProvider);
        return function;
    }
}
