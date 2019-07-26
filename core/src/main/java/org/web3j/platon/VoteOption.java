package org.web3j.platon;

public enum VoteOption {

    YEAS(1), NAYS(2), ABSTENTIONS(3);

    int value;

    VoteOption(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
