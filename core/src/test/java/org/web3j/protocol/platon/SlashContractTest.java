package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.DoubleSignType;
import org.web3j.platon.bean.Evidences;
import org.web3j.platon.contracts.SlashContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.JSONUtil;

import java.math.BigInteger;

public class SlashContractTest {
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

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

    private SlashContract slashContract;

    private Credentials credentials;

    @Before
    public void init() {
        credentials = Credentials.create("0xa56f68ca7aa51c24916b9fff027708f856650f9ff36cc3c8da308040ebcc7867");

        slashContract = SlashContract.load(web3j,
                credentials, "100");
    }

    /**
     * 举报双签
     * data 证据的json值，格式为RPC接口Evidences的返回值
     */
    @Test
    public void reportDuplicateSign() {

        try {
            PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(data).send();
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
            BaseResponse baseResponse = slashContract.checkDoubleSign(DoubleSignType.PREPARE, "0x7ad2a071b1854d977a0f058028837d77a0da6aa4", BigInteger.valueOf(1889)).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
