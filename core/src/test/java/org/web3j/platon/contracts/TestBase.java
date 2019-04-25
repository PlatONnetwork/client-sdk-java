package org.web3j.platon.contracts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.bouncycastle.util.encoders.Hex;
import org.junit.Assert;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.platon.contracts.bean.Node;
import org.web3j.platon.contracts.data.TestData;
import org.web3j.platon.contracts.util.FakeNodeGenerator;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultWasmGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Files;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Chendongming
 * @Date: 2019/4/16 11:28
 * @Description:
 */
public class TestBase {
    private Logger logger = LoggerFactory.getLogger(TestBase.class);
    public static final String web3jAddress = TestData.web3jTestAddress;
    protected Web3j web3j = Web3j.build(new HttpService(web3jAddress));
    protected String adminWalletPassword = TestData.adminWalletPassword;
    protected String adminWalletAddress = TestData.adminTestWalletAddress;
    protected String adminWalletRelativePath = TestData.adminTestWalletRelativePath;

    protected String multisigBinary;
    protected Credentials adminCredentials;

    protected Credentials ownerCredentials;
    protected String ownerWalletAddress = TestData.ownerWalletAddress;
    protected String ownerWalletRelativePath = TestData.ownerWalletRelativePath;
    protected String ownerWalletPassword = TestData.ownerWalletPassword;
    protected String multisigContractRelativePath = TestData.multisigContractRelativePath;
    protected CandidateContract candidateContract;
    protected TicketContract ticketContract;
    //质押金额, 单位 wei
    protected BigInteger depositAmount = TestData.depositAmount;

    @Before
    public void init() throws IOException, CipherException {
        URL url;
        String path;

        url = TestBase.class.getClassLoader().getResource(adminWalletRelativePath);
        path = url.getPath();
        adminCredentials = WalletUtils.loadCredentials(adminWalletPassword, path);

        url = TestBase.class.getClassLoader().getResource(ownerWalletRelativePath);
        path = url.getPath();
        ownerCredentials = WalletUtils.loadCredentials(ownerWalletPassword, path);

        url = TestBase.class.getClassLoader().getResource(multisigContractRelativePath);
        path = url.getPath();
        byte[] dataBytes = Files.readBytes(new File(path));
        multisigBinary = Hex.toHexString(dataBytes);

        candidateContract = CandidateContract.load(web3j, adminCredentials, new DefaultWasmGasProvider());
        ticketContract = TicketContract.load(web3j, adminCredentials, new DefaultWasmGasProvider());
    }

    protected BigDecimal balanceInETH(String address) throws IOException {
        BigInteger balance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send().getBalance();
        BigDecimal bal = Convert.fromWei(balance.toString(), Convert.Unit.ETHER);
        return bal;
    }

    protected List<Node> deposit(int depositCount){
        List<Node> nodes = FakeNodeGenerator.getFakeNodes("http://","192.168.9",12789, 13250, depositCount);
        nodes.forEach(node -> {
            try {
                deposit(node);
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        return nodes;
    }

    protected void deposit(Node node) throws InterruptedException {
        // 节点ID
        String nodeId = node.getId();
        //节点IP
        String host = node.getIp();
        //质押金退款地址
        String owner = adminWalletAddress;
        //出块奖励佣金比，以10000为基数(eg：5%，则fee=500)
        BigInteger fee =  BigInteger.valueOf(500L);
        //节点P2P端口号
        String port = node.getP2pPort();
        //附加数据
        JSONObject extra = new JSONObject();
        //节点名称
        extra.put("nodeName", node.getName());
        //节点logo
        extra.put("nodePortrait", "1");
        //机构简介
        extra.put("nodeDiscription", "Unit Test "+node.getName());
        //机构名称
        extra.put("nodeDepartment", "Unit Test "+node.getName());
        //官网
        extra.put("officialWebsite", "https://unittest.com/");

        try {
            //调用接口
            TransactionReceipt receipt = candidateContract.CandidateDeposit(nodeId, owner, fee, host, port, extra.toJSONString(),depositAmount).send();
            Assert.assertNotNull(receipt);
            logger.info("CandidateDeposit TransactionReceipt:{}", JSON.toJSONString(receipt));
            //查看返回event
            List<CandidateContract.CandidateDepositEventEventResponse>  events = candidateContract.getCandidateDepositEventEvents(receipt);
            Assert.assertNotNull(events);
            for (CandidateContract.CandidateDepositEventEventResponse event : events) {
                Assert.assertNotNull(event.param1);
                logger.info("CandidateDeposit event:{}", JSON.toJSONString(event.param1));
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(1);
    }

    protected String voteTicket(Node node) throws InterruptedException {
        String txHash = null;
        //购票数量
        BigInteger count = BigInteger.valueOf(1L);
        try {
            //选票单价
            String priceStr = ticketContract.GetTicketPrice().send();
            Assert.assertNotNull(priceStr);
            BigInteger price =  new BigInteger(priceStr);
            //调用接口
            TransactionReceipt receipt = ticketContract.VoteTicket(count, price, node.getId()).send();
            Assert.assertNotNull(receipt);
            logger.info("TicketContract TransactionReceipt:{}", JSON.toJSONString(receipt));
            //查看返回event
            List<TicketContract.VoteTicketEventEventResponse> events = ticketContract.getVoteTicketEventEvents(receipt);
            Assert.assertNotNull(events);
            for (TicketContract.VoteTicketEventEventResponse event : events) {
                Assert.assertNotNull(event.param1);
                JSONObject result = JSON.parseObject(event.param1);
                logger.info("TicketContract event:{}", result);
            }
            txHash = receipt.getTransactionHash();
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        if(txHash==null) throw new RuntimeException("Tx hash is null!");
        TimeUnit.SECONDS.sleep(1);
        return txHash;

//        20:09:43.610 [main] DEBUG org.web3j.ticketContract.test.TicketContractTest - TicketContract TransactionReceipt:{"blockHash":"0x85a49bf683c123b4e650721338acad3c6bd3ff71e0f20de8baaf16e5e3e90f67","blockNumber":15548,"blockNumberRaw":"0x3cbc","cumulativeGasUsed":34832,"cumulativeGasUsedRaw":"0x8810","from":"0x493301712671ada506ba6ca7891f436d29185821","gasUsed":34832,"gasUsedRaw":"0x8810","logs":[{"address":"0x1000000000000000000000000000000000000002","blockHash":"0x85a49bf683c123b4e650721338acad3c6bd3ff71e0f20de8baaf16e5e3e90f67","blockNumber":15548,"blockNumberRaw":"0x3cbc","data":"0xebaa7b22526574223a747275652c2244617461223a2235222c224572724d7367223a2273756363657373227d","logIndex":0,"logIndexRaw":"0x0","removed":false,"topics":["0xee64e28ecf7347fbf257cb1ced716cf9d51a1e24f06e0bbe2c3d4f40924049c9"],"transactionHash":"0x7afe907caf45da59bef88b6b708fe45958143a41baa51f6c4410f87d1338e49f","transactionIndex":0,"transactionIndexRaw":"0x0"}],"logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000001000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000200000000000000","status":"0x1","statusOK":true,"to":"0x1000000000000000000000000000000000000002","transactionHash":"0x7afe907caf45da59bef88b6b708fe45958143a41baa51f6c4410f87d1338e49f","transactionIndex":0,"transactionIndexRaw":"0x0"}
//        20:09:43.629 [main] DEBUG org.web3j.ticketContract.test.TicketContractTest - TicketContract event:{"Ret":true,"ErrMsg":"success","Data":"5"}
//        20:09:43.633 [main] DEBUG org.web3j.ticketContract.test.TicketContractTest - TicketContract tickets:[0x6bf2236d95a98c798abf760e43d8a1a0f375ce095f6f286198053800262988c5, 0x7f3d95634ebdbf0121a7de207b00cf2d2b4846000ec41b4a8a88d1e019701a5e, 0x57d1a6fcd7932a9615f593dbc6d6999912e170286ed1be0c0b31f6f14087c3b4, 0x3ad918e72305b6666efe2abd232554c34bab3f7cd5aa679334cb3ca3a14851ce, 0xe4f0b42906eed5f5e77cb233bd11d30e664f32b5602bda7f84d155462d496cbb]
    }

    protected void applyWithdraw(Node node) throws InterruptedException {
        //退款金额, 单位 wei
        try {
            //调用接口
            TransactionReceipt receipt = candidateContract.CandidateApplyWithdraw(node.getId(), depositAmount).send();
            Assert.assertNotNull(receipt);
            logger.info("TransactionReceipt:{}", JSON.toJSONString(receipt));
            //查看返回event
            List<CandidateContract.CandidateApplyWithdrawEventEventResponse>  events = candidateContract.getCandidateApplyWithdrawEventEvents(receipt);
            Assert.assertNotNull(events);
            for (CandidateContract.CandidateApplyWithdrawEventEventResponse event : events) {
                Assert.assertNotNull(event.param1);
                logger.info("event:{}", JSON.toJSONString(event.param1));
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(1);
    }

    protected void withdraw(Node node) throws InterruptedException {
        try {
            //调用接口
            TransactionReceipt receipt = candidateContract.CandidateWithdraw(node.getId()).send();
            Assert.assertNotNull(receipt);
            logger.info("TransactionReceipt:{}", JSON.toJSONString(receipt));
            //查看返回event
            List<CandidateContract.CandidateWithdrawEventEventResponse>  events = candidateContract.getCandidateWithdrawEventEvents(receipt);
            Assert.assertNotNull(events);
            for (CandidateContract.CandidateWithdrawEventEventResponse event : events) {
                Assert.assertNotNull(event.param1);
                logger.info("event:{}", JSON.toJSONString(event.param1));
            }
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
        TimeUnit.SECONDS.sleep(1);
    }

    protected void retreat(List<Node> nodes){
        if (nodes==null) return;
        nodes.forEach(node -> {
            try {
                retreat(node);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    protected void retreat(Node node) throws InterruptedException {
        applyWithdraw(node);
        withdraw(node);
    }

    protected TransactionReceipt recharge(String address, BigDecimal amountInETH) throws Exception {
        RawTransactionManager transactionManager = new RawTransactionManager(web3j, adminCredentials);
        TransactionReceipt receipt = new Transfer(web3j, transactionManager).sendFunds(address, amountInETH, Convert.Unit.ETHER).send();
        return receipt;
    }
}
