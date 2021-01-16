package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_call.
 */
public class PlatonCall extends Response<String> {
    public String getValue() {
        return getResult();
    }
}
