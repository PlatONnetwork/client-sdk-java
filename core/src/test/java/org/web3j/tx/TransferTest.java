package org.web3j.tx;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.SampleKeys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class TransferTest extends ManagedTransactionTester {

    protected TransactionReceipt transactionReceipt;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        transactionReceipt = prepareTransfer();
    }

    @Test
    @Ignore
    public void transfer() throws Exception {
        //TODO
        Web3j web3j = Web3j.build(new HttpService("http://10.10.8.200:6789"));
        Credentials credentials = Credentials.create("0xa7f1d33a30c1e8b332443825f2209755c52086d0a88b084301a6727d9f84bf32");
        Transfer.sendFunds(web3j,credentials,100,"0x8b77ac9fabb6fe247ee91ca07ea4f62c6761e79b", BigDecimal.valueOf(100), Convert.Unit.LAT).send();
    }

    @Test
    public void testSendFunds() throws Exception {
        assertThat(sendFunds(SampleKeys.CREDENTIALS, ADDRESS,
                BigDecimal.TEN, Convert.Unit.LAT),
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
