package com.platon.abi.wasm;

import com.platon.rlp.wasm.RLPCodec;
import com.platon.utils.Numeric;

import java.lang.reflect.ParameterizedType;

public class WasmReturnDecoder {
	public static <T> T decode(String input, Class<T> clazz) {
		byte[] data = Numeric.hexStringToByteArray(input);
		return RLPCodec.decode(data, clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T decode(String input, Class<T> clazz, ParameterizedType parameterizedType) {
		byte[] data = Numeric.hexStringToByteArray(input);
		return (T) RLPCodec.decodeContainer(data, parameterizedType);
	}
}
