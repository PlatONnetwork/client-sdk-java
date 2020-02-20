package org.web3j.abi;

import org.web3j.abi.datatypes.WasmEvent;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

public class WasmEventEncoder {
	private WasmEventEncoder() {
	}

	public static String encode(WasmEvent event) {
		return buildEventSignature(event.getName());
	}

	public static String buildEventSignature(String methodSignature) {
		byte[] input = methodSignature.getBytes();
		byte[] hash = Hash.sha3(input);
		return Numeric.toHexString(hash);
	}

}
