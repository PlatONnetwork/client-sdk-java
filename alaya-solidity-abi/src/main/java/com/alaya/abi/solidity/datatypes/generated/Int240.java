package com.alaya.abi.solidity.datatypes.generated;

import java.math.BigInteger;
import com.alaya.abi.solidity.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int240 extends Int {
    public static final Int240 DEFAULT = new Int240(BigInteger.ZERO);

    public Int240(BigInteger value) {
        super(240, value);
    }

    public Int240(long value) {
        this(BigInteger.valueOf(value));
    }
}
