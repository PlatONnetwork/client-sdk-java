package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class PlatonSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
