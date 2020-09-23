package com.alaya.abi.wasm;

import java.lang.reflect.ParameterizedType;

import com.alaya.utils.Numeric;

import com.alaya.rlp.wasm.RLPCodec;

public class WasmReturnDecoder {
	public static <T> T decode(String input, Class<T> clazz, long chainId) {
		byte[] data = Numeric.hexStringToByteArray(input);
		return RLPCodec.decode(data, clazz, chainId);
	}

	@SuppressWarnings("unchecked")
	public static <T> T decode(String input, Class<T> clazz, ParameterizedType parameterizedType, long chainId) {
		byte[] data = Numeric.hexStringToByteArray(input);
		return (T) RLPCodec.decodeContainer(data, parameterizedType, chainId);
	}
}
