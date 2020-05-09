//package com.platon.sdk.contracts.ppos;
//
//import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
//import com.platon.sdk.contracts.ppos.abi.Function;
//import com.platon.sdk.contracts.ppos.abi.custom.NodeId;
//import com.platon.sdk.contracts.ppos.dto.CallResponse;
//import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
//import com.platon.sdk.contracts.ppos.dto.common.ContractAddress;
//import com.platon.sdk.contracts.ppos.dto.common.ErrorCode;
//import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
//import com.platon.sdk.contracts.ppos.dto.resp.Reward;
//import com.platon.sdk.contracts.ppos.exception.NoSupportFunctionType;
//import org.web3j.abi.datatypes.BytesType;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.RemoteCall;
//import org.web3j.protocol.core.methods.response.Log;
//import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//import org.web3j.protocol.exceptions.TransactionException;
//import org.web3j.rlp.RlpDecoder;
//import org.web3j.rlp.RlpList;
//import org.web3j.rlp.RlpString;
//import org.web3j.rlp.RlpType;
//import org.web3j.tx.TransactionManager;
//import org.web3j.tx.gas.GasProvider;
//import org.web3j.utils.Numeric;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class RewardContract extends BaseContract {
//	/**
//	 * 加载合约, 默认ReadonlyTransactionManager事务管理
//	 *
//	 * @param web3j
//	 * @return
//	 */
//    public static RewardContract load(Web3j web3j) {
//        return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, web3j);
//    }
//
//    /**
//     * 加载合约
//     *
//     * @param web3j
//     * @param transactionManager
//     * @return
//     */
//    public static RewardContract load(Web3j web3j, TransactionManager transactionManager) {
//    	return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, web3j, transactionManager);
//    }
//
//    /**
//     * 加载合约, 默认RawTransactionManager事务管理
//     *
//     * @param web3j
//     * @param credentials
//     * @param chainId
//     * @return
//     */
//    public static RewardContract load(Web3j web3j, Credentials credentials, long chainId) {
//        return new RewardContract(ContractAddress.REWARD_CONTRACT_ADDRESS, chainId, web3j, credentials);
//    }
//
//    private RewardContract(String contractAddress, Web3j web3j) {
//        super(contractAddress, web3j);
//    }
//
//    private RewardContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
//        super(contractAddress, chainId, web3j, credentials);
//    }
//
//    private RewardContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
//        super(contractAddress, web3j, transactionManager);
//    }
//
//
//    /**
//     * 提取账户当前所有的可提取的委托奖励
//     *
//     * @return
//     */
//    public RemoteCall<TransactionResponse> withdrawDelegateReward() {
//        Function function = createWithdrawDelegateRewardFunction();
//        return executeRemoteCallTransaction(function);
//    }
//
//    /**
//     * 提取账户当前所有的可提取的委托奖励
//     *
//     * @param gasProvider       用户指定的gasProvider
//     * @return
//     */
//    public RemoteCall<TransactionResponse> withdrawDelegateReward(GasProvider gasProvider) {
//        Function function = createWithdrawDelegateRewardFunction();
//        return executeRemoteCallTransaction(function, gasProvider);
//    }
//
//    /**
//     * 提取账户当前所有的可提取的委托奖励的gasProvider
//     *
//     * @return
//     */
//    public GasProvider getWithdrawDelegateRewardGasProvider() throws IOException, NoSupportFunctionType {
//    	Function function = createWithdrawDelegateRewardFunction();
//    	return getDefaultGasProvider(function);
//    }
//
//    /**
//     * 提取账户当前所有的可提取的委托奖励
//     *
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> withdrawDelegateRewardReturnTransaction() {
//        Function function = createWithdrawDelegateRewardFunction();
//        return executeRemoteCallTransactionStep1(function);
//    }
//
//    /**
//     * 提取账户当前所有的可提取的委托奖励
//     *
//     * @param gasProvider       用户指定的gasProvider
//     * @return
//     */
//    public RemoteCall<PlatonSendTransaction> withdrawDelegateRewardReturnTransaction( GasProvider gasProvider) {
//        Function function = createWithdrawDelegateRewardFunction();
//        return executeRemoteCallTransactionStep1(function,gasProvider);
//    }
//
//    private Function createWithdrawDelegateRewardFunction() {
//    	Function function = new Function(FunctionType.WITHDRAW_DELEGATE_REWARD_FUNC_TYPE);
//        return function;
//    }
//
//    /**
//     *  获得提取的明细（当提取账户当前所有的可提取的委托奖励成功时调用）
//     *
//     * @param transactionReceipt
//     * @return
//     * @throws TransactionException
//     */
//    public List<Reward> decodeWithdrawDelegateRewardLog(TransactionReceipt transactionReceipt) throws TransactionException {
//        List<Log> logs = transactionReceipt.getLogs();
//        if(logs==null||logs.isEmpty()){
//            throw new TransactionException("TransactionReceipt logs is empty");
//        }
//
//        String logData = logs.get(0).getData();
//        if(null == logData || "".equals(logData) ){
//            throw new TransactionException("TransactionReceipt logs[0].data is empty");
//        }
//
//        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
//        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
//        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
//        int statusCode = Integer.parseInt(decodedStatus);
//
//        if(statusCode != ErrorCode.SUCCESS){
//            throw new TransactionException("TransactionResponse code is 0");
//        }
//
//        List<Reward> rewards = new ArrayList<>();
//        ((RlpList)((RlpList)RlpDecoder.decode(((RlpString)rlpList.get(1)).getBytes())).getValues().get(0)).getValues()
//                .stream()
//                .forEach(rl -> {
//                    RlpList rlpL = (RlpList)rl;
//                    Reward reward = new Reward();
//                    reward.setNodeId(((RlpString)rlpL.getValues().get(0)).asString());
//                    reward.setStakingNum(((RlpString)rlpL.getValues().get(1)).asPositiveBigInteger());
//                    reward.setRewardBigIntegerValue((((RlpString)rlpL.getValues().get(2)).asPositiveBigInteger()));
//                    rewards.add(reward);
//                });
//
//        return  rewards;
//    }
//
//    /**
//     * 查询当前账户地址所委托的节点的NodeID和质押Id
//     *
//     * @param address 查询的地址
//     * @param nodeList 节点id列表
//     * @return
//     */
//    public RemoteCall<CallResponse<List<Reward>>> getDelegateReward(String address,List<String> nodeList) {
//        List<NodeId> bytesTypeList = nodeList.stream().map(nodeId ->  new NodeId(nodeId)).collect(Collectors.toList());
//        CustomStaticArray<NodeId> dynamicArray =  new CustomStaticArray<>(bytesTypeList);
//        Function function = new Function(FunctionType.GET_DELEGATE_REWARD_FUNC_TYPE,
//                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(address)), dynamicArray));
//        return executeRemoteCallListValueReturn(function, Reward.class);
//    }
//}
