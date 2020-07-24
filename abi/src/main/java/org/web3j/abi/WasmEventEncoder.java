package org.web3j.abi;

import com.platon.rlp.RLPCodec;
import com.platon.rlp.datatypes.*;
import org.web3j.abi.datatypes.WasmEvent;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WasmEventEncoder {

	private static final int MAX_BIT_LENGTH = 256;
	private static final int MAX_BYTE_LENGTH = MAX_BIT_LENGTH / 8;
	private static final int MAX_HEX_LENGTH = MAX_BIT_LENGTH / 4;

	private WasmEventEncoder() {
	}

	public static String encode(WasmEvent event) {
		return encodeString(event.getName());
	}

	public static String encodeIndexParameter(Object o) {
		if (o instanceof Int) {
			return encodeInt((Int)o);
		} else if(o instanceof Uint){
			return encodeUint((Uint) o);
		} else if(o instanceof WasmAddress){
			return encodeAddress((WasmAddress) o);
		} else if(o instanceof Boolean){
			return encodeBoolean((Boolean) o);
		} else if(o instanceof String){
			return encodeString((String) o);
		} else if(o instanceof List){
			return encodeList((List<?>) o);
		} else if(o instanceof byte[]){
			return encodeBytes((byte[]) o);
		} else if(o.getClass().isArray()){
			return encodeArray((Object[]) o);
		} else {
			return encodeBytes(RLPCodec.encode(o));
		}
	}

	private static String encodeUint(BigInteger number) {
		return Numeric.toHexStringWithPrefixZeroPadded(number, MAX_HEX_LENGTH);
	}

	private static String encodeInt(Int number) {
		return encodeUint(number.getUnsingedValue());
	}

	private static String encodeUint(Uint number) {
		return encodeUint(number.getValue());
	}

	private static String encodeString(String string) {
		byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
		return encodeBytes(bytes);
	}

	private static String encodeArray(Object[] objectArray) {
		return encodeList(Arrays.asList(objectArray));
	}

	private static String encodeList(List<?> uintList) {
		if(uintList.stream().filter(item -> item instanceof Uint8).count() == uintList.size()){
			return encodeBytes(uintList.stream().map(item -> (((Uint8) item).getValue().byteValue())).collect(Collectors.toList()));
		}

		if(uintList.stream().filter(item -> item instanceof Int8).count() == uintList.size()){
			return encodeBytes(uintList.stream().map(item -> (((Int8) item).getValue())).collect(Collectors.toList()));
		}

		return encodeBytes(RLPCodec.encode(uintList));
	}

	private static String encodeBytes(List<Byte> list){
		byte[] bytes = new byte[list.size()];
		for (int i = 0; i < list.size(); i++) {
			bytes[i] = list.get(i);
		}
		return encodeBytes(bytes);
	}

	private static String encodeBytes(byte[] bytes){
		if(bytes.length > MAX_BYTE_LENGTH){
			bytes = Hash.sha3(bytes);
		}
		return encodeUint(Numeric.toBigInt(bytes));
	}

	private static String encodeBoolean(Boolean bool) {
		BigInteger number = BigInteger.ZERO;
		if(bool){
			number = BigInteger.ONE;
		}
		return encodeUint(number);
	}

	private static String encodeAddress(WasmAddress address) {
		return encodeUint(address.getBigIntValue());
	}
}
