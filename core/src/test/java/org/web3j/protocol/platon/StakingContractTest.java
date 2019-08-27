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

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.127:6781"));

    private StakingContract stakingContract;

    String nodeId = "47eddf1110e92262fd593df81307eca0cb544669986baa702fe11942fca14e20bd7436f2da1c4b23c1c72a4873bd6b322c8525e4324f8c85ed55ae98d5a115f2";
    String stakingAmount = "5000000000000000000000000";
    StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
    String benifitAddress = "0x5e57ae97e714abe990c882377aaf9c57f4ea363b";
    String externalId = "liyf-test-id";
    String nodeName = "liyf-test";
    String webSite = "www.baidu.com";
    String details = "details";
    String blsPubKey = "cb261f65773299e9f04ae05ea3e4c82c96940bf5e2123659e847f50951bfd824d2422300b0160c7ea64bf19d05d2a1d413d95efc077b698d57ab488e1aa03420";

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0x59a9fac3bc8024169df74e6c0c861e1a5fdbe620b8a7a0c1dd0539d02c4e6add");

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

        String fromAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("59a9fac3bc8024169df74e6c0c861e1a5fdbe620b8a7a0c1dd0539d02c4e6add")));
        try {
            PlatonGetBalance platonGetBalance = web3j.platonGetBalance("0x"+fromAddress, DefaultBlockParameterName.LATEST).send();
            System.out.println(platonGetBalance.getBalance().longValue());
        } catch (IOException e) {
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
