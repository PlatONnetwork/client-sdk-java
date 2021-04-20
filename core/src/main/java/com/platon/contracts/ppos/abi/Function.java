package com.platon.contracts.ppos.abi;

import com.platon.abi.solidity.datatypes.Type;

import java.util.List;

public class Function {

    private int type;

    private List<Type> inputParameters;

    public Function(int type) {
        this.type = type;
    }

    public Function(int type, List<Type> inputParameters) {
        this.type = type;
        this.inputParameters = inputParameters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Type> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<Type> inputParameters) {
        this.inputParameters = inputParameters;
    }
}
