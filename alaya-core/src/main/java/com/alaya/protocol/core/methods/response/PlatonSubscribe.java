package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

public class PlatonSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
