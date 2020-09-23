package com.alaya.abi.solidity.datatypes;

import com.alaya.abi.solidity.TypeReference;

import java.util.List;
import java.util.stream.Collectors;
import static com.alaya.abi.solidity.Utils.convert;

/**
 * Event wrapper type.
 */
@SuppressWarnings("rawtypes")
public class Event {
	private String name;
	private int functionType;
	private List<TypeReference<Type>> parameters;

	public Event(String name, List<TypeReference<?>> parameters) {
		this.name = name;
		this.parameters = convert(parameters);
	}

	public Event(int functionType, List<TypeReference<?>> parameters) {
		this.functionType = functionType;
		this.parameters = convert(parameters);
	}

	public String getName() {
		return name;
	}

	public int getFunctionType() {
		return functionType;
	}

	public List<TypeReference<Type>> getParameters() {
		return parameters;
	}

	public List<TypeReference<Type>> getIndexedParameters() {
		return parameters.stream().filter(TypeReference::isIndexed).collect(Collectors.toList());
	}

	public List<TypeReference<Type>> getNonIndexedParameters() {
		return parameters.stream().filter(p -> !p.isIndexed()).collect(Collectors.toList());
	}
}
