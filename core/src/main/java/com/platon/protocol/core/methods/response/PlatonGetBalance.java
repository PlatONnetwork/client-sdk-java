package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_getBalance.
 */
public class PlatonGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
