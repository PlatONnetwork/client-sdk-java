package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_gasPrice.
 */
public class PlatonGasPrice extends Response<String> {
    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(getResult());
    }
}
