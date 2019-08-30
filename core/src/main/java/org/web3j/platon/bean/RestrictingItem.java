package org.web3j.platon.bean;

import com.alibaba.fastjson.annotation.JSONField;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class RestrictingItem {
    /**
     * 锁仓余额
     */
    private BigInteger balance;
    /**
     * 质押/抵押金额
     */
    private BigInteger pledge;
    /**
     * 欠释放金额
     */
    private BigInteger debt;
    /**
     * 锁仓分录信息
     */
    @JSONField(name = "plans")
    private List<RestrictingInfo> info;

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = Numeric.decodeQuantity(balance);
    }

    public BigInteger getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        this.pledge = Numeric.decodeQuantity(pledge);
    }

    public BigInteger getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = Numeric.decodeQuantity(debt);
    }

    public List<RestrictingInfo> getInfo() {
        return info;
    }

    public void setInfo(List<RestrictingInfo> info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "RestrictingItem{" +
                "balance=" + balance +
                ", pledge=" + pledge +
                ", debt=" + debt +
                ", info=" + info +
                '}';
    }
}
