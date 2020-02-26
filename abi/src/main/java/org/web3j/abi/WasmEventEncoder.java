package org.web3j.abi;

import org.web3j.abi.datatypes.WasmEvent;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import com.platon.rlp.RLPCodec;

public class WasmEventEncoder {
	private WasmEventEncoder() {
	}

	public static String encode(WasmEvent event) {
		byte[] hash = Hash.sha3(RLPCodec.encode(event.getName()));
		return Numeric.toHexString(hash);
	}

	public static String encodeIndexParameter(Object o) {
		byte[] hash = Hash.sha3(RLPCodec.encode(o));
		return Numeric.toHexString(hash);
	}
}
