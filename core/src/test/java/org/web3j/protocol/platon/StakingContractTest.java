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
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

/**
 * 质押相关接口，包括
 * 发起质押
 * 修改质押信息
 * 增持质押
 * 撤销质押
 */
public class StakingContractTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private StakingContract stakingContract;

    String nodeId = "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c";
    String stakingAmount = "1000000000000000000000000000";
    StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
    String benifitAddress = "0x5e57ae97e714abe990c882377aaf9c57f4ea363b";
    String externalId = "liyf-test-id";
    String nodeName = "liyf-test";
    String webSite = "www.baidu.com";
    String details = "details";
    String blsPubKey = "cec189e90234b2c4d9e55402c1abf7cfbbc85dbf1b6b43820a2c6f953464c201bf6d1c3f51cf5e7cbc6e40815406f611b1aeca99acd782ed8b8e33c82f71ee08";

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");

        stakingContract = StakingContract.load(
                web3j,
                credentials, "100");

    }

    /**
     * 发起质押
     * typ 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * benefitAddress 用于接受出块奖励和质押奖励的收益账户
     * nodeId 被质押的节点Id(也叫候选人的节点Id)
     * externalId 外部Id(有长度限制，给第三方拉取节点描述的Id)
     * nodeName 被质押节点的名称(有长度限制，表示该节点的名称)
     * website 节点的第三方主页(有长度限制，表示该节点的主页)
     * details 节点的描述(有长度限制，表示该节点的描述)
     * amount 质押的von
     */
    @Test
    public void staking() {
        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
                    .setNodeId(nodeId)
                    .setAmount(new BigInteger(stakingAmount))
                    .setStakingAmountType(stakingAmountType)
                    .setBenifitAddress(benifitAddress)
                    .setExternalId(externalId)
                    .setNodeName(nodeName)
                    .setWebSite(webSite)
                    .setDetails(details)
                    .setBlsPubKey(blsPubKey)
                    .build()).send();
            BaseResponse baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
                    .setNodeId(nodeId)
                    .setAmount(new BigInteger(stakingAmount))
                    .setStakingAmountType(stakingAmountType)
                    .setBenifitAddress(benifitAddress)
                    .setExternalId(externalId)
                    .setNodeName(nodeName)
                    .setWebSite(webSite)
                    .setDetails(details)
                    .setBlsPubKey(blsPubKey)
                    .build()).send();
            BaseResponse baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 修改质押信息
     * benefitAddress 用于接受出块奖励和质押奖励的收益账户
     * nodeId 被质押的节点Id(也叫候选人的节点Id)
     * externalId 外部Id(有长度限制，给第三方拉取节点描述的Id)
     * nodeName 被质押节点的名称(有长度限制，表示该节点的名称)
     * website 节点的第三方主页(有长度限制，表示该节点的主页)
     * details 节点的描述(有长度限制，表示该节点的描述)
     */
    @Test
    public void updateStakingInfo() {
        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(nodeId, benifitAddress, externalId, nodeName, "https://www.github.com/", details).send();
            BaseResponse baseResponse = stakingContract.getUpdateStakingInfoResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 增持质押
     * nodeId 被质押的节点Id(也叫候选人的节点Id)
     * typ 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
     * amount 质押的von
     */
    @Test
    public void addStaking() {
        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, BigInteger.valueOf(10000)).send();
            BaseResponse baseResponse = stakingContract.getAddStakingResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 撤销质押(一次性发起全部撤销，多次到账)
     * nodeId 被质押的节点的NodeId
     */
    @Test
    public void unStaking() {
        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
            BaseResponse baseResponse = stakingContract.getUnStakingResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询当前节点的质押信息
     * nodeId 被质押的节点的NodeId
     */
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
