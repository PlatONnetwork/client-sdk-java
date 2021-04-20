package com.platon.rlp.wasm;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.platon.rlp.wasm.RLPCodec.encodeBytes;

/**
 * immutable rlp item
 */
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PUBLIC)
public final class RLPItem implements RLPElement {
	private static byte[] NULL_ENCODED = encodeBytes(null);

	public static final RLPItem ONE = new RLPItem(new LazyByteArray(new byte[] { 1 }));

	private LazyByteArray data;

	private Long longNumber;

	private LazyByteArray encoded;

	void setEncoded(LazyByteArray encoded) {
		this.encoded = encoded;
	}

	public byte[] asBytes() {
		return data.get();
	}

	public static RLPItem fromByte(byte b) {
		return fromLong(Byte.toUnsignedLong(b));
	}

	public static RLPItem fromShort(short s) {
		return fromLong(Short.toUnsignedLong(s));
	}

	public static RLPItem fromBoolean(boolean b) {
		return b ? ONE : NULL;
	}

	public static RLPItem fromInt(int i) {
		return fromLong(Integer.toUnsignedLong(i));
	}

	public static RLPItem fromLong(long l) {
		if (l == 0)
			return NULL;
		if (l == 1)
			return ONE;
		int leadingZeroBytes = Long.numberOfLeadingZeros(l) / Byte.SIZE;

		return fromBytes(concat(
				// trim zero bytes
				Arrays.copyOfRange(ByteBuffer.allocate(Long.BYTES).putLong(l).array(), leadingZeroBytes, Long.BYTES)));
	}

	public static RLPItem fromString(String s) {
		if (s == null)
			return NULL;
		return fromBytes(s.getBytes(StandardCharsets.UTF_8));
	}

	public static RLPItem fromBytes(byte[] data) {
		if (data == null || data.length == 0)
			return NULL;
		if (data.length == 1 && (Byte.toUnsignedInt(data[0]) == 1))
			return ONE;
		return new RLPItem(new LazyByteArray(data));
	}

	public static RLPItem fromBigInteger(BigInteger bigInteger) {
		if (bigInteger == null || bigInteger.equals(BigInteger.ZERO))
			return NULL;
		if (bigInteger.equals(BigInteger.ONE))
			return ONE;
		if (bigInteger.compareTo(BigInteger.ZERO) < 0)
			throw new RuntimeException("negative numbers are not allowed");

		return fromBytes(asUnsignedByteArray(bigInteger));
	}

	private static byte[] asUnsignedByteArray(BigInteger value) {
		byte[] bytes = value.toByteArray();

		if (bytes[0] == 0) {
			byte[] tmp = new byte[bytes.length - 1];

			System.arraycopy(bytes, 1, tmp, 0, tmp.length);

			return tmp;
		}
		return bytes;
	}

	public static final RLPItem NULL = new RLPItem(LazyByteArray.EMPTY);

	RLPItem(LazyByteArray data) {
		this.data = data;
	}

	@Override
	public boolean isRLPList() {
		return false;
	}

	@Override
	public RLPList asRLPList() {
		throw new RuntimeException("not a rlp list");
	}

	@Override
	public RLPItem asRLPItem() {
		return this;
	}

	public boolean isNull() {
		return this == NULL || data.size() == 0;
	}

	public byte asByte() {
		if (Long.compareUnsigned(asLong(), 0xffL) > 0)
			throw new RuntimeException("invalid byte, overflow");
		return (byte) asLong();
	}

	public short asShort() {
		if (Long.compareUnsigned(asLong(), 0xffff) > 0)
			throw new RuntimeException("invalid short, overflow");
		return (short) asLong();
	}

	public int asInt() {
		if (Long.compareUnsigned(asLong(), 0xffffffff) > 0)
			throw new RuntimeException("invalid int, overflow");
		return (int) asLong();
	}

	public long asLong() {
		if (isNull()) {
			return 0;
		}
		if (this == ONE)
			return 1;
		if (longNumber != null)
			return longNumber;
		// numbers are ont starts with zero byte
		byte[] data = asBytes();
		if (data.length > 0 && data[0] == 0)
			throw new RuntimeException("not a number");
		if (data.length > Long.BYTES)
			throw new RuntimeException("not a number");
		longNumber = ByteBuffer.wrap(concat(new byte[Long.BYTES - data.length], data)).getLong();
		return longNumber;
	}

	public BigInteger asBigInteger() {
		if (isNull())
			return BigInteger.ZERO;
		if (this == ONE)
			return BigInteger.ONE;
		byte[] data = asBytes();
		if (data[0] == 0)
			throw new RuntimeException("not a number");
		return new BigInteger(1, data);
	}

	public String asString() {
		if (isNull())
			return "";
		return new String(asBytes(), StandardCharsets.UTF_8);
	}

	public boolean asBoolean() {
		if (asLong() > 1)
			throw new RuntimeException("not a boolean");
		return asLong() == 1;
	}

	public byte[] getEncoded() {
		if (isNull())
			return NULL_ENCODED;
		if (encoded == null)
			encoded = new LazyByteArray(encodeBytes(asBytes()));
		return encoded.get();
	}

	/**
	 * Returns the values from each provided array combined into a single array. For example, {@code concat(new byte[] {a, b}, new byte[] {}, new
	 * byte[] {c}} returns the array {@code {a, b, c}}.
	 *
	 * @param arrays zero or more {@code byte} arrays
	 * @return a single array containing all the values from the source arrays, in order
	 */
	private static byte[] concat(byte[]... arrays) {
		int length = 0;
		for (byte[] array : arrays) {
			length += array.length;
		}
		byte[] result = new byte[length];
		int pos = 0;
		for (byte[] array : arrays) {
			System.arraycopy(array, 0, result, pos, array.length);
			pos += array.length;
		}
		return result;
	}

	@Override
	public boolean isRLPItem() {
		return true;
	}

	@Override
	public RLPElement get(int index) {
		throw new RuntimeException("not a rlp list");
	}

	@Override
	public boolean add(RLPElement element) {
		throw new RuntimeException("not a rlp list");
	}

	@Override
	public RLPElement set(int index, RLPElement element) {
		throw new RuntimeException("not a rlp list");
	}

	@Override
	public int size() {
		throw new RuntimeException("not a rlp list");
	}
}
