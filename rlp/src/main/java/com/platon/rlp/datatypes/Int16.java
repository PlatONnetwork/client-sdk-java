package com.platon.rlp.datatypes;

import java.math.BigInteger;

public class Int16 extends Int {
	public short value;
	public final static int size = 64;

	private Int16(short value) {
		this.value = value;
		this.unsingedValue = new BigInteger(Long.toUnsignedString(encodeZigZag64(value)));
	}

	private Int16(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = (short) decodeZigZag64(unsingedValue.longValue());
	}

	public static Int16 of(long value) {
		if (!(BigInteger.valueOf(value).bitLength() < 16)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 16");
		}
		return new Int16((short) value);
	}

	public static Int16 ofUnsignedValue(BigInteger unsingedValue) {
		return new Int16(unsingedValue);
	}

	public static Int16 of(BigInteger value) {
		if (!(value.bitLength() < 16)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 16");
		}
		return new Int16(encodeZigZag(value, size));
	}

	public short getValue() {
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

		Int16 that = (Int16) o;

		return value == that.value;
	}

	@Override
	public String toString() {
		return Long.valueOf(value).toString();
	}
}
