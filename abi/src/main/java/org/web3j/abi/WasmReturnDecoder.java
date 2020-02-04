package org.web3j.abi;

import org.web3j.utils.Numeric;

import com.platon.rlp.RLPCodec;

public class WasmReturnDecoder {
	public static <T> T decode(String input, Class<T> clazz) {
		byte[] data = Numeric.hexStringToByteArray(input);
		return RLPCodec.decode(data, clazz);
	}
}
