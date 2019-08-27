package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

public class DulicateViewChange {

    @JSONField(name = "ViewA")
    private DuplicateView viewA;
    @JSONField(name = "ViewB")
    private DuplicateView viewB;

    public DulicateViewChange() {

    }

    public DuplicateView getViewA() {
        return viewA;
    }

    public void setViewA(DuplicateView viewA) {
        this.viewA = viewA;
    }

    public DuplicateView getViewB() {
        return viewB;
    }

    public void setViewB(DuplicateView viewB) {
        this.viewB = viewB;
    }
}
