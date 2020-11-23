package com.alaya.contracts.ppos.exception;

import com.alaya.protocol.exceptions.TransactionException;

public class EstimateGasException extends TransactionException {
    public EstimateGasException(String message) {
        super(message);
    }
}
