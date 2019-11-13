package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class GovernParam {

    @JSONField(name = "ParamItem")
    private ParamItem paramItem;

    @JSONField(name = "ParamValue")
    private ParamValue paramValue;

    public GovernParam() {
    }

    public ParamItem getParamItem() {
        return paramItem;
    }

    public void setParamItem(ParamItem paramItem) {
        this.paramItem = paramItem;
    }

    public ParamValue getParamValue() {
        return paramValue;
    }

    public void setParamValue(ParamValue paramValue) {
        this.paramValue = paramValue;
    }
}
