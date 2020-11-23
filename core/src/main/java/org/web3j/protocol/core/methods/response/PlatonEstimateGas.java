package org.web3j.protocol.core.methods.response;

import org.web3j.protocol.core.Response;

/**
 * eth_estimateGas.
 */
public class PlatonEstimateGas extends Response<String>{
    /*public BigInteger getAmountUsed() throws EstimateGasException {
        String result = getResult();
        if(hasError()){
            throw new EstimateGasException(result); //error code or error message.
        }else{
            return Numeric.decodeQuantity(result);
        }
    }*/
}
