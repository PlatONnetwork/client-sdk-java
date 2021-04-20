package com.platon.protocol.core.methods.response;

import com.platon.protocol.core.Response;

/**
 * eth_estimateGas.
 */
public class PlatonEstimateGas extends Response<String> {
    /*public BigInteger getAmountUsed() throws EstimateGasException {
        String result = getResult();
        if(hasError()){
            throw new EstimateGasException(result);
        }else{
            return Numeric.decodeQuantity(getResult());
        }
    }*/
}
