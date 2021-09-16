package com.platon.protocol.admin.methods.response;


import com.platon.protocol.core.Response;

/** personal_ecRecover. */
public class PersonalEcRecover extends Response<String> {
    public String getRecoverAccountId() {
        return getResult();
    }
}
