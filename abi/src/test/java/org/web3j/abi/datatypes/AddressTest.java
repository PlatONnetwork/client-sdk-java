package org.web3j.abi.datatypes;

import com.platon.sdk.utlis.Bech32;
import com.platon.sdk.utlis.NetworkParameters;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AddressTest {

    @Test
    public void testToString() {
        assertThat(new Address("lat1qqzjkzpnpczawv0r3jzkcyzr9z8hm96yzdxuyk").toString(),
                is("lat1qqzjkzpnpczawv0r3jzkcyzr9z8hm96yzdxuyk"));
        assertThat(new Address("lat1qqzjkzpnpczawv0r3jzkcyzr9z8hm96yzdxuyk").toString(),
                is("lat1qqzjkzpnpczawv0r3jzkcyzr9z8hm96yzdxuyk"));
    }
}
