package org.web3j.platon.contracts;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.FunctionType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.ParamProposal;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.VoteOption;
import org.web3j.platon.bean.TallyResult;
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

public class ProposalContract extends PlatOnContract {

    public static ProposalContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ProposalContract("", PROPOSAL_CONTRACT_ADDRESS, web3j, credentials, contractGasProvider);
    }

    public static ProposalContract load(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ProposalContract("", PROPOSAL_CONTRACT_ADDRESS, web3j, transactionManager, contractGasProvider);
    }

    public static ProposalContract load(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String chainId) {
        return new ProposalContract("", PROPOSAL_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    protected ProposalContract(String contractBinary, String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, transactionManager, gasProvider);
    }

    public ProposalContract(String contractBinary, String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, web3j, credentials, gasProvider);
    }

    public ProposalContract(String contractBinary, String contractAddress, String chainId, Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        super(contractBinary, contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 查询提案
     *
     * @param proposalId
     * @return
     */
    public RemoteCall<BaseResponse<Proposal>> getProposal(String proposalId) {
        Function function = new Function(FunctionType.GET_PROPOSAL_FUNC_TYPE,
                Arrays.asList(new Utf8String(proposalId)), Collections.emptyList());
        return new RemoteCall<BaseResponse<Proposal>>(new Callable<BaseResponse<Proposal>>() {
            @Override
            public BaseResponse<Proposal> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseObject((String) response.data, Proposal.class);
                return response;
            }
        });
    }

    /**
     * 查询提案结果
     *
     * @param proposalId
     * @return
     */
    public RemoteCall<BaseResponse<TallyResult>> getTallyResult(String proposalId) {
        Function function = new Function(FunctionType.GET_TALLY_RESULT_FUNC_TYPE,
                Arrays.asList(new Utf8String(proposalId)), Collections.emptyList());
        return new RemoteCall<BaseResponse<TallyResult>>(new Callable<BaseResponse<TallyResult>>() {
            @Override
            public BaseResponse<TallyResult> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseObject((String) response.data, TallyResult.class);
                return response;
            }
        });
    }

    /**
     * 获取提案列表
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<Proposal>>> getProposalList() {
        Function function = new Function(FunctionType.GET_PROPOSAL_LIST_FUNC_TYPE,
                Arrays.<Type>asList(), Collections.emptyList());
        return new RemoteCall<BaseResponse<List<Proposal>>>(new Callable<BaseResponse<List<Proposal>>>() {
            @Override
            public BaseResponse<List<Proposal>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, Proposal.class);
                return response;
            }
        });
    }

    /**
     * 给提案投票
     *
     * @param proposalID 提案ID
     * @param verifier   投票验证人
     * @param voteOption 投票选项
     * @return
     */
    public RemoteCall<BaseResponse> vote(String proposalID, String verifier, VoteOption voteOption) {
        Function function = new Function(FunctionType.VOTE_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue())),
                Collections.emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * @param proposalID 提案ID
     * @param verifier   投票验证人
     * @param voteOption 投票选项
     * @return
     */
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(String proposalID, String verifier, VoteOption voteOption) {
        Function function = new Function(FunctionType.VOTE_FUNC_TYPE,
                Arrays.<Type>asList(new Utf8String(verifier),
                        new Utf8String(proposalID), new Uint16(voteOption.getValue())),
                Collections.emptyList());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取投票结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getVoteResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.VOTE_FUNC_TYPE);
    }


    /**
     * 版本声明
     *
     * @param activeNode 声明的节点，只能是验证人/候选人
     * @param version    声明的版本
     * @return
     */
    public RemoteCall<BaseResponse> declareVersion(String activeNode, BigInteger version) {
        Function function = new Function(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(activeNode)),
                        new Uint32(version)),
                Collections.emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * @param activeNode 声明的节点，只能是验证人/候选人
     * @param version    声明的版本
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(String activeNode, BigInteger version) {
        Function function = new Function(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.<Type>asList(new Utf8String(activeNode),
                        new Uint16(version)),
                Collections.emptyList());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取版本声明的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getDeclareVersionResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.DELEGATE_FUNC_TYPE);
    }

    /**
     * 提交提案
     *
     * @param proposal 包括文本提案和版本提案
     * @return
     */
    public RemoteCall<BaseResponse> submitProposal(Proposal proposal) {
        if (proposal == null) {
            throw new NullPointerException("proposal must not be null");
        }
        Function function = new Function(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters(),
                Collections.emptyList());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 提交提案
     *
     * @param proposal
     * @return
     */
    public RemoteCall<PlatonSendTransaction> submitProposalReturnTransaction(Proposal proposal) {
        if (proposal == null) {
            throw new NullPointerException("proposal must not be null");
        }
        Function function = new Function(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters(),
                Collections.emptyList());
        return executeRemoteCallPlatonTransaction(function);
    }

    /**
     * 获取提交提案的结果
     *
     * @param ethSendTransaction
     * @return
     */
    public RemoteCall<BaseResponse> getSubmitProposalResult(PlatonSendTransaction ethSendTransaction) {
        return executeRemoteCallTransactionWithFunctionType(ethSendTransaction, FunctionType.SUBMIT_TEXT_FUNC_TYPE);
    }

    /**
     * 查询已生效的版本
     *
     * @return
     */
    public RemoteCall<BaseResponse> getActiveVersion() {
        final Function function = new Function(FunctionType.GET_ACTIVE_VERSION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return new RemoteCall<BaseResponse>(new Callable<BaseResponse>() {
            @Override
            public BaseResponse call() throws Exception {
                return executePatonCall(function);
            }
        });
    }

    /**
     * 查询节点代码版本
     *
     * @return
     */
    public RemoteCall<BaseResponse> getProgramVersion() {
        final Function function = new Function(FunctionType.GET_PROGRAM_VERSION,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 查询可治理参数列表
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<ParamProposal>>> getParamList() {
        final Function function = new Function(FunctionType.GET_PARAM_LIST,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return new RemoteCall<BaseResponse<List<ParamProposal>>>(new Callable<BaseResponse<List<ParamProposal>>>() {
            @Override
            public BaseResponse<List<ParamProposal>> call() throws Exception {
                BaseResponse response = executePatonCall(function);
                response.data = JSONUtil.parseArray((String) response.data, Node.class);
                return response;
            }
        });
    }
}
