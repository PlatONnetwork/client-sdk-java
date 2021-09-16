package com.platon.protocol.admin;

import com.platon.protocol.Web3j;
import com.platon.protocol.Web3jService;
import com.platon.protocol.admin.methods.response.*;
import com.platon.protocol.core.Request;
import com.platon.protocol.core.methods.PlatonSignTransaction;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.core.methods.response.VoidResponse;

import java.math.BigInteger;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC Request object building factory for common Parity and Geth. 
 */
public interface Admin extends Web3j {

    static Admin build(Web3jService web3jService) {
        return new JsonRpc2_0Admin(web3jService);
    }
    
    static Admin build(
            Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Admin(web3jService, pollingInterval, scheduledExecutorService);
    }

    Request<?, PersonalImportRawKey> personalImportRawKey(String keydata, String password);

    Request<?, BooleanResponse> personalLockAccount(String accountId);

    Request<?, PersonalSign> personalSign(String message, String accountId, String password);

    Request<?, PersonalSign> personalSignAndSendTransaction(Transaction transaction, String password);

    Request<?, PlatonSignTransaction> personalSignTransaction(Transaction transaction, String password);

    Request<?, PersonalEcRecover> personalEcRecover(String message, String signiture);

    Request<?, PersonalListWallets> personalListWallets();

    Request<?, PersonalListAccounts> personalListAccounts();
    
    Request<?, NewAccountIdentifier> personalNewAccount(String password);
    
    Request<?, PersonalUnlockAccount> personalUnlockAccount(
     String address, String passphrase, BigInteger duration);
    
    Request<?, PersonalUnlockAccount> personalUnlockAccount(
     String address, String passphrase);

    Request<?, VoidResponse> personalOpenWallet(String url, String passphrase);
    
    Request<?, PlatonSendTransaction> personalSendTransaction(
     Transaction transaction, String password);

    Request<?, TxPoolContent> txPoolContent();

}   
