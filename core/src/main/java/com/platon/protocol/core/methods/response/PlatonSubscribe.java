package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

public class PlatonSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
