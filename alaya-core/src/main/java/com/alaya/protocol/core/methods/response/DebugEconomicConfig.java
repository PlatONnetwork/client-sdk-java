package com.alaya.protocol.core.methods.response;

import com.alaya.utils.JSONUtil;
import com.alaya.protocol.core.methods.response.bean.EconomicConfig;
import com.alaya.protocol.core.Response;

/**
 * platon_evidences.
 */
public class DebugEconomicConfig extends Response<String> {

    public EconomicConfig getEconomicConfig() {
        return JSONUtil.parseObject(getResult(), EconomicConfig.class);
    }
}
