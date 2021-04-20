package com.platon.abi.solidity.datatypes;

/** ABI Types. */
public interface Type<T> {
    int MAX_BIT_LENGTH = 256;
    int MAX_BYTE_LENGTH = MAX_BIT_LENGTH / 8;

    default int bytes32PaddedLength() {
        return MAX_BYTE_LENGTH;
    }

    T getValue();

    String getTypeAsString();
}
