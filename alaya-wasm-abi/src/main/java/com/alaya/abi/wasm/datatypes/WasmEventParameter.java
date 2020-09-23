package com.alaya.abi.wasm.datatypes;

import java.lang.reflect.ParameterizedType;

public class WasmEventParameter {
	private Class<?> type;
	private ParameterizedType parameterizedType;
	private boolean indexed;

	public WasmEventParameter(Class<?> type, ParameterizedType parameterizedType, boolean indexed) {
		this.type = type;
		this.parameterizedType = parameterizedType;
		this.indexed = indexed;
	}

	public WasmEventParameter(Class<?> type) {
		this(type, null, false);
	}

	public WasmEventParameter(Class<?> type, ParameterizedType parameterizedType) {
		this(type, parameterizedType, false);
	}

	public WasmEventParameter(Class<?> type, boolean indexed) {
		this(type, null, indexed);
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public ParameterizedType getParameterizedType() {
		return parameterizedType;
	}

	public void setParameterizedType(ParameterizedType parameterizedType) {
		this.parameterizedType = parameterizedType;
	}

	public boolean isIndexed() {
		return indexed;
	}

	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

}
