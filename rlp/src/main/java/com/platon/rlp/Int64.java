package com.platon.rlp;

import java.math.BigInteger;

/**
 * 
 * Signed integer wrapper class
 * 
 * @author oliver
 *
 */
public class Int64 {
	public long value;
	public BigInteger unsingedValue;

	private Int64(long value) {
		this.value = value;
		long data = encodeZigZag64(value);
		this.unsingedValue = new BigInteger(Long.toUnsignedString(data));
	}

	private Int64(BigInteger unsingedValue) {
		this.unsingedValue = unsingedValue;
		this.value = decodeZigZag64(unsingedValue.longValue());
	}

	public static Int64 of(long value) {
		return new Int64(value);
	}

	public static Int64 of(int value) {
		return new Int64(value);
	}

	public static Int64 of(short value) {
		return new Int64(value);
	}

	public static Int64 of(byte value) {
		return new Int64(value);
	}

	/**
	 * Use when decoding
	 * 
	 * @param unsingedValue
	 */
	protected static Int64 of(BigInteger unsingedValue) {
		return new Int64(unsingedValue);
	}

	public BigInteger getUnsingedValue() {
		return unsingedValue;
	}

	public long getValue() {
		return value;
	}

	public static long encodeZigZag64(long value) {
		return (value << 1) ^ (value >> 63);
	}

	public static long decodeZigZag64(long encoded) {
		return (encoded >>> 1) ^ -(encoded & 1);
	}
}
