package org.web3j.crypto;

import com.platon.sdk.utlis.NetworkParameters;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
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
        //TODO TEST
        assertThat(credentials.getAddress(NetworkParameters.MainNetParams), is(SampleKeys.ADDRESS));
        assertThat(credentials.getEcKeyPair(), is(SampleKeys.KEY_PAIR));
    }
}
