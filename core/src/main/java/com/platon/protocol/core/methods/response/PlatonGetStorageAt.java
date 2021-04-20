package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class PlatonGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
