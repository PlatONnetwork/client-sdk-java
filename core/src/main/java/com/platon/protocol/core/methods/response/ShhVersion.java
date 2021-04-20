package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * shh_version.
 */
public class ShhVersion extends Response<String> {

    public String getVersion() {
        return getResult();
    }
}
