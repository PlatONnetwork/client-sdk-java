//package com.platon.sdk.contracts.ppos;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.web3j.crypto.Credentials;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.core.DefaultBlockParameterName;
//import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
//import org.web3j.protocol.http.HttpService;
//import org.web3j.tx.Transfer;
//import org.web3j.utils.Convert.Unit;
//
//import com.platon.sdk.contracts.ppos.dto.BaseResponse;
//import com.platon.sdk.contracts.ppos.dto.CallResponse;
//import com.platon.sdk.contracts.ppos.dto.enums.VoteOption;
//import com.platon.sdk.contracts.ppos.dto.resp.Proposal;
//import com.platon.sdk.contracts.ppos.dto.resp.TallyResult;
//import com.platon.sdk.contracts.ppos.utils.ProposalUtils;
//
//
///**
// * 治理相关接口，包括，
// * 提交文本提案
// * 提交升级提案
// * 提交参数提案
// * 给提案投票
// * 版本声明
// * 查询提案
// * 查询提案结果
// * 查询提案列表
// * 查询生效版本
// * 查询节点代码版本
// * 查询可治理参数列表
// */
//public class ProposalContractTest {
//
//    private String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
//    long chainId = 103;
//    String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
//    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));
//
//    private Credentials superCredentials;
//    private Credentials stakingCredentials;
//    private Credentials voteCredentials;
//
//	private ProposalContract proposalContract;
//
//    @Before
//    public void init() throws Exception {
//
//    	superCredentials = Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
//    	System.out.println("superCredentials balance="+ web3j.platonGetBalance(superCredentials.getAddress(chainId), DefaultBlockParameterName.LATEST).send().getBalance());
//
//    	stakingCredentials = Credentials.create("0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
//    	System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(stakingCredentials.getAddress(chainId), DefaultBlockParameterName.LATEST).send().getBalance());
//
//    	voteCredentials = superCredentials;
//
//        proposalContract = ProposalContract.load(web3j, stakingCredentials, chainId);
//    }
//
//    @Test
//    public void transfer() throws Exception {
//    	Transfer.sendFunds(web3j, superCredentials, chainId, stakingCredentials.getAddress(chainId), new BigDecimal("10000"), Unit.LAT).send();
//    	System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(stakingCredentials.getAddress(chainId), DefaultBlockParameterName.LATEST).send().getBalance());
//    }
//
//
//    /**
//     * 提交文本提案
//     */
//    @Test
//    public void submitTextProposal() {
//        try {
//        	Proposal proposal = Proposal.createSubmitTextProposalParam(nodeId, String.valueOf(new Date().getTime()));
//            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//            System.out.println("发起提案结果："+baseResponse.toString());
//
//            voteForProposal(platonSendTransaction.getTransactionHash());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    /**
//     * 提交参数提案
//     */
//    @Test
//    public void submitParamProposal() {
//        try {
//
//            String module = "staking";
//            String name = "operatingThreshold";
//            String value = "50000000000000000000";
//
//        	Proposal proposal = Proposal.createSubmitParamProposalParam(nodeId, String.valueOf(new Date().getTime()), module, name, value);
//            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//            System.out.println("发起提案结果："+baseResponse.toString());
//
//            voteForProposal(platonSendTransaction.getTransactionHash());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 提交升级提案
//     */
//    @Test
//    public void submitVersionProposal() {
//        try {
//        	BigInteger newVersion =  ProposalUtils.versionStrToInteger("1.0.0");
//        	BigInteger endVotingRounds =  BigInteger.valueOf(4);
//            Proposal proposal = Proposal.createSubmitVersionProposalParam(nodeId, String.valueOf(new Date().getTime()), newVersion, endVotingRounds);
//            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//            System.out.println("发起提案结果："+baseResponse.toString());
//
//            voteForProposal(platonSendTransaction.getTransactionHash());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 提交升级取消提案
//     */
//    @Test
//    public void submitCancalVersionProposal() {
//        try {
//        	BigInteger newVersion =  ProposalUtils.versionStrToInteger("1.0.0");
//        	BigInteger endVotingRounds =  BigInteger.valueOf(4);
//            Proposal proposal = Proposal.createSubmitVersionProposalParam(nodeId,  String.valueOf(new Date().getTime()), newVersion, endVotingRounds);
//            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//            System.out.println("发起提案结果："+baseResponse.toString());
//
//            voteForProposal(platonSendTransaction.getTransactionHash());
//
//
//            //取消提案
//            System.out.println("取消提案开始");
//
//
//            PlatonSendTransaction platonSendTransactionCan = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitCancelProposalParam(nodeId, "35", BigInteger.valueOf(2),platonSendTransaction.getTransactionHash())).send();
//            BaseResponse baseResponseCan = proposalContract.getTransactionResponse(platonSendTransactionCan).send();
//            System.out.println(baseResponseCan.toString());
//
//            voteForProposal(platonSendTransactionCan.getTransactionHash());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 提交参数取消提案
//     */
//    @Test
//    public void submitCancalParamProposal() {
//        try {
//
//            String module = "staking";
//            String name = "operatingThreshold";
//            String value = "10000000000000000000";
//
//        	Proposal proposal = Proposal.createSubmitParamProposalParam(nodeId, String.valueOf(new Date().getTime()), module, name, value);
//            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(proposal).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//            System.out.println("发起提案结果："+baseResponse.toString());
//
//            voteForProposal(platonSendTransaction.getTransactionHash());
//
//
//            //取消提案
//            System.out.println("取消提案开始");
//
//
//            PlatonSendTransaction platonSendTransactionCan = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitCancelProposalParam(nodeId, String.valueOf(new Date().getTime()), BigInteger.valueOf(2),platonSendTransaction.getTransactionHash())).send();
//            BaseResponse baseResponseCan = proposalContract.getTransactionResponse(platonSendTransactionCan).send();
//            System.out.println(baseResponseCan.toString());
//
//            voteForProposal(platonSendTransactionCan.getTransactionHash());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public void voteForProposal(String proposalID) {
//        vote(proposalID,
//        		"0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47",
//        		"http://192.168.120.146:6789", VoteOption.YEAS);
//
//        vote(proposalID,
//        		"e0b6af6cc2e10b2b74540b87098083d48343805a3ff09c655eab0b20dba2b2851aea79ee75b6e150bde58ead0be03ee4a8619ea1dfaf529cbb8ff55ca23531ed",
//        		"http://192.168.120.147:6789", VoteOption.YEAS);
//
//        vote(proposalID,
//        		"15245d4dceeb7552b52d70e56c53fc86aa030eab6b7b325e430179902884fca3d684b0e896ea421864a160e9c18418e4561e9a72f911e2511c29204a857de71a",
//        		"http://192.168.120.148:6789", VoteOption.YEAS);
//
//        vote(proposalID,
//        		"fb886b3da4cf875f7d85e820a9b39df2170fd1966ffa0ddbcd738027f6f8e0256204e4873a2569ef299b324da3d0ed1afebb160d8ff401c2f09e20fb699e4005",
//        		"http://192.168.120.149:6789", VoteOption.YEAS);
//    }
//
//    public void vote(String proposalID, String nodeId, String nodeHost, VoteOption voteOption) {
//        try {
//        	Web3j web3j =  Web3j.build(new HttpService(nodeHost));
//        	ProposalContract voteContract = ProposalContract.load(web3j, voteCredentials, chainId);
//            BaseResponse baseResponse = voteContract.vote(web3j.getProgramVersion().send().getAdminProgramVersion(), voteOption,proposalID, nodeId).send();
//            System.out.println("投票结果："+baseResponse.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void queryResult() {
//        try {
//        	queryResult("0x8f2bcff7522165b9662e406cd5703930baca38a6946d7a48fc57dd2230fb1b50");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void queryResult(String proposalID) {
//        try {
//            CallResponse<Proposal> baseResponse = proposalContract.getProposal(proposalID).send();
//            System.out.println("提案信息："+baseResponse);
//            CallResponse<TallyResult> result = proposalContract.getTallyResult(proposalID).send();
//		    System.out.println("提案结果："+ result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 查询节点的链生效版本
//     */
//    @Test
//    public void getActiveVersion() {
//        try {
//            BaseResponse baseResponse = proposalContract.getActiveVersion().send();
//            System.out.println(baseResponse.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 版本声明
//     * activeNode 声明的节点，只能是验证人/候选人
//     */
//    @Test
//    public void declareVersion() {
//        try {
//
//            PlatonSendTransaction platonSendTransaction = proposalContract.declareVersionReturnTransaction(web3j.getProgramVersion().send().getAdminProgramVersion(), nodeId).send();
//            BaseResponse baseResponse = proposalContract.getTransactionResponse(platonSendTransaction).send();
//
//
//            System.out.println(baseResponse.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 查询提案列表
//     */
//    @Test
//    public void listProposal() {
//        try {
//            CallResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
//            List<Proposal> proposalList = baseResponse.getData();
//            for (Proposal proposal : proposalList) {
//            	CallResponse<TallyResult> result = proposalContract.getTallyResult(proposal.getProposalId()).send();
//				System.out.print(proposal);
//			    System.out.println(result);
//			}
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void getGovernParamValue() throws Exception {
//
//        String module = "staking";
//        String name = "operatingThreshold";
//        System.out.println(proposalContract.getGovernParamValue(module, name).send());
//    }
//
//    @Test
//    public void getParamList() throws Exception {
//    	String module = "staking";
//        System.out.println(proposalContract.getParamList(module).send());
//    }
//
//}
