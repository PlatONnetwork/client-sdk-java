package com.alaya.tx;

import java.math.BigDecimal;

import com.alaya.parameters.NetworkParameters;
import org.junit.Test;

import com.alaya.crypto.SampleKeys;
import com.alaya.protocol.core.methods.response.TransactionReceipt;
import com.alaya.tx.exceptions.TxHashMismatchException;
import com.alaya.utils.Convert;

public class RawTransactionManagerTest extends ManagedTransactionTester {

    @Test(expected = TxHashMismatchException.class)
    public void testTxHashMismatch() throws Exception {
        TransactionReceipt transactionReceipt = prepareTransfer();
        prepareTransaction(transactionReceipt);

        TransactionManager transactionManager =
                new RawTransactionManager(web3j, SampleKeys.CREDENTIALS, NetworkParameters.MainNetParams.getChainId());
        Transfer transfer = new Transfer(web3j, transactionManager);
        transfer.sendFunds(ADDRESS, BigDecimal.ONE, Convert.Unit.ATP).send();
    }
}
