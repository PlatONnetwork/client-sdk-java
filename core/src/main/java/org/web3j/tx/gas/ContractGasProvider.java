package org.web3j.tx.gas;

import java.math.BigInteger;

public interface ContractGasProvider {
    BigInteger getGasPrice(String contractFunc);

    BigInteger getGasPrice(int functionType);

    @Deprecated
    BigInteger getGasPrice();

    BigInteger getGasLimit(String contractFunc);

    BigInteger getGasLimit(int functionType);

    @Deprecated
    BigInteger getGasLimit();
}
