package com.alaya.rlp.wasm.datatypes;

import java.math.BigInteger;

public class Uint16 extends Uint {
	public Uint16(BigInteger value) {
		super(16, value);
	}

	public static Uint16 of(long val) {
		return new Uint16(BigInteger.valueOf(val));
	}

	public static Uint16 of(BigInteger val) {
		return new Uint16(val);
	}

	public static Uint16 of(String val) {
		return new Uint16(new BigInteger(val));
	}
}
