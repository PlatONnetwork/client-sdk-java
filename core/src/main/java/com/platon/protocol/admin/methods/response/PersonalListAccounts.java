package com.platon.protocol.admin.methods.response;

import com.platon.protocol.core.Response;

import java.util.List;

/**
 * personal_listAccounts.
 */
public class PersonalListAccounts extends Response<List<String>> {
    public List<String> getAccountIds() {
        return getResult();
    }
}
