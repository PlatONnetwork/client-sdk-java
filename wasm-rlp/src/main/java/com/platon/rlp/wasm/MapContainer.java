package com.platon.rlp.wasm;

import com.platon.rlp.wasm.datatypes.Pair;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@SuppressWarnings("rawtypes")
public class MapContainer<M extends Map<K, V>, K, V> implements Container<V> {
	Class mapType;

	public ContainerType getType() {
		return ContainerType.MAP;
	}

	public Container keyType;
	public Container valueType;

	public MapContainer(Class mapType) {
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
		return this;
	}

	@Override
	public PairContainer<? extends Pair<?, V>, ?, V> asPair() {
		throw new RuntimeException("not a pair container");
	}
}
