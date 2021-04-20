package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;
import com.platon.utils.Numeric;

import java.math.BigInteger;

/**
 * shh_newFilter.
 */
public class ShhNewFilter extends Response<String> {

    public BigInteger getFilterId() {
        return Numeric.decodeQuantity(getResult());
    }
}
