package com.alaya.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;

public class ParamItem {

    /**
     * 参数模块
     */
    @JSONField(name = "Module")
    private String module;
    /**
     * 参数名称
     */
    @JSONField(name = "Name")
    private String name;
    /**
     * 参数说明
     */
    @JSONField(name = "Desc")
    private String desc;

    public ParamItem() {
    }

    public ParamItem(String module, String name) {
        this.module = module;
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ParamItem{" +
                "module='" + module + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
