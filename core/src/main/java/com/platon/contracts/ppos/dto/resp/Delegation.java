package com.platon.contracts.ppos.dto.resp;

import com.alibaba.fastjson.annotation.JSONField;
import com.platon.utils.Numeric;

import java.math.BigInteger;

public class Delegation {

    /**
     * 委托人的账户地址
     */
    @JSONField(name = "Addr")
    private String delegateAddress;
    /**
     * 验证人的节点Id
     */
    @JSONField(name = "NodeId")
    private String nodeId;
    /**
     * 发起质押时的区块高度
     */
    @JSONField(name = "StakingBlockNum")
    private BigInteger stakingBlockNum;
    /**
     * 最近一次对该候选人发起的委托时的结算周期
     */
    @JSONField(name = "DelegateEpoch")
    private BigInteger delegateEpoch;
    /**
     * 发起委托账户的自由金额的锁定期委托的von
     */
    @JSONField(name = "Released")
    private BigInteger delegateReleased;
    /**
     * 发起委托账户的自由金额的犹豫期委托的von
     */
    @JSONField(name = "ReleasedHes")
    private BigInteger delegateReleasedHes;
    /**
     * 发起委托账户的锁仓金额的锁定期委托的von
     */
    @JSONField(name = "RestrictingPlan")
    private BigInteger delegateLocked;
    /**
     * 发起委托账户的锁仓金额的犹豫期委托的von
     */
    @JSONField(name = "RestrictingPlanHes")
    private BigInteger delegateLockedHes;
    /**
     * 待领取的委托收益von
     */
    @JSONField(name = "CumulativeIncome")
    private BigInteger cumulativeIncome;
    /**
     * 待领取的委托收益von
     */
    @JSONField(name = "LockHes")
    private BigInteger lockHes;
    /**
     * 待领取的委托收益von
     */
    @JSONField(name = "LockRestrictingHes")
    private BigInteger lockRestrictingHes;

    public BigInteger getLockRestrictingHes() {
        return lockRestrictingHes;
    }

    public void setLockRestrictingHes(String lockRestrictingHes) {
        if(lockRestrictingHes != null && lockRestrictingHes.length()>0) {
            this.lockRestrictingHes = Numeric.decodeQuantity(lockRestrictingHes);
        }else {
            this.lockRestrictingHes = BigInteger.ZERO;
        }
    }

    public BigInteger getLockHes() {
        return lockHes;
    }

    public void setLockHes(String lockHes) {
        if(lockHes != null && lockHes.length()>0) {
            this.lockHes = Numeric.decodeQuantity(lockHes);
        }else {
            this.lockHes = BigInteger.ZERO;
        }
    }

    public BigInteger getCumulativeIncome() {
        return cumulativeIncome;
    }

    public void setCumulativeIncome(String cumulativeIncome) {
        if(cumulativeIncome != null && cumulativeIncome.length()>0) {
            this.cumulativeIncome = Numeric.decodeQuantity(cumulativeIncome);
        }else {
            this.cumulativeIncome = BigInteger.ZERO;
        }
    }

    public String getDelegateAddress() {
        return delegateAddress;
    }

    public void setDelegateAddress(String delegateAddress) {
        this.delegateAddress = delegateAddress;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public BigInteger getStakingBlockNum() {
        return stakingBlockNum;
    }

    public void setStakingBlockNum(BigInteger stakingBlockNum) {
        this.stakingBlockNum = stakingBlockNum;
    }

    public BigInteger getDelegateEpoch() {
        return delegateEpoch;
    }

    public void setDelegateEpoch(BigInteger delegateEpoch) {
        this.delegateEpoch = delegateEpoch;
    }

    public BigInteger getDelegateReleased() {
        return delegateReleased;
    }

    public void setDelegateReleased(String delegateReleased) {
      	if(delegateReleased != null && delegateReleased.length()>0) {
      		this.delegateReleased = Numeric.decodeQuantity(delegateReleased);
      	}else {
      		this.delegateReleased = BigInteger.ZERO;
		}
    }

    public BigInteger getDelegateReleasedHes() {
        return delegateReleasedHes;
    }

    public void setDelegateReleasedHes(String delegateReleasedHes) {
      	if(delegateReleasedHes != null && delegateReleasedHes.length()>0) {
      		this.delegateReleasedHes = Numeric.decodeQuantity(delegateReleasedHes);
      	}else {
      		this.delegateReleasedHes = BigInteger.ZERO;
		}
    }

    public BigInteger getDelegateLocked() {
        return delegateLocked;
    }

    public void setDelegateLocked(String delegateLocked) {
      	if(delegateLocked != null && delegateLocked.length()>0) {
      		this.delegateLocked = Numeric.decodeQuantity(delegateLocked);
      	}else {
      		this.delegateLocked = BigInteger.ZERO;
		}
    }

    public BigInteger getDelegateLockedHes() {
        return delegateLockedHes;
    }

    public void setDelegateLockedHes(String delegateLockedHes) {
      	if(delegateLockedHes != null && delegateLockedHes.length()>0) {
      		this.delegateLockedHes = Numeric.decodeQuantity(delegateLockedHes);
      	}else {
      		this.delegateLockedHes = BigInteger.ZERO;
		}
    }

    @Override
    public String toString() {
        return "Delegation{" +
                "delegateAddress='" + delegateAddress + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", stakingBlockNum=" + stakingBlockNum +
                ", delegateEpoch=" + delegateEpoch +
                ", delegateReleased=" + delegateReleased +
                ", delegateReleasedHes=" + delegateReleasedHes +
                ", delegateLocked=" + delegateLocked +
                ", delegateLockedHes=" + delegateLockedHes +
                ", cumulativeIncome=" + cumulativeIncome +
                '}';
    }
}
