package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_protocolVersion.
 */
public class PlatonProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
