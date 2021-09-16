package com.platon.protocol.core;

import com.platon.protocol.admin.methods.response.BooleanResponse;
import com.platon.protocol.admin.methods.response.TxPoolStatus;
import com.platon.protocol.admin.methods.response.admin.AdminDataDir;
import com.platon.protocol.core.methods.DebugWaitSlashingNodeList;
import com.platon.protocol.core.methods.PlatonSignTransaction;
import com.platon.protocol.core.methods.request.ShhFilter;
import com.platon.protocol.core.methods.response.*;

import java.math.BigInteger;

/**
 * Core Ethereum JSON-RPC API.
 */
public interface Platon {
    Request<?, Web3ClientVersion> web3ClientVersion();

    Request<?, Web3Sha3> web3Sha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, AdminNodeInfo> adminNodeInfo();

    Request<?, AdminPeers> adminPeers();

    Request<?, BooleanResponse> adminAddPeer(String url);

    Request<?, BooleanResponse> adminRemovePeer(String url);

    Request<?, AdminDataDir> adminDataDir();

    Request<?, BooleanResponse> adminStartRPC(String host,int port,String cors,String apis);

    Request<?, BooleanResponse> adminStartWS(String host,int port,String cors,String apis);

    Request<?, BooleanResponse> adminStopRPC();

    Request<?, BooleanResponse> adminStopWS();

    Request<?, BooleanResponse> adminExportChain(String file);

    Request<?, BooleanResponse> adminImportChain(String file);

    Request<?, PlatonProtocolVersion> platonProtocolVersion();

    Request<?, PlatonSyncing> platonSyncing();

    Request<?, PlatonGasPrice> platonGasPrice();

    Request<?, PlatonAccounts> platonAccounts();

    Request<?, PlatonBlockNumber> platonBlockNumber();

    Request<?, PlatonGetBalance> platonGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonGetStorageAt> platonGetStorageAt(
            String address, BigInteger position,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonGetTransactionCount> platonGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonGetBlockTransactionCountByHash> platonGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, PlatonGetBlockTransactionCountByNumber> platonGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonGetCode> platonGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonSign> platonSign(String address, String sha3HashOfDataToSign);

    Request<?, PlatonSendTransaction> platonSendTransaction(
            com.platon.protocol.core.methods.request.Transaction transaction);

    Request<?, PlatonSendTransaction> platonSendRawTransaction(
            String signedTransactionData);

    Request<?, PlatonCall> platonCall(
            com.platon.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, PlatonEstimateGas> platonEstimateGas(
            com.platon.protocol.core.methods.request.Transaction transaction);

    Request<?, PlatonBlock> platonGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, PlatonBlock> platonGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);

    Request<?, PlatonTransaction> platonGetTransactionByHash(String transactionHash);

    Request<?, PlatonTransaction> platonGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, PlatonTransaction> platonGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, PlatonGetTransactionReceipt> platonGetTransactionReceipt(String transactionHash);

    Request<?, PlatonFilter> platonNewFilter(com.platon.protocol.core.methods.request.PlatonFilter ethFilter);

    Request<?, PlatonFilter> platonNewBlockFilter();

    Request<?, PlatonFilter> platonNewPendingTransactionFilter();

    Request<?, PlatonUninstallFilter> platonUninstallFilter(BigInteger filterId);

    Request<?, PlatonLog> platonGetFilterChanges(BigInteger filterId);

    Request<?, PlatonLog> platonGetFilterLogs(BigInteger filterId);

    Request<?, PlatonLog> platonGetLogs(com.platon.protocol.core.methods.request.PlatonFilter ethFilter);

    Request<?, PlatonPendingTransactions> platonPendingTx();

    Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    Request<?, ShhPost> shhPost(
            com.platon.protocol.core.methods.request.ShhPost shhPost);

    Request<?, ShhVersion> shhVersion();

    Request<?, ShhNewIdentity> shhNewIdentity();

    Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    Request<?, ShhNewGroup> shhNewGroup();

    Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    Request<?, ShhMessages> shhGetMessages(BigInteger filterId);

    Request<?, TxPoolStatus> txPoolStatus();

    Request<?, PlatonEvidences> platonEvidences();
    
    Request<?, AdminProgramVersion> getProgramVersion();
    
    Request<?, AdminSchnorrNIZKProve> getSchnorrNIZKProve();
    
    Request<?, DebugEconomicConfig> getEconomicConfig();

    Request<?, PlatonChainId> getChainId();

    Request<?, DebugWaitSlashingNodeList> getWaitSlashingNodeList();

    Request<?, PlatonRawTransaction> platonGetRawTransactionByHash(String transactionHash);

    Request<?, PlatonRawTransaction> platonGetRawTransactionByBlockHashAndIndex(String blockHash, String index);

    Request<?, PlatonRawTransaction> platonGetRawTransactionByBlockNumberAndIndex(String blockNumber, String index);

    Request<?, PlatonGetAddressHrp> platonGetAddressHrp();

    Request<?, PlatonSignTransaction> platonSignTransaction(com.platon.protocol.core.methods.request.Transaction transaction);

    Request<?, BooleanResponse> minerSetGasPrice(String minGasPrice);

    Request<?, AdminPeerEvents> adminPeerEvents();
}
