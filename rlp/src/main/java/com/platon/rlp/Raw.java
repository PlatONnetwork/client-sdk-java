package com.platon.rlp;

import lombok.Builder;

import java.util.Collection;
import java.util.Map;

@Builder
public class Raw<V> implements Container<V> {
	Class<V> rawType;

	public ContainerType getType() {
		return ContainerType.RAW;
	}

	@Override
	public Class<V> asRaw() {
		return rawType;
	}

	@Override
	public CollectionContainer<? extends Collection<V>, V> asCollection() {
		throw new RuntimeException("not a collection container");
	}

	@Override
	public MapContainer<? extends Map<?, V>, ?, V> asMap() {
		throw new RuntimeException("not a map container");
	}

	Raw() {
		
	}

	public Raw(Class<V> rawType) {
		this.rawType = rawType;
	}
}
