package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_uninstallFilter.
 */
public class PlatonUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
