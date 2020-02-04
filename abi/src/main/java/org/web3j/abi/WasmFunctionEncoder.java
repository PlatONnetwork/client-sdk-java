package org.web3j.abi;

import java.util.ArrayList;
import java.util.List;
import org.web3j.abi.datatypes.WasmFunction;
import org.web3j.utils.Numeric;
import com.platon.rlp.RLPCodec;

public class WasmFunctionEncoder {
	public static byte[] MAGIC_NUM = new byte[] { 0x00, 0x61, 0x73, 0x6d };

	public static String DEPLOY_METHOD_NAME = "init";

	public static String encode(WasmFunction function) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(function.getName());
		parameters.addAll(function.getInputParameters());

		byte[] data = RLPCodec.encode(parameters);

		return Numeric.toHexStringNoPrefix(data);
	}

	public static String encodeConstructor(String code, List<?> inputParameters) {
		List<Object> parameters = new ArrayList<>();
		parameters.add(DEPLOY_METHOD_NAME);
		parameters.addAll(inputParameters);
		byte[] parameterData = RLPCodec.encode(parameters);

		byte[] codeBinary = Numeric.hexStringToByteArray(code);
		Object[] objs = new Object[] { codeBinary, parameterData };
		byte[] data = RLPCodec.encode(objs);

		byte[] result = new byte[MAGIC_NUM.length + data.length];
		System.arraycopy(MAGIC_NUM, 0, result, 0, MAGIC_NUM.length);
		System.arraycopy(data, 0, result, MAGIC_NUM.length, data.length);

		return Numeric.toHexStringNoPrefix(result);
	}

}
