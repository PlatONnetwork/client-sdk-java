package com.platon.abi.solidity.datatypes.generated;

import com.platon.abi.solidity.datatypes.StaticArray;
import com.platon.abi.solidity.datatypes.Type;

import java.util.List;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.web3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray15<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray15(List<T> values) {
        super(15, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray15(T... values) {
        super(15, values);
    }

    public StaticArray15(Class<T> type, List<T> values) {
        super(type, 15, values);
    }

    @SafeVarargs
    public StaticArray15(Class<T> type, T... values) {
        super(type, 15, values);
    }
}
