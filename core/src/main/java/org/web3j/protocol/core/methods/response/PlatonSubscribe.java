package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

public class PlatonSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
