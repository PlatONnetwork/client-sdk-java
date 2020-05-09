package org.web3j.crypto;

import com.platon.sdk.utlis.NetworkParameters;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Bech32Test {
    @Test
    public void addressEncodetOnTestNet() {
        String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
        String bech32Address = Bech32.addressEncode(NetworkParameters.TestNetParams.getHrp(), hex);
        assertThat(bech32Address, is("lax1f7wp58h65lvphgw2hurl9sa943w0f7qc3dp390"));
    }

    @Test
    public void addressEncodetOnMainNet() {
        String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
        String bech32Address = Bech32.addressEncode(NetworkParameters.MainNetParams.getHrp(), hex);
        assertThat(bech32Address, is("lat1f7wp58h65lvphgw2hurl9sa943w0f7qc7gn7tq"));
    }
}
