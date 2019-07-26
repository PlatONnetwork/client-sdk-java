package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class RestrictingInfo {

    /**
     * 释放区块高度
     */
    private BigInteger blockNum;
    /**
     * 释放金额
     */
    private BigInteger amount;

    public BigInteger getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(BigInteger blockNum) {
        this.blockNum = blockNum;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RestrictingInfo{" +
                "blockNum=" + blockNum +
                ", amount=" + amount +
                '}';
    }
}
