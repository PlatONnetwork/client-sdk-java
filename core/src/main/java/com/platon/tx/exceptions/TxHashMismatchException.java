package com.platon.tx.exceptions;

import java.io.IOException;

public class TxHashMismatchException extends IOException {
    private String txHashLocal;
    private String txHashRemote;

    public TxHashMismatchException(String txHashLocal, String txHashRemote) {
        this.txHashLocal = txHashLocal;
        this.txHashRemote = txHashRemote;
    }

    public String getTxHashLocal() {
        return txHashLocal;
    }

    public String getTxHashRemote() {
        return txHashRemote;
    }
}
