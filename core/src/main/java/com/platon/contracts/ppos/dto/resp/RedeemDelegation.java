package com.platon.contracts.ppos.dto.resp;

import java.math.BigInteger;
import java.util.Optional;

public class RedeemDelegation {

    /**
     * 成功领取的委托金,回到余额
     */
    private BigInteger released;

    /**
     * 成功领取的委托金,回到锁仓账户
     */
    private BigInteger restrictingPlan;

    public BigInteger getReleased() {
        return released;
    }

    public void setReleased(BigInteger released) {
        this.released = released;
    }

    public BigInteger getRestrictingPlan() {
        return restrictingPlan;
    }

    public void setRestrictingPlan(BigInteger restrictingPlan) {
        this.restrictingPlan = restrictingPlan;
    }

    @Override
    public String toString() {
        return "RedeemDelegation{" +
                "released=" + released +
                ", restrictingPlan=" + restrictingPlan +
                '}';
    }
}
