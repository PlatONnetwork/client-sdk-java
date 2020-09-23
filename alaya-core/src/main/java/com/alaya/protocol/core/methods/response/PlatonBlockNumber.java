package com.alaya.protocol.core.methods.response;

import java.math.BigInteger;

import com.alaya.utils.Numeric;
import com.alaya.protocol.core.Response;

/**
 * eth_blockNumber.
 */
public class PlatonBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
