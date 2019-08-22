package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class ProgramVersion {

    /**
     * 代码版本
     */
    @JSONField(name = "ProgramVersion")
    private BigInteger programVersion;
    /**
     * 代码版本签名
     */
    @JSONField(name = "ProgramVersionSign")
    private String programVersionSign;

    public ProgramVersion() {
    }

    public BigInteger getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(BigInteger programVersion) {
        this.programVersion = programVersion;
    }

    public String getProgramVersionSign() {
        return programVersionSign;
    }

    public void setProgramVersionSign(String programVersionSign) {
        this.programVersionSign = programVersionSign;
    }
}
