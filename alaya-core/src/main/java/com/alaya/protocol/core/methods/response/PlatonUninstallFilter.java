package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.Response;

/**
 * eth_uninstallFilter.
 */
public class PlatonUninstallFilter extends Response<Boolean> {
    public boolean isUninstalled() {
        return getResult();
    }
}
