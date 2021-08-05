package com.platon.protocol.admin;

import com.platon.protocol.Web3jService;
import com.platon.protocol.admin.methods.response.NewAccountIdentifier;
import com.platon.protocol.admin.methods.response.PersonalListAccounts;
import com.platon.protocol.admin.methods.response.PersonalUnlockAccount;
import com.platon.protocol.admin.methods.response.TxPoolContent;
import com.platon.protocol.core.JsonRpc2_0Web3j;
import com.platon.protocol.core.Request;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

/**
 * JSON-RPC 2.0 factory implementation for common Parity and Geth.
 */
public class JsonRpc2_0Admin extends JsonRpc2_0Web3j implements Admin {

    public JsonRpc2_0Admin(Web3jService web3jService) {
        super(web3jService);
    }
    
    public JsonRpc2_0Admin(Web3jService web3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(web3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, PersonalListAccounts> personalListAccounts() {
        return new Request<>(
                "personal_listAccounts",
                Collections.<String>emptyList(),
                web3jService,
                PersonalListAccounts.class);
    }

    @Override
    public Request<?, NewAccountIdentifier> personalNewAccount(String password) {
        return new Request<>(
                "personal_newAccount",
                Arrays.asList(password),
                web3jService,
                NewAccountIdentifier.class);
    }   

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password,
            BigInteger duration) {
        List<Object> attributes = new ArrayList<>(3);
        attributes.add(accountId);
        attributes.add(password);
        
        if (duration != null) {
            // Parity has a bug where it won't support a duration
            // See https://github.com/ethcore/parity/issues/1215
            attributes.add(duration.longValue());
        } else {
            // we still need to include the null value, otherwise Parity rejects request
            attributes.add(null);
        }
        
        return new Request<>(
                "personal_unlockAccount",
                attributes,
                web3jService,
                PersonalUnlockAccount.class);
    }
    
    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password) {
        
        return personalUnlockAccount(accountId, password, null);
    }
    
    @Override
    public Request<?, PlatonSendTransaction> personalSendTransaction(
            Transaction transaction, String passphrase) {
        return new Request<>(
                "personal_sendTransaction",
                Arrays.asList(transaction, passphrase),
                web3jService,
                PlatonSendTransaction.class);
    }

    @Override
    public Request<?, TxPoolContent> txPoolContent() {
        return new Request<>(
                "txpool_content",
                Collections.<String>emptyList(),
                web3jService,
                TxPoolContent.class);
    }

}
