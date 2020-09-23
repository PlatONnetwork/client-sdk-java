package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class PlatonSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
