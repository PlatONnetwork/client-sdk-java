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
public class StaticArray17<T extends Type> extends StaticArray<T> {
    @Deprecated
    public StaticArray17(List<T> values) {
        super(17, values);
    }

    @Deprecated
    @SafeVarargs
    public StaticArray17(T... values) {
        super(17, values);
    }

    public StaticArray17(Class<T> type, List<T> values) {
        super(type, 17, values);
    }

    @SafeVarargs
    public StaticArray17(Class<T> type, T... values) {
        super(type, 17, values);
    }
}
