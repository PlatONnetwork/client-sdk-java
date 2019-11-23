package org.web3j.protocol.platon;


import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.Response;
import org.web3j.platon.contracts.NodeContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.JSONUtil;
import org.web3j.utils.Numeric;
import org.web3j.utils.PlatOnUtil;

import java.math.BigInteger;
import java.util.List;

/**
 * 节点列表相关接口，包括，
 * 查询当前结算周期的验证人列表
 * 查询当前共识周期的验证人列表
 * 查询所有实时的候选人列表
 */
public class NodeContractTest {


    private String nodeId = "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429";

    protected Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));

    private Credentials credentials;

    private NodeContract nodeContract;

    @Before
    public void init() {
        credentials = Credentials.create("0xbfa6c75e2240a4735fdc99a73b48ae42d625f34b859327fc2f0e553f7e97888e");

        nodeContract = NodeContract.load(web3j,
                credentials, "100");
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
