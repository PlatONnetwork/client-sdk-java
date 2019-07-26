package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class DelegationIdInfo {

    /**
     * 验证人节点的地址
     */
    @JSONField(name = "Addr")
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public String toString() {
        return "DelegationIdInfo{" +
                "address='" + address + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", stakingBlockNum=" + stakingBlockNum +
                '}';
    }
}
