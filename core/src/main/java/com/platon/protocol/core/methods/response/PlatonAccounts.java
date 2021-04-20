package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

import java.util.List;

/**
 * eth_accounts.
 */
public class PlatonAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
