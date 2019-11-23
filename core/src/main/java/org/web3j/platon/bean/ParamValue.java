package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ParamValue {

    /**
     * 旧参数值
     */
    @JSONField(name = "StaleValue")
    private String staleValue;
    /**
     * 参数值
     */
    @JSONField(name = "Value")
    private String value;
    /**
     * 块高。(>=ActiveBLock，将取Value;否则取StaleValue)
     */
    @JSONField(name = "ActiveBlock")
    private String activeBlock;

    public ParamValue() {
    }

    public String getStaleValue() {
        return staleValue;
    }

    public void setStaleValue(String staleValue) {
        this.staleValue = staleValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getActiveBlock() {
        return activeBlock;
    }

    public void setActiveBlock(String activeBlock) {
        this.activeBlock = activeBlock;
    }

    @Override
    public String toString() {
        return "ParamValue{" +
                "staleValue='" + staleValue + '\'' +
                ", value='" + value + '\'' +
                ", activeBlock='" + activeBlock + '\'' +
                '}';
    }
}
