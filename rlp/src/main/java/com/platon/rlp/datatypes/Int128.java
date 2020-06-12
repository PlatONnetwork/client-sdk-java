package com.platon.rlp.datatypes;

import java.math.BigInteger;

/**
 * 
 * Signed integer wrapper class
 * 
 * @author oliver
 *
 */
public class Int128 extends Int {
	public long value;

	private Int128(long value) {
		this.value = value;
		this.unsingedValue = new BigInteger(Long.toUnsignedString(encodeZigZag128(value)));
	}

	private Int128(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = decodeZigZag128(unsingedValue.longValue());
	}

	public static Int128 of(long value) {
		return new Int128(value);
	}

	public static Int128 of(BigInteger unsingedValue) {
		return new Int128(unsingedValue);
	}

	public long getValue() {
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
		return Long.valueOf(value).toString();
	}
}
