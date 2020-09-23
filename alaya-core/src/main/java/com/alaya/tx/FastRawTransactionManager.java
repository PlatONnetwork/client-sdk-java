package com.alaya.tx;

import java.io.IOException;
import java.math.BigInteger;

import com.alaya.crypto.Credentials;
import com.alaya.tx.response.TransactionReceiptProcessor;
import com.alaya.protocol.Web3j;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

    private volatile BigInteger nonce = BigInteger.valueOf(-1);

    public FastRawTransactionManager(Web3j web3j, Credentials credentials, long chainId) {
        super(web3j, credentials, chainId);
    }


    public FastRawTransactionManager(
            Web3j web3j, Credentials credentials, long chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(web3j, credentials, chainId, transactionReceiptProcessor);
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
