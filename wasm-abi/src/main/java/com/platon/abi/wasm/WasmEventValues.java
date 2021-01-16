package com.platon.abi.wasm;

import java.util.List;

public class WasmEventValues {
	private List<String> indexedValues;
	private List<?> nonIndexedValues;

	public WasmEventValues(List<String> indexedValues, List<?> nonIndexedValues) {
		this.indexedValues = indexedValues;
		this.nonIndexedValues = nonIndexedValues;
	}

	public List<String> getIndexedValues() {
		return indexedValues;
	}

	public void setIndexedValues(List<String> indexedValues) {
		this.indexedValues = indexedValues;
	}

	public List<?> getNonIndexedValues() {
		return nonIndexedValues;
	}

	public void setNonIndexedValues(List<?> nonIndexedValues) {
		this.nonIndexedValues = nonIndexedValues;
	}

}
