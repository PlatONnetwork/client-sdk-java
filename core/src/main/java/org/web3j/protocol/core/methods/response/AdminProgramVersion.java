package org.web3j.protocol.core.methods.response;

import org.web3j.platon.bean.ProgramVersion;
import org.web3j.protocol.core.Response;

/**
 * platon_evidences.
 */
public class AdminProgramVersion extends Response<ProgramVersion> {

    public ProgramVersion getAdminProgramVersion() {
        return getResult();
    }
}
