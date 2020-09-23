package com.alaya.tx.response;

import java.io.IOException;

import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.methods.response.TransactionReceipt;
import com.alaya.protocol.exceptions.TransactionException;

/**
 * Return an {@link EmptyTransactionReceipt} receipt back to callers containing only the
 * transaction hash.
 */
public class NoOpProcessor extends TransactionReceiptProcessor {

    public NoOpProcessor(Web3j web3j) {
        super(web3j);
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {
        return new EmptyTransactionReceipt(transactionHash);
    }
}
