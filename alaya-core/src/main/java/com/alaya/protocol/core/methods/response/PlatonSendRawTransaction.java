package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_sendRawTransaction.
 */
public class PlatonSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
