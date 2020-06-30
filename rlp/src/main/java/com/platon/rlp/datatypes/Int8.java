package com.platon.rlp.datatypes;

import java.math.BigInteger;

public class Int8 extends Int {
	public byte value;
	public final static int size = 64;

	private Int8(byte value) {
		this.value = value;
		this.unsingedValue = new BigInteger(Long.toUnsignedString(encodeZigZag64(value)));
	}

	private Int8(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = (byte) decodeZigZag64(unsingedValue.longValue());
	}

	public static Int8 of(long value) {
		if (!(BigInteger.valueOf(value).bitLength() < 8)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 8");
		}
		return new Int8((byte) value);
	}

	public static Int8 ofUnsignedValue(BigInteger unsingedValue) {
		return new Int8(unsingedValue);
	}

	public static Int8 of(BigInteger value) {
		if (!(value.bitLength() < 8)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 8");
		}
		return new Int8(encodeZigZag(value, size));
	}

	public byte getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Int8 that = (Int8) o;

		return value == that.value;
	}

	@Override
	public String toString() {
		return Long.valueOf(value).toString();
	}
}
