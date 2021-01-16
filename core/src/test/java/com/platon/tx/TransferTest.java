package com.platon.tx;

import com.platon.crypto.Credentials;
import com.platon.crypto.SampleKeys;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import com.platon.utils.Convert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransferTest extends ManagedTransactionTester {

    protected TransactionReceipt transactionReceipt;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        transactionReceipt = prepareTransfer();
    }

    @Test
    public void testSendFunds() throws Exception {
        assertThat(sendFunds(SampleKeys.CREDENTIALS, ADDRESS, BigDecimal.TEN, Convert.Unit.KPVON),
                is(transactionReceipt));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testTransferInvalidValue() throws Exception {
        sendFunds(SampleKeys.CREDENTIALS, ADDRESS,
                new BigDecimal(0.1), Convert.Unit.VON);
    }

    protected TransactionReceipt sendFunds(Credentials credentials, String toAddress,
                                           BigDecimal value, Convert.Unit unit) throws Exception {
        return new Transfer(web3j, getVerifiedTransactionManager(credentials))
                .sendFunds(toAddress, value, unit).send();
    }
}
