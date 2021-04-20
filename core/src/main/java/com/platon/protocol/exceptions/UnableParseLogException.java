package com.platon.protocol.exceptions;

public class UnableParseLogException extends TransactionException {

    public UnableParseLogException(String message) {
        super(message);
    }

    public UnableParseLogException(Throwable cause) {
        super(cause);
    }
}
