package com.platon.contracts.ppos;

import com.platon.abi.solidity.datatypes.BytesType;
import com.platon.bech32.Bech32;
import com.platon.contracts.ppos.abi.CustomStaticArray;
import com.platon.contracts.ppos.abi.Function;
import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.RestrictingPlan;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.common.FunctionType;
import com.platon.contracts.ppos.dto.req.CreateRestrictingParam;
import com.platon.contracts.ppos.dto.resp.RestrictingItem;
import com.platon.contracts.ppos.exception.EstimateGasException;
import com.platon.contracts.ppos.exception.NoSupportFunctionType;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.tx.TransactionManager;
import com.platon.tx.gas.GasProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RestrictingPlanContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static RestrictingPlanContract load(Web3j web3j) {
        return new RestrictingPlanContract(NetworkParameters.getPposContractAddressOfRestrctingPlan(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static RestrictingPlanContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new RestrictingPlanContract(NetworkParameters.getPposContractAddressOfRestrctingPlan(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @return
     */
    public static RestrictingPlanContract load(Web3j web3j, Credentials credentials) {
        return new RestrictingPlanContract(NetworkParameters.getPposContractAddressOfRestrctingPlan(),  web3j, credentials);
    }

    private RestrictingPlanContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private RestrictingPlanContract(String contractAddress, Web3j web3j, Credentials credentials) {
        super(contractAddress, web3j, credentials);
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
        Function function = createRestrictingPlanFunction(account, restrictingPlanList);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取创建锁仓计划的GasProvider
     *
     * @param param CreateRestrictingParam
     * @return
     */
    public GasProvider getCreateRestrictingPlanGasProvider(CreateRestrictingParam param) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createRestrictingPlanFunction(param.getAccount(), Arrays.asList(param.getPlans()));
        return getDefaultGasProvider(function);
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
        Function function = createRestrictingPlanFunction(account, restrictingPlanList);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取创建锁仓计划的gasProvider
     *
     * @param account
     * @param restrictingPlanList
     * @return
     */
    public GasProvider getCreateRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList) throws IOException, EstimateGasException, NoSupportFunctionType {
    	Function function = createRestrictingPlanFunction(account, restrictingPlanList);
    	return getDefaultGasProvider(function);
    }

    /**
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<PlatonSendTransaction> createRestrictingPlanReturnTransaction(String account, List<RestrictingPlan> restrictingPlanList) {
    	Function function = createRestrictingPlanFunction(account, restrictingPlanList);
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
        Function function = createRestrictingPlanFunction(account, restrictingPlanList);
        return executeRemoteCallTransactionStep1(function,  gasProvider);
    }

    private Function createRestrictingPlanFunction(String account, List<RestrictingPlan> restrictingPlanList) {
    	Function function = new Function(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.asList(new BytesType(Bech32.addressDecode(account)), new CustomStaticArray<RestrictingPlan>(restrictingPlanList)));
        return function;
    }

    /**
     * 获取锁仓信息
     *
     * @param account 锁仓释放到账账户
     * @return
     */
    public RemoteCall<CallResponse<RestrictingItem>> getRestrictingInfo(String account) {
    	Function function = new Function(
                FunctionType.GET_RESTRICTINGINFO_FUNC_TYPE,
                Arrays.asList(new BytesType(Bech32.addressDecode(account))));
        return executeRemoteCallObjectValueReturn(function, RestrictingItem.class);
    }
}
