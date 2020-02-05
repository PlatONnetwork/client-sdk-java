package com.platon.rlp;

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
public class CollectionContainer<C extends Collection<T>, T> implements Container<T> {
	Class collectionType;

	public ContainerType getType() {
		return ContainerType.COLLECTION;
	}

	Container contentType;

	CollectionContainer(Class collectionType) {
		this.collectionType = collectionType;
	}

	@Override
	public Class<T> asRaw() {
		throw new RuntimeException("not a raw type");
	}

	@Override
	public CollectionContainer<C, T> asCollection() {
		return this;
	}

	@Override
	public MapContainer<? extends Map<?, T>, ?, T> asMap() {
		throw new RuntimeException("not a map container");
	}
}