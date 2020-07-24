package org.web3j.abi;

import com.platon.rlp.datatypes.*;
import org.web3j.utils.Numeric;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WasmEventDecoder {

	private static final int MAX_BIT_LENGTH = 256;
	private static final int MAX_BYTE_LENGTH = MAX_BIT_LENGTH / 8;
	private static final int MAX_HEX_LENGTH = MAX_BIT_LENGTH / 4;

	private WasmEventDecoder() {
	}

	public static byte[] decodeIndexParameterByteArray(String topic) {
		byte[] bytes = Numeric.hexStringToByteArray(topic);
		int index = 0;
		for (index = 0; index < bytes.length; index++) {
			if(bytes[index] != 0){
				break;
			}
		}
		byte[] newBytes = new byte[bytes.length - index];
		System.arraycopy(bytes,index, newBytes, 0, newBytes.length);
		return newBytes;
	}

	public static <T> List<T> decodeIndexParameterList(String topic, Class<T> type) {
		try{
			if (Int8.class.isAssignableFrom(type)) {
				return (List<T>) decodeInt8List(topic);
			}  else if (Uint8.class.isAssignableFrom(type)) {
				return (List<T>) decodeUint8List(topic);
			}  else if (byte.class.isAssignableFrom(type)) {
				return (List<T>) decodeByteList(topic);
			} else {
				throw new UnsupportedOperationException("Topic cannot be decode: " + type.getClass());
			}
		} catch (UnsupportedOperationException e){
			throw e;
		} catch (Exception e){
			throw new UnsupportedOperationException("decode error ",e);
		}
	}

	private static List<Byte> decodeByteList(String topic) {
		return  null;
	}

	private static List<Uint8> decodeUint8List(String topic) {
		List<Uint8> result = new ArrayList<>();
		byte[] bytes = Numeric.hexStringToByteArray(topic);
		int index = 0;
		for (index = 0; index < bytes.length; index++) {
			if(bytes[index] != 0){
				break;
			}
		}
		byte[] newBytes = new byte[bytes.length - index];
		System.arraycopy(bytes,index, newBytes, 0, newBytes.length);
		for (int i = 0; i < newBytes.length; i++) {
			result.add(Uint8.of(new BigInteger(1, new byte[]{newBytes[i]})));
		}
		return result;
	}

	private static List<Int8> decodeInt8List(String topic) {
		List<Int8> result = new ArrayList<>();
		byte[] bytes = Numeric.hexStringToByteArray(topic);
		int index = 0;
		for (index = 0; index < bytes.length; index++) {
			if(bytes[index] != 0){
				break;
			}
		}
		byte[] newBytes = new byte[bytes.length - index];
		System.arraycopy(bytes,index, newBytes, 0, newBytes.length);
		for (int i = 0; i < newBytes.length; i++) {
			result.add(Int8.of(new BigInteger( new byte[]{newBytes[i]})));
		}
		return result;
	}

	public static <T> T decodeIndexParameter(String topic, Class<T> type) {
		try{
			if (Int.class.isAssignableFrom(type)) {
				return (T) decodeInt(topic, (Class<Int>) type);
			} else if(Uint.class.isAssignableFrom(type)){
				return (T) decodeUint(topic, (Class<Uint>) type);
			} else if(Boolean.class.isAssignableFrom(type)){
				return (T) decodeBoolean(topic);
			} else if(String.class.isAssignableFrom(type)){
				return (T) decodeString(topic);
			} else if(WasmAddress.class.isAssignableFrom(type)){
				return (T) decodeAddress(topic);
			} else {
				throw new UnsupportedOperationException("Topic cannot be decode: " + type.getClass());
			}
		} catch (UnsupportedOperationException e){
			throw e;
		} catch (Exception e){
			throw new UnsupportedOperationException("decode error ",e);
		}

	}


	private static WasmAddress decodeAddress(String topic) {
		return new WasmAddress(Numeric.toBigInt(topic));
	}

	private static String decodeString(String topic) {
		byte[] bytes = Numeric.hexStringToByteArray(topic);
		int index = 0;
		for (index = 0; index < bytes.length; index++) {
			if(bytes[index] != 0){
				break;
			}
		}
		byte[] newBytes = new byte[bytes.length - index];
		System.arraycopy(bytes,index, newBytes, 0, newBytes.length);
		return new String(newBytes, StandardCharsets.UTF_8);
	}

	private static Boolean decodeBoolean(String topic) {
		BigInteger bigInteger = Numeric.toBigInt(topic);
		if(bigInteger.signum() == 0){
			return Boolean.FALSE;
		} else if(bigInteger.compareTo(BigInteger.ONE) == 0) {
			return Boolean.TRUE;
		} else {
			throw new UnsupportedOperationException("decode boolean error value = " + bigInteger);
		}
	}

	private static <T extends Uint> T decodeUint(String topic, Class<Uint> type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		BigInteger bigInteger = Numeric.toBigInt(topic);
		Method method = type.getMethod("of", BigInteger.class);
		return (T) method.invoke(null, bigInteger);
	}

	private static <T extends Int> T decodeInt(String topic, Class<Int> type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		BigInteger bigInteger = Numeric.toBigInt(topic);
		Method method = type.getMethod("ofUnsignedValue", BigInteger.class);
		return (T) method.invoke(null, bigInteger);
	}
}
