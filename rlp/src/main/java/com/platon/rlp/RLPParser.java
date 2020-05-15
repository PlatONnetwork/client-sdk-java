package com.platon.rlp;

import java.math.BigInteger;
import java.util.Arrays;

import static com.platon.rlp.RLPConstants.*;

final class RLPParser {
	private static int byteArrayToInt(byte[] b) {
		if (b == null || b.length == 0)
			return 0;
		return new BigInteger(1, b).intValue();
	}

	private byte[] raw;

	private int offset;

	private int limit;

	static RLPElement fromEncoded( byte[] data, boolean lazy) {
		RLPParser parser = new RLPParser(data);
		if (parser.estimateSize() != data.length)
			throw new RuntimeException("invalid encoding");

		return lazy ? parser.readLazy() : parser.readElement();
	}

	private RLPParser(byte[] data) {
		this.raw = data;
		this.limit = data.length;
	}

	private RLPParser(byte[] data, int offset, int limit) {
		this.raw = data;
		this.offset = offset;
		this.limit = limit;
	}

	private RLPParser readAsParser(int length) {
		if (offset + length > limit)
			throw new RuntimeException("read overflow");

		RLPParser parser = new RLPParser(raw, offset, offset + length);
		offset += length;
		return parser;
	}

	private int estimateSize() {
		int prefix = peek();
		if (prefix < OFFSET_SHORT_ITEM) {
			return 1;
		}
		if (prefix <= OFFSET_LONG_ITEM) {
			return prefix - OFFSET_SHORT_ITEM + 1;
		}
		if (prefix < OFFSET_SHORT_LIST) {
			// skip
			return byteArrayToInt(Arrays.copyOfRange(raw, offset + 1, offset + 1 + prefix - OFFSET_LONG_ITEM)) + 1 + prefix - OFFSET_LONG_ITEM;
		}
		if (prefix <= OFFSET_LONG_LIST) {
			return prefix - OFFSET_SHORT_LIST + 1;
		}
		return byteArrayToInt(Arrays.copyOfRange(raw, offset + 1, offset + 1 + prefix - OFFSET_LONG_LIST)) + 1 + prefix - OFFSET_LONG_LIST;
	}

	private int read() {
		if (offset >= limit)
			throw new RuntimeException("read overflow");

		return Byte.toUnsignedInt(raw[offset++]);
	}

	private byte[] read(int n) {
		if (offset + n > limit)
			throw new RuntimeException("read overflow");

		byte[] res = Arrays.copyOfRange(raw, offset, offset + n);
		offset += n;
		return res;
	}

	private void skip(int n) {
		offset += n;
	}

	private int peek() {
		return Byte.toUnsignedInt(raw[offset]);
	}

	boolean peekIsList() {
		return peek() >= OFFSET_SHORT_LIST;
	}

	private RLPList readList(boolean lazy) {
		int offset = this.offset;
		int prefix = read();
		RLPList list = RLPList.createEmpty();
		RLPParser parser;
		if (prefix <= OFFSET_LONG_LIST) {
			int len = prefix - OFFSET_SHORT_LIST; // length of length the encoded bytes
			// skip preifx
			if (len == 0)
				return list;
			parser = readAsParser(len);
		} else {
			int lenlen = prefix - OFFSET_LONG_LIST; // length of length the encoded list
			int lenlist = byteArrayToInt(read(lenlen)); // length of encoded bytes
			parser = readAsParser(lenlist);
		}
		int limit = parser.limit;
		while (parser.hasRemaining()) {
			list.add(lazy ? parser.readLazyElement() : parser.readElement());
		}
		list.setEncoded(new LazyByteArray(raw, offset, limit));
		return list;
	}

	RLPElement readElement() {
		if (peekIsList())
			return readList(false);
		return readItem();
	}

	RLPElement readLazy() {
		if (peekIsList())
			return readList(true);
		return readItem();
	}

	LazyElement readLazyElement() {
		return new LazyElement(readAsParser(estimateSize()));
	}

	private RLPItem readItem() {
		int initOffset = this.offset;
		int prefix = read();
		if (prefix < OFFSET_SHORT_ITEM) {
			return RLPItem.fromBytes(new byte[] { (byte) prefix });
		}
		if (prefix <= OFFSET_LONG_ITEM) {
			int length = prefix - OFFSET_SHORT_ITEM;
			if (length == 0)
				return RLPItem.NULL;
			RLPItem item = new RLPItem(new LazyByteArray(raw, offset, offset + length));
			skip(length);
			return item;
		}
		int lengthBits = prefix - OFFSET_LONG_ITEM; // length of length the encoded bytes
		// skip
		int length = byteArrayToInt(read(lengthBits));
		int stopLimit = this.limit;
		RLPItem item = new RLPItem(new LazyByteArray(raw, offset, offset + length));
		item.setEncoded(new LazyByteArray(raw, initOffset, stopLimit));
		skip(length);
		return item;
	}

	private boolean hasRemaining() {
		return offset < limit;
	}

	LazyByteArray getLazyByteArray() {
		return new LazyByteArray(raw, offset, limit);
	}
}
