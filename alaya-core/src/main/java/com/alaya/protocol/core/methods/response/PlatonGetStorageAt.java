package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_getStorageAt.
 */
public class PlatonGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
