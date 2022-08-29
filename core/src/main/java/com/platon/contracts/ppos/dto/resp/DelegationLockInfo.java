package com.platon.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class DelegationLockInfo {

    /**
     * 处于解锁期的委托金,待用户领取后返回到用户余额
     */
    @JSONField(name = "Released")
    private BigInteger released;
    /**
     * 处于解锁期的委托金,待用户领取后返回到用户锁仓账户
     */
    @JSONField(name = "RestrictingPlan")
    private BigInteger restrictingPlan;

    /**
     * 处于锁定期的委托金
     */
    @JSONField(name = "Locks")
    private List<DelegationLockItem> locks;

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

    public List<DelegationLockItem> getLocks() {
        return locks;
    }
    public void setLocks(List<DelegationLockItem> locks) {
        this.locks = locks;
    }

    @Override
    public String toString() {
        return "DelegationLockInfo{" +
                "released=" + released +
                ", restrictingPlan=" + restrictingPlan +
                ", locks=" + locks +
                '}';
    }
}
