package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_getCode.
 */
public class PlatonGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
