package org.web3j.protocol.core.methods.response;

import org.web3j.platon.bean.EconomicConfig;
import org.web3j.protocol.core.Response;
import org.web3j.utils.JSONUtil;

/**
 * platon_evidences.
 */
public class DebugEconomicConfig extends Response<String> {

    public EconomicConfig getEconomicConfig() {
        return JSONUtil.parseObject(getResult(), EconomicConfig.class);
    }
}
