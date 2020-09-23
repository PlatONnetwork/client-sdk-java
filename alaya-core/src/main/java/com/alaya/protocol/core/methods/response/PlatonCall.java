package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_call.
 */
public class PlatonCall extends Response<String> {
    public String getValue() {
        return getResult();
    }
}
