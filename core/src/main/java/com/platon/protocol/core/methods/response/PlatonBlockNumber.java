package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.utils.Numeric;

import java.math.BigInteger;

/**
 * eth_blockNumber.
 */
public class PlatonBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }
}
