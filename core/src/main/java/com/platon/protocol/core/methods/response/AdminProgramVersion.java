package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.protocol.core.methods.response.bean.ProgramVersion;

/**
 * platon_evidences.
 */
public class AdminProgramVersion extends Response<ProgramVersion> {

    public ProgramVersion getAdminProgramVersion() {
        return getResult();
    }
}
