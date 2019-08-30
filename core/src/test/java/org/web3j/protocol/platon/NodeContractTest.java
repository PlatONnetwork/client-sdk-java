package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;
import org.web3j.platon.contracts.NodeContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import java.util.List;

/**
 * 节点列表相关接口，包括，
 * 查询当前结算周期的验证人列表
 * 查询当前共识周期的验证人列表
 * 查询所有实时的候选人列表
 */
public class NodeContractTest {

    private String nodeId = "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429";

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private Credentials credentials;

    private NodeContract nodeContract;

    @Before
    public void init() {
        credentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");

        nodeContract = NodeContract.load(web3j,
                credentials,
                new DefaultWasmGasProvider(), "100");
    }

    /**
     * 查询当前结算周期的验证人队列
     */
    @Test
    public void getVerifierList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询当前共识周期的验证人列表
     */
    @Test
    public void getValidatorList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询所有实时的候选人列表
     */
    @Test
    public void getCandidateList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(nodeList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
