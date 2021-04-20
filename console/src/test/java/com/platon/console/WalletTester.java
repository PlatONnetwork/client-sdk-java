package com.platon.console;

import com.platon.TempFileProvider;
import com.platon.crypto.SampleKeys;
import org.junit.Before;

import static org.mockito.Mockito.mock;

public abstract class WalletTester extends TempFileProvider {

    static final char[] WALLET_PASSWORD = SampleKeys.PASSWORD.toCharArray();

    IODevice console;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        console = mock(IODevice.class);
    }
}
