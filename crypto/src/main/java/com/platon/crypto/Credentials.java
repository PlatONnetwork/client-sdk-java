package com.platon.crypto;

import com.platon.bech32.Bech32;
import com.platon.parameters.NetworkParameters;
import com.platon.utils.Numeric;
import org.bouncycastle.util.encoders.Hex;

import java.util.Objects;

/**
 * Credentials wrapper.
 */
public class Credentials {

    private final ECKeyPair ecKeyPair;
    private final String address;

    private Credentials(ECKeyPair ecKeyPair, String address) {
        this.ecKeyPair = ecKeyPair;
        this.address = address;
    }

    public ECKeyPair getEcKeyPair() {
        return ecKeyPair;
    }

    public String getAddress(NetworkParameters networkParameters) {
        byte[] originBytes= Bech32.addressDecode(address);
        return Bech32.addressEncode(networkParameters.getHrp(), Hex.toHexString(originBytes));
    }

    public String getAddress() {
        return address;
    }

    public static Credentials create(ECKeyPair ecKeyPair) {
        String hexAddress = Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
        String address = Bech32.addressEncode(NetworkParameters.getHrp(),hexAddress);
        return new Credentials(ecKeyPair, address);
    }

    public static Credentials create(String privateKey, String publicKey) {
        return create(new ECKeyPair(Numeric.toBigInt(privateKey), Numeric.toBigInt(publicKey)));
    }

    public static Credentials create(String privateKey) {
        return create(ECKeyPair.create(Numeric.toBigInt(privateKey)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(ecKeyPair, that.ecKeyPair) &&
                Objects.equals(address, that.address) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ecKeyPair, address);
    }
}
