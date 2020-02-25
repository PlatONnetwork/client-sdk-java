package org.web3j.abi.datatypes;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class WasmFunction {
	private String name;
	private int type;
	private List<?> inputParameters;
	private Class<?> outputParameter;
	private ParameterizedType outputParameterizedType;

	public WasmFunction(String name, List<?> inputParameters, Class<?> outputParameter) {
		this.name = name;
		this.inputParameters = inputParameters;
		this.outputParameter = outputParameter;
	}

	public WasmFunction(String name, List<?> inputParameters, Class<?> outputParameter, ParameterizedType outputParameterizedType) {
		this.name = name;
		this.inputParameters = inputParameters;
		this.outputParameter = outputParameter;
		this.outputParameterizedType = outputParameterizedType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<?> getInputParameters() {
		return inputParameters;
	}

	public void setInputParameters(List<?> inputParameters) {
		this.inputParameters = inputParameters;
	}

	public Class<?> getOutputParameter() {
		return outputParameter;
	}

	public void setOutputParameter(Class<?> outputParameter) {
		this.outputParameter = outputParameter;
	}

	public ParameterizedType getOutputParameterizedType() {
		return outputParameterizedType;
	}

	public void setOutputParameterizedType(ParameterizedType outputParameterizedType) {
		this.outputParameterizedType = outputParameterizedType;
	}

}
