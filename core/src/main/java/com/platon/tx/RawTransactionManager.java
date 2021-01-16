package com.platon.tx;

import com.platon.crypto.Credentials;
import com.platon.crypto.Hash;
import com.platon.crypto.RawTransaction;
import com.platon.crypto.TransactionEncoder;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonGetTransactionCount;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.tx.exceptions.TxHashMismatchException;
import com.platon.tx.response.TransactionReceiptProcessor;
import com.platon.utils.Numeric;
import com.platon.utils.TxHashVerifier;

import java.io.IOException;
import java.math.BigInteger;

/**
 * TransactionManager implementation using Ethereum wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 */
public class RawTransactionManager extends TransactionManager {

    private final Web3j web3j;
    final Credentials credentials;


    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(Web3j web3j, Credentials credentials) {
        super(web3j, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;
    }

    public RawTransactionManager(Web3j web3j, Credentials credentials, TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;
    }

    public RawTransactionManager(Web3j web3j, Credentials credentials, int attempts, long sleepDuration) {
        super(web3j, attempts, sleepDuration, credentials.getAddress());

        this.web3j = web3j;
        this.credentials = credentials;
    }

    protected BigInteger getNonce() throws IOException {
        PlatonGetTransactionCount ethGetTransactionCount = web3j.platonGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        if (ethGetTransactionCount.getTransactionCount().intValue() == 0) {
            ethGetTransactionCount = web3j.platonGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        }

        return ethGetTransactionCount.getTransactionCount();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
    }

    @Override
    public PlatonSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value) throws IOException {

        BigInteger nonce = getNonce();

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data);

        return signAndSend(rawTransaction);
    }

    public PlatonSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {

        byte[] signedMessage;

        if (NetworkParameters.getChainId() > ChainId.NONE) {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, NetworkParameters.getChainId(), credentials);
        } else {
            signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        }

        String hexValue = Numeric.toHexString(signedMessage);
        PlatonSendTransaction ethSendTransaction = web3j.platonSendRawTransaction(hexValue).send();

        if (ethSendTransaction != null && !ethSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = ethSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return ethSendTransaction;
    }
}
