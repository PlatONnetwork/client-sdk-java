package org.web3j.protocol.platon;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.PlatOnEventEncoder;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.EconomicConfig;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.bean.UpdateStakingParam;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.DebugEconomicConfig;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonGetBalance;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.Transfer;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
//    private Web3j web3j = Web3j.build(new HttpService("https://aton.main.platon.network/rpc"));
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.9.190:443/rpc"));

    private StakingContract stakingContract;

    String nodeId = "4181b4611a5e76726add1f2aac376a9e62ceb4526a2ee2092466f37303fb2ec474b6a5f6f164330d2ee77b0a775ab84dd8e7b2a23042ee12a7e05b1d358b2538";
    String stakingAmount = "1800000000000000000000000";
    StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
    String benifitAddress = "0x82c53dcb0bb305ce1f0bfd092090f779e87c6f58";
    String externalId = "platonThreesomeId";
    String nodeName = "threesome";
    String webSite = "www.baidu.com";
    String details = "Three Some Team Node";
    String blsPubKey = "8bd1afb431ec95ba2b8f1fb63d396f6f1b0f4679f796b80c9e4b35272bbc1a38276f38478ea11e3acf30e861402ba00e201909ae4b9a548cf0dfac09402d09714f0969a11acaa5fd39c92d38fd712a31a6b009c8901a5e071d3775dd9ad10e96";

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0x4947398900e3f3f5e056102ea1ebbc6197118a099940057b2e40116aa1493e7d");

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

//        String fromAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("4947398900e3f3f5e056102ea1ebbc6197118a099940057b2e40116aa1493e7d")));
//
////        try {
////            PlatonGetBalance platonGetBalance = web3j.platonGetBalance("0xbe8c8bfc195f3c9f89405b0f2e749c7a0e9f91b0", DefaultBlockParameterName.LATEST).send();
////            PlatonBlock platonBlock = web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
////            System.out.println("jjjjj" + platonGetBalance.getBalance().toString(10));
////            System.out.println("bbbbb" + platonBlock.getBlock().getNumber().toString(10));
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        try {
//            PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
//                    .setNodeId(nodeId)
//                    .setAmount(new BigInteger(stakingAmount))
//                    .setStakingAmountType(stakingAmountType)
//                    .setBenifitAddress(benifitAddress)
//                    .setExternalId(externalId)
//                    .setNodeName(nodeName)
//                    .setWebSite(webSite)
//                    .setDetails(details)
//                    .setBlsPubKey(blsPubKey)
//                    .setProcessVersion(stakingContract.getProgramVersion())
//                    .setBlsProof(stakingContract.getAdminSchnorrNIZKProve())
//                    .build()).send();
//            BaseResponse baseResponse = stakingContract.getStakingResult(new PlatonSendTransaction()).send();
//            System.out.println(baseResponse.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
                    .setBenifitAddress("0x67ce01568e4157624cc15d427a742559e2f37bfe")
                    .setExternalId("4EEB8C9E9B711E02")
                    .setNodeName(nodeName)
                    .setDetails(details)
                    .setWebSite(webSite)
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
            PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, new BigInteger("80000000000000000000000")).send();
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

    @Test
    public void getEconomicConfig() {
        try {
            DebugEconomicConfig economicConfig = web3j.getEconomicConfig().send();
            System.out.println(JSONUtil.toJSONString(economicConfig.getEconomicConfig()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
