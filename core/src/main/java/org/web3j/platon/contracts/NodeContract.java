package org.web3j.platon.contracts;

import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.bean.Node;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;

import java.util.List;
import java.util.concurrent.Callable;

public class NodeContract extends PlatOnContract {

    public static NodeContract load(Web3j web3j) {
        return new NodeContract(ContractAddress.NODE_CONTRACT_ADDRESS, web3j);
    }

    public static NodeContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new NodeContract(ContractAddress.NODE_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    public static NodeContract load(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, String chainId) {
        return new NodeContract(ContractAddress.NODE_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    private NodeContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private NodeContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private NodeContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials, GasProvider gasProvider) {
        super(contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 查询当前结算周期的验证人队列
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<Node>>> getVerifierList() {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.GET_VERIFIERLIST_FUNC_TYPE);
        return new RemoteCall<BaseResponse<List<Node>>>(new Callable<BaseResponse<List<Node>>>() {
            @Override
            public BaseResponse<List<Node>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, Node.class);
                return response;
            }
        });
    }

    /**
     * 查询当前共识周期的验证人列表
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<Node>>> getValidatorList() {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.GET_VALIDATORLIST_FUNC_TYPE);
        return new RemoteCall<BaseResponse<List<Node>>>(new Callable<BaseResponse<List<Node>>>() {
            @Override
            public BaseResponse<List<Node>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, Node.class);
                return response;
            }
        });
    }

    /**
     * 查询所有实时的候选人列表
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<Node>>> getCandidateList() {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.GET_CANDIDATELIST_FUNC_TYPE);
        return new RemoteCall<BaseResponse<List<Node>>>(new Callable<BaseResponse<List<Node>>>() {
            @Override
            public BaseResponse<List<Node>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, Node.class);
                return response;
            }
        });
    }
}
