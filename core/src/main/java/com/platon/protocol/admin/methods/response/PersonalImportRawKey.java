package com.platon.protocol.admin.methods.response;


import com.platon.protocol.core.Response;

/** personal_importRawKey. */
public class PersonalImportRawKey extends Response<String> {
    public String getAccountId() {
        return getResult();
    }
}
