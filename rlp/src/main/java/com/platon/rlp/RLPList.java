package com.platon.rlp;

import java.math.BigInteger;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public final class RLPList implements RLPElement, List<RLPElement> {
	static byte[] EMPTY_ENCODED_LIST = RLPCodec.encodeElements(new ArrayList<>());

	public static RLPList of(RLPElement... elements) {
		return new RLPList(Arrays.asList(elements));
	}

	public static RLPList fromElements(Collection<? extends RLPElement> elements) {
		return new RLPList(new ArrayList<>(elements));
	}

	public static RLPList createEmpty() {
		return new RLPList();
	}

	public static RLPList createEmpty(int cap) {
		return new RLPList(new ArrayList<>(cap));
	}

	private List<RLPElement> elements = new ArrayList<>();

	private LazyByteArray encoded;

	void setEncoded(LazyByteArray encoded) {
		this.encoded = encoded;
	}

	private void setDirty() {
		encoded = null;
	}

	private RLPList() {
	}

	RLPList(List<RLPElement> elements) {
		this.elements = elements;
	}

	@Override
	public boolean isRLPList() {
		return true;
	}

	@Override
	public RLPList asRLPList() {
		return this;
	}

	@Override
	public RLPItem asRLPItem() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public byte[] getEncoded() {
		if (size() == 0)
			return EMPTY_ENCODED_LIST;
		if (encoded != null)
			return encoded.get();
		encoded = new LazyByteArray(RLPCodec.encodeElements(stream().map(RLPElement::getEncoded).collect(Collectors.toList())));
		return encoded.get();
	}

	@Override
	public boolean isNull() {
		return false;
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public Iterator<RLPElement> iterator() {
		return elements.iterator();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	@Override
	public boolean add(RLPElement rlpElement) {
		setDirty();
		return elements.add(rlpElement);
	}

	@Override
	public boolean remove(Object o) {
		boolean removed = elements.remove(o);
		if (removed)
			setDirty();
		return removed;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends RLPElement> c) {
		if (c.size() > 0)
			setDirty();
		return elements.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends RLPElement> c) {
		boolean success = elements.addAll(index, c);
		setDirty();
		return success;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean success = elements.removeAll(c);
		if (success)
			setDirty();
		return success;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean success = elements.retainAll(c);
		setDirty();
		return success;
	}

	@Override
	public void replaceAll(UnaryOperator<RLPElement> operator) {
		elements.replaceAll(operator);
		setDirty();
	}

	@Override
	public void sort(Comparator<? super RLPElement> c) {
		elements.sort(c);
		setDirty();
	}

	@Override
	public void clear() {
		elements.clear();
		setDirty();
	}

	@Override
	public boolean equals(Object o) {
		return elements.equals(o);
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	@Override
	public RLPElement get(int index) {
		return elements.get(index);
	}

	@Override
	public RLPElement set(int index, RLPElement element) {
		RLPElement ret = elements.set(index, element);
		setDirty();
		return ret;
	}

	@Override
	public void add(int index, RLPElement element) {
		elements.add(index, element);
		setDirty();
	}

	@Override
	public RLPElement remove(int index) {
		RLPElement ret = elements.remove(index);
		setDirty();
		return ret;
	}

	@Override
	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return elements.lastIndexOf(o);
	}

	@Override
	public ListIterator<RLPElement> listIterator() {
		return elements.listIterator();
	}

	@Override
	public ListIterator<RLPElement> listIterator(int index) {
		return elements.listIterator(index);
	}

	@Override
	public RLPList subList(int fromIndex, int toIndex) {
		return new RLPList(elements.subList(fromIndex, toIndex));
	}

	@Override
	public Spliterator<RLPElement> spliterator() {
		return elements.spliterator();
	}

	@Override
	public boolean isRLPItem() {
		return false;
	}

	@Override
	public byte[] asBytes() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public byte asByte() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public short asShort() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public long asLong() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public BigInteger asBigInteger() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public String asString() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public boolean asBoolean() {
		throw new RuntimeException("not a rlp item");
	}

	@Override
	public int asInt() {
		throw new RuntimeException("not a rlp item");
	}
}
