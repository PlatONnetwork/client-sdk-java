package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class ValidateNode {

    private BigInteger index;

    private String address;

    @JSONField(name = "NodeID")
    private String nodeId;

    private String blsPubKey;

    public ValidateNode() {
    }

    public BigInteger getIndex() {
        return index;
    }

    public void setIndex(BigInteger index) {
        this.index = index;
    }

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

    public String getBlsPubKey() {
        return blsPubKey;
    }

    public void setBlsPubKey(String blsPubKey) {
        this.blsPubKey = blsPubKey;
    }
}
