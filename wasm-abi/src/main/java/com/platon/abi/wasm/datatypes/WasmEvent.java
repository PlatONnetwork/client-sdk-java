package com.platon.abi.wasm.datatypes;

import java.util.List;

public class WasmEvent {
	private String name;
	private List<WasmEventParameter> indexedParameters;
	private List<WasmEventParameter> nonIndexedParameters;

	public WasmEvent(String name, List<WasmEventParameter> indexedParameters, List<WasmEventParameter> nonIndexedParameters) {
		this.name = name;
		this.indexedParameters = indexedParameters;
		this.nonIndexedParameters = nonIndexedParameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WasmEventParameter> getIndexedParameters() {
		return indexedParameters;
	}

	public void setIndexedParameters(List<WasmEventParameter> indexedParameters) {
		this.indexedParameters = indexedParameters;
	}

	public List<WasmEventParameter> getNonIndexedParameters() {
		return nonIndexedParameters;
	}

	public void setNonIndexedParameters(List<WasmEventParameter> nonIndexedParameters) {
		this.nonIndexedParameters = nonIndexedParameters;
	}
}
