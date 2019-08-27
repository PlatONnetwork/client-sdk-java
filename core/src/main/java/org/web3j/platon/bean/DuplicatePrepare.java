package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class DuplicatePrepare {

    @JSONField(name = "PrepareA")
    private DuplicateView prepareA;
    @JSONField(name = "PrepareB")
    private DuplicateView prepareB;

    public DuplicatePrepare() {
    }

    public DuplicateView getPrepareA() {
        return prepareA;
    }

    public void setPrepareA(DuplicateView prepareA) {
        this.prepareA = prepareA;
    }

    public DuplicateView getPrepareB() {
        return prepareB;
    }

    public void setPrepareB(DuplicateView prepareB) {
        this.prepareB = prepareB;
    }
}
