package com.alaya.protocol.exceptions;

public class UnableParseLogException extends TransactionException {

    public UnableParseLogException(String message) {
        super(message);
    }

    public UnableParseLogException(Throwable cause) {
        super(cause);
    }
}
