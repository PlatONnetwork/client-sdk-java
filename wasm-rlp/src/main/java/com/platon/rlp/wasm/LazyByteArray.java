package com.platon.rlp.wasm;

import java.util.Arrays;

// reduce byte array copy by lazy loading
final class LazyByteArray {
	private byte[] data;
	private int offset;
	private int limit;

	static LazyByteArray EMPTY = new LazyByteArray(new byte[0]);

	LazyByteArray(byte[] data) {
		this.data = data;
		this.limit = data.length;
	}

	LazyByteArray(byte[] data, int offset, int limit) {
		this.data = data;
		this.offset = offset;
		this.limit = limit;
	}

	byte[] get() {
		if (offset == 0 && limit == data.length)
			return data;
		data = Arrays.copyOfRange(data, offset, limit);
		offset = 0;
		limit = data.length;
		return data;
	}

	int size() {
		return limit - offset;
	}
}
