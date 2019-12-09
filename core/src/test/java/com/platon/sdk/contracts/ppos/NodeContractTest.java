package com.platon.sdk.contracts.ppos;
//package org.web3j.protocol.platon;
//
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.web3j.platon.bean.Node;
//import org.web3j.protocol.Web3j;
//import org.web3j.protocol.http.HttpService;
//
//import com.platon.sdk.contracts.inner.NodeContract;
//import com.platon.sdk.contracts.inner.dto.CallResponse;
//
//public class NodeContractTest {
//
//	private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));
//    
//    private NodeContract nodeContract;
//
//    @Before
//    public void init() {
//        nodeContract = NodeContract.load(web3j);
//    }
//
//    
//    /**
//     *  查询当前结算周期的验证人队列
//     */
//    @Test
//    public void getVerifierList() {
//        try {
//            CallResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
//            System.out.println(baseResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//               *  查询当前共识周期的验证人列表
//     */
//    @Test
//    public void getValidatorList() {
//        try {
//        	CallResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
//            System.out.println(baseResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//                *  查询所有实时的候选人列表
//     */
//    @Test
//    public void getCandidateList() {
//        try {
//        	CallResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
//            System.out.println(baseResponse);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
