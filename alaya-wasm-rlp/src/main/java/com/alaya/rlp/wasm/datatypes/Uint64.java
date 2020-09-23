package com.alaya.rlp.wasm.datatypes;

import java.math.BigInteger;

public class Uint64 extends Uint {
	public Uint64(BigInteger value) {
		super(64, value);
	}

	public static Uint64 of(long val) {
		return new Uint64(BigInteger.valueOf(val));
	}

	public static Uint64 of(BigInteger val) {
		return new Uint64(val);
	}

	public static Uint64 of(String val) {
		return new Uint64(new BigInteger(val));
	}
}
