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

    private Web3j web3j = Web3j.build(new HttpService("http://10.10.8.118:6789"));

    private Credentials credentials;

    private NodeContract nodeContract;

    @Before
    public void init() {
        credentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");

        nodeContract = NodeContract.load(web3j,
                credentials, "100");
    }

    /**
     * 查询当前结算周期的验证人队列
     */
    @Test
    public void getVerifierList() {
//        try {
//            BaseResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
//            List<Node> nodeList = baseResponse.data;
//            System.out.println(nodeList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        String text = "7b22436f6465223a302c22526574223a5b7b224e6f64654964223a223466636332353163663662663365613533613734383937316132323366353637363232356565343338306236356337383839613262343931653135353164343566653966636331396336616635346463663064353332336235616138656531643931393739313639353038326261653166383664643238326462613431353066222c22426c735075624b6579223a22643334316130633438356339656330306365636637656131363332336335343739303066366131626163623964616163623030633262386261636565363331663735643564333162373538313462376631616533613465313862373163363137626332663233306461613063383933373436656438376230386232646639336361346464646532383136623361633431306239393830626363303438353231353632613362326430306539303066643737376433636638386365363738373139222c225374616b696e6741646472657373223a22307863316633333062323134363638626561633265363431386464363531623039633735396134626635222c2242656e6566697441646472657373223a22307831303030303030303030303030303030303030303030303030303030303030303030303030303033222c225374616b696e675478496e646578223a302c2250726f6772616d56657273696f6e223a313739322c225374616b696e67426c6f636b4e756d223a302c22536861726573223a223078313364613332396236333336343731383030303030222c2245787465726e616c4964223a22222c224e6f64654e616d65223a22706c61746f6e2e6e6f64652e31222c2257656273697465223a227777772e706c61746f6e2e6e6574776f726b222c2244657461696c73223a2254686520506c61744f4e204e6f6465222c2256616c696461746f725465726d223a307d5d7d";

        RlpList rlpList = RlpDecoder.decode(Hex.decode(text));

        List<RlpType> rlpTypeList = rlpList.getValues();

        StringBuilder sb = new StringBuilder();

        for (RlpType rlpType : rlpTypeList) {
            byte[] bytes = RlpEncoder.encode(rlpType);
            sb.append(new String(bytes));
        }

        System.out.println(sb.toString());

        Response response = JSONUtil.parseObject(sb.toString(), Response.class);

        Object object = response.getRet();

        System.out.println(object);

//        RlpString rlpString = (RlpString) rlpStringList.get(0);

//        RlpList rl = (RlpList) rlpList.getValues().get(0);
//
//        RlpString rlpType = (RlpString) rl.getValues().get(0);
//        RlpString rlpTyp1 = (RlpString) rl.getValues().get(1);
//        RlpString rlpTyp2 = (RlpString) rl.getValues().get(2);

//        RlpList rlps = RlpDecoder.decode(rlpType.getBytes());
//        BigInteger functionType = new BigInteger(((RlpString) rlps.getValues().get(0)).getBytes());
//
//        RlpList rlps1 = RlpDecoder.decode(rlpTyp1.getBytes());
//        String account = Numeric.toHexString(((RlpString) rlps1.getValues().get(0)).getBytes());
//
//        RlpList rlps2 = RlpDecoder.decode(rlpTyp2.getBytes());
//        List<RlpType> rlpTypeList = ((RlpList) rlps2.getValues().get(0)).getValues();
//
//        for (RlpType rlpType1 : rlpTypeList) {
//            RlpList rlpList1 = (RlpList) rlpType1;
//            RlpString rlpString = (RlpString) rlpList1.getValues().get(0);
//            RlpString rlpString1 = (RlpString) rlpList1.getValues().get(1);
//            byte[] bytes = RlpEncoder.encode(rlpString);
//            byte[] bytes1 = RlpEncoder.encode(rlpString1);
//            System.out.println(new BigInteger(1, bytes) + ":" + new BigInteger(1, bytes1));
//        }

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
