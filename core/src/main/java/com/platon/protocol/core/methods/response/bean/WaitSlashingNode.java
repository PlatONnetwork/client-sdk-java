package com.platon.protocol.core.methods.response.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;

/**
 * @Author liushuyu
 * @Date 2021/7/6 14:38
 * @Version
 * @Desc
 */
public class WaitSlashingNode {

    //零出块的节点ID
    @JsonProperty("NodeId")
    private String nodeId;

    //观察期内第一次零出块时所处共识轮数
    @JsonProperty("Round")
    private BigInteger round;

    //零出块次数位图（从Round开始，1表示该轮未出块）
    @JsonProperty("CountBit")
    private BigInteger countBit;


    public String getNodeId() {
        return nodeId;
    }

    @JsonIgnore
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public BigInteger getRound() {
        return round;
    }

    @JsonIgnore
    public void setRound(BigInteger round) {
        this.round = round;
    }

    public BigInteger getCountBit() {
        return countBit;
    }

    @JsonIgnore
    public void setCountBit(BigInteger countBit) {
        this.countBit = countBit;
    }

    @Override
    public String toString() {
        return "WaitSlashingNode{" +
                "nodeId='" + nodeId + '\'' +
                ", round=" + round +
                ", countBit=" + countBit +
                '}';
    }
}
