package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * shh_uninstallFilter.
 */
public class ShhUninstallFilter extends Response<Boolean> {

    public boolean isUninstalled() {
        return getResult();
    }
}
