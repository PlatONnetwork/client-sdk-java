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
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.List;

public class ProposalContractTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));
    private Credentials credentials;
    private ProposalContract proposalContract;

    @Before
    public void init() {

        credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");

        proposalContract = ProposalContract.load(web3j,
                credentials, "100");
    }

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

    @Test
    public void submitTextProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            System.out.println(blockNumber);
            BigInteger endVoltingBlock = blockNumber.divide(BigInteger.valueOf(200)).multiply(BigInteger.valueOf(200)).add(BigInteger.valueOf(200).multiply(BigInteger.valueOf(10))).subtract(BigInteger.valueOf(10));

            BaseResponse baseResponse = proposalContract.submitProposal(new Proposal.Builder(ProposalType.TEXT_PROPOSAL)
                    .setVerifier("411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c")
                    .setUrl("http://www.test.inet")
                    .setEndVotingBlock(endVoltingBlock)
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void submitVersionProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            BigInteger endVoltingBlock = blockNumber.divide(BigInteger.valueOf(200)).multiply(BigInteger.valueOf(200)).add(BigInteger.valueOf(200).multiply(BigInteger.valueOf(10))).subtract(BigInteger.valueOf(10));
            BigInteger activeBlock = endVoltingBlock.add(BigInteger.valueOf(10)).add(BigInteger.valueOf(1000));
            BaseResponse baseResponse = proposalContract.submitProposal(new Proposal.Builder(ProposalType.VERSION_PROPOSAL)
                    .setVerifier("411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c")
                    .setUrl("http://www.test.inet")
                    .setEndVotingBlock(endVoltingBlock)
                    .setNewVersion(BigInteger.valueOf(5000))
                    .setActiveBlock(activeBlock)
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void submitParamProposal() {
        try {
            BaseResponse baseResponse = proposalContract.submitProposal(new Proposal.Builder(ProposalType.PARAM_PROPOSAL)
                    .setVerifier("1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429")
                    .setUrl("http://www.test.inet")
                    .setEndVotingBlock(BigInteger.valueOf(100000))
                    .setParamName("ParamName")
                    .setCurrentValue("0.85")
                    .setNewValue("1.02")
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getActiveVersion() {
        try {
            BaseResponse baseResponse = proposalContract.getActiveVersion().send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vote() {
        try {
            BaseResponse baseResponse = proposalContract.vote("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747", "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c", VoteOption.YEAS).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void declareVersion() {
        try {
            BaseResponse baseResponse = proposalContract.declareVersion("411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c").send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProposal() {
        try {
            BaseResponse<Proposal> baseResponse = proposalContract.getProposal("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTallyResult() {
        try {
            BaseResponse<TallyResult> baseResponse = proposalContract.getTallyResult("0x359a26418d0d5d4dbe3c392862fdcfe83e0e33fe5720897698ea403b82bbd747").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
