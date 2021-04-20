package com.platon.abi.solidity.datatypes.generated;

import com.platon.abi.solidity.datatypes.Uint;

import java.math.BigInteger;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint40 extends Uint {
    public static final Uint40 DEFAULT = new Uint40(BigInteger.ZERO);

    public Uint40(BigInteger value) {
        super(40, value);
    }

    public Uint40(long value) {
        this(BigInteger.valueOf(value));
    }
}
