package com.alaya.contracts.ppos.dto.resp;

import com.alaya.utils.Numeric;

import java.math.BigInteger;

public class RestrictingInfo {

    /**
     * 释放区块高度
     */
    private BigInteger blockNumber;
    /**
     * 释放金额
     */
    private BigInteger amount;

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = Numeric.decodeQuantity(amount);
    }

    @Override
    public String toString() {
        return "RestrictingInfo{" +
                "blockNumber=" + blockNumber +
                ", amount=" + amount +
                '}';
    }
}
