package org.web3j.protocol.platon;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.bean.UpdateStakingParam;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonGetBalance;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.Transfer;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 质押相关接口，包括
 * 发起质押
 * 修改质押信息
 * 增持质押
 * 撤销质押
 */
public class StakingContractTest {

    //        private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.88:6788"));
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private StakingContract stakingContract;

    String nodeId = "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c";
    String stakingAmount = "5000000000000000000000000";
    StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
    String benifitAddress = "0x5e57ae97e714abe990c882377aaf9c57f4ea363b";
    String externalId = "liyf-test-id";
    String nodeName = "liyf-test";
    String webSite = "www.baidu.com";
    String details = "details";
    String blsPubKey = "80d98a48400a36e3da9de8e227e4a8c8fa3f90c08c82a467c9ac01298c2eb57f543d7e9568b0f381cc6c9de911870d1292b62459d083700d3958d775fca60e41ddd7d8532163f5acabaa6e0c47b626c39de51d9d67fb97a5af1871a661ca7788";

    private String privateKey2 = "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b";

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");

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

//        String fromAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b")));
//
//        try {
//            PlatonGetBalance platonGetBalance = web3j.platonGetBalance("0x"+fromAddress, DefaultBlockParameterName.LATEST).send();
//            System.out.println(platonGetBalance.getBalance().longValue());
//            System.out.println(web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST,false).send().getBlock().getNumber());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

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
                    .setProcessVersion(stakingContract.getProgramVersion())
                    .setBlsProof(stakingContract.getAdminSchnorrNIZKProve())
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
            PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
                    .setNodeId(nodeId)
                    .setBenifitAddress(benifitAddress)
                    .setExternalId(externalId)
                    .setNodeName("https://www.github.com/")
                    .setDetails(details)
                    .build()).send();
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
            PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, new BigInteger(stakingAmount)).send();
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
