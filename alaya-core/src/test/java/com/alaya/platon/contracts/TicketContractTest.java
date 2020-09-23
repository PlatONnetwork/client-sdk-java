//package org.web3j.platon.contracts;
//
//import com.alibaba.fastjson.JSON;
//import org.junit.Assert;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.web3j.platon.contracts.bean.Node;
//
//import java.util.List;
//import java.util.Map;
//
//@SuppressWarnings("unchecked")
//public class TicketContractTest extends TestBase {
//
//    private Logger logger = LoggerFactory.getLogger(TicketContractTest.class);
//
//    @Test
//    public void getPrice() {
//        try {
//            String result = ticketContract.GetTicketPrice().send();
//            Assert.assertNotNull(result);
//            logger.info("Ticket price: {}",result);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void GetPoolRemainder() {
//        List<Node> nodes = deposit(1);
//        try {
//            String result = ticketContract.GetPoolRemainder().send();
//            Assert.assertNotNull(result);
//            logger.info("{}",result);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void VoteTicket() {
//        List<Node> nodes = deposit(1);
//        try{
//            //调用接口
//            voteTicket(nodes.get(0));
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    @Test
//    public void GetCandidateEpoch() {
//        List<Node> nodes = deposit(1);
//        try{
//            //调用接口
//            String result = ticketContract.GetCandidateEpoch(nodes.get(0).getId()).send();
//            Assert.assertNotNull(result);
//            logger.info("GetCandidateEpoch: {}",result);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    @Test
//    public void GetTicketCountByTxHash() {
//        List<Node> nodes = deposit(1);
//        try{
//            //调用接口
//            String txHash = voteTicket(nodes.get(0));
//            String result = ticketContract.GetTicketCountByTxHash(txHash).send();
//            Assert.assertNotNull(result);
//            Map<String,Integer> resMap = JSON.parseObject(result,Map.class);
//            logger.info("GetTicketCountByTxHash: {}",resMap);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    @Test
//    public void GetCandidateTicketCount() {
//        List<Node> nodes = deposit(1);
//        try{
//            //调用接口
//            voteTicket(nodes.get(0));
//            String result = ticketContract.GetCandidateTicketCount(nodes.get(0).getId()).send();
//            Assert.assertNotNull(result);
//            Map<String,Integer> resMap = JSON.parseObject(result,Map.class);
//            resMap.forEach((k,v)->logger.info("nodeId: {}, ticketCount: {} ",k ,v));
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//}
