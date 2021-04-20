package com.platon.crypto;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CredentialsTest {
    @Test
    public void testCredentialsFromString() {
        Credentials credentials = Credentials.create(SampleKeys.KEY_PAIR);
        verify(credentials);
    }

    @Test
    public void testCredentialsFromECKeyPair() {
        Credentials credentials = Credentials.create(
                SampleKeys.PRIVATE_KEY_STRING, SampleKeys.PUBLIC_KEY_STRING);
        verify(credentials);
    }

    private void verify(Credentials credentials) {
        assertEquals(credentials.getAddress(),SampleKeys.BECH32_ADDRESS);
        assertThat(credentials.getEcKeyPair(), is(SampleKeys.KEY_PAIR));
    }
}
