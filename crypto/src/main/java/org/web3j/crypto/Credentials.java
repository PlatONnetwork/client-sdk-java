package org.web3j.crypto;

import com.platon.sdk.utlis.Bech32;
import com.platon.sdk.utlis.NetworkParameters;
import org.web3j.utils.Numeric;

import java.util.Objects;

/**
 * Credentials wrapper.
 */
public class Credentials {

    private final ECKeyPair ecKeyPair;
    private final String latAddress;
    private final String laxAddress;

    private Credentials(ECKeyPair ecKeyPair, String latAddress, String laxAddress) {
        this.ecKeyPair = ecKeyPair;
        this.latAddress = latAddress;
        this.laxAddress = laxAddress;
    }

    public ECKeyPair getEcKeyPair() {
        return ecKeyPair;
    }

    public String getAddress(NetworkParameters networkParameters) {
        networkParameters.getHrp();
        if(NetworkParameters.Hrp.LAT.getHrp().equals(networkParameters.getHrp())){
            return latAddress;
        }else {
            return laxAddress;
        }
    }

    public String getAddress(long chainId) {
        if(NetworkParameters.MainNetParams.getChainId() == chainId){
            return latAddress;
        }else {
            return laxAddress;
        }
    }

    public String getAddress() {
        if(NetworkParameters.CurrentNetwork.getChainId() == NetworkParameters.MainNetParams.getChainId()){
            return latAddress;
        }else {
            return laxAddress;
        }
    }

    public static Credentials create(ECKeyPair ecKeyPair) {
        String address = Numeric.prependHexPrefix(Keys.getAddress(ecKeyPair));
        String latAddress = Bech32.addressEncode(NetworkParameters.Hrp.LAT.getHrp(),address);
        String laxAddress = Bech32.addressEncode(NetworkParameters.Hrp.LAX.getHrp(),address);
        return new Credentials(ecKeyPair, latAddress, laxAddress);
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
                Objects.equals(latAddress, that.latAddress) &&
                Objects.equals(laxAddress, that.laxAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ecKeyPair, latAddress, laxAddress);
    }
}
