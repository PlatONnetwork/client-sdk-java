package com.alaya.abi.wasm;

import com.alaya.abi.wasm.datatypes.WasmFunction;
import com.alaya.utils.Numeric;
import com.alaya.rlp.wasm.RLPCodec;
import com.alaya.rlp.wasm.datatypes.Uint64;

import java.util.ArrayList;
import java.util.List;

public class WasmFunctionEncoder {
	public static byte[] MAGIC_NUM = new byte[] { 0x00, 0x61, 0x73, 0x6d };

	public static String DEPLOY_METHOD_NAME = "init";

	private static final long FNV_64_INIT = 0xcbf29ce484222325L;
	private static final long FNV_64_PRIME = 0x100000001b3L;

	public static String encode(WasmFunction function) {
		List<Object> parameters = new ArrayList<>();
		// parameters.add(function.getName());
		parameters.add(fnvOne64Hash(function.getName()));

		for (Object o : function.getInputParameters()) {
			if (!o.equals(Void.class)) {
				parameters.add(o);
			}
		}

		byte[] data = RLPCodec.encode(parameters);
		return Numeric.toHexStringNoPrefix(data);
	}

	public static String encodeConstructor(String code, List<?> inputParameters) {
		List<Object> parameters = new ArrayList<>();
		// parameters.add(DEPLOY_METHOD_NAME);
		parameters.add(fnvOne64Hash(DEPLOY_METHOD_NAME));
		for (Object o : inputParameters) {
			if (!o.equals(Void.class)) {
				parameters.add(o);
			}
		}
		byte[] parameterData = RLPCodec.encode(parameters);

		byte[] codeBinary = Numeric.hexStringToByteArray(code);
		Object[] objs = new Object[] { codeBinary, parameterData };
		byte[] data = RLPCodec.encode(objs);

		byte[] result = new byte[MAGIC_NUM.length + data.length];
		System.arraycopy(MAGIC_NUM, 0, result, 0, MAGIC_NUM.length);
		System.arraycopy(data, 0, result, MAGIC_NUM.length, data.length);

		return Numeric.toHexStringNoPrefix(result);
	}

	public static Uint64 fnvOne64Hash(String value) {
		long rv = FNV_64_INIT;

		int len = value.length();
		for (int i = 0; i < len; i++) {
			rv *= FNV_64_PRIME;
			rv ^= value.charAt(i);
		}

		return Uint64.of(Long.toUnsignedString(rv));
	}
}
