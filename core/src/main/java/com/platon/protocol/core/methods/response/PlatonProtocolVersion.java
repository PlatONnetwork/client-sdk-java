package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_protocolVersion.
 */
public class PlatonProtocolVersion extends Response<String> {
    public String getProtocolVersion() {
        return getResult();
    }
}
