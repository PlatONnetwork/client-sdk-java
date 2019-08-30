package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.CustomStaticArray;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.bean.RestrictingItem;
import org.web3j.platon.bean.RestrictingPlan;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import rx.Observable;

public class RestrictingPlanContract extends PlatOnContract {

    /**
     * 查询操作
     *
     * @param web3j
     * @return
     */
    public static RestrictingPlanContract load(Web3j web3j) {
        return new RestrictingPlanContract(ContractAddress.RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j);
    }

    /**
     * sendRawTransaction 使用用户自定义的gasProvider，必须传chainId
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

    /**
     * 创建锁仓计划
     *
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<BaseResponse> createRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList) {
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList)));
        return executeRemoteCallTransactionWithFunctionType(function);
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
    public RemoteCall<BaseResponse> createRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList, GasProvider gasProvider) {
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList)), gasProvider);
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取创建锁仓计划的gasProvider
     *
     * @param account
     * @param restrictingPlanList
     * @return
     */
    public Observable<GasProvider> getCreateRestrictingPlan(String account, List<RestrictingPlan> restrictingPlanList) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(
                        FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                        Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList))).getGasProvider();
            }
        });
    }

    /**
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<PlatonSendTransaction> createRestrictingPlanReturnTransaction(String account, List<RestrictingPlan> restrictingPlanList) {
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList)));
        return executeRemoteCallPlatonTransaction(function);
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
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList)), gasProvider);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getCreateRestrictingPlanResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE);
    }

    /**
     * 获取锁仓信息
     *
     * @param account 锁仓释放到账账户
     * @return
     */
    public RemoteCall<BaseResponse<RestrictingItem>> getRestrictingInfo(String account) {
        final PlatOnFunction function = new PlatOnFunction(
                FunctionType.GET_RESTRICTINGINFO_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account))));
        return new RemoteCall<BaseResponse<RestrictingItem>>(new Callable<BaseResponse<RestrictingItem>>() {
            @Override
            public BaseResponse<RestrictingItem> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseObject((String) response.data, RestrictingItem.class);
                return response;
            }
        });
    }

}
