package com.platon.protocol.admin;

import com.platon.protocol.Web3j;
import com.platon.protocol.Web3jService;
import com.platon.protocol.admin.methods.response.NewAccountIdentifier;
import com.platon.protocol.admin.methods.response.PersonalListAccounts;
import com.platon.protocol.admin.methods.response.PersonalUnlockAccount;
import com.platon.protocol.core.Request;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;

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

    public Request<?, PersonalListAccounts> personalListAccounts();
    
    public Request<?, NewAccountIdentifier> personalNewAccount(String password);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase, BigInteger duration);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase);
    
    public Request<?, PlatonSendTransaction> personalSendTransaction(
            Transaction transaction, String password);

}   
