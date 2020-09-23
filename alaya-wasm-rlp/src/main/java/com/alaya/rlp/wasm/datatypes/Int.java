package com.alaya.rlp.wasm.datatypes;

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

	public static BigInteger encodeZigZag(BigInteger value, int size) {
		if(BigInteger.ZERO.compareTo(value) == 0){
			return BigInteger.ZERO;
		}
		return (value.shiftLeft(1)).xor(value.shiftRight(size - 1));
	}

	public static BigInteger decodeZigZag(BigInteger encoded) {
		return encoded.shiftRight(1).xor(encoded.and(BigInteger.ONE).negate());
	}

	public static String toUnsignedString(BigInteger bigInteger, int size){
		if(BigInteger.ZERO.compareTo(bigInteger) == 0){
			return BigInteger.ZERO.toString();
		}
		BigInteger result = bigInteger.signum() > 0 ? bigInteger: BigInteger.ONE.shiftLeft(size).add(bigInteger);;
		return result.toString();
	}
}
