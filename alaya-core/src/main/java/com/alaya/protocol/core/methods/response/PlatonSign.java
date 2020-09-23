package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_sign.
 */
public class PlatonSign extends Response<String> {
    public String getSignature() {
        return getResult();
    }
}
