package com.alaya.protocol.core.methods.response;

import java.math.BigInteger;

import com.alaya.utils.Numeric;
import com.alaya.protocol.core.Response;

/**
 * eth_getTransactionCount.
 */
public class PlatonGetTransactionCount extends Response<String> {
    public BigInteger getTransactionCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
