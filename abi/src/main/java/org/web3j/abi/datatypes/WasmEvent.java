package org.web3j.abi.datatypes;

import java.util.List;

public class WasmEvent {
	private String name;
	private List<Class<?>> indexedParameters;
	private List<Class<?>> nonIndexedParameters;

	public WasmEvent(String name, List<Class<?>> indexedParameters, List<Class<?>> nonIndexedParameters) {
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

	public List<Class<?>> getIndexedParameters() {
		return indexedParameters;
	}

	public void setIndexedParameters(List<Class<?>> indexedParameters) {
		this.indexedParameters = indexedParameters;
	}

	public List<Class<?>> getNonIndexedParameters() {
		return nonIndexedParameters;
	}

	public void setNonIndexedParameters(List<Class<?>> nonIndexedParameters) {
		this.nonIndexedParameters = nonIndexedParameters;
	}

}
