package com.platon.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

/**
 * @author liushuyu
 * @date 2022/8/21 20:01
 * @desc
 */
public class DelegationLockInfo {

    /**
     * 处于锁定期的委托金,结构见下
     */
    @JSONField(name = "Locks")
    private List<Lock> locks;
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

    public List<Lock> getLocks() {
        return locks;
    }

    public BigInteger getReleased() {
        return released;
    }

    public BigInteger getRestrictingPlan() {
        return restrictingPlan;
    }

    public void setLocks(List<Lock> locks) {
        this.locks = locks;
    }

    public void setReleased(String released) {
        if (released != null && released.length() > 0) {
            this.released = Numeric.decodeQuantity(released);
        } else {
            this.released = BigInteger.ZERO;
        }
    }

    public void setRestrictingPlan(String restrictingPlan) {
        if (restrictingPlan != null && restrictingPlan.length() > 0) {
            this.restrictingPlan = Numeric.decodeQuantity(restrictingPlan);
        } else {
            this.restrictingPlan = BigInteger.ZERO;
        }
    }

    @Override
    public String toString() {
        return "DelegationLockInfo{" +
                "locks=" + locks +
                ", released='" + released + '\'' +
                ", restrictingPlan='" + restrictingPlan + '\'' +
                '}';
    }

    public static class Lock {
        /**
         * 解锁的周期
         */
        @JSONField(name = "Epoch")
        private Integer epoch;
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

        public Integer getEpoch() {
            return epoch;
        }

        public void setEpoch(Integer epoch) {
            this.epoch = epoch;
        }

        public BigInteger getReleased() {
            return released;
        }

        public void setReleased(String released) {
            if (released != null && released.length() > 0) {
                this.released = Numeric.decodeQuantity(released);
            } else {
                this.released = BigInteger.ZERO;
            }
        }

        public BigInteger getRestrictingPlan() {
            return restrictingPlan;
        }

        public void setRestrictingPlan(String restrictingPlan) {
            if (restrictingPlan != null && restrictingPlan.length() > 0) {
                this.restrictingPlan = Numeric.decodeQuantity(restrictingPlan);
            } else {
                this.restrictingPlan = BigInteger.ZERO;
            }
        }

        @Override
        public String toString() {
            return "Lock{" +
                    "epoch=" + epoch +
                    ", released='" + released + '\'' +
                    ", restrictingPlan='" + restrictingPlan + '\'' +
                    '}';
        }
    }
}
