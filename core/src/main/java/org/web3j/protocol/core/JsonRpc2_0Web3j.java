package org.web3j.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.web3j.protocol.core.methods.response.*;
import rx.Observable;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.methods.request.ShhFilter;
import org.web3j.protocol.core.methods.request.ShhPost;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.rx.JsonRpc2_0Rx;
import org.web3j.protocol.websocket.events.LogNotification;
import org.web3j.protocol.websocket.events.NewHeadsNotification;
import org.web3j.utils.Async;
import org.web3j.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Web3j implements Web3j {

    public static final int DEFAULT_BLOCK_TIME = 2 * 1000;

    protected final Web3jService web3jService;
    private final JsonRpc2_0Rx web3jRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;

    public JsonRpc2_0Web3j(Web3jService web3jService) {
        this(web3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Web3j(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.web3jService = web3jService;
        this.web3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public Request<?, Web3ClientVersion> web3ClientVersion() {
        return new Request<>(
                "web3_clientVersion",
                Collections.<String>emptyList(),
                web3jService,
                Web3ClientVersion.class);
    }

    @Override
    public Request<?, Web3Sha3> web3Sha3(String data) {
        return new Request<>(
                "web3_sha3",
                Arrays.asList(data),
                web3jService,
                Web3Sha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                web3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                web3jService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                web3jService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, PlatonProtocolVersion> platonProtocolVersion() {
        return new Request<>(
                "platon_protocolVersion",
                Collections.<String>emptyList(),
                web3jService,
                PlatonProtocolVersion.class);
    }

    @Override
    public Request<?, PlatonSyncing> platonSyncing() {
        return new Request<>(
                "platon_syncing",
                Collections.<String>emptyList(),
                web3jService,
                PlatonSyncing.class);
    }

    @Override
    public Request<?, PlatonGasPrice> platonGasPrice() {
        return new Request<>(
                "platon_gasPrice",
                Collections.<String>emptyList(),
                web3jService,
                PlatonGasPrice.class);
    }

    @Override
    public Request<?, PlatonAccounts> platonAccounts() {
        return new Request<>(
                "platon_accounts",
                Collections.<String>emptyList(),
                web3jService,
                PlatonAccounts.class);
    }

    @Override
    public Request<?, PlatonBlockNumber> platonBlockNumber() {
        return new Request<>(
                "platon_blockNumber",
                Collections.<String>emptyList(),
                web3jService,
                PlatonBlockNumber.class);
    }

    @Override
    public Request<?, PlatonGetBalance> platonGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                PlatonGetBalance.class);
    }

    @Override
    public Request<?, PlatonGetStorageAt> platonGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                web3jService,
                PlatonGetStorageAt.class);
    }

    @Override
    public Request<?, PlatonGetTransactionCount> ethGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                PlatonGetTransactionCount.class);
    }

    @Override
    public Request<?, PlatonGetBlockTransactionCountByHash> platonGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "platon_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                web3jService,
                PlatonGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, PlatonGetBlockTransactionCountByNumber> ethGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                web3jService,
                PlatonGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, PlatonGetCode> platonGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                web3jService,
                PlatonGetCode.class);
    }

    @Override
    public Request<?, PlatonSign> platonSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "platon_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                web3jService,
                PlatonSign.class);
    }

    @Override
    public Request<?, PlatonSendTransaction>
            ethSendTransaction(
            Transaction transaction) {
        return new Request<>(
                "platon_sendTransaction",
                Arrays.asList(transaction),
                web3jService,
                PlatonSendTransaction.class);
    }

    @Override
    public Request<?, PlatonSendTransaction>
    platonSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "platon_sendRawTransaction",
                Arrays.asList(signedTransactionData),
                web3jService,
                PlatonSendTransaction.class);
    }

    @Override
    public Request<?, PlatonCall> platonCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "platon_call",
                Arrays.asList(transaction, defaultBlockParameter),
                web3jService,
                PlatonCall.class);
    }

    @Override
    public Request<?, PlatonEstimateGas> platonEstimateGas(Transaction transaction) {
        return new Request<>(
                "platon_estimateGas",
                Arrays.asList(transaction),
                web3jService,
                PlatonEstimateGas.class);
    }

    @Override
    public Request<?, PlatonBlock> platonGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "platon_getBlockByHash",
                Arrays.asList(
                        blockHash,
                        returnFullTransactionObjects),
                web3jService,
                PlatonBlock.class);
    }

    @Override
    public Request<?, PlatonBlock> platonGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "platon_getBlockByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                web3jService,
                PlatonBlock.class);
    }

    @Override
    public Request<?, PlatonTransaction> platonGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "platon_getTransactionByHash",
                Arrays.asList(transactionHash),
                web3jService,
                PlatonTransaction.class);
    }


    @Override
    public Request<?, PlatonPendingTransactions> platonPendingTx() {
        return new Request<>(
                "platon_pendingTransactions",
                Collections.<String>emptyList(),
                web3jService,
                PlatonPendingTransactions.class);
    }


    @Override
    public Request<?, PlatonTransaction> platonGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "platon_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                PlatonTransaction.class);
    }

    @Override
    public Request<?, PlatonTransaction> platonGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "platon_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                web3jService,
                PlatonTransaction.class);
    }

    @Override
    public Request<?, PlatonGetTransactionReceipt> platonGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "platon_getTransactionReceipt",
                Arrays.asList(transactionHash),
                web3jService,
                PlatonGetTransactionReceipt.class);
    }

    @Override
    public Request<?, PlatonFilter> platonNewFilter(
            org.web3j.protocol.core.methods.request.PlatonFilter platonFilter) {
        return new Request<>(
                "platon_newFilter",
                Arrays.asList(platonFilter),
                web3jService,
                PlatonFilter.class);
    }

    @Override
    public Request<?, PlatonFilter> platonNewBlockFilter() {
        return new Request<>(
                "platon_newBlockFilter",
                Collections.<String>emptyList(),
                web3jService,
                PlatonFilter.class);
    }

    @Override
    public Request<?, PlatonFilter> platonNewPendingTransactionFilter() {
        return new Request<>(
                "platon_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                web3jService,
                PlatonFilter.class);
    }

    @Override
    public Request<?, PlatonUninstallFilter> platonUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "platon_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                PlatonUninstallFilter.class);
    }

    @Override
    public Request<?, PlatonLog> platonGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "platon_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                PlatonLog.class);
    }

    @Override
    public Request<?, PlatonLog> platonGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "platon_getFilterLogs",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                PlatonLog.class);
    }

    @Override
    public Request<?, PlatonLog> platonGetLogs(
            org.web3j.protocol.core.methods.request.PlatonFilter platonFilter) {
        return new Request<>(
                "platon_getLogs",
                Arrays.asList(platonFilter),
                web3jService,
                PlatonLog.class);
    }

    @Override
    public Request<?, DbPutString> dbPutString(
            String databaseName, String keyName, String stringToStore) {
        return new Request<>(
                "db_putString",
                Arrays.asList(databaseName, keyName, stringToStore),
                web3jService,
                DbPutString.class);
    }

    @Override
    public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
        return new Request<>(
                "db_getString",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetString.class);
    }

    @Override
    public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
        return new Request<>(
                "db_putHex",
                Arrays.asList(databaseName, keyName, dataToStore),
                web3jService,
                DbPutHex.class);
    }

    @Override
    public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
        return new Request<>(
                "db_getHex",
                Arrays.asList(databaseName, keyName),
                web3jService,
                DbGetHex.class);
    }

    @Override
    public Request<?, org.web3j.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
        return new Request<>(
                "shh_post",
                Arrays.asList(shhPost),
                web3jService,
                org.web3j.protocol.core.methods.response.ShhPost.class);
    }

    @Override
    public Request<?, ShhVersion> shhVersion() {
        return new Request<>(
                "shh_version",
                Collections.<String>emptyList(),
                web3jService,
                ShhVersion.class);
    }

    @Override
    public Request<?, ShhNewIdentity> shhNewIdentity() {
        return new Request<>(
                "shh_newIdentity",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewIdentity.class);
    }

    @Override
    public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
        return new Request<>(
                "shh_hasIdentity",
                Arrays.asList(identityAddress),
                web3jService,
                ShhHasIdentity.class);
    }

    @Override
    public Request<?, ShhNewGroup> shhNewGroup() {
        return new Request<>(
                "shh_newGroup",
                Collections.<String>emptyList(),
                web3jService,
                ShhNewGroup.class);
    }

    @Override
    public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
        return new Request<>(
                "shh_addToGroup",
                Arrays.asList(identityAddress),
                web3jService,
                ShhAddToGroup.class);
    }

    @Override
    public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
        return new Request<>(
                "shh_newFilter",
                Arrays.asList(shhFilter),
                web3jService,
                ShhNewFilter.class);
    }

    @Override
    public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "shh_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhUninstallFilter.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "shh_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
        return new Request<>(
                "shh_getMessages",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                web3jService,
                ShhMessages.class);
    }

    @Override
    public Observable<NewHeadsNotification> newHeadsNotifications() {
        return web3jService.subscribe(
                new Request<>(
                        "platon_subscribe",
                        Collections.singletonList("newHeads"),
                        web3jService,
                        PlatonSubscribe.class),
                "platon_unsubscribe",
                NewHeadsNotification.class
        );
    }

    @Override
    public Observable<LogNotification> logsNotifications(
            List<String> addresses, List<String> topics) {

        Map<String, Object> params = createLogsParams(addresses, topics);

        return web3jService.subscribe(
                new Request<>(
                        "platon_subscribe",
                        Arrays.asList("logs", params),
                        web3jService,
                        PlatonSubscribe.class),
                "platon_unsubscribe",
                LogNotification.class
        );
    }

    private Map<String, Object> createLogsParams(List<String> addresses, List<String> topics) {
        Map<String, Object> params = new HashMap<>();
        if (!addresses.isEmpty()) {
            params.put("address", addresses);
        }
        if (!topics.isEmpty()) {
            params.put("topics", topics);
        }
        return params;
    }

    @Override
    public Observable<String> platonBlockHashObservable() {
        return web3jRx.ethBlockHashObservable(blockTime);
    }

    @Override
    public Observable<String> platonPendingTransactionHashObservable() {
        return web3jRx.ethPendingTransactionHashObservable(blockTime);
    }

    @Override
    public Observable<Log> platonLogObservable(
            org.web3j.protocol.core.methods.request.PlatonFilter ethFilter) {
        return web3jRx.ethLogObservable(ethFilter, blockTime);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            transactionObservable() {
        return web3jRx.transactionObservable(blockTime);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            pendingTransactionObservable() {
        return web3jRx.pendingTransactionObservable(blockTime);
    }

    @Override
    public Observable<PlatonBlock> blockObservable(boolean fullTransactionObjects) {
        return web3jRx.blockObservable(fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<PlatonBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Observable<PlatonBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        return web3jRx.replayBlocksObservable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return web3jRx.replayTransactionsObservable(startBlock, endBlock);
    }

    @Override
    public Observable<PlatonBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<PlatonBlock> onCompleteObservable) {
        return web3jRx.catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, onCompleteObservable);
    }

    @Override
    public Observable<PlatonBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestBlockObservable(startBlock, fullTransactionObjects);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestTransactionObservable(DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestTransactionObservable(startBlock);
    }

    @Override
    public Observable<PlatonBlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return web3jRx.catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<org.web3j.protocol.core.methods.response.Transaction>
            catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock) {
        return web3jRx.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                startBlock, blockTime);
    }

    @Override
    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            web3jService.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close web3j service", e);
        }
    }

    @Override
    public Request<?, PlatonEvidences> platonEvidences() {
        return null;
    }
}
