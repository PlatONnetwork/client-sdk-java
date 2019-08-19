package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class StakingContractTest {

    private static final int OFFSET_SHORT_ITEM = 0x80;
    private static final int OFFSET_LONG_ITEM = 0xb7;
    private static final int SIZE_THRESHOLD = 56;

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6795"));

    private StakingContract stakingContract;

    private String nodeId = "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429";
    private String benifitAddress = "12c171900f010b17e969702efa044d077e868082";
    private String externalId = "111111";
    private String nodeName = "platon";
    private String websites = "https://www.test.network";
    private String details = "supper node";

    private String address = "0x493301712671Ada506ba6Ca7891F436D29185821";
    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");

        stakingContract = StakingContract.load(
                web3j,
                credentials, "100");
    }


    @Test
    public void staking() {

//        try {
//            BaseResponse baseResponse = stakingContract.getProgramVersion(web3j).send();
//            System.out.println(baseResponse.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String nodeId = "3aca21c898c892dae5081682119573c86b4dec3d50875cd95358cc28068231929827f3ee95471cb857986ad7f4b7d64f54be493dc3d476f603bdc2bc8a64e79b";
        String stakingAmount = "1000000000000000000000000000";
        StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
        String benifitAddress = "0x5e57ae97e714abe990c882377aaf9c57f4ea363b";
        String externalId = "liyf-test-id";
        String nodeName = "liyf-test";
        String webSite = "www.baidu.com";
        String details = "details";
        String nodeVersion = "1792";

        try {
            BaseResponse baseResponse = stakingContract.staking(new StakingParam.Builder()
                    .setNodeId(nodeId)
                    .setAmount(new BigInteger(stakingAmount))
                    .setStakingAmountType(stakingAmountType)
                    .setBenifitAddress(benifitAddress)
                    .setExternalId(externalId)
                    .setNodeName(nodeName)
                    .setWebSite(webSite)
                    .setDetails(details)
                    .setProcessVersion(new BigInteger(stakingAmount))
                    .build()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void updateStakingInfo() {
        try {
            BaseResponse baseResponse = stakingContract.updateStakingInfo(nodeId, benifitAddress, externalId, nodeName, "www.baidu.com", details).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addStaking() {
        try {
            BaseResponse baseResponse = stakingContract.addStaking(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, BigInteger.valueOf(10000)).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unStaking() {
        try {
            BaseResponse baseResponse = stakingContract.unStaking(nodeId).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getStakingInfo() {
        try {
            BaseResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
            Node node = baseResponse.data;
            System.out.println(node.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
