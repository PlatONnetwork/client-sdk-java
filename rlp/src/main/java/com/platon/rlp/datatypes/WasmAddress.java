package com.platon.rlp.datatypes;

import java.math.BigInteger;

import com.platon.sdk.utlis.Bech32;
import com.platon.sdk.utlis.NetworkParameters;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.utils.Numeric;

public class WasmAddress {
	private byte[] value;
	private BigInteger bigIntValue;
	private String address;

	public static final int LENGTH = 160;
	public static final int LENGTH_IN_HEX = LENGTH >> 2;

	public WasmAddress(byte[] value) {
		this(value,NetworkParameters.CurrentNetwork.getHrp());
	}

	public WasmAddress(byte[] value, long chainId) {
		this(value,NetworkParameters.getHrp(chainId));
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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		WasmAddress address = (WasmAddress) o;

		return value != null ? value.equals(address.value) : address.value == null;
	}

	@Override
	public int hashCode() {
		return value != null ? value.hashCode() : 0;
	}
}