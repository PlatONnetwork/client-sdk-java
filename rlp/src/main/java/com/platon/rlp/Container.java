package com.platon.rlp;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings({ "rawtypes", "unchecked" })
public interface Container<V> {
	Set<Class<? extends Map>> SUPPORTED_MAPS = new HashSet<>(
			Arrays.asList(Map.class, HashMap.class, ConcurrentMap.class, ConcurrentHashMap.class, TreeMap.class));

	Set<Class<? extends Collection>> SUPPORTED_COLLECTIONS = new HashSet<>(Arrays.asList(Collection.class, List.class, ArrayList.class, Set.class,
			Queue.class, Deque.class, HashSet.class, TreeSet.class, LinkedList.class, ArrayDeque.class));

	static Container<?> fromField(Field field) {
		Container<?> container = fromType(field.getGenericType());
		Class clazz = null;
		if (field.isAnnotationPresent(RLPDecoding.class)) {
			clazz = field.getAnnotation(RLPDecoding.class).as();
		}
		if (clazz == null || clazz == Void.class)
			return container;

		if (!Collection.class.isAssignableFrom(clazz) && !Map.class.isAssignableFrom(clazz))
			throw new RuntimeException("@RLPDecoding.as must be a collection of map type while " + clazz.getName() + " found");

		if (container.getType() == ContainerType.RAW)
			throw new RuntimeException("@RLPDecoding.as is used on collection or map typed field other than " + field.getName());

		if (!field.getType().isAssignableFrom(clazz))
			throw new RuntimeException("cannot assign " + clazz + " to " + field.getType());

		if (container.getType() == ContainerType.COLLECTION) {
			container.asCollection().collectionType = clazz;
		}

		if (container.getType() == ContainerType.MAP) {
			container.asMap().mapType = clazz;
		}

		return container;
	}

	// from class without generic variable
	static Container fromClass(Class clazz) {
		if (Collection.class.isAssignableFrom(clazz)) {
			if (!SUPPORTED_COLLECTIONS.contains(clazz))
				throw new RuntimeException(clazz + " is not supported, please use one of type "
						+ SUPPORTED_COLLECTIONS.stream().map(x -> x.getName()).reduce("", String::concat));
			return new CollectionContainer(clazz);
		}

		if (Map.class.isAssignableFrom(clazz)) {
			if (!SUPPORTED_MAPS.contains(clazz))
				throw new RuntimeException(clazz + " is not supported, please use one of type "
						+ SUPPORTED_MAPS.stream().map(x -> x.getName()).reduce("", String::concat));
			return new MapContainer(clazz);
		}

		return new Raw(clazz);
	}

	static Container fromType(Type type) {
		if (type instanceof Class) {
			return fromClass((Class) type);
		}
		if (!(type instanceof ParameterizedType))
			throw new RuntimeException("type variable " + type + " is not allowed in rlp decoding");
		ParameterizedType parameterizedType = (ParameterizedType) type;
		Type[] types = parameterizedType.getActualTypeArguments();
		Class clazz = (Class) parameterizedType.getRawType();
		Container container = fromClass(clazz);
		switch (container.getType()) {
		case RAW:
			return container;
		case MAP: {
			MapContainer con = container.asMap();
			con.keyType = fromType(types[0]);
			con.valueType = fromType(types[1]);
			return con;
		}
		case COLLECTION: {
			CollectionContainer con = container.asCollection();
			con.contentType = fromType(types[0]);
			return con;
		}
		default:
			throw new RuntimeException("this is unreachable");
		}
	}

	ContainerType getType();

	Class<V> asRaw();

	CollectionContainer<? extends Collection<V>, V> asCollection();

	MapContainer<? extends Map<?, V>, ?, V> asMap();
}
