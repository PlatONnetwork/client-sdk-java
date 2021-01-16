package com.platon.rlp.wasm;

public interface RLPDecoder<T> {
	T decode(RLPElement element);

	class None implements RLPDecoder<Object> {
		@Override
		public Object decode(RLPElement element) {
			return null;
		}
	}
}
