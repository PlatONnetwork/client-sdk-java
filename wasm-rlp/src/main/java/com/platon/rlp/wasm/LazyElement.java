package com.platon.rlp.wasm;

import java.math.BigInteger;

public class LazyElement implements RLPElement {
	private RLPElement delegate;

	private RLPParser parser;

	private LazyByteArray encoded;

	LazyElement(RLPParser parser) {
		this.parser = parser;
		this.encoded = parser.getLazyByteArray();
	}

	private void parse() {
		if (delegate != null)
			return;
		delegate = parser.readLazy();
	}

	@Override
	public boolean isRLPList() {
		if (delegate != null)
			return delegate.isRLPList();
		return parser.peekIsList();
	}

	@Override
	public boolean isRLPItem() {
		return !isRLPList();
	}

	@Override
	public RLPList asRLPList() {
		parse();
		return delegate.asRLPList();
	}

	@Override
	public RLPItem asRLPItem() {
		parse();
		return delegate.asRLPItem();
	}

	@Override
	public boolean isNull() {
		parse();
		return delegate.isNull();
	}

	@Override
	public byte[] getEncoded() {
		if (delegate == null || isRLPItem())
			return encoded.get();
		parse();
		return delegate.getEncoded();
	}

	@Override
	public byte[] asBytes() {
		parse();
		return delegate.asBytes();
	}

	@Override
	public byte asByte() {
		parse();
		return delegate.asByte();
	}

	@Override
	public short asShort() {
		parse();
		return delegate.asShort();
	}

	@Override
	public int asInt() {
		parse();
		return delegate.asInt();
	}

	@Override
	public long asLong() {
		parse();
		return delegate.asLong();
	}

	@Override
	public BigInteger asBigInteger() {
		parse();
		return delegate.asBigInteger();
	}

	@Override
	public String asString() {
		parse();
		return delegate.asString();
	}

	@Override
	public boolean asBoolean() {
		parse();
		return delegate.asBoolean();
	}

	@Override
	public <T> T as(Class<T> clazz) {
		parse();
		return delegate.as(clazz);
	}

	@Override
	public RLPElement get(int index) {
		parse();
		return delegate.get(index);
	}

	@Override
	public boolean add(RLPElement element) {
		parse();
		return delegate.add(element);
	}

	@Override
	public RLPElement set(int index, RLPElement element) {
		parse();
		return delegate.set(index, element);
	}

	@Override
	public int size() {
		parse();
		return delegate.size();
	}
}
