package com.alaya.tx.gas;

import java.math.BigInteger;

public interface GasProvider {

    BigInteger getGasPrice();

    BigInteger getGasLimit();
}
