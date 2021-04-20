package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.protocol.core.methods.response.bean.EconomicConfig;
import com.platon.utils.JSONUtil;

/**
 * platon_evidences.
 */
public class DebugEconomicConfig extends Response<String> {

    public EconomicConfig getEconomicConfig() {
        return JSONUtil.parseObject(getResult(), EconomicConfig.class);
    }
}
