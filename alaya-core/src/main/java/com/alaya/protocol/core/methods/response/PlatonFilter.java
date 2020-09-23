package com.alaya.protocol.core.methods.response;

import java.math.BigInteger;

import com.alaya.utils.Numeric;
import com.alaya.protocol.core.Response;

/**
 * eth_newFilter.
 */
public class PlatonFilter extends Response<String> {
    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
