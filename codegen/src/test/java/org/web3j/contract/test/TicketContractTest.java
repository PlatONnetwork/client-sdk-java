package org.web3j.contract.test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.platon.contracts.TicketContract;
import org.web3j.platon.contracts.TicketContract.VoteTicketEventEventResponse;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TicketContractTest {

    private Logger logger = LoggerFactory.getLogger(TicketContractTest.class);

    private static final Credentials CREDENTIALS = loadCredentials();
    
    private  TicketContract contract;

    private static Credentials loadCredentials() {
        Credentials credentials = null;
        try {
            URL url = TicketContractTest.class.getClassLoader().getResource("wallet/admin.json");
            String path = url.getPath();
            credentials = WalletUtils.loadCredentials("88888888",path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.9.76:8789"));
   // private Web3j web3j = Web3j.build(new HttpService("http://10.10.8.209:6789"));
   
    
    @Before
    public void init() {
    	contract = TicketContract.load(
                web3j,
                CREDENTIALS,
                new DefaultWasmGasProvider()
        );
    }

    @Test
    public void getPrice() throws Exception {
        String price = contract.GetTicketPrice().send();
        logger.debug("Ticket price: {}",price);
    }
    
    @Test
    public void GetPoolRemainder() throws Exception {
        String detail = contract.GetPoolRemainder().send();
        logger.debug("{}",detail);
    }

    
    @Test
    public void VoteTicket() throws Exception {
    	//选票单价
    	String priceStr = contract.GetTicketPrice().send();
    	BigInteger price =  new BigInteger(priceStr);
    	//购票数量
    	BigInteger count = BigInteger.valueOf(5L);
    	//候选人节点Id
    	String nodeId = "0x4f6c8fd10bfb512793f81a3594120c76b6991d3d06c0cc652035cbfae3fcd7cdc3f3d7a82021dfdb9ea99f014755ec1a640d832a0362b47be688bb31d504f62d";

    	//调用接口
        TransactionReceipt receipt = contract.VoteTicket(count, price, nodeId).send();
        logger.debug("TicketContract TransactionReceipt:{}", JSON.toJSONString(receipt));
        
        //查看返回event
        List<VoteTicketEventEventResponse>  events = contract.getVoteTicketEventEvents(receipt);
        JSONObject result = null;
        for (VoteTicketEventEventResponse event : events) {
        	result = JSON.parseObject(event.param1);
        	logger.debug("TicketContract event:{}", result);
		} 
        
        //获得票id       
        String txHash = receipt.getTransactionHash();
        int tickets = result.getIntValue("Data");
        
        List<String> ticketList = contract.VoteTicketIds(tickets, txHash);
        logger.debug("TicketContract tickets:{}", ticketList);
        
//        20:09:43.610 [main] DEBUG org.web3j.contract.test.TicketContractTest - TicketContract TransactionReceipt:{"blockHash":"0x85a49bf683c123b4e650721338acad3c6bd3ff71e0f20de8baaf16e5e3e90f67","blockNumber":15548,"blockNumberRaw":"0x3cbc","cumulativeGasUsed":34832,"cumulativeGasUsedRaw":"0x8810","from":"0x493301712671ada506ba6ca7891f436d29185821","gasUsed":34832,"gasUsedRaw":"0x8810","logs":[{"address":"0x1000000000000000000000000000000000000002","blockHash":"0x85a49bf683c123b4e650721338acad3c6bd3ff71e0f20de8baaf16e5e3e90f67","blockNumber":15548,"blockNumberRaw":"0x3cbc","data":"0xebaa7b22526574223a747275652c2244617461223a2235222c224572724d7367223a2273756363657373227d","logIndex":0,"logIndexRaw":"0x0","removed":false,"topics":["0xee64e28ecf7347fbf257cb1ced716cf9d51a1e24f06e0bbe2c3d4f40924049c9"],"transactionHash":"0x7afe907caf45da59bef88b6b708fe45958143a41baa51f6c4410f87d1338e49f","transactionIndex":0,"transactionIndexRaw":"0x0"}],"logsBloom":"0x00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000001000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000080000000000200000000000000","status":"0x1","statusOK":true,"to":"0x1000000000000000000000000000000000000002","transactionHash":"0x7afe907caf45da59bef88b6b708fe45958143a41baa51f6c4410f87d1338e49f","transactionIndex":0,"transactionIndexRaw":"0x0"}
//        20:09:43.629 [main] DEBUG org.web3j.contract.test.TicketContractTest - TicketContract event:{"Ret":true,"ErrMsg":"success","Data":"5"}
//        20:09:43.633 [main] DEBUG org.web3j.contract.test.TicketContractTest - TicketContract tickets:[0x6bf2236d95a98c798abf760e43d8a1a0f375ce095f6f286198053800262988c5, 0x7f3d95634ebdbf0121a7de207b00cf2d2b4846000ec41b4a8a88d1e019701a5e, 0x57d1a6fcd7932a9615f593dbc6d6999912e170286ed1be0c0b31f6f14087c3b4, 0x3ad918e72305b6666efe2abd232554c34bab3f7cd5aa679334cb3ca3a14851ce, 0xe4f0b42906eed5f5e77cb233bd11d30e664f32b5602bda7f84d155462d496cbb]

    }
    
    @Test
    public void GetTicketDetail() throws Exception {
    	//票id
    	String ticketId = "0x6bf2236d95a98c798abf760e43d8a1a0f375ce095f6f286198053800262988c5";

        String detail = contract.GetTicketDetail(ticketId).send();

        logger.debug("{}",detail);

    }
    
    @Test
    public void GetBatchTicketDetail() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        //票id
        stringBuilder.append("0x6bf2236d95a98c798abf760e43d8a1a0f375ce095f6f286198053800262988c5");
        //分割
        stringBuilder.append(":");
        //票id
        stringBuilder.append("0x7f3d95634ebdbf0121a7de207b00cf2d2b4846000ec41b4a8a88d1e019701a5e");

        String detail = contract.GetBatchTicketDetail(stringBuilder.toString()).send();

        logger.debug("{}",detail);

    }
    
    @Test
    public void GetCandidateTicketIds() throws Exception {
    	//节点id
    	String nodeId = "0x4f6c8fd10bfb512793f81a3594120c76b6991d3d06c0cc652035cbfae3fcd7cdc3f3d7a82021dfdb9ea99f014755ec1a640d832a0362b47be688bb31d504f62d";
    	
        String ids = contract.GetCandidateTicketIds(nodeId).send();
        
        logger.debug("CandidateTicketIds: {}",ids);
    }
    
    
    @Test
    public void GetBatchCandidateTicketIds() throws Exception {

        StringBuilder stringBuilder = new StringBuilder();
        //候选人id
        stringBuilder.append("0x4f6c8fd10bfb512793f81a3594120c76b6991d3d06c0cc652035cbfae3fcd7cdc3f3d7a82021dfdb9ea99f014755ec1a640d832a0362b47be688bb31d504f62d");
        //分割
        stringBuilder.append(":");
        //候选人id
        stringBuilder.append("0x01d033b5b07407e377a3eb268bdc3f07033774fb845b7826a6b741430c5e6b719bda5c4877514e8052fa5dbc2f20fb111a576f6696b6a16ca765de49e11e0541");
        
    	
        String detail = contract.GetBatchCandidateTicketIds(stringBuilder.toString()).send();

        logger.debug("{}",detail);

    }



    @Test
    public void GetCandidateEpoch() throws Exception {
    	//节点id
    	String nodeId = "0x4f6c8fd10bfb512793f81a3594120c76b6991d3d06c0cc652035cbfae3fcd7cdc3f3d7a82021dfdb9ea99f014755ec1a640d832a0362b47be688bb31d504f62d";
    	
        String detail = contract.GetCandidateEpoch(nodeId).send();

        logger.debug("{}",detail);

    }



}
