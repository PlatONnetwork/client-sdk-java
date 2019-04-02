package org.web3j.contract.test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.Map;

import jnr.ffi.annotations.In;
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


   private Web3j web3j = Web3j.build(new HttpService("http://0.0.0.0:0000"));
   
    
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
        

    }



    @Test
    public void GetCandidateEpoch() throws Exception {
    	//节点id
    	String nodeId = "0xd124e660938dc3fd63d913ff753fafc262764b22294431e760b572b0b58d5e6b813b32ccbacc326c03171542ae0ff8ff6528625a2d612e0c49240f111eba3c22";
    	String a = contract.GetCandidateTicketCount(nodeId).send();
        //String detail = contract.GetCandidateEpoch(nodeId).send();

        logger.debug("{}",a);

    }



    @Test
    public void GetTicketCountByTxHashTest() throws Exception{
        String hash = "0x3051e2a46f8ae3139903f2128d8801505b265ca2201dc3cad041038bad0b63d9";
        String res = contract.GetTicketCountByTxHash(hash).send();
        Map<String,Integer> resMap = JSON.parseObject(res,Map.class);
        logger.debug("{}",resMap);


    }

    @Test
    public void GetCandidateTicketCountTest() throws  Exception{
        String nodeId ="";
        String res = contract.GetCandidateTicketCount(nodeId).send();
        Map<String,Integer> resMap = JSON.parseObject(res,Map.class);
    }

}
