package com.platon.contracts.ppos.exception;

import com.platon.protocol.exceptions.TransactionException;

public class EstimateGasException extends TransactionException {
    public EstimateGasException(String message) {
        super(message);
    }
}
