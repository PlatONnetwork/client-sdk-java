package org.web3j.tx.gas;

import java.math.BigInteger;

public class ContractGasProvider implements GasProvider {

    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public ContractGasProvider(BigInteger gasPrice, BigInteger gasLimit) {
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
    }

    @Override
    public BigInteger getGasPrice() {
        return gasPrice;
    }

    @Override
    public BigInteger getGasLimit() {
        return gasLimit;
    }
}
