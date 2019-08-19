package org.web3j.tx.gas;

import java.math.BigInteger;

public class DefaultWasmGasProvider implements GasProvider {
    private BigInteger gasPrice = BigInteger.valueOf(1_000_000_000L);
    private BigInteger invokeGasLimit = BigInteger.valueOf(2_000_000L);

    public DefaultWasmGasProvider() {

    }

    public DefaultWasmGasProvider(BigInteger gasPrice, BigInteger invokeGasLimit) {
        this.gasPrice = gasPrice;
        this.invokeGasLimit = invokeGasLimit;
    }

    @Override
    public BigInteger getGasPrice() {
        return gasPrice;
    }


    @Override
    public BigInteger getGasLimit() {
        return invokeGasLimit;
    }
}
