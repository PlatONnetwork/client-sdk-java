package com.platon.tx.gas;

import java.math.BigInteger;

public interface GasProvider {

    BigInteger getGasPrice();

    BigInteger getGasLimit();
}
