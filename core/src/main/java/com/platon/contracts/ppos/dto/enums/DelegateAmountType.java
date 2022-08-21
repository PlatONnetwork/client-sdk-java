package com.platon.contracts.ppos.dto.enums;

public enum DelegateAmountType {

    FREE_AMOUNT_TYPE(0), RESTRICTING_AMOUNT_TYPE(1), DELEGATE_LOCK_AMOUNT_TYPE(3);

    int value;

    DelegateAmountType(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
