package com.alaya.rlp.wasm.datatypes;

import java.math.BigInteger;

public class Uint8 extends Uint {
	public Uint8(BigInteger value) {
		super(8, value);
	}

	public static Uint8 of(long val) {
		return new Uint8(BigInteger.valueOf(val));
	}

	public static Uint8 of(BigInteger val) {
		return new Uint8(val);
	}

	public static Uint8 of(String val) {
		return new Uint8(new BigInteger(val));
	}

}
