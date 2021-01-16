package com.platon.contracts.ppos.exception;

import com.platon.protocol.exceptions.TransactionException;

public class NoSupportFunctionType extends TransactionException {

    public NoSupportFunctionType(int type) {
        super(String.valueOf(type));
    }

    /*public NoSupportFunctionType(String message) {
        super(message);
    }

    public NoSupportFunctionType(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSupportFunctionType(Throwable cause) {
        super(cause);
    }*/
}
