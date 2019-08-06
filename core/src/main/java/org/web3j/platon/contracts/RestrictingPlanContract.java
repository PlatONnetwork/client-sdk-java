package org.web3j.platon.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.CustomStaticArray;
import org.web3j.platon.FunctionType;
import org.web3j.platon.bean.RestrictingItem;
import org.web3j.platon.bean.RestrictingPlan;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RestrictingPlanContract extends PlatOnContract {

    public static RestrictingPlanContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RestrictingPlanContract("", RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static RestrictingPlanContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RestrictingPlanContract("", RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static RestrictingPlanContract load(Web3j web3j, ContractGasProvider contractGasProvider) {
        return new RestrictingPlanContract("", RESTRICTING_PLAN_CONTRACT_ADDRESS, web3j, contractGasProvider);
    }

    public static RestrictingPlanContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String chainId) {
        return new RestrictingPlanContract("", RESTRICTING_PLAN_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    protected RestrictingPlanContract(String contractBinary, String contractAddress, Web3j web3j, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, new ReadonlyTransactionManager(web3j, contractAddress), gasProvider);
    }

    protected RestrictingPlanContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    public RestrictingPlanContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    public RestrictingPlanContract(String contractBinary, String contractAddress, String chainId, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, chainId, web3j, credentials, gasProvider);
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
        final Function function = new Function(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account)), new CustomStaticArray(restrictingPlanList)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * @param account             锁仓释放到账账户
     * @param restrictingPlanList 其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。
     *                            如果 account 是激励池地址，那么 period 值是 120（即，30*4） 的倍数。
     *                            另外，period * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     * @return
     */
    public RemoteCall<PlatonSendTransaction> createRestrictingPlanReturnTransaction(String account, List<RestrictingPlan> restrictingPlanList) {
        final Function function = new Function(
                FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE,
                Arrays.<Type>asList(new Utf8String(account), new StaticArray(restrictingPlanList)),
                Collections.<TypeReference<?>>emptyList());
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
        final Function function = new Function(
                FunctionType.GET_RESTRICTINGINFO_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(account))),
                Collections.<TypeReference<?>>emptyList());
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
