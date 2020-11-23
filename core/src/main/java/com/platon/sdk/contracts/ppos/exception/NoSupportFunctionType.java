package com.platon.sdk.contracts.ppos.exception;

import org.web3j.protocol.exceptions.TransactionException;

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
