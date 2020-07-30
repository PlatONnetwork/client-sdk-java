package com.platon.rlp.datatypes;

import java.math.BigInteger;

public class Uint32 extends Uint {
	public Uint32(BigInteger value) {
		super(32, value);
	}

	public static Uint32 of(long val) {
		return new Uint32(BigInteger.valueOf(val));
	}

	public static Uint32 of(BigInteger val) {
		return new Uint32(val);
	}

	public static Uint32 of(String val) {
		return new Uint32(new BigInteger(val));
	}
}
