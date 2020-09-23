package com.alaya.protocol.core.methods.response;

import java.util.List;

import com.alaya.protocol.core.Response;

/**
 * eth_accounts.
 */
public class PlatonAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
