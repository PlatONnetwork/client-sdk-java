package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

import java.util.List;

/**
 * User: dongqile
 * Date: 2018/11/12
 * Time: 14:19
 */
public class PlatonPendingTransactions extends Response<List<Transaction>> {
    public List<Transaction> getTransactions() {
        return getResult();
    }
}