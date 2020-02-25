package com.platon.rlp.datatypes;

import java.math.BigInteger;

/**
 * 
 * Signed integer wrapper class
 * 
 * @author oliver
 *
 */
public class Int64 extends Int {
	public long value;

	private Int64(long value) {
		this.value = value;
		this.unsingedValue = new BigInteger(Long.toUnsignedString(encodeZigZag64(value)));
	}

	private Int64(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = decodeZigZag64(unsingedValue.longValue());
	}

	public static Int64 of(long value) {
		return new Int64(value);
	}

	public static Int64 of(BigInteger unsingedValue) {
		return new Int64(unsingedValue);
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

		Int64 that = (Int64) o;

		return value == that.value;
	}

	@Override
	public String toString() {
		return Long.valueOf(value).toString();
	}
}
