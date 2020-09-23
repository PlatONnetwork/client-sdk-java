package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.methods.response.bean.ProgramVersion;
import com.alaya.protocol.core.Response;

/**
 * platon_evidences.
 */
public class AdminProgramVersion extends Response<ProgramVersion> {

    public ProgramVersion getAdminProgramVersion() {
        return getResult();
    }
}
