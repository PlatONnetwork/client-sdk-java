package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.DuplicateSignType;
import org.web3j.platon.contracts.SlashContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class SlashContractTest {
    //    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.112.120:8222"));

    private String address = "0x493301712671Ada506ba6Ca7891F436D29185821";
    private String benifitAddress = "0x12c171900f010b17e969702efa044d077e868082";
    private String data = "{\n" +
            "         \"duplicate_prepare\": [\n" +
            "          {\n" +
            "           \"PrepareA\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0x2e9be564726853b352753b670aea793e351f5e26b1beaa1ca65af1b3253cc710\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n" +
            "           },\n" +
            "           \"PrepareB\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0x2e9be564726853b352753b670aea793e351f5e26b1beaa1ca65af1b3253cc710\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n" +
            "           }\n" +
            "          }\n" +
            "         ],\n" +
            "         \"duplicate_vote\": [\n" +
            "          {\n" +
            "           \"VoteA\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0x79e0a15bf743b2ba83cd4f361a6f5e10c6106cbfe5f2177dba2a21825cba9485\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n" +
            "           },\n" +
            "           \"VoteB\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0x79e0a15bf743b2ba83cd4f361a6f5e10c6106cbfe5f2177dba2a21825cba9485\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\"\n" +
            "           }\n" +
            "          }\n" +
            "         ],\n" +
            "         \"duplicate_viewchange\": [\n" +
            "          {\n" +
            "           \"ViewA\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0xeed3a28c993de8634038f69b25949b9cc1e9b3adf1045fc13989d87ce04d0752\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n" +
            "            \"block_epoch\": 0,\n" +
            "            \"block_view\": 0\n" +
            "           },\n" +
            "           \"ViewB\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0xeed3a28c993de8634038f69b25949b9cc1e9b3adf1045fc13989d87ce04d0752\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0x7ad2a071b1854d977a0f058028837d77a0da6aa4\",\n" +
            "             \"NodeID\": \"5327a7555985e560b629ed29623e9e57108f9a88fcae20acb11baa8d3ad04910f56b4a546188155778f4840cbaf09ea54ce887fdbdab4b4f49b9069e4bf873f8\",\n" +
            "             \"blsPubKey\": null\n" +
            "            },\n" +
            "            \"signature\": \"0x0000000000000000000000000000000000000000000000000000000000000000\",\n" +
            "            \"block_epoch\": 0,\n" +
            "            \"block_view\": 0\n" +
            "           }\n" +
            "          }\n" +
            "         ]\n" +
            "        }";

    private String evidence = "{\n" +
            "           \"vote_a\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0xdb8e3281bbd47c0fbdc1fa74c55dc1907b28b1552fcca3c14933e090f7680230\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0xcf071a864d320d0de9cfefce84d7c32764a04bf4\",\n" +
            "             \"NodeID\": \"f8b53c97b0026fe3cca2e5aff159dd95a11a457b39550927f89760b87a298f04901f984e0663ba11a0095648cf3619f882d2447b68ce186266796b95fc993460\",\n" +
            "             \"blsPubKey\": \"2d5ee61e4ed4aac6610fc0744a0726579af80a61fca6d6e100c3c767ed9b07259a85e2ff0b5605693e39452d41300f1f3f304cc83e27775e4c56937979fe8486\"\n" +
            "            },\n" +
            "            \"signature\": \"0xb4d30e820d0918c5ea3df8116df1eddeca943856d0f106fd7a9c15b7d996ef14\"\n" +
            "           },\n" +
            "           \"vote_b\": {\n" +
            "            \"epoch\": 1,\n" +
            "            \"view_number\": 1,\n" +
            "            \"block_hash\": \"0xdb8e3281bbd47c0fbdc1fa74c55dc1907b28b1552fcca3c14933e090f7680230\",\n" +
            "            \"block_number\": 1,\n" +
            "            \"block_index\": 1,\n" +
            "            \"validate_node\": {\n" +
            "             \"index\": 0,\n" +
            "             \"address\": \"0xcf071a864d320d0de9cfefce84d7c32764a04bf4\",\n" +
            "             \"NodeID\": \"f8b53c97b0026fe3cca2e5aff159dd95a11a457b39550927f89760b87a298f04901f984e0663ba11a0095648cf3619f882d2447b68ce186266796b95fc993460\",\n" +
            "             \"blsPubKey\": \"2d5ee61e4ed4aac6610fc0744a0726579af80a61fca6d6e100c3c767ed9b07259a85e2ff0b5605693e39452d41300f1f3f304cc83e27775e4c56937979fe8486\"\n" +
            "            },\n" +
            "            \"signature\": \"0xb4d30e820d0918c5ea3df8116df1eddeca943856d0f106fd7a9c15b7d996ef14\"\n" +
            "           }\n" +
            "          }";

    private SlashContract slashContract;

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xc783df0e98baf34f2ed791f6087be8e3f55fe9c4e4687e0ddc30a37abc15b287");

        slashContract = SlashContract.load(web3j,
                credentials, "102");
    }

    /**
     * 举报双签
     * data 证据的json值，格式为RPC接口Evidences的返回值
     */
    @Test
    public void reportDuplicateSign() {

//        try {
//            PlatonEvidences platonEvidences = web3j.platonEvidences().send();
//
//            System.out.println(platonEvidences.getEvidences());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        try {
            PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_VOTE, evidence).send();
            BaseResponse baseResponse = slashContract.getReportDoubleSignResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询节点是否已被举报过多签
     * typ  代表双签类型，1：prepare，2：viewChange，3：TimestampViewChange
     * addr 举报的节点地址
     * blockNumber 多签的块高
     */
    @Test
    public void checkDuplicateSign() {
        try {
            BaseResponse baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0x7ad2a071b1854d977a0f058028837d77a0da6aa4", BigInteger.valueOf(1889)).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
