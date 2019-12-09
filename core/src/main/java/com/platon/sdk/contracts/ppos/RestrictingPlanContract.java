package com.platon.sdk.contracts.ppos;

import java.util.Arrays;
import java.util.List;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
import com.platon.sdk.contracts.ppos.abi.PlatOnFunction;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ContractAddress;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.resp.RestrictingItem;
import com.platon.sdk.contracts.ppos.dto.resp.RestrictingPlan;

public class RestrictingPlanContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static RestrictingPlanContract load(Web3j web3j) {
        return new RestrictingPlanContract(ContractAddress.RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static RestrictingPlanContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new RestrictingPlanContract(ContractAddress.RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static RestrictingPlanContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new RestrictingPlanContract(ContractAddress.RESTRICTING_PLAN_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    private RestrictingPlanContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private RestrictingPlanContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }
    
    private RestrictingPlanContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 创建锁仓计划
     *
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<TransactionResponse> createRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList) {
        PlatOnFunction function = createRestrictingPlanFunction(account, restrictingPlanList, null);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 创建锁仓计划
     *
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> createRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList, GasProvider gasProvider) {
        PlatOnFunction function = createRestrictingPlanFunction(account, restrictingPlanList, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取创建锁仓计划的gasProvider
     *
     * @param account
     * @param restrictingPlanList
     * @return
     */
    public GasProvider getCreateRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList) {
    	PlatOnFunction function = createRestrictingPlanFunction(account, restrictingPlanList, null);
    	return function.getGasProvider();
    }

    /**
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<PlatonSendTransaction> createRestrictingPlanReturnTransaction(String account, List<RestrictingPlan> restrictingPlanList) {
    	PlatOnFunction function = createRestrictingPlanFunction(account, restrictingPlanList, null);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> createRestrictingPlanReturnTransaction(String account, List<RestrictingPlan> restrictingPlanList, GasProvider gasProvider) {
        PlatOnFunction function = createRestrictingPlanFunction(account, restrictingPlanList, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createRestrictingPlanFunction(String account, List<RestrictingPlan> restrictingPlanList, GasProvider gasProvider) {          
    	PlatOnFunction function = new PlatOnFunction(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray<RestrictingPlan>(restrictingPlanList)), gasProvider);
        return function;
    }

    /**
     * 获取锁仓信息
     *
     * @param account 锁仓释放到账账户
     * @return
     */
    public RemoteCall<CallResponse<RestrictingItem>> getRestrictingInfo(String account) {
    	PlatOnFunction function = new PlatOnFunction(
                FunctionType.GET_RESTRICTINGINFO_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(account))));
        return executeRemoteCallObjectValueReturn(function, RestrictingItem.class);
    }
}
