package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class DuplicateView {

    private BigInteger epoch;

    @JSONField(name = "view_number")
    private BigInteger viewNumber;
    @JSONField(name = "block_hash")
    private String blockHash;
    @JSONField(name = "block_number")
    private BigInteger blockNumber;
    @JSONField(name = "validate_node")
    private ValidateNode validateNode;
    @JSONField(name = "signature")
    private String signature;
    @JSONField(name = "block_epoch")
    private BigInteger blockEpoch;
    @JSONField(name = "block_view")
    private BigInteger blockView;

    public DuplicateView() {
    }

    public BigInteger getEpoch() {
        return epoch;
    }

    public void setEpoch(BigInteger epoch) {
        this.epoch = epoch;
    }

    public BigInteger getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(BigInteger viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public ValidateNode getValidateNode() {
        return validateNode;
    }

    public void setValidateNode(ValidateNode validateNode) {
        this.validateNode = validateNode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public BigInteger getBlockEpoch() {
        return blockEpoch;
    }

    public void setBlockEpoch(BigInteger blockEpoch) {
        this.blockEpoch = blockEpoch;
    }

    public BigInteger getBlockView() {
        return blockView;
    }

    public void setBlockView(BigInteger blockView) {
        this.blockView = blockView;
    }
}
