package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_sendRawTransaction.
 */
public class PlatonSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
