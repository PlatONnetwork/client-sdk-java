package com.platon.tx;

import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

    private volatile BigInteger nonce = BigInteger.valueOf(-1);

    public FastRawTransactionManager(Web3j web3j, Credentials credentials) {
        super(web3j, credentials);
    }


    public FastRawTransactionManager(
            Web3j web3j, Credentials credentials,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(web3j, credentials, transactionReceiptProcessor);
    }

    @Override
    protected synchronized BigInteger getNonce() throws IOException {
        if (nonce.signum() == -1) {
            // obtain lock
            nonce = super.getNonce();
        } else {
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }

    public BigInteger getCurrentNonce() {
        return nonce;
    }

    public synchronized void resetNonce() throws IOException {
        nonce = super.getNonce();
    }

    public synchronized void setNonce(BigInteger value) {
        nonce = value;
    }
}
