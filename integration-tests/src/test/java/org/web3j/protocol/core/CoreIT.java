package org.web3j.protocol.core;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JSON-RPC 2.0 Integration Tests.
 */
public class CoreIT {

    private Web3j web3j;

    private IntegrationTestConfig config = new TestnetConfig();

    public CoreIT() { }

    @Before
    public void setUp() {
        this.web3j = Web3j.build(new HttpService());
    }

    @Test
    public void testWeb3ClientVersion() throws Exception {
        Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("Ethereum client version: " + clientVersion);
        assertFalse(clientVersion.isEmpty());
    }

    @Test
    public void testWeb3Sha3() throws Exception {
        Web3Sha3 web3Sha3 = web3j.web3Sha3("0x68656c6c6f20776f726c64").send();
        assertThat(web3Sha3.getResult(),
                is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() throws Exception {
        NetVersion netVersion = web3j.netVersion().send();
        assertFalse(netVersion.getNetVersion().isEmpty());
    }

    @Test
    public void testNetListening() throws Exception {
        NetListening netListening = web3j.netListening().send();
        assertTrue(netListening.isListening());
    }

    @Test
    public void testNetPeerCount() throws Exception {
        NetPeerCount netPeerCount = web3j.netPeerCount().send();
        assertTrue(netPeerCount.getQuantity().signum() == 1);
    }

    @Test
    public void testEthProtocolVersion() throws Exception {
        PlatonProtocolVersion ethProtocolVersion = web3j.platonProtocolVersion().send();
        assertFalse(ethProtocolVersion.getProtocolVersion().isEmpty());
    }

    @Test
    public void testEthSyncing() throws Exception {
        PlatonSyncing ethSyncing = web3j.platonSyncing().send();
        assertNotNull(ethSyncing.getResult());
    }

    @Test
    public void testEthGasPrice() throws Exception {
        PlatonGasPrice ethGasPrice = web3j.platonGasPrice().send();
        assertTrue(ethGasPrice.getGasPrice().signum() == 1);
    }

    @Test
    public void testEthAccounts() throws Exception {
        PlatonAccounts ethAccounts = web3j.platonAccounts().send();
        assertNotNull(ethAccounts.getAccounts());
    }

    @Test
    public void testEthBlockNumber() throws Exception {
        PlatonBlockNumber ethBlockNumber = web3j.platonBlockNumber().send();
        assertTrue(ethBlockNumber.getBlockNumber().signum() == 1);
    }

    @Test
    public void testEthGetBalance() throws Exception {
        PlatonGetBalance ethGetBalance = web3j.platonGetBalance(
                config.validAccount(), DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(ethGetBalance.getBalance().signum() == 1);
    }

    @Test
    public void testEthGetStorageAt() throws Exception {
        PlatonGetStorageAt ethGetStorageAt = web3j.platonGetStorageAt(
                config.validContractAddress(),
                BigInteger.valueOf(0),
                DefaultBlockParameter.valueOf("latest")).send();
        assertThat(ethGetStorageAt.getData(), is(config.validContractAddressPositionZero()));
    }

    @Test
    public void testEthGetTransactionCount() throws Exception {
        PlatonGetTransactionCount ethGetTransactionCount = web3j.platonGetTransactionCount(
                config.validAccount(),
                DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(ethGetTransactionCount.getTransactionCount().signum() == 1);
    }

    @Test
    public void testEthGetBlockTransactionCountByHash() throws Exception {
        PlatonGetBlockTransactionCountByHash ethGetBlockTransactionCountByHash =
                web3j.platonGetBlockTransactionCountByHash(
                        config.validBlockHash()).send();
        assertThat(ethGetBlockTransactionCountByHash.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testEthGetBlockTransactionCountByNumber() throws Exception {
        PlatonGetBlockTransactionCountByNumber ethGetBlockTransactionCountByNumber =
                web3j.platonGetBlockTransactionCountByNumber(
                        DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(ethGetBlockTransactionCountByNumber.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testEthGetCode() throws Exception {
        PlatonGetCode ethGetCode = web3j.platonGetCode(config.validContractAddress(),
                DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(ethGetCode.getCode(), is(config.validContractCode()));
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testEthSign() throws Exception {
        // EthSign platonSign = web3j.platonSign();
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testEthSendTransaction() throws Exception {
        PlatonSendTransaction ethSendTransaction = web3j.platonSendTransaction(
                config.buildTransaction()).send();
        assertFalse(ethSendTransaction.getTransactionHash().isEmpty());
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testEthSendRawTransaction() throws Exception {

    }

    @Test
    public void testEthCall() throws Exception {
        PlatonCall ethCall = web3j.platonCall(config.buildTransaction(),
                DefaultBlockParameter.valueOf("latest")).send();

        assertThat(DefaultBlockParameterName.LATEST.getValue(), is("latest"));
        assertThat(ethCall.getValue(), is("0x"));
    }

    @Test
    public void testEthEstimateGas() throws Exception {
        PlatonEstimateGas ethEstimateGas = web3j.platonEstimateGas(config.buildTransaction())
                .send();
        assertTrue(ethEstimateGas.getAmountUsed().signum() == 1);
    }

    @Test
    public void testEthGetBlockByHashReturnHashObjects() throws Exception {
        PlatonBlock ethBlock = web3j.platonGetBlockByHash(config.validBlockHash(), false)
                .send();

        PlatonBlock.Block block = ethBlock.getBlock();
        assertNotNull(ethBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                is(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testEthGetBlockByHashReturnFullTransactionObjects() throws Exception {
        PlatonBlock ethBlock = web3j.platonGetBlockByHash(config.validBlockHash(), true)
                .send();

        PlatonBlock.Block block = ethBlock.getBlock();
        assertNotNull(ethBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testEthGetBlockByNumberReturnHashObjects() throws Exception {
        PlatonBlock ethBlock = web3j.platonGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), false).send();

        PlatonBlock.Block block = ethBlock.getBlock();
        assertNotNull(ethBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testEthGetBlockByNumberReturnTransactionObjects() throws Exception {
        PlatonBlock ethBlock = web3j.platonGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), true).send();

        PlatonBlock.Block block = ethBlock.getBlock();
        assertNotNull(ethBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testEthGetTransactionByHash() throws Exception {
        PlatonTransaction ethTransaction = web3j.platonGetTransactionByHash(
                config.validTransactionHash()).send();
        assertTrue(ethTransaction.getTransaction().isPresent());
        Transaction transaction = ethTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
    }

    @Test
    public void testEthGetTransactionByBlockHashAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        PlatonTransaction ethTransaction = web3j.platonGetTransactionByBlockHashAndIndex(
                config.validBlockHash(), index).send();
        assertTrue(ethTransaction.getTransaction().isPresent());
        Transaction transaction = ethTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testEthGetTransactionByBlockNumberAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        PlatonTransaction ethTransaction = web3j.platonGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(config.validBlock()), index).send();
        assertTrue(ethTransaction.getTransaction().isPresent());
        Transaction transaction = ethTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testEthGetTransactionReceipt() throws Exception {
        PlatonGetTransactionReceipt ethGetTransactionReceipt = web3j.platonGetTransactionReceipt(
                config.validTransactionHash()).send();
        assertTrue(ethGetTransactionReceipt.getTransactionReceipt().isPresent());
        TransactionReceipt transactionReceipt =
                ethGetTransactionReceipt.getTransactionReceipt().get();
        assertThat(transactionReceipt.getTransactionHash(), is(config.validTransactionHash()));
    }

    @Test
    public void testFiltersByFilterId() throws Exception {
        org.web3j.protocol.core.methods.request.PlatonFilter ethFilter =
                new org.web3j.protocol.core.methods.request.PlatonFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress());

        String eventSignature = config.encodedEvent();
        ethFilter.addSingleTopic(eventSignature);

        // eth_newFilter
        PlatonFilter ethNewFilter = web3j.platonNewFilter(ethFilter).send();
        BigInteger filterId = ethNewFilter.getFilterId();

        // eth_getFilterLogs
        PlatonLog ethFilterLogs = web3j.platonGetFilterLogs(filterId).send();
        List<PlatonLog.LogResult> filterLogs = ethFilterLogs.getLogs();
        assertFalse(filterLogs.isEmpty());

        // eth_getFilterChanges - nothing will have changed in this interval
        PlatonLog ethLog = web3j.platonGetFilterChanges(filterId).send();
        assertTrue(ethLog.getLogs().isEmpty());

        // eth_uninstallFilter
        PlatonUninstallFilter ethUninstallFilter = web3j.platonUninstallFilter(filterId).send();
        assertTrue(ethUninstallFilter.isUninstalled());
    }

    @Test
    public void testEthNewBlockFilter() throws Exception {
        PlatonFilter ethNewBlockFilter = web3j.platonNewBlockFilter().send();
        assertNotNull(ethNewBlockFilter.getFilterId());
    }

    @Test
    public void testEthNewPendingTransactionFilter() throws Exception {
        PlatonFilter ethNewPendingTransactionFilter =
                web3j.platonNewPendingTransactionFilter().send();
        assertNotNull(ethNewPendingTransactionFilter.getFilterId());
    }


    @Test
    public void testPendingTx() throws Exception {
        web3j = Web3j.build(new HttpService());
        PlatonPendingTransactions a = web3j.platonPendingTx().send();
        List<Transaction> transaction = a.getTransactions();
        for(Transaction transaction1 :transaction ){
            String from = transaction1.getFrom();
            System.out.println(from);
        }

    }

    @Test
    public void testEthGetLogs() throws Exception {
        org.web3j.protocol.core.methods.request.PlatonFilter ethFilter =
                new org.web3j.protocol.core.methods.request.PlatonFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress()
        );

        ethFilter.addSingleTopic(config.encodedEvent());

        PlatonLog ethLog = web3j.platonGetLogs(ethFilter).send();
        List<PlatonLog.LogResult> logs = ethLog.getLogs();
        assertFalse(logs.isEmpty());
    }

    // @Test
    // public void testEthGetWork() throws Exception {
    //     EthGetWork ethGetWork = requestFactory.ethGetWork();
    //     assertNotNull(ethGetWork.getResult());
    // }

    @Test
    public void testEthSubmitWork() throws Exception {

    }

    @Test
    public void testEthSubmitHashrate() throws Exception {

    }

    @Test
    public void testDbPutString() throws Exception {

    }

    @Test
    public void testDbGetString() throws Exception {

    }

    @Test
    public void testDbPutHex() throws Exception {

    }

    @Test
    public void testDbGetHex() throws Exception {

    }

    @Test
    public void testShhPost() throws Exception {

    }

    @Ignore // The method shh_version does not exist/is not available
    @Test
    public void testShhVersion() throws Exception {
        ShhVersion shhVersion = web3j.shhVersion().send();
        assertNotNull(shhVersion.getVersion());
    }

    @Ignore  // The method shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewIdentity() throws Exception {
        ShhNewIdentity shhNewIdentity = web3j.shhNewIdentity().send();
        assertNotNull(shhNewIdentity.getAddress());
    }

    @Test
    public void testShhHasIdentity() throws Exception {

    }

    @Ignore  // The method shh_newIdentity does not exist/is not available
    @Test
    public void testShhNewGroup() throws Exception {
        ShhNewGroup shhNewGroup = web3j.shhNewGroup().send();
        assertNotNull(shhNewGroup.getAddress());
    }

    @Ignore  // The method shh_addToGroup does not exist/is not available
    @Test
    public void testShhAddToGroup() throws Exception {

    }

    @Test
    public void testShhNewFilter() throws Exception {

    }

    @Test
    public void testShhUninstallFilter() throws Exception {

    }

    @Test
    public void testShhGetFilterChanges() throws Exception {

    }

    @Test
    public void testShhGetMessages() throws Exception {

    }
}
