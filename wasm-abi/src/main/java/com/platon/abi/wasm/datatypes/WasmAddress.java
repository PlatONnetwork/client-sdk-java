package com.platon.abi.wasm.datatypes;

import com.platon.bech32.Bech32;
import com.platon.parameters.NetworkParameters;
import com.platon.utils.Numeric;

import java.math.BigInteger;
import java.util.Objects;

public class WasmAddress {
	private byte[] value;
	private BigInteger bigIntValue;
	private String address;

	public static final int LENGTH = 160;
	public static final int LENGTH_IN_HEX = LENGTH >> 2;

	public WasmAddress(byte[] value) {
		this(value, NetworkParameters.getHrp());
	}

	public WasmAddress(byte[] value, long chainId) {
		this(value,NetworkParameters.getHrp());
	}

	public WasmAddress(byte[] value, String hrp) {
		this.value = value;
		this.bigIntValue = Numeric.toBigInt(value);
		this.address = Bech32.addressEncode(hrp, Numeric.toHexStringWithPrefixZeroPadded(bigIntValue, LENGTH_IN_HEX));
	}

	public WasmAddress(String bechValue) {
		this.value = Numeric.hexStringToByteArray(Bech32.addressDecodeHex(bechValue));
		this.bigIntValue = Numeric.toBigInt(value);
		this.address = bechValue;
	}

	public WasmAddress(BigInteger value) {
		this(value.toByteArray());
	}

	public byte[] getValue() {
		return value;
	}

	public String getAddress() {
		return address;
	}

	public BigInteger getBigIntValue() {
		return bigIntValue;
	}

	@Override
	public String toString() {
		return address;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WasmAddress address = (WasmAddress) o;
		return Objects.equals(bigIntValue, address.bigIntValue);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bigIntValue);
	}
}