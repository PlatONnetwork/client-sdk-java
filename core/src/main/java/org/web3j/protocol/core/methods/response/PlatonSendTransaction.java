package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * eth_sendTransaction.
 */
public class PlatonSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
