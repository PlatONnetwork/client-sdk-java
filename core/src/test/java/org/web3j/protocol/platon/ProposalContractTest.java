package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.ProposalType;
import org.web3j.platon.VoteOption;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.List;


/**
 * 治理相关接口，包括，
 * 提交文本提案
 * 提交升级提案
 * 提交参数提案
 * 给提案投票
 * 版本声明
 * 查询提案
 * 查询提案结果
 * 查询提案列表
 * 查询生效版本
 * 查询节点代码版本
 * 查询可治理参数列表
 */
public class ProposalContractTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));
    private Credentials credentials;
    private String nodeId = "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c";
    private ProposalContract proposalContract;
    private String pIDID = "";

    @Before
    public void init() {

        credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");

        proposalContract = ProposalContract.load(web3j,
                credentials, "100");
    }

    /**
     * 查询提案列表
     */
    @Test
    public void listProposal() {
        try {
            BaseResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
            List<Proposal> proposalList = baseResponse.data;
            System.out.println(proposalList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交文本提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     */
    @Test
    public void submitTextProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            System.out.println(blockNumber);
            BigInteger endVoltingBlock = blockNumber.divide(BigInteger.valueOf(200)).multiply(BigInteger.valueOf(200)).add(BigInteger.valueOf(200).multiply(BigInteger.valueOf(10))).subtract(BigInteger.valueOf(10));

            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitTextProposalParam(nodeId, pIDID)).send();
            BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交升级提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * newVersion 升级版本
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     * activeBlock(endVotingBlock + 10 + 4*200 < 生效块高 <= endVotingBlock + 10 + 10*200)
     */
    @Test
    public void submitVersionProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            BigInteger endVoltingBlock = blockNumber.divide(BigInteger.valueOf(200)).multiply(BigInteger.valueOf(200)).add(BigInteger.valueOf(200).multiply(BigInteger.valueOf(10))).subtract(BigInteger.valueOf(10));
            BigInteger activeBlock = endVoltingBlock.add(BigInteger.valueOf(10)).add(BigInteger.valueOf(1000));
            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitVersionProposalParam(nodeId, pIDID, BigInteger.valueOf(5000), endVoltingBlock)).send();
            BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交参数提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * newVersion 升级版本
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     * paramName 参数名称
     * currentValue 当前值
     * newValue 新的值
     */
    @Test
    public void submitParamProposal() {
        try {
            BaseResponse baseResponse = proposalContract.submitProposal(Proposal.createSubmitCancelProposalParam(nodeId, pIDID, BigInteger.valueOf(100000), "")).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询节点的链生效版本
     */
    @Test
    public void getActiveVersion() {
        try {
            BaseResponse baseResponse = proposalContract.getActiveVersion().send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给提案投票
     * verifier 投票验证人
     * proposalID 提案ID
     * option 投票选项 Years(0x01)支持 Nays(0x02)反对 Abstentions(0x03)弃权
     */
    @Test
    public void vote() {
        try {
            BaseResponse baseResponse = proposalContract.vote("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747", "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c", VoteOption.YEAS).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 版本声明
     * activeNode 声明的节点，只能是验证人/候选人
     */
    @Test
    public void declareVersion() {
        try {
            BaseResponse baseResponse = proposalContract.declareVersion("411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c").send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询提案
     * proposalID 提案ID
     */
    @Test
    public void getProposal() {
        try {
            BaseResponse<Proposal> baseResponse = proposalContract.getProposal("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询提案结果
     * proposalID 提案ID
     */
    @Test
    public void getTallyResult() {
        try {
            BaseResponse<TallyResult> baseResponse = proposalContract.getTallyResult("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询节点代码版本
     */
    @Test
    public void getProgramVersion() {
        try {
            BaseResponse baseResponse = proposalContract.getProgramVersion().send();
            System.out.println(baseResponse.data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
