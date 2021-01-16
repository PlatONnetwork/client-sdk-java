package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * db_getHex.
 */
public class DbGetHex extends Response<String> {

    public String getStoredValue() {
        return getResult();
    }
}
