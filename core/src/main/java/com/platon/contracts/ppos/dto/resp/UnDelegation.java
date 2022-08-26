package com.platon.contracts.ppos.dto.resp;

import java.math.BigInteger;
import java.util.Optional;

public class UnDelegation {

    /**
     * 委托的收益
     */
    private BigInteger delegateIncome;

    /**
     * 撤销的委托金退回用户余额 （节点1.3.0后版本存在）
     */
    private Optional<BigInteger> released = Optional.empty();

    /**
     * 撤销的委托金退回用户锁仓账户（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> restrictingPlan = Optional.empty();

    /**
     * 撤销的委托金转到锁定期,来自余额（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> lockReleased = Optional.empty();

    /**
     * 撤销的委托金转到锁定期,来自锁仓账户（节点1.3.0后版本存在）
     */
    private Optional<BigInteger> lockRestrictingPlan = Optional.empty();

    public BigInteger getDelegateIncome() {
        return delegateIncome;
    }

    public void setDelegateIncome(BigInteger delegateIncome) {
        this.delegateIncome = delegateIncome;
    }

    public Optional<BigInteger> getReleased() {
        return released;
    }

    public void setReleased(Optional<BigInteger> released) {
        this.released = released;
    }

    public Optional<BigInteger> getRestrictingPlan() {
        return restrictingPlan;
    }

    public void setRestrictingPlan(Optional<BigInteger> restrictingPlan) {
        this.restrictingPlan = restrictingPlan;
    }

    public Optional<BigInteger> getLockReleased() {
        return lockReleased;
    }

    public void setLockReleased(Optional<BigInteger> lockReleased) {
        this.lockReleased = lockReleased;
    }

    public Optional<BigInteger> getLockRestrictingPlan() {
        return lockRestrictingPlan;
    }

    public void setLockRestrictingPlan(Optional<BigInteger> lockRestrictingPlan) {
        this.lockRestrictingPlan = lockRestrictingPlan;
    }

    @Override
    public String toString() {
        return "UnDelegation{" +
                "delegateIncome=" + delegateIncome +
                ", released=" + released +
                ", restrictingPlan=" + restrictingPlan +
                ", lockReleased=" + lockReleased +
                ", lockRestrictingPlan=" + lockRestrictingPlan +
                '}';
    }
}
