package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

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
     * 处于撤销计划中的von
     */
    @JSONField(name = "Reduction")
    private BigInteger delegateReduction;

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

    public void setDelegateReleased(BigInteger delegateReleased) {
        this.delegateReleased = delegateReleased;
    }

    public BigInteger getDelegateReleasedHes() {
        return delegateReleasedHes;
    }

    public void setDelegateReleasedHes(BigInteger delegateReleasedHes) {
        this.delegateReleasedHes = delegateReleasedHes;
    }

    public BigInteger getDelegateLocked() {
        return delegateLocked;
    }

    public void setDelegateLocked(BigInteger delegateLocked) {
        this.delegateLocked = delegateLocked;
    }

    public BigInteger getDelegateLockedHes() {
        return delegateLockedHes;
    }

    public void setDelegateLockedHes(BigInteger delegateLockedHes) {
        this.delegateLockedHes = delegateLockedHes;
    }

    public BigInteger getDelegateReduction() {
        return delegateReduction;
    }

    public void setDelegateReduction(BigInteger delegateReduction) {
        this.delegateReduction = delegateReduction;
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
                ", delegateReduction=" + delegateReduction +
                '}';
    }
}
