package com.platon.tx.gas;

import java.math.BigInteger;

public class DefaultGasProvider extends ContractGasProvider {

    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(210000);

    public static final BigInteger GAS_PRICE = BigInteger.valueOf(500000000000L);

    public DefaultGasProvider() {
        super(GAS_PRICE, GAS_LIMIT);
    }
}
