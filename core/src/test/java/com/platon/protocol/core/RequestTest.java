package com.platon.protocol.core;

import com.platon.protocol.RequestTester;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.methods.request.PlatonFilter;
import com.platon.protocol.core.methods.request.ShhFilter;
import com.platon.protocol.core.methods.request.ShhPost;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.http.HttpService;
import com.platon.utils.Numeric;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Arrays;

public class RequestTest extends RequestTester {

    private Web3j web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Web3j.build(httpService);
    }

    @Test
    public void testWeb3ClientVersion() throws Exception {
        web3j.web3ClientVersion().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"web3_clientVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testWeb3Sha3() throws Exception {
        web3j.web3Sha3("0x68656c6c6f20776f726c64").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"web3_sha3\","
                        + "\"params\":[\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testNetVersion() throws Exception {
        web3j.netVersion().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"net_version\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetListening() throws Exception {
        web3j.netListening().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"net_listening\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetPeerCount() throws Exception {
        web3j.netPeerCount().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"net_peerCount\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthProtocolVersion() throws Exception {
        web3j.platonProtocolVersion().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_protocolVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthSyncing() throws Exception {
        web3j.platonSyncing().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_syncing\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthGasPrice() throws Exception {
        web3j.platonGasPrice().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_gasPrice\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthAccounts() throws Exception {
        web3j.platonAccounts().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_accounts\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthBlockNumber() throws Exception {
        web3j.platonBlockNumber().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_blockNumber\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthGetBalance() throws Exception {
        web3j.platonGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getBalance\","
                        + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                        + "\"id\":1}");
    }

    @Test
    public void testEthGetStorageAt() throws Exception {
        web3j.platonGetStorageAt("0x295a70b2de5e3953354a6a8344e616ed314d7251", BigInteger.ZERO,
                DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getStorageAt\","
                + "\"params\":[\"0x295a70b2de5e3953354a6a8344e616ed314d7251\",\"0x0\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testEthGetTransactionCount() throws Exception {
        web3j.platonGetTransactionCount("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getTransactionCount\","
                + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testEthGetBlockTransactionCountByHash() throws Exception {
        web3j.platonGetBlockTransactionCountByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getBlockTransactionCountByHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testEthGetBlockTransactionCountByNumber() throws Exception {
        web3j.platonGetBlockTransactionCountByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getBlockTransactionCountByNumber\","
                + "\"params\":[\"0xe8\"],\"id\":1}");
    }

    @Test
    public void testEthGetCode() throws Exception {
        web3j.platonGetCode("0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x2"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getCode\","
                + "\"params\":[\"0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0x2\"],\"id\":1}");
    }

    @Test
    public void testEthSign() throws Exception {
        web3j.platonSign("0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab",
                "0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_sign\","
                + "\"params\":[\"0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab\","
                + "\"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470\"],"
                + "\"id\":1}");
    }

    @Test
    public void testEthSendTransaction() throws Exception {
        web3j.platonSendTransaction(new Transaction(
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                BigInteger.ONE,
                Numeric.toBigInt("0x9184e72a000"),
                Numeric.toBigInt("0x76c0"),
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                Numeric.toBigInt("0x9184e72a"),
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb"
                        + "970870f072445675058bb8eb970870f072445675")).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_sendTransaction\",\"params\":[{\"from\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"gas\":\"0x76c0\",\"gasPrice\":\"0x9184e72a000\",\"value\":\"0x9184e72a\",\"data\":\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\",\"nonce\":\"0x1\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testEthSendRawTransaction() throws Exception {
        web3j.platonSendRawTransaction(
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f"
                        + "072445675058bb8eb970870f072445675").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_sendRawTransaction\",\"params\":[\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\"],\"id\":1}");
        //CHECKSTYLE:ON
    }


    @Test
    public void testEthCall() throws Exception {
        web3j.platonCall(Transaction.createEthCallTransaction(
                "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x0"),
                DefaultBlockParameter.valueOf("latest")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_call\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"data\":\"0x0\"},"
                + "\"latest\"],\"id\":1}");
    }

    @Test
    public void testEthEstimateGas() throws Exception {
        web3j.platonEstimateGas(
                Transaction.createEthCallTransaction(
                        "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", "0x0")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_estimateGas\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\",\"data\":\"0x0\"}],"
                + "\"id\":1}");
    }

    @Test
    public void testEthEstimateGasContractCreation() throws Exception {
        web3j.platonEstimateGas(
                Transaction.createContractTransaction(
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", BigInteger.ONE,
                        BigInteger.TEN, "")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_estimateGas\","
                + "\"params\":[{\"from\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\","
                + "\"gasPrice\":\"0xa\",\"data\":\"0x\",\"nonce\":\"0x1\"}],\"id\":1}");
    }

    @Test
    public void testEthGetBlockByHash() throws Exception {
        web3j.platonGetBlockByHash(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", true).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getBlockByHash\",\"params\":["
                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\""
                        + ",true],\"id\":1}");
    }

    @Test
    public void testEthGetBlockByNumber() throws Exception {
        web3j.platonGetBlockByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x1b4")), true).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getBlockByNumber\","
                + "\"params\":[\"0x1b4\",true],\"id\":1}");
    }

    @Test
    public void testEthGetTransactionByHash() throws Exception {
        web3j.platonGetTransactionByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getTransactionByHash\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testEthGetTransactionByBlockHashAndIndex() throws Exception {
        web3j.platonGetTransactionByBlockHashAndIndex(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                BigInteger.ZERO).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getTransactionByBlockHashAndIndex\",\"params\":[\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\"0x0\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testEthGetTransactionByBlockNumberAndIndex() throws Exception {
        web3j.platonGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x29c")), BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getTransactionByBlockNumberAndIndex\","
                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
    }

    @Test
    public void testEthGetTransactionReceipt() throws Exception {
        web3j.platonGetTransactionReceipt(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getTransactionReceipt\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testEthNewFilter() throws Exception {
        PlatonFilter ethFilter = new PlatonFilter()
                .addSingleTopic("0x12341234");

        web3j.platonNewFilter(ethFilter).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_newFilter\","
                + "\"params\":[{\"topics\":[\"0x12341234\"]}],\"id\":1}");
    }

    @Test
    public void testEthNewBlockFilter() throws Exception {
        web3j.platonNewBlockFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_newBlockFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthNewPendingTransactionFilter() throws Exception {
        web3j.platonNewPendingTransactionFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_newPendingTransactionFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testEthUninstallFilter() throws Exception {
        web3j.platonUninstallFilter(Numeric.toBigInt("0xb")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_uninstallFilter\","
                + "\"params\":[\"0x0b\"],\"id\":1}");
    }

    @Test
    public void testEthGetFilterChanges() throws Exception {
        web3j.platonGetFilterChanges(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getFilterChanges\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testEthGetFilterLogs() throws Exception {
        web3j.platonGetFilterLogs(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getFilterLogs\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testEthGetLogs() throws Exception {
        web3j.platonGetLogs(new PlatonFilter().addSingleTopic(
                "0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b"))
                .send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getLogs\","
                + "\"params\":[{\"topics\":["
                + "\"0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b\"]}],"
                + "\"id\":1}");
    }

    @Test
    public void testEthGetLogsWithNumericBlockRange() throws Exception {
        web3j.platonGetLogs(new PlatonFilter(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8")),
                DefaultBlockParameter.valueOf("latest"), ""))
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"platon_getLogs\","
                        + "\"params\":[{\"topics\":[],\"fromBlock\":\"0xe8\","
                        + "\"toBlock\":\"latest\",\"address\":[\"\"]}],\"id\":1}");
    }

    @Test
    public void testDbPutString() throws Exception {
        web3j.dbPutString("testDB", "myKey", "myString").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"db_putString\","
                + "\"params\":[\"testDB\",\"myKey\",\"myString\"],\"id\":1}");
    }

    @Test
    public void testDbGetString() throws Exception {
        web3j.dbGetString("testDB", "myKey").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"db_getString\","
                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    }

    @Test
    public void testDbPutHex() throws Exception {
        web3j.dbPutHex("testDB", "myKey", "0x68656c6c6f20776f726c64").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"db_putHex\","
                + "\"params\":[\"testDB\",\"myKey\",\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testDbGetHex() throws Exception {
        web3j.dbGetHex("testDB", "myKey").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"db_getHex\","
                + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    }

    @Test
    public void testShhVersion() throws Exception {
        web3j.shhVersion().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_version\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhPost() throws Exception {
        //CHECKSTYLE:OFF
        web3j.shhPost(new ShhPost(
                "0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1",
                "0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1",
                Arrays.asList("0x776869737065722d636861742d636c69656e74", "0x4d5a695276454c39425154466b61693532"),
                "0x7b2274797065223a226d6",
                Numeric.toBigInt("0x64"),
                Numeric.toBigInt("0x64"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_post\",\"params\":[{\"from\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\",\"to\":\"0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1\",\"topics\":[\"0x776869737065722d636861742d636c69656e74\",\"0x4d5a695276454c39425154466b61693532\"],\"payload\":\"0x7b2274797065223a226d6\",\"priority\":\"0x64\",\"ttl\":\"0x64\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewIdentity() throws Exception {
        web3j.shhNewIdentity().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_newIdentity\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhHasIdentity() throws Exception {
        //CHECKSTYLE:OFF
        web3j.shhHasIdentity("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_hasIdentity\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewGroup() throws Exception {
        web3j.shhNewGroup().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_newGroup\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testShhAddToGroup() throws Exception {
        //CHECKSTYLE:OFF
        web3j.shhAddToGroup("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_addToGroup\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhNewFilter() throws Exception {
        //CHECKSTYLE:OFF
        web3j.shhNewFilter(
                new ShhFilter("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1")
                        .addSingleTopic("0x12341234bf4b564f")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_newFilter\",\"params\":[{\"topics\":[\"0x12341234bf4b564f\"],\"to\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"}],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testShhUninstallFilter() throws Exception {
        web3j.shhUninstallFilter(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_uninstallFilter\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }

    @Test
    public void testShhGetFilterChanges() throws Exception {
        web3j.shhGetFilterChanges(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_getFilterChanges\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }

    @Test
    public void testShhGetMessages() throws Exception {
        web3j.shhGetMessages(Numeric.toBigInt("0x7")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"shh_getMessages\","
                + "\"params\":[\"0x07\"],\"id\":1}");
    }

    @Test
    public void testAdminAddPeer() throws Exception {
        web3j.adminAddPeer("url").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"admin_addPeer\",\"params\":[\"url\"],\"id\":1}");
    }

    @Test
    public void testAdminDataDir() throws Exception {
        web3j.adminDataDir().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"admin_datadir\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testAdminRemovePeer() throws Exception {
        web3j.adminRemovePeer("url").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"admin_removePeer\",\"params\":[\"url\"],\"id\":1}");
    }

    @Test
    public void testTxPoolStatus() throws Exception {
        web3j.txPoolStatus().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"txpool_status\",\"params\":[],\"id\":1}");
    }

}
