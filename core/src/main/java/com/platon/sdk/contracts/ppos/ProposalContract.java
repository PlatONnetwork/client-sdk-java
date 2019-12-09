package com.platon.sdk.contracts.ppos;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.platon.bean.ProgramVersion;
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
import com.platon.sdk.contracts.ppos.dto.enums.VoteOption;
import com.platon.sdk.contracts.ppos.dto.resp.GovernParam;
import com.platon.sdk.contracts.ppos.dto.resp.Proposal;
import com.platon.sdk.contracts.ppos.dto.resp.TallyResult;

public class ProposalContract extends BaseContract {

	/**
	 * 加载合约, 默认ReadonlyTransactionManager事务管理
	 * 
	 * @param web3j
	 * @return
	 */
    public static ProposalContract load(Web3j web3j) {
        return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, web3j);
    }
    
    /**
     * 加载合约
     * 
     * @param web3j
     * @param transactionManager
     * @return
     */
    public static ProposalContract load(Web3j web3j, TransactionManager transactionManager) {
    	return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, web3j, transactionManager);
    }

    /**
     * 加载合约, 默认RawTransactionManager事务管理
     * 
     * @param web3j
     * @param credentials
     * @param chainId
     * @return
     */
    public static ProposalContract load(Web3j web3j, Credentials credentials, String chainId) {
        return new ProposalContract(ContractAddress.PROPOSAL_CONTRACT_ADDRESS, chainId, web3j, credentials);
    }

    private ProposalContract(String contractAddress, Web3j web3j) {
        super(contractAddress, web3j);
    }

    private ProposalContract(String contractAddress, String chainId, Web3j web3j, Credentials credentials) {
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PROPOSAL_FUNC_TYPE,
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_TALLY_RESULT_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(proposalId))));
        return executeRemoteCallObjectValueReturn(function, TallyResult.class);
    }

    /**
     * 获取提案列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<Proposal>>> getProposalList() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PROPOSAL_LIST_FUNC_TYPE,
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
    public RemoteCall<TransactionResponse> vote(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier) throws Exception {
        PlatOnFunction function = createVoteFunction(programVersion, proposalID, verifier, voteOption, null);
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
    public RemoteCall<TransactionResponse> vote(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createVoteFunction(programVersion, proposalID, verifier, voteOption, gasProvider);
        return executeRemoteCallTransaction(function);
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
    public GasProvider getVoteProposalGasProvider(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier) throws Exception {
        PlatOnFunction function = createVoteFunction(programVersion, proposalID, verifier, voteOption, null);
        return function.getGasProvider();
    }

    /**
     * @param programVersion
     * @param voteOption     投票选项
     * @param proposalID     提案ID
     * @param verifier       投票验证人
     * @return
     */
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(ProgramVersion programVersion, VoteOption voteOption, String proposalID, String verifier) throws Exception {
        PlatOnFunction function = createVoteFunction(programVersion, proposalID, verifier, voteOption, null);
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
    public RemoteCall<PlatonSendTransaction> voteReturnTransaction(ProgramVersion programVersion, String proposalID, String verifier, VoteOption voteOption, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createVoteFunction(programVersion, proposalID, verifier, voteOption, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    private PlatOnFunction createVoteFunction(ProgramVersion programVersion, String proposalID, String verifier, VoteOption voteOption, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = new PlatOnFunction(FunctionType.VOTE_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new BytesType(Numeric.hexStringToByteArray(proposalID)), new Uint8(voteOption.getValue()),
                        new Uint32(programVersion.getProgramVersion()),
                        new BytesType(Numeric.hexStringToByteArray(programVersion.getProgramVersionSign()))), gasProvider);
        return function;
    }

    /**
     * 版本声明
     *
     * @param verifier 声明的节点，只能是验证人/候选人
     * @return
     */
    public RemoteCall<TransactionResponse> declareVersion(ProgramVersion programVersion, String verifier) throws Exception {
        PlatOnFunction function = createDeclareVersionFunction(programVersion, verifier, null);
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
    public RemoteCall<TransactionResponse> declareVersion(ProgramVersion programVersion, String verifier, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createDeclareVersionFunction(programVersion, verifier, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取版本声明的gasProvider
     *
     * @param programVersion
     * @param verifier
     * @return
     */
    public GasProvider getDeclareVersionGasProvider(ProgramVersion programVersion, String verifier) throws Exception {
        PlatOnFunction function = createDeclareVersionFunction(programVersion, verifier, null);
        return function.getGasProvider();
    }

    /**
     * @param programVersion
     * @param verifier       声明的节点，只能是验证人/候选人
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(ProgramVersion programVersion, String verifier) throws Exception {
        PlatOnFunction function = createDeclareVersionFunction(programVersion, verifier, null);
        return executeRemoteCallTransactionStep1(function);
    }

    /**
     * @param verifier    声明的节点，只能是验证人/候选人
     * @param gasProvider
     * @return
     */
    public RemoteCall<PlatonSendTransaction> declareVersionReturnTransaction(ProgramVersion programVersion, String verifier, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = createDeclareVersionFunction(programVersion, verifier, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }
    
    private PlatOnFunction createDeclareVersionFunction(ProgramVersion programVersion, String verifier, GasProvider gasProvider) throws Exception {
        PlatOnFunction function = new PlatOnFunction(FunctionType.DECLARE_VERSION_FUNC_TYPE,
                Arrays.asList(new BytesType(Numeric.hexStringToByteArray(verifier)),
                        new Uint32(programVersion.getProgramVersion()),
                        new BytesType(Numeric.hexStringToByteArray(programVersion.getProgramVersionSign()))), gasProvider);
        return function;
    }

    /**
     * 提交提案
     *
     * @param proposal 包括文本提案和版本提案
     * @return
     */
    public RemoteCall<TransactionResponse> submitProposal(Proposal proposal) {
        PlatOnFunction function = createSubmitProposalFunction(proposal, null);
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
        PlatOnFunction function = createSubmitProposalFunction(proposal, gasProvider);
        return executeRemoteCallTransaction(function);
    }

    /**
     * 获取提交提案gasProvider
     *
     * @param proposal
     * @return
     */
    public GasProvider getSubmitProposalGasProvider(Proposal proposal) {
        PlatOnFunction function = createSubmitProposalFunction(proposal, null);
        return function.getGasProvider();
    }

    /**
     * 提交提案
     *
     * @param proposal
     * @return
     */
    public RemoteCall<PlatonSendTransaction> submitProposalReturnTransaction(Proposal proposal) {
        PlatOnFunction function = createSubmitProposalFunction(proposal, null);
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
        PlatOnFunction function = createSubmitProposalFunction(proposal, gasProvider);
        return executeRemoteCallTransactionStep1(function);
    }

    private PlatOnFunction createSubmitProposalFunction(Proposal proposal, GasProvider gasProvider) {
        if (proposal == null) {
            throw new NullPointerException("proposal must not be null");
        }
        PlatOnFunction function = new PlatOnFunction(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters(), gasProvider);
        return function;
    }

    /**
     * 查询已生效的版本
     *
     * @return
     */
    public RemoteCall<CallResponse<BigInteger>> getActiveVersion() {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_ACTIVE_VERSION);
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_GOVERN_PARAM_VALUE, Arrays.asList(new Utf8String(module), new Utf8String(name)));
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
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_ACCUVERIFIERS_COUNT, Arrays.asList(new BytesType(Numeric.hexStringToByteArray(proposalId)), new BytesType(Numeric.hexStringToByteArray(blockHash))));
        return executeRemoteCallListValueReturn(function, BigInteger.class);
    }

    /**
     * 查询可治理参数列表
     *
     * @return
     */
    public RemoteCall<CallResponse<List<GovernParam>>> getParamList(String module) {
        PlatOnFunction function = new PlatOnFunction(FunctionType.GET_PARAM_LIST, Arrays.asList(new Utf8String(module)));
        return executeRemoteCallListValueReturn(function, GovernParam.class);
    }
}
