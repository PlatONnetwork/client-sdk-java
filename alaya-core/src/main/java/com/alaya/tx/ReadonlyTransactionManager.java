package com.alaya.tx;

import java.io.IOException;
import java.math.BigInteger;

import com.alaya.protocol.Web3j;
import com.alaya.protocol.core.methods.response.PlatonSendTransaction;

/**
 * Transaction manager implementation for read-only operations on smart contracts.
 */
public class ReadonlyTransactionManager extends TransactionManager {

    public ReadonlyTransactionManager(Web3j web3j, String fromAddress) {
        super(web3j, fromAddress);
    }

    @Override
    public PlatonSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, String data, BigInteger value)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }
}
