package org.web3j.tx.gas;

import java.math.BigInteger;

import org.web3j.tx.PlatOnContract;

public class DefaultWasmGasProvider implements ContractGasProvider {
    private BigInteger gasPrice = BigInteger.valueOf(1_000_000_000L);
    private BigInteger deployGasLimit = BigInteger.valueOf(250_000_000L);
    private BigInteger invokeGasLimit = BigInteger.valueOf(2_000_000L);
    
    public DefaultWasmGasProvider() {
    
    }

    public DefaultWasmGasProvider(BigInteger gasPrice, BigInteger deployGasLimit, BigInteger invokeGasLimit) {
        this.gasPrice = gasPrice;
        this.deployGasLimit = deployGasLimit;
        this.invokeGasLimit = invokeGasLimit;
    }

    @Override
    public BigInteger getGasPrice(String contractFunc) {
        return gasPrice;
    }

    @Override
    public BigInteger getGasPrice() {
        return gasPrice;
    }

    @Override
    public BigInteger getGasLimit(String contractFunc) {
    	if(PlatOnContract.FUNC_DEPLOY.equals(contractFunc)) {
    		return deployGasLimit;
    	}else {
			return invokeGasLimit;
		}
    }

    @Override
    public BigInteger getGasLimit() {
        return deployGasLimit.max(invokeGasLimit);  
    }
}
