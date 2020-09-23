package com.alaya.contracts.ppos.dto.enums;

public enum StakingAmountType {

    FREE_AMOUNT_TYPE(0), RESTRICTING_AMOUNT_TYPE(1);

    int value;

    StakingAmountType(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
