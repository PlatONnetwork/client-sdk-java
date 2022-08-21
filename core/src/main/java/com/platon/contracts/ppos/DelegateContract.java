package com.platon.contracts.ppos;

import com.platon.abi.solidity.datatypes.BytesType;
import com.platon.abi.solidity.datatypes.generated.Uint16;
import com.platon.abi.solidity.datatypes.generated.Uint256;
import com.platon.abi.solidity.datatypes.generated.Uint64;
import com.platon.bech32.Bech32;
import com.platon.contracts.ppos.abi.Function;
import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.common.ErrorCode;
import com.platon.contracts.ppos.dto.common.FunctionType;
import com.platon.contracts.ppos.dto.enums.DelegateAmountType;
import com.platon.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.contracts.ppos.dto.resp.Delegation;
import com.platon.contracts.ppos.dto.resp.DelegationIdInfo;
import com.platon.contracts.ppos.dto.resp.DelegationLockInfo;
import com.platon.contracts.ppos.exception.EstimateGasException;
import com.platon.contracts.ppos.exception.NoSupportFunctionType;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.RemoteCall;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.protocol.exceptions.TransactionException;
import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import com.platon.tx.TransactionManager;
import com.platon.tx.gas.GasProvider;
import com.platon.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class DelegateContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static DelegateContract load(Web3j web3j) {
        return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static DelegateContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @return
     */
    public static DelegateContract load(Web3j web3j, Credentials credentials) {
        return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(), web3j, credentials);
    }

    private DelegateContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private DelegateContract(String contractAddress, Web3j web3j, Credentials credentials) {
        super(contractAddress, web3j, credentials);
    }

    private DelegateContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param delegateAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount) {
        Function function = createDelegateFunction(nodeId, delegateAmountType, amount);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param delegateAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount, GasProvider gasProvider) {
        Function function = createDelegateFunction(nodeId, delegateAmountType, amount);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 发起委托的gasProvider
     *
     * @param nodeId
     * @param delegateAmountType
     * @param amount
     * @return
             */
    public GasProvider getDelegateGasProvider(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createDelegateFunction(nodeId, delegateAmountType, amount);
        return 	getDefaultGasProvider(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param delegateAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> delegateReturnTransaction(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount) {
        Function function = createDelegateFunction(nodeId, delegateAmountType, amount);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param delegateAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额  3:委托锁定金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> delegateReturnTransaction(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount, GasProvider gasProvider) {
        Function function = createDelegateFunction(nodeId, delegateAmountType, amount );
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createDelegateFunction(String nodeId, DelegateAmountType delegateAmountType, BigInteger amount) {
    	Function function = new Function(FunctionType.DELEGATE_FUNC_TYPE,
                								Arrays.asList(new Uint16(delegateAmountType.getValue())
                								, new BytesType(Numeric.hexStringToByteArray(nodeId))
                								, new Uint256(amount)));
        return function;
    }


    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId          被质押的节点的NodeId
     * @param stakingBlockNum 代表着某个node的某次质押的唯一标示
     * @param amount          减持委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<TransactionResponse> unDelegate(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
        Function function =  createUnDelegateFunction(nodeId, stakingBlockNum, amount);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId          被质押的节点的NodeId
     * @param stakingBlockNum 代表着某个node的某次质押的唯一标示
     * @param amount          减持委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @param gasProvider     用户指定的gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> unDelegate(String nodeId, BigInteger stakingBlockNum, BigInteger amount, GasProvider gasProvider) {
        Function function = createUnDelegateFunction(nodeId, stakingBlockNum, amount);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)的gasProvider
     *
     * @param nodeId
     * @param stakingBlockNum
     * @param amount
     * @return
     */
    public GasProvider getUnDelegateGasProvider(String nodeId, BigInteger stakingBlockNum, BigInteger amount) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createUnDelegateFunction(nodeId, stakingBlockNum, amount);
    	return getDefaultGasProvider(function);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId          被质押的节点的NodeId
     * @param stakingBlockNum 代表着某个node的某次质押的唯一标示
     * @param amount          减持委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unDelegateReturnTransaction(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
        Function function = createUnDelegateFunction(nodeId, stakingBlockNum, amount);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId          被质押的节点的NodeId
     * @param stakingBlockNum 代表着某个node的某次质押的唯一标示
     * @param amount          减持委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> unDelegateReturnTransaction(String nodeId, BigInteger stakingBlockNum, BigInteger amount, GasProvider gasProvider) {
        Function function = createUnDelegateFunction(nodeId, stakingBlockNum, amount);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createUnDelegateFunction(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
    	Function function = new Function(FunctionType.WITHDREW_DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))
                        , new Uint256(amount)));
        return function;
    }

    /**
     *  获得解除委托时所提取的委托收益（当减持/撤销委托成功时调用）
     *
     * @param transactionReceipt
     * @return
     * @throws TransactionException
     */
    public BigInteger decodeUnDelegateLog(TransactionReceipt transactionReceipt) throws TransactionException {
        List<Log> logs = transactionReceipt.getLogs();
        if(logs==null||logs.isEmpty()){
            throw new TransactionException("TransactionReceipt logs is empty");
        }

        String logData = logs.get(0).getData();
        if(null == logData || "".equals(logData) ){
            throw new TransactionException("TransactionReceipt logs[0].data is empty");
        }

        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);

        if(statusCode != ErrorCode.SUCCESS){
            throw new TransactionException("TransactionResponse code is 0");
        }

        return  ((RlpString)((RlpList)RlpDecoder.decode(((RlpString)rlpList.get(1)).getBytes())).getValues().get(0)).asPositiveBigInteger();
    }

    /**
     * 查询当前单个委托信息
     *
     * @param nodeId          验证人的节点Id
     * @param delAddr         委托人账户地址
     * @param stakingBlockNum 发起质押时的区块高度
     * @return
     */
    public RemoteCall<CallResponse<Delegation>> getDelegateInfo(String nodeId, String delAddr, BigInteger stakingBlockNum) {

        Function function = new Function(FunctionType.GET_DELEGATEINFO_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Bech32.addressDecode(delAddr))
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))));

        return executeRemoteCallObjectValueReturn(function, Delegation.class);
    }

    /**
     * 查询当前账户地址所委托的节点的NodeID和质押Id
     *
     * @param address
     * @return
     */
    public RemoteCall<CallResponse<List<DelegationIdInfo>>> getRelatedListByDelAddr(String address) {
        Function function = new Function(FunctionType.GET_DELEGATELIST_BYADDR_FUNC_TYPE,
                Arrays.asList(new BytesType(Bech32.addressDecode(address))));
        return executeRemoteCallListValueReturn(function, DelegationIdInfo.class);
    }

    /**
     * 领取解锁的委托金
     *
     * @return
     * 注:交易结果存储在交易回执的logs.data中，如果成功赎回委托，
     * 存储 rlp.Encode([][]byte{[]byte(状态码0)， rlp.Encode(领取的委托金,回到余额), rlp.Encode(领取的委托金,回到锁仓账户) })
     *
     * released | *big.int | 成功领取的委托金,回到余额
     * restrictingPlan | *big.int | 成功领取的委托金,回到锁仓账户
     */
    public RemoteCall<TransactionResponse> redeemDelegation() {
        Function function = new Function(FunctionType.REDEEM_DELEGATION_FUNC_TYPE);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 查询用户处于锁定期与解锁期的委托信息
     *
     * @param address 委托人账户地址
     * @return
     */
    public RemoteCall<CallResponse<DelegationLockInfo>> getDelegationLockInfo(String address) {
        Function function = new Function(FunctionType.GET_DELEGATION_LOCKINFO_FUNC_TYPE,
                Arrays.asList(new BytesType(Bech32.addressDecode(address))));
        return executeRemoteCallObjectValueReturn(function, DelegationLockInfo.class);
    }

}
