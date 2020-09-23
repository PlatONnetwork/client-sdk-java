package com.alaya.contracts.ppos;

import com.alaya.contracts.ppos.dto.resp.Node;
import com.alaya.contracts.ppos.dto.CallResponse;
import org.junit.Before;
import org.junit.Test;
import com.alaya.protocol.Web3j;
import com.alaya.protocol.http.HttpService;

import java.util.List;

public class NodeContractTest {

	private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));
    long chainId = 103;

    private NodeContract nodeContract;

    @Before
    public void init() {
        nodeContract = NodeContract.load(web3j,chainId);
    }


    /**
     *  查询当前结算周期的验证人队列
     */
    @Test
    public void getVerifierList() {
        try {
            CallResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
            baseResponse.getData().stream().forEach(
                 item -> System.out.println(item.getNodeId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *  查询当前共识周期的验证人列表
     */
    @Test
    public void getValidatorList() {
        try {
        	CallResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
            baseResponse.getData().stream().forEach(
                    item -> System.out.println(item.getNodeId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *  查询所有实时的候选人列表
     */
    @Test
    public void getCandidateList() {
        try {
        	CallResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
            baseResponse.getData().stream().forEach(
                    item -> System.out.println(item.getNodeId())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
