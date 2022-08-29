package com.platon.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.utils.Numeric;

import java.math.BigInteger;

public class DelegationLockItem {

    /**
     * 解锁的周期
     */
    @JSONField(name = "Epoch")
    private BigInteger epoch;
    /**
     * 锁定的金额,自由账户
     */
    @JSONField(name = "Released")
    private BigInteger released;
    /**
     * 锁定的金额,锁仓账户
     */
    @JSONField(name = "RestrictingPlan")
    private BigInteger restrictingPlan;

    public BigInteger getEpoch() {
        return epoch;
    }
    public void setEpoch(BigInteger epoch) {
        this.epoch = epoch;
    }

    public BigInteger getReleased() {
        return released;
    }
    public void setReleased(String released) {
        if(released != null && released.length()>0) {
            this.released = Numeric.decodeQuantity(released);
        }else {
            this.released = BigInteger.ZERO;
        }
    }

    public BigInteger getRestrictingPlan() {
        return restrictingPlan;
    }
    public void setRestrictingPlan(String restrictingPlan) {
        if(restrictingPlan != null && restrictingPlan.length()>0) {
            this.restrictingPlan = Numeric.decodeQuantity(restrictingPlan);
        }else {
            this.restrictingPlan = BigInteger.ZERO;
        }
    }

    @Override
    public String toString() {
        return "DelegationLockItem{" +
                "epoch=" + epoch +
                ", released=" + released +
                ", restrictingPlan=" + restrictingPlan +
                '}';
    }
}
