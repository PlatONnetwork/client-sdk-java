package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;
import org.web3j.platon.contracts.NodeContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;
import org.web3j.utils.JSONUtil;

import java.util.List;

public class NodeContractTest {

    private String nodeId = "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429";

    //    private Web3j web3j = Web3jFactory.build(new HttpService("http://10.10.8.157:6789"));
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.9.76:6789"));

    private Credentials credentials;

    private NodeContract nodeContract;

    @Before
    public void init() {
        credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");

        nodeContract = NodeContract.load(web3j,
                credentials,
                new DefaultWasmGasProvider(), "100");
    }

    @Test
    public void getVerifierList() {
        try {
//            BaseResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();

            String json = "[{\n" +
                    "\t\"NodeId\": \"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050\",\n" +
                    "\t\"StakingAddress\": \"0x493301712671ada506ba6ca7891f436d29185821\",\n" +
                    "\t\"BenefitAddress\": \"0x1000000000000000000000000000000000000003\",\n" +
                    "\t\"StakingTxIndex\": 1,\n" +
                    "\t\"ProgramVersion\": 1792,\n" +
                    "\t\"Status\": 0,\n" +
                    "\t\"StakingEpoch\": 0,\n" +
                    "\t\"StakingBlockNum\": 0,\n" +
                    "\t\"Shares\": \"1000000000000000000000011200000000000000000000000000000789\",\n" +
                    "\t\"Released\": 1000000000000000000000011200000000000000000000000000000789,\n" +
                    "\t\"Released1\": 10000000000000000000000112,\n" +
                    "\t\"ReleasedHes\": 0,\n" +
                    "\t\"RestrictingPlan\": 0,\n" +
                    "\t\"RestrictingPlanHes\": 0,\n" +
                    "\t\"ExternalId\": \"\",\n" +
                    "\t\"NodeName\": \"platon.node.1\",\n" +
                    "\t\"Website\": \"www.platon.network\",\n" +
                    "\t\"Details\": \"The PlatON Node\"\n" +
                    "}]";
            List<Node> nodeList = JSONUtil.parseArray(json, Node.class);
//           List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //1101
    @Test
    public void getValidatorList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //1102
    @Test
    public void getCandidateList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
