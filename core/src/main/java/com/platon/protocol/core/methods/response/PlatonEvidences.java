package com.platon.protocol.core.methods.response;

import com.alibaba.fastjson.JSON;
import com.platon.protocol.core.Response;
import com.platon.protocol.core.methods.response.bean.Evidences;

/**
 * platon_evidences.
 */
public class PlatonEvidences extends Response<String> {

    public String getEvidencesStr() {
        return getResult();
    }
    
    public Evidences getEvidences() {    	
    	return JSON.parseObject(getResult(), Evidences.class); 
    }
}
