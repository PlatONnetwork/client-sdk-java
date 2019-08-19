package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.datatypes.generated.Uint32;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.VoteOption;
import org.web3j.platon.bean.ParamProposal;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.platon.bean.TextProposal;
import org.web3j.platon.bean.VersionProposal;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import java.math.BigInteger;
import java.util.List;

public class ProposalContractTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6793"));
    private Credentials credentials;
    private ProposalContract proposalContract;

    @Before
    public void init() {

        credentials = Credentials.create("0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");

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
            BaseResponse baseResponse = proposalContract.submitProposal(new TextProposal.Builder()
                    .setVerifier("1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429")
                    .setUrl("http://www.test.inet")
                    .setEndVoltingBlock(BigInteger.valueOf(100000))
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void submitVersionProposal() {
        try {
            BaseResponse baseResponse = proposalContract.submitProposal(new VersionProposal.Builder()
                    .setVerifier("1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429")
                    .setUrl("http://www.test.inet")
                    .setEndVoltingBlock(BigInteger.valueOf(1000))
                    .setNewVersion(BigInteger.valueOf(1))
                    .setActiveBlock(BigInteger.valueOf(1000))
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void submitParamProposal() {
        try {
            BaseResponse baseResponse = proposalContract.submitProposal(new ParamProposal.Builder()
                    .setVerifier("1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429")
                    .setUrl("http://www.test.inet")
                    .setEndVoltingBlock(BigInteger.valueOf(100000))
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
            BaseResponse baseResponse = proposalContract.vote("0x12c171900f010b17e969702efa044d077e86808212c171900f010b17e969702e", "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429", VoteOption.YEAS).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void declareVersion() {
        try {
            BaseResponse baseResponse = proposalContract.declareVersion("1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429", BigInteger.valueOf(1)).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProposal() {
        try {
            BaseResponse<Proposal> baseResponse = proposalContract.getProposal("0x12c171900f010b17e969702efa044d077e86808212c171900f010b17e969702e").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTallyResult() {
        try {
            BaseResponse<TallyResult> baseResponse = proposalContract.getTallyResult("0x12c171900f010b17e969702efa044d077e86808212c171900f010b17e969702e").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getProgramVersion() {
        try {
//            BaseResponse baseResponse = proposalContract.getProgramVersion().send();
            Uint32 uint32 = new Uint32(65536);

            byte[] bytes = uint32.getValue().toByteArray();

            System.out.println(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getParamList() {
        try {
            BaseResponse<List<ParamProposal>> baseResponse = proposalContract.getParamList().send();
            List<ParamProposal> paramProposalList = baseResponse.data;
            System.out.println(paramProposalList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
