package com.alaya.abi.solidity.datatypes.generated;

import java.math.BigInteger;
import com.alaya.abi.solidity.datatypes.Uint;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint232 extends Uint {
    public static final Uint232 DEFAULT = new Uint232(BigInteger.ZERO);

    public Uint232(BigInteger value) {
        super(232, value);
    }

    public Uint232(long value) {
        this(BigInteger.valueOf(value));
    }
}
