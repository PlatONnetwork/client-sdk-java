package com.alaya.contracts.ppos.dto.common;

public enum DuplicateSignType {

    PREPARE_BLOCK(1), PREPARE_VOTE(2),VIEW_CHANGE(3);

    int value;

    DuplicateSignType(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
