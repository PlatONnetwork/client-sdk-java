package com.alaya.rlp.wasm;

import java.util.Collection;
import java.util.Map;

import com.alaya.rlp.wasm.datatypes.Pair;

@SuppressWarnings("rawtypes")
public class PairContainer<P extends Pair<K, V>, K, V> implements Container<V> {
	Class mapType;

	public ContainerType getType() {
		return ContainerType.PAIR;
	}

	public Container keyType;
	public Container valueType;

	public PairContainer(Class mapType) {
		this.mapType = mapType;
	}

	@Override
	public Class<V> asRaw() {
		throw new RuntimeException("not a raw type");
	}

	@Override
	public CollectionContainer<? extends Collection<V>, V> asCollection() {
		throw new RuntimeException("not a collection container");
	}

	@Override
	public MapContainer<? extends Map<?, V>, ?, V> asMap() {
		throw new RuntimeException("not a map container");
	}

	public PairContainer<? extends Pair<?, V>, ?, V> asPair() {
		return this;
	}
}
