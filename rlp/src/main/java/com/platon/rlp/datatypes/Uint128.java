package com.platon.rlp.datatypes;

import java.math.BigInteger;

public class Uint128 extends Uint {
	private Uint128(BigInteger value) {
		super(128, value);
	}

	public static Uint128 of(long val) {
		return new Uint128(BigInteger.valueOf(val));
	}

	public static Uint128 of(BigInteger val) {
		return new Uint128(val);
	}

	public static Uint128 of(String val) {
		return new Uint128(new BigInteger(val));
	}
}
