package com.platon.rlp.wasm.datatypes;

import java.math.BigInteger;

public abstract class Uint {
	public BigInteger value;

	private int bitSize;

	public Uint(int bitSize, BigInteger value) {
		if (!valid(bitSize, value))
			throw new UnsupportedOperationException("Data length overflow, Bitsize must be in range 0 < bitSize <=" + bitSize);

		if (value.compareTo(BigInteger.ZERO) < 0)
			throw new UnsupportedOperationException("Negative numbers are not allowed");

		this.value = value;
		this.bitSize = bitSize;
	}

	private boolean valid(int bitSize, BigInteger value) {
		return value.bitLength() <= bitSize;
	}

	public int getBitSize() {
		return bitSize;
	}

	public BigInteger getValue() {
		return value;
	}

	public BigInteger value() {
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

		Uint that = (Uint) o;

		return value != null ? value.equals(that.value) : that.value == null;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
