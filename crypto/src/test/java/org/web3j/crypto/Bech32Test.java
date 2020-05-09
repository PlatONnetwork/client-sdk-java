package org.web3j.crypto;

import com.platon.sdk.utlis.NetworkParameters;
import org.junit.Test;

import java.math.BigInteger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.web3j.crypto.ContractUtils.generateContractAddress;

public class Bech32Test {
    @Test
    public void addressEncodetOnTestNet() {
        String hex = "0x493301712671ada506ba6ca7891f436d29185821";
        String bech32Address = Bech32.addressEncode(NetworkParameters.TestNetParams.getHrp(), hex);
        assertThat(bech32Address, is("lax1fyeszufxwxk62p46djncj86rd553skpptsj8v6"));
    }

    @Test
    public void addressEncodetOnMainNet() {
        String hex = "0x493301712671ada506ba6ca7891f436d29185821";
        String bech32Address = Bech32.addressEncode(NetworkParameters.MainNetParams.getHrp(), hex);
        assertThat(bech32Address, is("lat1fyeszufxwxk62p46djncj86rd553skppy4qgz4"));
    }
}
