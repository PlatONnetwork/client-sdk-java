package com.alaya.protocol.core.methods.response;

import java.math.BigInteger;

import com.alaya.utils.Numeric;
import com.alaya.protocol.core.Response;

/**
 * eth_estimateGas.
 */
public class PlatonEstimateGas extends Response<String> {
    public BigInteger getAmountUsed() {
        return Numeric.decodeQuantity(getResult());
    }
}
