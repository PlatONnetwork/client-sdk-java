package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.FunctionType;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Delegation;
import org.web3j.platon.bean.DelegationIdInfo;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class DelegateContract extends PlatOnContract {

    public static DelegateContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DelegateContract("", DELEGATE_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static DelegateContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DelegateContract("", DELEGATE_CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static DelegateContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String chainId) {
        return new DelegateContract("", DELEGATE_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    public DelegateContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    public DelegateContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    public DelegateContract(String contractBinary, String contractAddress, String chainId, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 发起委托
     *
     * @param nodeId            被质押的节点的NodeId
     * @param stakingAmountType 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额
     * @param amount            委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<BaseResponse> delegate(String nodeId, StakingAmountType stakingAmountType, BigInteger amount) {
        Function function = new Function(FunctionType.DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint16(stakingAmountType.getValue())
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))
                        , new Uint256(amount)), Collections.EMPTY_LIST);
        return executeRemoteCallTransactionWithFunctionType(function);
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
        Function function = new Function(FunctionType.DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint16(stakingAmountType.getValue())
                        , new Utf8String(nodeId)
                        , new Uint256(amount)), Collections.EMPTY_LIST);
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取委托结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getDelegateResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.DELEGATE_FUNC_TYPE);
    }

    /**
     * 减持/撤销委托(全部减持就是撤销)
     *
     * @param nodeId          被质押的节点的NodeId
     * @param stakingBlockNum 代表着某个node的某次质押的唯一标示
     * @param amount          减持委托的金额(按照最小单位算，1LAT = 10**18 von)
     * @return
     */
    public RemoteCall<BaseResponse> unDelegate(String nodeId, BigInteger stakingBlockNum, BigInteger amount) {
        Function function = new Function(FunctionType.WITHDREW_DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))
                        , new Uint256(amount)), Collections.EMPTY_LIST);
        return executeRemoteCallTransactionWithFunctionType(function, amount);
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
        Function function = new Function(FunctionType.WITHDREW_DELEGATE_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new Utf8String(nodeId)
                        , new Uint256(amount)), Collections.EMPTY_LIST);
        return executeRemoteCallPlatonTransaction(function, amount);
    }

    /**
     * 获取减持/撤销委托(全部减持就是撤销)的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getUnDelegateResult(PlatonSendTransaction ethSendTransaction) {

        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.WITHDREW_DELEGATE_FUNC_TYPE);
    }

    /**
     * 查询当前单个委托信息
     *
     * @param nodeId          验证人的节点Id
     * @param delAddr         委托人账户地址
     * @param stakingBlockNum 验证人的节点Id
     * @return
     */
    public RemoteCall<BaseResponse<Delegation>> getDelegateInfo(String nodeId, String delAddr, BigInteger stakingBlockNum) {

        Function function = new Function(FunctionType.GET_DELEGATEINFO_FUNC_TYPE,
                Arrays.asList(new Uint64(stakingBlockNum)
                        , new BytesType(Numeric.hexStringToByteArray(delAddr))
                        , new BytesType(Numeric.hexStringToByteArray(nodeId))), Collections.EMPTY_LIST);

        return new RemoteCall<BaseResponse<Delegation>>(new Callable<BaseResponse<Delegation>>() {
            @Override
            public BaseResponse<Delegation> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseObject((String) response.data, Delegation.class);
                return response;
            }
        });
    }

    /**
     * 查询当前账户地址所委托的节点的NodeID和质押Id
     *
     * @param address
     * @return
     */
    public RemoteCall<BaseResponse<List<DelegationIdInfo>>> getRelatedListByDelAddr(String address) {
        Function function = new Function(FunctionType.GET_DELEGATELIST_BYADDR_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(address))),
                Collections.EMPTY_LIST);

        return new RemoteCall<BaseResponse<List<DelegationIdInfo>>>(new Callable<BaseResponse<List<DelegationIdInfo>>>() {
            @Override
            public BaseResponse<List<DelegationIdInfo>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, DelegationIdInfo.class);
                return response;
            }
        });
    }

}
