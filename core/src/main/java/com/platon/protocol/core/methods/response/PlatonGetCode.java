package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_getCode.
 */
public class PlatonGetCode extends Response<String> {
    public String getCode() {
        return getResult();
    }
}
