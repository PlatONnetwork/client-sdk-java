package org.web3j.abi.datatypes;

import org.web3j.abi.TypeReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Event wrapper type.
 */
public class Event {
    private String name;
    private int functionType;
    private List<TypeReference<Type>> indexedParameters = new ArrayList<TypeReference<Type>>();
    ;
    private List<TypeReference<Type>> nonIndexedParameters = new ArrayList<TypeReference<Type>>();

    public Event(String name, List<TypeReference<?>> parameters) {
        this.name = name;
        this.nonIndexedParameters = convert(parameters);
    }

    public Event(int functionType, List<TypeReference<?>> parameters) {
        this.functionType = functionType;
        this.nonIndexedParameters = convert(parameters);
    }

    public Event(String name, List<TypeReference<?>> indexedParameters,
                 List<TypeReference<?>> nonIndexedParameters) {
        this.name = name;
        this.indexedParameters = convert(indexedParameters);
        this.nonIndexedParameters = convert(nonIndexedParameters);
    }

    public Event(int functionType, List<TypeReference<?>> indexedParameters,
                 List<TypeReference<?>> nonIndexedParameters) {
        this.functionType = functionType;
        this.indexedParameters = convert(indexedParameters);
        this.nonIndexedParameters = convert(nonIndexedParameters);
    }

    public String getName() {
        return name;
    }

    public int getFunctionType() {
        return functionType;
    }

    public List<TypeReference<Type>> getIndexedParameters() {
        return indexedParameters;
    }

    public List<TypeReference<Type>> getNonIndexedParameters() {
        return nonIndexedParameters;
    }

    private List<TypeReference<Type>> convert(List<TypeReference<?>> parameters) {
        List<TypeReference<Type>> typeReferenceList = new ArrayList<TypeReference<Type>>();
        for (TypeReference<?> typeReference : parameters) {
            if (typeReference.isIndexed()) {
                continue;
            }
            typeReferenceList.add((TypeReference<Type>) typeReference);
        }
        return typeReferenceList;
    }
}
