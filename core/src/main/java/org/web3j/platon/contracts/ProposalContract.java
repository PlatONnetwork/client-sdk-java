package org.web3j.platon.contracts;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ContractAddress;
import org.web3j.platon.FunctionType;
import org.web3j.platon.PlatOnFunction;
import org.web3j.platon.VoteOption;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.ParamProposal;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.PlatOnContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;

public class ProposalContract extends PlatOnContract {

    /**
     * 查询操作
     *
     * @param web3j
     * @return
     */
    public static ProposalContract load(Web3j web3j) {
        return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, web3j);
    }

    /**
     * sendRawTransaction 使用用户自定义的gasProvider，必须传chainId
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static ProposalContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    /**
     * sendRawTransaction 使用默认的gasProvider，必须传chainId
     *
     * @param web3j
     * @param credentials
     * @param contractGasProvider
     * @param chainId
     * @return
     */
    public static ProposalContract load(Web3j web3j, Credentials credentials, GasProvider contractGasProvider, String chainId) {
        return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, chainId, web3j, credentials, contractGasProvider);
    }

    private ProposalContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private ProposalContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private ProposalContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials, GasProvider gasProvider) {
        super(contractAddress, chainId, web3j, credentials, gasProvider);
    }

    /**
     * 查询提案
     *
     * @param proposalId
     * @return
     */
    public RemoteCall<BaseResponse<Proposal>> getProposal(String proposalId) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PROPOSAL_FUNC_TYPE,
                Arrays.asList(new Utf8String(proposalId)));
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_TALLY_RESULT_FUNC_TYPE,
                Arrays.asList(new Utf8String(proposalId)));
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PROPOSAL_LIST_FUNC_TYPE,
                Arrays.<Type>asList());
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
    public RemoteCall<BaseResponse> vote(String proposalID, String verifier, VoteOption voteOption) throws Exception {
        BigInteger programVersion = new BigInteger(getProgramVersion().send().data);
        PlatOnFunction function = new PlatOnFunction(FunctionType.VOTE_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue()),
                        new Uint32(programVersion)));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 给提案投票
     *
     * @param proposalID 提案ID
     * @param verifier   投票验证人
     * @param voteOption 投票选项
     * @return
     */
    public Observable<GasProvider> getVoteProposalGasProvider(String proposalID, String verifier, VoteOption voteOption) {
        return Observable.fromCallable(new Callable<BigInteger>() {
            @Override
            public BigInteger call() throws Exception {
                return new BigInteger(getProgramVersion().send().data);
            }
        }).map(new Func1<BigInteger, GasProvider>() {
            @Override
            public GasProvider call(BigInteger bigInteger) {
                return new PlatOnFunction(FunctionType.VOTE_FUNC_TYPE,
                        Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                                new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue()),
                                new Uint32(bigInteger))).getGasProvider();
            }
        });
    }

    /**
     * @param proposalID 提案ID
     * @param verifier   投票验证人
     * @param voteOption 投票选项
     * @return
     */
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(String proposalID, String verifier, VoteOption voteOption) throws Exception {
        BigInteger programVersion = new BigInteger(getProgramVersion().send().data);
        PlatOnFunction function = new PlatOnFunction(FunctionType.VOTE_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue()),
                        new Uint32(programVersion)));
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
     * @return
     */
    public RemoteCall<BaseResponse> declareVersion(String activeNode) throws Exception{
        BigInteger processVersion = new BigInteger(getProgramVersion().send().data);
        PlatOnFunction function = new PlatOnFunction(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(activeNode)),
                        new Uint32(processVersion)));
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取版本声明的gasProvider
     *
     * @param activeNode
     * @return
     */
    public Observable<GasProvider> getDeclareVersionGasProvider(String activeNode) {
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                BigInteger processVersion = new BigInteger(getProgramVersion().send().data);
                return new PlatOnFunction(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                        Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(activeNode)),
                                new Uint32(processVersion))).getGasProvider();
            }
        });
    }

    /**
     * @param activeNode 声明的节点，只能是验证人/候选人
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(String activeNode) throws Exception{
        BigInteger processVersion = new BigInteger(getProgramVersion().send().data);
        PlatOnFunction function = new PlatOnFunction(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.<Type>asList(new BytesType(Numeric.hexStringToByteArray(activeNode)),
                        new Uint32(processVersion)));
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
        PlatOnFunction function = new PlatOnFunction(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters());
        return executeRemoteCallTransactionWithFunctionType(function);
    }

    /**
     * 获取提交提案gasProvider
     * @param proposal
     * @return
     */
    public Observable<GasProvider> getSubmitProposalGasProvider(Proposal proposal){
        if (proposal == null) {
            return Observable.error(new Throwable("proposal must not be null"));
        }
        return Observable.fromCallable(new Callable<GasProvider>() {
            @Override
            public GasProvider call() throws Exception {
                return new PlatOnFunction(proposal.getSubmitFunctionType(),
                        proposal.getSubmitInputParameters()).getGasProvider();
            }
        });
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
        PlatOnFunction function = new PlatOnFunction(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters());
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
        final PlatOnFunction function = new PlatOnFunction(FunctionType.GET_ACTIVE_VERSION);
        return new RemoteCall<BaseResponse>(new Callable<BaseResponse>() {
            @Override
            public BaseResponse call() throws Exception {
                return executePatonCall(function);
            }
        });
    }

    /**
     * 查询可治理参数列表
     *
     * @return
     */
    public RemoteCall<BaseResponse<List<ParamProposal>>> getParamList() {
        final PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PARAM_LIST);
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
