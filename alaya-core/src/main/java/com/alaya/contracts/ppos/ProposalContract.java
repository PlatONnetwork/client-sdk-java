package com.alaya.contracts.ppos;

import com.alaya.abi.solidity.datatypes.BytesType;
import com.alaya.abi.solidity.datatypes.Utf8String;
import com.alaya.abi.solidity.datatypes.generated.Uint32;
import com.alaya.abi.solidity.datatypes.generated.Uint8;
import com.alaya.contracts.ppos.abi.Function;
import com.alaya.contracts.ppos.dto.CallResponse;
import com.alaya.contracts.ppos.dto.TransactionResponse;
import com.alaya.contracts.ppos.dto.common.FunctionType;
import com.alaya.contracts.ppos.dto.enums.VoteOption;
import com.alaya.contracts.ppos.dto.resp.GovernParam;
import com.alaya.contracts.ppos.dto.resp.Proposal;
import com.alaya.contracts.ppos.dto.resp.TallyResult;
import com.alaya.contracts.ppos.exception.EstimateGasException;
import com.alaya.contracts.ppos.exception.NoSupportFunctionType;
import com.alaya.crypto.Credentials;
import com.alaya.parameters.NetworkParameters;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.RemoteCall;
import com.alaya.protocol.core.methods.response.PlatonSendTransaction;
import com.alaya.protocol.core.methods.response.bean.ProgramVersion;
import com.alaya.tx.TransactionManager;
import com.alaya.tx.gas.GasProvider;
import com.alaya.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class ProposalContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 *
	 * @param web3j
	 * @return
	 */
    public static ProposalContract load(Web3j web3j, long chainId) {
        return new ProposalContract(NetworkParameters.getPposContractAddressOfProposal(chainId), web3j);
    }

    /**
     * 加载合约
     *
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static ProposalContract load(Web3j web3j, TransactionManager transactionManager, long chainId) {
    	return new ProposalContract(NetworkParameters.getPposContractAddressOfProposal(chainId), web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     *
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static ProposalContract load(Web3j web3j, Credentials credentials, long chainId) {
        return new ProposalContract(NetworkParameters.getPposContractAddressOfProposal(chainId), chainId, web3j, credentials);
    }

    private ProposalContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private ProposalContract(String contractAddress, long chainId, Web3j web3j, Credentials credentials) {
        super(contractAddress, chainId, web3j, credentials);
    }

    private ProposalContract(String contractAddress, Web3j web3j, TransactionManager transactionManager) {
        super(contractAddress, web3j, transactionManager);
    }

    /**
     * 查询提案
     *
     * @param proposalId
     * @return
     */
    public RemoteCall<CallResponse<Proposal>> getProposal(String proposalId) {
        Function function = new Function(FunctionType.GET_PROPOSAL_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(proposalId))));
        return executeRemoteCallObjectValueReturn(function, Proposal.class);
    }

    /**
     * 查询提案结果
     *
     * @param proposalId
     * @return
     */
    public RemoteCall<CallResponse<TallyResult>> getTallyResult(String proposalId) {
        Function function = new Function(FunctionType.GET_TALLY_RESULT_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(proposalId))));
        return executeRemoteCallObjectValueReturn(function, TallyResult.class);
    }

    /**
     * 获取提案列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Proposal>>> getProposalList() {
        Function function = new Function(FunctionType.GET_PROPOSAL_LIST_FUNC_TYPE,
                Arrays.asList());
        return executeRemoteCallListValueReturn(function, Proposal.class);
    }

    /**
     * 给提案投票
     *
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @return
     */
    public RemoteCall<TransactionResponse> vote(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier)  {
        Function function = createVoteFunction(programVersion, proposalID, verifier, voteOption);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 给提案投票
     *
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> vote(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier, GasProvider gasProvider)  {
        Function function = createVoteFunction(programVersion, proposalID, verifier, voteOption);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 给提案投票
     *
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @return
     */
    public GasProvider getVoteProposalGasProvider(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createVoteFunction(programVersion, proposalID, verifier, voteOption);
        return getDefaultGasProvider(function);
    }

    /**
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @return
     */
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier) {
        Function function = createVoteFunction(programVersion, proposalID, verifier, voteOption);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(ProgramVersion programVersion, String proposalID, String verifier, VoteOption voteOption, GasProvider gasProvider)  {
        Function function = createVoteFunction(programVersion, proposalID, verifier, voteOption);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createVoteFunction(ProgramVersion programVersion, String proposalID, String verifier, VoteOption voteOption) {
        Function function = new Function(FunctionType.VOTE_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue()),
                        new Uint32(programVersion.getProgramVersion()),
                        new BytesType(Numeric.hexStringToByteArray(programVersion.getProgramVersionSign()))));
        return function;
    }

    /**
     * 版本声明
     *
     * @param verifier 声明的节点，只能是验证人/候选人
     * @return
     */
    public RemoteCall<TransactionResponse> declareVersion(ProgramVersion programVersion, String verifier)  {
        Function function = createDeclareVersionFunction(programVersion, verifier);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 版本声明
     *
     * @param programVersion
     * @param verifier       声明的节点，只能是验证人/候选人
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> declareVersion(ProgramVersion programVersion, String verifier, GasProvider gasProvider) {
        Function function = createDeclareVersionFunction(programVersion, verifier);
        return executeRemoteCallTransaction(function, gasProvider);
    }

    /**
     * 获取版本声明的gasProvider
     *
     * @param programVersion
     * @param verifier
     * @return
     */
    public GasProvider getDeclareVersionGasProvider(ProgramVersion programVersion, String verifier) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createDeclareVersionFunction(programVersion, verifier);
        return getDefaultGasProvider(function);
    }

    /**
     * @param programVersion
     * @param verifier       声明的节点，只能是验证人/候选人
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(ProgramVersion programVersion, String verifier)  {
        Function function = createDeclareVersionFunction(programVersion, verifier);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * @param verifier    声明的节点，只能是验证人/候选人
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(ProgramVersion programVersion, String verifier, GasProvider gasProvider) {
        Function function = createDeclareVersionFunction(programVersion, verifier);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createDeclareVersionFunction(ProgramVersion programVersion, String verifier)  {
        Function function = new Function(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new Uint32(programVersion.getProgramVersion()),
                        new BytesType(Numeric.hexStringToByteArray(programVersion.getProgramVersionSign()))));
        return function;
    }

    /**
     * 提交提案
     *
     * @param proposal 包括文本提案和版本提案
     * @return
     */
    public RemoteCall<TransactionResponse> submitProposal(Proposal proposal) {
        Function function = createSubmitProposalFunction(proposal);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 提交提案
     *
     * @param proposal    包括文本提案和版本提案
     * @param gasProvider
     * @return
     */
    public RemoteCall<TransactionResponse> submitProposal(Proposal proposal, GasProvider gasProvider) {
        Function function = createSubmitProposalFunction(proposal);
        return executeRemoteCallTransaction(function,gasProvider);
    }

    /**
     * 获取提交提案gasProvider
     *
     * @param proposal
     * @return
     */
    public GasProvider getSubmitProposalGasProvider(Proposal proposal) throws IOException, EstimateGasException, NoSupportFunctionType {
        Function function = createSubmitProposalFunction(proposal);
        return getDefaultGasProvider(function);
    }

    /**
     * 提交提案
     *
     * @param proposal
     * @return
     */
    public RemoteCall<PlatonSendTransaction> submitProposalReturnTransaction(Proposal proposal) {
        Function function = createSubmitProposalFunction(proposal);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * 提交提案
     *
     * @param proposal
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> submitProposalReturnTransaction(Proposal proposal, GasProvider gasProvider) {
        Function function = createSubmitProposalFunction(proposal);
        return executeRemoteCallTransactionStep1(function, gasProvider);
    }

    private Function createSubmitProposalFunction(Proposal proposal) {
        if (proposal == null) {
            throw new NullPointerException("proposal must not be null");
        }
        Function function = new Function(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters());
        return function;
    }

    /**
     * 查询已生效的版本
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getActiveVersion() {
        Function function = new Function(FunctionType.GET_ACTIVE_VERSION);
        return executeRemoteCallObjectValueReturn(function, BigInteger.class);
    }

    /**
     * 查询当前块高的治理参数值
     *
     * @param module 参数模块
     * @param name   参数名称
     * @return
     */
    public RemoteCall<CallResponse<String>> getGovernParamValue(String module, String name) {
        Function function = new Function(FunctionType.GET_GOVERN_PARAM_VALUE, Arrays.asList(new Utf8String(module), new Utf8String(name)));
        return executeRemoteCallObjectValueReturn(function, String.class);
    }

    /**
     * 查询提案的累积可投票人数
     *
     * @param proposalId 提案ID
     * @param blockHash  块hash
     * @return
     */
    public RemoteCall<CallResponse<List<BigInteger>>> getAccuVerifiersCount(String proposalId, String blockHash) {
        Function function = new Function(FunctionType.GET_ACCUVERIFIERS_COUNT, Arrays.asList(new BytesType(Numeric.hexStringToByteArray(proposalId)), new BytesType(Numeric.hexStringToByteArray(blockHash))));
        return executeRemoteCallListValueReturn(function, BigInteger.class);
    }

    /**
     * 查询可治理参数列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<GovernParam>>> getParamList(String module) {
        Function function = new Function(FunctionType.GET_PARAM_LIST, Arrays.asList(new Utf8String(module)));
        return executeRemoteCallListValueReturn(function, GovernParam.class);
    }
}
