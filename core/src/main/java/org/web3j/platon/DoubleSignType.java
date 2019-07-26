package org.web3j.platon;

public enum DoubleSignType {

    PREPARE(1), VIEWCHANGE(2);

    int value;

    DoubleSignType(int val) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
