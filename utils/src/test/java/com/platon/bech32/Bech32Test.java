package com.platon.bech32;

import com.platon.parameters.NetworkParameters;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Bech32Test {

    @Test
    public void addressEncodetOnTestNet() {
        NetworkParameters.init(20000L, "atx");
        String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
        String bech32Address = Bech32.addressEncode(NetworkParameters.getHrp(), hex);
        assertThat(bech32Address, is("atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89"));
    }

    @Test
    public void addressEncodetOnMainNet() {
        String hex = "0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818";
        String bech32Address = Bech32.addressEncode(NetworkParameters.getHrp(), hex);
        assertThat(bech32Address, is("atp1f7wp58h65lvphgw2hurl9sa943w0f7qc879x50"));
    }

    @Test
    public void addressDecodeHexOnTestNet() {
        NetworkParameters.init(20000L, "atx");
        String bech32Address = "atx1f7wp58h65lvphgw2hurl9sa943w0f7qcdcev89";
        String hex =  Bech32.addressDecodeHex(bech32Address);
        assertThat(hex, is("0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818"));
    }

    @Test
    public void addressDecodeHexOnMainNet() {
        String bech32Address = "atp1f7wp58h65lvphgw2hurl9sa943w0f7qc879x50";
        String hex =  Bech32.addressDecodeHex(bech32Address);
        assertThat(hex, is("0x4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818"));

    }

    @Test
    public void add() {
        NetworkParameters.init(201030L, "atx");
        String hex = "0x1000000000000000000000000000000000000005";
        String bech32Address = Bech32.addressEncode(NetworkParameters.getHrp(), hex);
        System.out.println("bech32Address:" + bech32Address);
        assertThat(bech32Address, is("atx1zqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq9zmm967"));
    }


    @Test
    public void testChangeHrp() {
        NetworkParameters.init(201030L, "atx");
        String hex = "0x1000000000000000000000000000000000000005";
        String bech32Address = Bech32.addressEncode(NetworkParameters.getHrp(), hex);

        String newAddr = Bech32.changeHrp(bech32Address,"lxy");
        System.out.println("newAddr:" + newAddr);
    }

}
