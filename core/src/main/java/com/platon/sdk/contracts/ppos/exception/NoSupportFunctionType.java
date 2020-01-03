package com.platon.sdk.contracts.ppos.exception;

public class NoSupportFunctionType extends  Exception {

    public NoSupportFunctionType() {
    }

    public NoSupportFunctionType(String message) {
        super(message);
    }

    public NoSupportFunctionType(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSupportFunctionType(Throwable cause) {
        super(cause);
    }
}
