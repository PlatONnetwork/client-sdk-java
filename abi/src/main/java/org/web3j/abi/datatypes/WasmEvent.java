package org.web3j.abi.datatypes;

import java.util.List;

public class WasmEvent {
	private String name;
	private List<?> indexedParameters;
	private List<?> nonIndexedParameters;

	public WasmEvent(String name, List<?> indexedParameters, List<?> nonIndexedParameters) {
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

	public List<?> getIndexedParameters() {
		return indexedParameters;
	}

	public void setIndexedParameters(List<?> indexedParameters) {
		this.indexedParameters = indexedParameters;
	}

	public List<?> getNonIndexedParameters() {
		return nonIndexedParameters;
	}

	public void setNonIndexedParameters(List<?> nonIndexedParameters) {
		this.nonIndexedParameters = nonIndexedParameters;
	}

}
