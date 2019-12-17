package com.platon.sdk.contracts.ppos;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.datatypes.BytesType;
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

import com.platon.sdk.contracts.ppos.abi.PlatOnFunction;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
import com.platon.sdk.contracts.ppos.dto.common.ContractAddress;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.sdk.contracts.ppos.dto.resp.Delegation;
import com.platon.sdk.contracts.ppos.dto.resp.DelegationIdInfo;

public class DelegateContract extends BaseContract {
	
	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static DelegateContract load(Web3j web3j) {
        return new DelegateContract(ContractAddress.DELEGATE_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static DelegateContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new DelegateContract(ContractAddress.DELEGATE_CONTRACT_ADDRESS, web3j, transactionManager);
    }
    
    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static DelegateContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new DelegateContract(ContractAddress.DELEGATE_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }
    
    private DelegateContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private DelegateContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
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
        PlatOnFunction function = createDelegateFunction(nodeId, stakingAmountType, amount, null);
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
        PlatOnFunction function = createDelegateFunction(nodeId, stakingAmountType, amount, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 发起委托的gasProvider
     * 
     * @param nodeId
     * @param stakingAmountType
     * @param amount
     * @return
     */
    public GasProvider getDelegateGasProvider(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
    	PlatOnFunction function = createDelegateFunction(nodeId, stakingAmountType, amount, null);
    	return function.getGasProvider();
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
        PlatOnFunction function = createDelegateFunction(nodeId, stakingAmountType, amount, null);
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
        PlatOnFunction function = createDelegateFunction(nodeId, stakingAmountType, amount, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createDelegateFunction(String nodeId, StakingAmountType stakingAmountType, BigInteger amount, GasProvider gasProvider) {
    	PlatOnFunction function = new PlatOnFunction(FunctionType.DELEGATE_FUNC_TYPE,
                								Arrays.asList(new Uint16(stakingAmountType.getValue())
                								, new BytesType(Numeric.hexStringToByteArray(nodeId))
                								, new Uint256(amount)), gasProvider);
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
        PlatOnFunction function =  createUnDelegateFunction(nodeId, stakingBlockNum, amount, null);
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
        PlatOnFunction function = createUnDelegateFunction(nodeId, stakingBlockNum, amount, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)的gasProvider
     *
     * @param nodeId
     * @param stakingBlockNum
     * @param amount
     * @return
     */
    public GasProvider getUnDelegateGasProvider(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
        PlatOnFunction function = createUnDelegateFunction(nodeId, stakingBlockNum, amount, null);
    	return function.getGasProvider();
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
        PlatOnFunction function = createUnDelegateFunction(nodeId, stakingBlockNum, amount, null);
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
        PlatOnFunction function = createUnDelegateFunction(nodeId, stakingBlockNum, amount, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createUnDelegateFunction(String nodeId, BigInteger stakingBlockNum, BigInteger amount, GasProvider gasProvider) {
    	PlatOnFunction function = new PlatOnFunction(FunctionType.WITHDREW_DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))
                        , new Uint256(amount)), gasProvider);
        return function;
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

        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_DELEGATEINFO_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Numeric.hexStringToByteArray(delAddr))
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_DELEGATELIST_BYADDR_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(address))));
        return executeRemoteCallListValueReturn(function, DelegationIdInfo.class);
    }
}
