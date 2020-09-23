package com.alaya.rlp.wasm.datatypes;

import java.math.BigInteger;

/**
 * 
 * Signed integer wrapper class
 * 
 * @author oliver
 *
 */
public class Int128 extends Int {
	public BigInteger value;

	public final static int size = 128;

	private Int128(long value) {
		this.value = BigInteger.valueOf(value);
		this.unsingedValue = encodeZigZag(this.value, size);
	}

	private Int128(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = decodeZigZag(this.unsingedValue);
	}

	public static Int128 of(long value) {
		return new Int128(value);
	}

	public static Int128 ofUnsignedValue(BigInteger value) {
		return new Int128(value);
	}

	public static Int128 of(BigInteger value) {
		if (!(value.bitLength() < 128)) {
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize < 128");
		}
		return new Int128(encodeZigZag(value, size));
	}

	public BigInteger getValue() {
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

		Int128 that = (Int128) o;

		return value == that.value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
