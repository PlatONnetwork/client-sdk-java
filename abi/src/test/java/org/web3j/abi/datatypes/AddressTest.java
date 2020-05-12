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
                is("0x00052b08330e05d731e38c856c1043288f7d9744"));
        assertThat(new Address("lat1qqzjkzpnpczawv0r3jzkcyzr9z8hm96yzdxuyk").toString(),
                is("0x00052b08330e05d731e38c856c1043288f7d9744"));
    }
}
