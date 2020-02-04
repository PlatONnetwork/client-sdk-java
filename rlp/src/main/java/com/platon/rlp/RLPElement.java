package com.platon.rlp;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static com.platon.rlp.RLPItem.NULL;
import static com.platon.rlp.RLPItem.ONE;

/**
 * The RLP encoding function takes in an item. An item is defined as follows
 * <p>
 * A string (ie. byte array) is an item A list of items is an item RLP could encode tree-like object RLP cannot determine difference between null
 * reference and emtpy byte array RLP cannot determine whether a box type is null or zero, e.g. Byte, Short, Integer, Long, BigInteger
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public interface RLPElement {
	boolean isRLPList();

	boolean isRLPItem();

	RLPList asRLPList();

	RLPItem asRLPItem();

	boolean isNull();

	byte[] getEncoded();

	byte[] asBytes();

	byte asByte();

	short asShort();

	int asInt();

	long asLong();

	int size();

	RLPElement get(int index);

	boolean add(RLPElement element);

	RLPElement set(int index, RLPElement element);

	BigInteger asBigInteger();

	String asString();

	boolean asBoolean();

	default <T> T as(Class<T> clazz) {
		return RLPCodec.decode(this, clazz);
	}

	static RLPElement fromEncoded(byte[] data) {
		return fromEncoded(data, true);
	}

	static RLPElement fromEncoded(byte[] data, boolean lazy) {
		return RLPParser.fromEncoded(data, lazy);
	}

	// convert any object as a rlp tree
	static RLPElement readRLPTree(Object t) {
		if (t == null)
			return NULL;

		if (t.getClass() == boolean.class || t instanceof Boolean) {
			return ((Boolean) t) ? ONE : NULL;
		}

		if (t instanceof RLPElement)
			return (RLPElement) t;

		RLPEncoder encoder = RLPUtils.getAnnotatedRLPEncoder(t.getClass());
		if (encoder != null) {
			return encoder.encode(t);
		}

		if (t instanceof BigInteger)
			return RLPItem.fromBigInteger((BigInteger) t);

		if (t instanceof byte[])
			return RLPItem.fromBytes((byte[]) t);

		if (t instanceof String)
			return RLPItem.fromString((String) t);

		// terminals
		if (t.getClass() == byte.class || (t instanceof Byte)) {
			return RLPItem.fromByte((byte) t);
		}

		if (t.getClass() == short.class || t instanceof Short) {
			return RLPItem.fromShort((short) t);
		}

		if (t.getClass() == int.class || t instanceof Integer) {
			return RLPItem.fromInt((int) t);
		}

		if (t.getClass() == long.class || t instanceof Long) {
			return RLPItem.fromLong((long) t);
		}

		if (t instanceof Map) {
			return RLPCodec.encodeMap((Map) t, null);
		}

		if (t.getClass().isArray()) {
			RLPList list = RLPList.createEmpty(Array.getLength(t));
			for (int i = 0; i < Array.getLength(t); i++) {
				list.add(readRLPTree(Array.get(t, i)));
			}
			return list;
		}

		if (t instanceof Collection) {
			return RLPCodec.encodeCollection((Collection) t, null);
		}

		// peek fields reflection
		List<Field> fields = RLPUtils.getRLPFields(t.getClass());

		if (fields.size() == 0)
			throw new RuntimeException("no encodable field of " + t.getClass().getName() + " found");

		return new RLPList(fields.stream().map(f -> {
			f.setAccessible(true);
			Object o;
			try {
				o = f.get(t);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			if (o == null)
				return NULL;

			RLPEncoder fieldEncoder = RLPUtils.getAnnotatedRLPEncoder(f);
			if (fieldEncoder != null) {
				return fieldEncoder.encode(o);
			}

			Comparator comparator = RLPUtils.getKeyOrdering(f);
			if (Set.class.isAssignableFrom(f.getType())) {
				return RLPCodec.encodeCollection((Collection) o, comparator);
			}

			comparator = RLPUtils.getKeyOrdering(f);
			if (Map.class.isAssignableFrom(f.getType())) {
				Map m = (Map) o;
				return RLPCodec.encodeMap(m, comparator);
			}

			return readRLPTree(o);
		}).collect(Collectors.toList()));
	}

}
