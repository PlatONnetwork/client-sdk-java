package com.platon.rlp.wasm;

public interface RLPEncoder<T> {
	RLPElement encode(T o);

	class None implements RLPEncoder<Object> {
		@Override
		public RLPElement encode(Object o) {
			return null;
		}
	}
}
