package com.platon.sdk.contracts.ppos;

import com.platon.sdk.contracts.ppos.abi.Function;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ErrorCode;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.sdk.contracts.ppos.dto.resp.Delegation;
import com.platon.sdk.contracts.ppos.dto.resp.DelegationIdInfo;
import com.platon.sdk.contracts.ppos.exception.EstimateGasException;
import com.platon.sdk.contracts.ppos.exception.NoSupportFunctionType;
import com.platon.sdk.utlis.Bech32;
import com.platon.sdk.utlis.NetworkParameters;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

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
    public static DelegateContract load(Web3j web3j, long chainId) {
        return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(chainId), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static DelegateContract load(Web3j web3j, TransactionManager transactionManager, long chainId) {
    	return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(chainId), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static DelegateContract load(Web3j web3j, Credentials credentials, long chainId) {
        return new DelegateContract(NetworkParameters.getPposContractAddressOfStaking(chainId), chainId, web3j, credentials);
    }

    private DelegateContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private DelegateContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private DelegateContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        Function function = createDelegateFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> delegate(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        Function function = createDelegateFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 发起委托的gasProvider
     *
     * @param nodeId
     * @param stakingAmountType
     * @param amount`
     * @return
             */
    public GasProvider getDelegateGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createDelegateFunction(nodeId, stakingAmountType, amount);
        return 	getDefaultGasProvider(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<PlatonSendTransaction> delegateReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        Function function = createDelegateFunction(nodeId, stakingAmountType, amount);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @param gasProvider       用户指定的gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> delegateReturnTransaction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
        Function function = createDelegateFunction(nodeId, stakingAmountType, amount );
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createDelegateFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
    	Function function = new Function(FunctionType.DELEGATE_FUNC_TYPE,
                								Arrays.asList(new Uint16(stakingAmountType.getValue())
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
    public GasProvider getUnDelegateGasProvider(String nodeId, BigInteger stakingBlockNum, BigInteger amount) throws IOException, NoSupportFunctionType, EstimateGasException {
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
}
