package com.alaya.abi.solidity.datatypes;

import java.math.BigInteger;

import com.alaya.abi.solidity.datatypes.generated.Uint160;
import com.alaya.bech32.Bech32;
import com.alaya.parameters.NetworkParameters;
import com.alaya.utils.Numeric;

/**
 * Address type, which is equivalent to uint160.
 */
public class Address implements Type<String> {

    public static final String TYPE_NAME = "address";
    public static final int LENGTH = 160;
    public static final int LENGTH_IN_HEX = LENGTH >> 2;

    private final Uint160 value;
    private final String address;

    public Address(Uint160 value, long chainId) {
        this(value, NetworkParameters.getHrp(chainId));
    }

    public Address(Uint160 value, String hrp) {
        this.value = value;
        this.address = Bech32.addressEncode(hrp, Numeric.toHexStringWithPrefixZeroPadded(value.getValue(), LENGTH_IN_HEX));
    }

    public Address(BigInteger inputValue, long chainId) {
        this.value = new Uint160(inputValue);
        this.address = Bech32.addressEncode(NetworkParameters.getHrp(chainId), Numeric.toHexStringWithPrefixZeroPadded(inputValue, LENGTH_IN_HEX));
    }

    public Address(String bechValue) {
        this.value = new Uint160(Numeric.toBigInt(Bech32.addressDecodeHex(bechValue)));
        this.address = bechValue;
    }

    public Uint160 toUint160() {
        return value;
    }

    @Override
    public String getTypeAsString() {
        return TYPE_NAME;
    }

    @Override
    public String toString() {
        return address;
    }

    @Override
    public String getValue() {
        return toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        return value != null ? value.equals(address.value) : address.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
