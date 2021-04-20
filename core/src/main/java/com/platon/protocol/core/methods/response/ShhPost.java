package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * shh_post.
 */
public class ShhPost extends Response<Boolean> {

    public boolean messageSent() {
        return getResult();
    }
}
