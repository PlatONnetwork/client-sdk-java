package com.platon.rlp.datatypes;

import java.math.BigInteger;

public abstract class Int {
	BigInteger unsingedValue;

	public BigInteger getUnsingedValue() {
		return unsingedValue;
	}

	public static long encodeZigZag64(long value) {
		return (value << 1) ^ (value >> 63);
	}

	public static long decodeZigZag64(long encoded) {
		return (encoded >>> 1) ^ -(encoded & 1);
	}

	public static long encodeZigZag128(long value) {
		return (value << 1) ^ (value >> 128);
	}

	public static long decodeZigZag128(long encoded) {
		return (encoded >>> 1) ^ -(encoded & 1);
	}
}
