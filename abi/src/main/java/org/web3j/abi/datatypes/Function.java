package org.web3j.abi.datatypes;

import java.math.BigInteger;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.generated.Uint16;
import org.web3j.abi.datatypes.generated.Uint32;

import static org.web3j.abi.Utils.convert;

/**
 * Function type.
 */
public class Function {
    private String name;
    private int type;
    private List<Type> inputParameters;
    private List<TypeReference<Type>> outputParameters;

    public Function(String name, List<Type> inputParameters,
                    List<TypeReference<?>> outputParameters) {
        this.name = name;
        this.inputParameters = inputParameters;
        this.outputParameters = convert(outputParameters);
    }

    public Function(int type, List<Type> inputParameters, List<TypeReference<?>> outputParameters) {
        this.type = type;
        this.inputParameters = inputParameters;
        this.outputParameters = convert(outputParameters);
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Type> getInputParameters() {
        return inputParameters;
    }

    public List<TypeReference<Type>> getOutputParameters() {
        return outputParameters;
    }
}
