package com.platon.sdk.contracts.inner;

import java.util.List;

import org.web3j.crypto.Credentials;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.bean.Node;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;

import com.platon.sdk.contracts.inner.dto.CallResponse;

public class NodeContract extends BaseContract {
	
	/**
	 * 加载合约，ReadonlyTransactionManager管理
	 * @param web3j
	 * @return
	 */
    public static NodeContract load(Web3j web3j) {
        return new NodeContract(ContractAddress.NODE_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约，自定义TransactionManager管理
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static NodeContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new NodeContract(ContractAddress.NODE_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    private NodeContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private NodeContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    /**
     * 查询当前结算周期的验证人队列
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getVerifierList() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_VERIFIERLIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }

    /**
     * 查询当前共识周期的验证人列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getValidatorList() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_VALIDATORLIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }

    /**
     * 查询所有实时的候选人列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getCandidateList() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_CANDIDATELIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }
}
