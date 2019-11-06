package org.web3j.ppos;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.web3j.Scenario;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.FunctionType;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.ProposalUtils;

public class ProposalScenario extends Scenario {
	
	private BigDecimal transferValue = new BigDecimal("1000");
	private int pid = new Random(System.currentTimeMillis()).nextInt();
	
	/**
	 * 正常的场景:
	 * 初始化账户余额
	 * 提交文本提案(2000)
	 * 给提案投票(2003)
	 * 查询提案(2100)
	 * 查询提案结果(2101)
	 * 查询提案列表(2102)
	 * 
	 * 提交升级提案(2001)
	 * 给提案投票(2003)
	 * 版本声明(2004)
	 * 查询生效版本(2103)
	 * 
	 * 提交取消提(2005)
	 * 给提案投票(2003)
	 */
	@Test
	public void executeScenario() throws Exception {
		//初始化账户余额
		transfer();
		
		BigInteger stakingBalance = web3j.platonGetBalance(proposalCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		BigInteger delegateBalance = web3j.platonGetBalance(voteCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		
		assertTrue(new BigDecimal(stakingBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		assertTrue(new BigDecimal(delegateBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		
		//提交文本提案(2000)
		BaseResponse createTextProposalResponse = createTextProposal();
		assertTrue(createTextProposalResponse.toString(),createTextProposalResponse.isStatusOk());
		String textProposalHash = createTextProposalResponse.transactionReceipt.getTransactionHash();
		
		//给提案投票(2003)
		for(VoteInfo voteInfo:voteInfos) {
			BaseResponse voteBaseResponse = vote(voteInfo,textProposalHash);
			assertTrue(voteBaseResponse.toString(),voteBaseResponse.isStatusOk());
		}
		
		//查询提案(2100)
		BaseResponse<Proposal> getProposalResponse = getProposal(textProposalHash);
		assertTrue(getProposalResponse.toString(),getProposalResponse.isStatusOk());
		
		//判断等待投票结果
		waitResult(getProposalResponse);
		
		//查询提案结果(2101)
		BaseResponse<TallyResult> getTallyResultResponse = getTallyResult(textProposalHash);
		assertTrue(getTallyResultResponse.toString(),getTallyResultResponse.isStatusOk());
		
		//查询提案列表(2102)
		BaseResponse<List<Proposal>> getProposalListResponse = getProposalList();
		assertTrue(getProposalListResponse.toString(),getProposalListResponse.isStatusOk());
		
		//提交升级提案(2001)
		BaseResponse createVersionProposalResponse = createVersionProposal();
		assertTrue(createVersionProposalResponse.toString(),createVersionProposalResponse.isStatusOk());
		//code = 302025 desc = verifier not upgraded
		//assertArrayEquals(createVersionProposalResponse.toString(), new int[] {createVersionProposalResponse.getCode()}, new int[] {302025});
		String versionProposalHash = createVersionProposalResponse.transactionReceipt.getTransactionHash(); 
		
		//版本声明(2004)
		BaseResponse declareVersionResponse = declareVersion();
		assertTrue(declareVersionResponse.toString(),declareVersionResponse.isStatusOk());
		
		//查询生效版本(2103)
		BaseResponse getActiveVersionResponse = getActiveVersion();
		assertTrue(getActiveVersionResponse.toString(),getActiveVersionResponse.isStatusOk());
		
		//提交取消提(2005)
		BaseResponse createCancalProposalResponse = createCancalProposal(versionProposalHash);
		assertTrue(createCancalProposalResponse.toString(),createCancalProposalResponse.isStatusOk());
		String cancalProposalHash = createCancalProposalResponse.transactionReceipt.getTransactionHash(); 
		
		//给提案投票(2003)
		for(VoteInfo voteInfo:voteInfos) {
			BaseResponse voteBaseResponse = vote(voteInfo,cancalProposalHash);
			assertTrue(voteBaseResponse.toString(),voteBaseResponse.isStatusOk());
		}
	}
	
	
    public BaseResponse createCancalProposal(String versionProposalId) throws Exception { 
    	Proposal proposal = Proposal.createSubmitCancelProposalParam(proposalNodeId, String.valueOf(pid+2), BigInteger.valueOf(2), versionProposalId);
    	PlatonSendTransaction platonSendTransactionCan = proposalContract.submitProposalReturnTransaction(proposal).send();
    	BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransactionCan, FunctionType.SUBMIT_CANCEL_FUNC_TYPE).send();
        return baseResponse;
    }
	
    public BaseResponse getActiveVersion() throws Exception {
        BaseResponse baseResponse = proposalContract.getActiveVersion().send();
        return baseResponse;
    }

    public BaseResponse declareVersion() throws Exception {
        PlatonSendTransaction platonSendTransaction = proposalContract.declareVersionReturnTransaction(proposalContract.getProgramVersion(),proposalNodeId).send();
        BaseResponse baseResponse = proposalContract.getDeclareVersionResult(platonSendTransaction).send();
        return baseResponse;
    }
	
    public BaseResponse createVersionProposal() throws Exception { 
    	BigInteger newVersion =  ProposalUtils.versionStrToInteger("100.0.0");
    	BigInteger endVotingRounds =  BigInteger.valueOf(4);
        Proposal proposal = Proposal.createSubmitVersionProposalParam(proposalNodeId,  String.valueOf(pid+1), newVersion, endVotingRounds);
        PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
        BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_VERSION_FUNC_TYPE).send();
        return baseResponse;
    }
    
    public BaseResponse<List<Proposal>> getProposalList() throws Exception {
        BaseResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
        return baseResponse;
    }
	
    public BaseResponse<TallyResult> getTallyResult(String proposalID) throws Exception {        
        BaseResponse<TallyResult> baseResponse = proposalContract.getTallyResult(proposalID).send();
        return baseResponse;
    }
	
    public BaseResponse<Proposal> getProposal(String proposalID) throws Exception {
        BaseResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
        return baseResponse;
    }
	
    public BaseResponse vote(VoteInfo voteInfo,String proposalID) throws Exception {   	
    	PlatonSendTransaction platonSendTransaction = voteInfo.getVoteContract().voteReturnTransaction(voteInfo.getVoteContract().getProgramVersion(), voteInfo.getVoteOption(),proposalID, voteInfo.getNodeId()).send();
        BaseResponse baseResponse = voteInfo.getVoteContract().getVoteResult(platonSendTransaction).send();
        return baseResponse;
    }
	
    public BaseResponse createTextProposal() throws Exception {   	
      	Proposal proposal = Proposal.createSubmitTextProposalParam(proposalNodeId, String.valueOf(pid));
        PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
        BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_TEXT_FUNC_TYPE).send();
        return baseResponse;
    }
	
	public void transfer() throws Exception {
		Transfer.sendFunds(web3j, superCredentials, chainId, proposalCredentials.getAddress(), transferValue, Unit.LAT).send();
		Transfer.sendFunds(web3j, superCredentials, chainId, voteCredentials.getAddress(), transferValue, Unit.LAT).send();
	}
	
	public void waitResult(BaseResponse<Proposal> getProposalResponse) throws Exception {
		int time = 0;
		while (true) {
			BigInteger endVotingBlock = getProposalResponse.data.getEndVotingBlock();
			BigInteger curBlockNumber = web3j.platonBlockNumber().send().getBlockNumber();
			if(curBlockNumber.compareTo(endVotingBlock)>0) {
				break;
			}
			TimeUnit.SECONDS.sleep(5);
			time++;
			if(time > 100) {
				throw new RuntimeException("等待超时");
			}
		}
	}
}
