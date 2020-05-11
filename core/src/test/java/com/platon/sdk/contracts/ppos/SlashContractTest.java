package com.platon.sdk.contracts.ppos;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.common.DuplicateSignType;

public class SlashContractTest {

    private String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
    long chainId = 103;
    String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));

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
            "            \"NodeID\": \"411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c\",\n" +
            "            \"blsPubKey\": \"80d98a48400a36e3da9de8e227e4a8c8fa3f90c08c82a467c9ac01298c2eb57f543d7e9568b0f381cc6c9de911870d1292b62459d083700d3958d775fca60e41ddd7d8532163f5acabaa6e0c47b626c39de51d9d67fb97a5af1871a661ca7788\"\n" +
            "        },\n" +
            "        \"signature\": \"0x351d4f08f4ceb1fed7c499a9bbd5a0d399b1678dc7f455e0cce5b2112199f18e351d4f08f4ceb1fed7c499a9bbd5a0d399b1678dc7f455e0cce5b2112199f18e\"\n" +
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
            "            \"NodeID\": \"411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c\",\n" +
            "            \"blsPubKey\": \"80d98a48400a36e3da9de8e227e4a8c8fa3f90c08c82a467c9ac01298c2eb57f543d7e9568b0f381cc6c9de911870d1292b62459d083700d3958d775fca60e41ddd7d8532163f5acabaa6e0c47b626c39de51d9d67fb97a5af1871a661ca7788\"\n" +
            "        },\n" +
            "        \"signature\": \"0xf710ff75b93e85fc314360632827eaad4203fc9fffbd5fab8c7406e33dd04a9b351d4f08f4ceb1fed7c499a9bbd5a0d399b1678dc7f455e0cce5b2112199f18e\"\n" +
            "    }\n" +
            "}";

    private SlashContract slashContract;

    private Credentials credentials;

    @Before
    public void init() {
        credentials = Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
        slashContract = SlashContract.load(web3j, credentials, chainId);
    }

    /**
     * 举报双签
     * data 证据的json值，格式为RPC接口Evidences的返回值
     */
    @Test
    public void reportDuplicateSign() {
        try {
            PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, evidence).send();
            BaseResponse baseResponse = slashContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse);
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

            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
