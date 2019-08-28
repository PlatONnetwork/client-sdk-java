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
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6796"));

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
            "    \"prepare_a\": {\n" +
            "        \"epoch\": 0,\n" +
            "        \"view_number\": 0,\n" +
            "        \"block_hash\": \"0x6b603a7674e42eeaa7d26abafc50f2f9b0fba2196c7195df8be87f96f4e75b04\",\n" +
            "        \"block_number\": 500,\n" +
            "        \"block_index\": 0,\n" +
            "        \"validate_node\": {\n" +
            "            \"index\": 0,\n" +
            "            \"address\": \"0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4\",\n" +
            "            \"NodeID\": \"19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365\",\n" +
            "            \"blsPubKey\": \"7f58e6c152917637069be6b3fab9ebe9d3e5c9f5cf7dbf34e95c89647b2c7001e01447b8e2f697bff8f963e44cf7ca15a183ff1c0e701089ee2cd381f217e112\"\n" +
            "        },\n" +
            "        \"signature\": \"0x351d4f08f4ceb1fed7c499a9bbd5a0d399b1678dc7f455e0cce5b2112199f18e\"\n" +
            "    },\n" +
            "    \"prepare_b\": {\n" +
            "        \"epoch\": 0,\n" +
            "        \"view_number\": 0,\n" +
            "        \"block_hash\": \"0xe26284d36031feff49b43e4132a8ef9e02492ba33060f557e1198f5d65367dbf\",\n" +
            "        \"block_number\": 500,\n" +
            "        \"block_index\": 0,\n" +
            "        \"validate_node\": {\n" +
            "            \"index\": 0,\n" +
            "            \"address\": \"0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4\",\n" +
            "            \"NodeID\": \"19f1c9aa5140bd1304a3260de640a521c33015da86b88cd2ecc83339b558a4d4afa4bd0555d3fa16ae43043aeb4fbd32c92b34de1af437811de51d966dc64365\",\n" +
            "            \"blsPubKey\": \"7f58e6c152917637069be6b3fab9ebe9d3e5c9f5cf7dbf34e95c89647b2c7001e01447b8e2f697bff8f963e44cf7ca15a183ff1c0e701089ee2cd381f217e112\"\n" +
            "        },\n" +
            "        \"signature\": \"0xf710ff75b93e85fc314360632827eaad4203fc9fffbd5fab8c7406e33dd04a9b\"\n" +
            "    }\n" +
            "}";

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
            PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, evidence).send();
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
            BaseResponse baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0xc30671be006dcbfd6d36bdf0dfdf95c62c23fad4", BigInteger.valueOf(500)).send();

            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
