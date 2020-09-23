package com.alaya.abi.solidity.datatypes.generated;

import java.math.BigInteger;

import com.alaya.abi.solidity.datatypes.Uint;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint64 extends Uint {
    public static final Uint64 DEFAULT = new Uint64(BigInteger.ZERO);

    public Uint64(BigInteger value) {
        super(64, value);
    }

    public Uint64(long value) {
        this(BigInteger.valueOf(value));
    }
}
