package com.platon.contracts.ppos;

import com.platon.contracts.ppos.abi.Function;
import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.common.FunctionType;
import com.platon.contracts.ppos.dto.resp.Node;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.RemoteCall;

import java.util.List;

public class NodeContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static NodeContract load(Web3j web3j) {
        return new NodeContract(NetworkParameters.getPposContractAddressOfStaking(), web3j);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @return
     */
    public static NodeContract load(Web3j web3j, Credentials credentials) {
        return new NodeContract(NetworkParameters.getPposContractAddressOfStaking(), web3j, credentials);
    }

    private NodeContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private NodeContract(String contractAddress,  Web3j web3j, Credentials credentials) {
        super(contractAddress, web3j, credentials);
    }

    /**
     * 查询当前结算周期的验证人队列
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getVerifierList() {
        Function function = new Function(FunctionType.GET_VERIFIERLIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }

    /**
     * 查询当前共识周期的验证人列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getValidatorList() {
        Function function = new Function(FunctionType.GET_VALIDATORLIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }

    /**
     * 查询所有实时的候选人列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Node>>> getCandidateList() {
        Function function = new Function(FunctionType.GET_CANDIDATELIST_FUNC_TYPE);
        return executeRemoteCallListValueReturn(function, Node.class);
    }
}
