package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * platon_evidences.
 */
public class AdminSchnorrNIZKProve extends Response<String> {

    public String getAdminSchnorrNIZKProve() {
        return getResult();
    }
}
