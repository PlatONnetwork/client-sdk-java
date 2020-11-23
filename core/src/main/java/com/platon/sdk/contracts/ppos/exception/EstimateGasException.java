package com.platon.sdk.contracts.ppos.exception;

import org.web3j.protocol.exceptions.TransactionException;

public class EstimateGasException extends TransactionException {
    public EstimateGasException(String message) {
        super(message);
    }
}
