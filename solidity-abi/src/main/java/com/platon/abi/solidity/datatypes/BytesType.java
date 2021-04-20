package com.platon.abi.solidity.datatypes;

import java.util.Arrays;

/**
 * Binary sequence of bytes.
 */
public class BytesType implements Type<byte[]> {

    public static final String TYPE_NAME = "bytes";

    private byte[] value;
    private String type;

    public BytesType(byte[] src, String type) {
        this.value = src;
        this.type = type;
    }

    public BytesType(byte[] src) {
        this.value = src;
        this.type = TYPE_NAME + src.length;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public String getTypeAsString() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BytesType bytesType = (BytesType) o;

        if (!Arrays.equals(value, bytesType.value)) {
            return false;
        }
        return type.equals(bytesType.type);

    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(value);
        result = 31 * result + type.hashCode();
        return result;
    }
}
