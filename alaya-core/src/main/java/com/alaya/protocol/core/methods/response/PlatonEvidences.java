package com.alaya.protocol.core.methods.response;

import com.alaya.protocol.core.methods.response.bean.Evidences;
import com.alaya.protocol.core.Response;

import com.alibaba.fastjson.JSON;

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
