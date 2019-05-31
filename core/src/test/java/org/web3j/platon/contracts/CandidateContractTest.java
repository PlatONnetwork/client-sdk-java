//package org.web3j.platon.contracts;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import org.junit.Assert;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.web3j.platon.contracts.CandidateContract.SetCandidateExtraEventEventResponse;
//import org.web3j.platon.contracts.bean.Node;
//import org.web3j.protocol.core.methods.response.TransactionReceipt;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.util.List;
//
//public class CandidateContractTest extends TestBase {
//
//    private Logger logger = LoggerFactory.getLogger(CandidateContractTest.class);
//
//    /**
//     * Query verifiers list
//     */
//    @Test
//    public void VerifiersList() {
//        List<Node> nodes = deposit(1);
//        try{
//            String result = candidateContract.GetVerifiersList().send();
//            logger.info("VerifiersList:{}",result);
//            Assert.assertNotNull(result);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * Query candidate details
//     */
//    @Test
//    public void CandidateList() {
//        List<Node> nodes = deposit(1);
//        try{
//            String result = candidateContract.GetCandidateList().send();
//            Assert.assertNotNull(result);
//            List<String> list = JSON.parseArray(result, String.class);
//            logger.info("CandidateList:{}",list);
//            Assert.assertNotNull(list);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * GetCandidateDetails
//     */
//    @Test
//    public void GetCandidateDetails() {
//        List<Node> nodes = deposit(1);
//    	try{
//            //调用接口
//            String result = candidateContract.GetCandidateDetails(nodes.get(0).getId()).send();
//            logger.info("CandidateDetails:{}",result);
//            Assert.assertNotNull(result);
//        }catch (Exception e){
//    	    Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * CandidateWithdrawInfos
//     */
//    @Test
//    public void CandidateWithdrawInfos() {
//        List<Node> nodes = deposit(1);
//        try{
//            //调用接口
//            String result = candidateContract.GetCandidateWithdrawInfos(nodes.get(0).getId()).send();
//            logger.info("CandidateWithdrawInfos:{}",result);
//            Assert.assertNotNull(result);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * CandidateDeposit
//     */
//    @Test
//    public void CandidateDeposit() {
//        List<Node> nodes=null;
//        try{
//            //调用接口
//            nodes = deposit(1);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * SetCandidateExtra
//     */
//    @Test
//    public void SetCandidateExtra() {
//        List<Node> nodes = deposit(1);
//        Node node = nodes.get(0);
//        //附加数据
//        JSONObject extra = new JSONObject();
//        //节点名称
//        extra.put("nodeName", node.getName()+"-SetCandidateExtra");
//        //节点logo
//        extra.put("nodePortrait", "1");
//        //机构简介
//        extra.put("nodeDiscription", "xxxx-nodeDiscription1");
//        //机构名称
//        extra.put("nodeDepartment", "xxxx-nodeDepartment");
//        //官网
//        extra.put("officialWebsite", "xxxx-officialWebsite");
//        try{
//            //调用接口
//            TransactionReceipt receipt = candidateContract.SetCandidateExtra(node.getId(),extra.toJSONString()).send();
//            logger.info("TransactionReceipt:{}", JSON.toJSONString(receipt));
//            Assert.assertNotNull(receipt);
//            //查看返回event
//            List<SetCandidateExtraEventEventResponse>  events = candidateContract.getSetCandidateExtraEventEvents(receipt);
//            Assert.assertNotNull(events);
//            for (SetCandidateExtraEventEventResponse event : events) {
//                Assert.assertNotNull(event.param1);
//                logger.info("event:{}", JSON.toJSONString(event.param1));
//            }
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            // 撤出候选列表
//            retreat(nodes);
//        }
//    }
//
//    /**
//     * CandidateApplyWithdraw
//     */
//    @Test
//    public void CandidateApplyWithdraw() {
//        List<Node> nodes = deposit(1);
//        Node node = nodes.get(0);
//        try{
//            //调用接口
//            applyWithdraw(node);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }finally {
//            try {
//                withdraw(node);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * CandidateWithdraw
//     */
//    @Test
//    public void CandidateWithdraw() {
//        List<Node> nodes = deposit(1);
//        Node node = nodes.get(0);
//        try{
//            applyWithdraw(node);
//            //调用接口
//            withdraw(node);
//        }catch (Exception e){
//            Assert.fail(e.getMessage());
//        }
//    }
//
//}
