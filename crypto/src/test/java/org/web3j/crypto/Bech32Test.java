package org.web3j.crypto;

import com.platon.sdk.utlis.NetworkParameters;
import org.junit.Test;

public class Bech32Test {
    @Test
    public void encode() {
        String hex = "0x493301712671ada506ba6ca7891f436d29185821";
        String bech32Address = Bech32.encode(NetworkParameters.TestNetParams.getHrp(), hex);
        System.out.println("platonFundAccount="+bech32Address);
    }
}
