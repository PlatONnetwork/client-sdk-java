package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * platon_evidences.
 */
public class PlatonEvidences extends Response<String> {
    public String getEvidences() {
        return getResult();
    }
}
