package com.platon.rlp.wasm.datatypes;

import java.math.BigInteger;

public class Int32 extends Int {
	public int value;
	public final static int size = 64;

	private Int32(int value) {
		this.value = value;
		this.unsingedValue = new BigInteger(Long.toUnsignedString(encodeZigZag64(value)));
	}

	private Int32(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = (int) decodeZigZag64(unsingedValue.longValue());
	}

	public static Int32 of(long value) {
		if (!(BigInteger.valueOf(value).bitLength() < 32)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 32");
		}
		return new Int32((int) value);
	}

	public static Int32 ofUnsignedValue(BigInteger unsingedValue) {
		return new Int32(unsingedValue);
	}

	public static Int32 of(BigInteger value) {
		if (!(value.bitLength() < 32)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 32");
		}
		return new Int32(encodeZigZag(value, size));
	}

	public int getValue() {
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

		Int32 that = (Int32) o;

		return value == that.value;
	}

	@Override
	public String toString() {
		return Long.valueOf(value).toString();
	}
}
