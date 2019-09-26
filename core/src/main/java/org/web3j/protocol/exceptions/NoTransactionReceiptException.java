package org.web3j.protocol.exceptions;

/**
 * Transaction timeout exception indicates that we have breached some threshold waiting for a
 * transaction to execute.
 */
public class NoTransactionReceiptException extends TransactionException {
    public NoTransactionReceiptException(String message) {
        super(message);
    }

    public NoTransactionReceiptException(Throwable cause) {
        super(cause);
    }
}
