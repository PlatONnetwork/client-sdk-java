package com.platon.sdk.contracts.ppos;

import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
import com.platon.sdk.contracts.ppos.abi.PlatOnFunction;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ContractAddress;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.sdk.contracts.ppos.dto.resp.Delegation;
import com.platon.sdk.contracts.ppos.dto.resp.DelegationIdInfo;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.generated.StaticArray15;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RewardContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static RewardContract load(Web3j web3j) {
        return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static RewardContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static RewardContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    private RewardContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private RewardContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private RewardContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }


    /**
     * 提取账户当前所有的可提取的委托奖励
     *
     * @return
     */
    public RemoteCall<TransactionResponse> withdrawDelegateReward() {
        PlatOnFunction function = createWithdrawDelegateRewardFunction(null);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 提取账户当前所有的可提取的委托奖励
     *
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> withdrawDelegateReward(GasProvider gasProvider) {
        PlatOnFunction function = createWithdrawDelegateRewardFunction(gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 提取账户当前所有的可提取的委托奖励的gasProvider
     *
     * @return
     */
    public GasProvider getWithdrawDelegateRewardGasProvider() {
    	PlatOnFunction function = createWithdrawDelegateRewardFunction(null);
    	return function.getGasProvider();
    }

    /**
     * 提取账户当前所有的可提取的委托奖励
     *
     * @return
     */
    public RemoteCall<PlatonSendTransaction> withdrawDelegateRewardReturnTransaction() {
        PlatOnFunction function = createWithdrawDelegateRewardFunction(null);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 提取账户当前所有的可提取的委托奖励
     *
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> withdrawDelegateRewardReturnTransaction( GasProvider gasProvider) {
        PlatOnFunction function = createWithdrawDelegateRewardFunction(gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createWithdrawDelegateRewardFunction(GasProvider gasProvider) {
    	PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDRAW_DELEGATE_REWARD_FUNC_TYPE, gasProvider);
        return function;
    }

    /**
     * 查询当前账户地址所委托的节点的NodeID和质押Id
     *
     * @param nodeList 节点id列表
     * @return
     */
    public RemoteCall<CallResponse<List<DelegationIdInfo>>> getDelegateReward(List<String> nodeList) {
        List<BytesType> bytesTypeList = nodeList.stream().map(nodeId ->  new BytesType(Numeric.hexStringToByteArray(nodeId))).collect(Collectors.toList());

        DynamicArray<BytesType> dynamicArray =  new DynamicArray<>(bytesTypeList);

        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_DELEGATE_REWARD_FUNC_TYPE,
                Arrays.asList(dynamicArray));
        return executeRemoteCallListValueReturn(function, DelegationIdInfo.class);
    }
}
