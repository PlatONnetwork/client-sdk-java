package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_sign.
 */
public class PlatonSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
