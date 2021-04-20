package com.platon.crypto;

public interface WalletFileInterface {
    public String getAddress();

    public void setAddress(String address);

    public WalletFile.Crypto getCrypto();

    //@JsonSetter("crypto")
    public void setCrypto(WalletFile.Crypto crypto);

    /*@JsonSetter("Crypto")  // older wallet files may have this attribute name
    public void setCryptoV1(WalletFile.Crypto crypto);*/

    public String getId() ;

    public void setId(String id);
    public int getVersion();

    public void setVersion(int version);
}
