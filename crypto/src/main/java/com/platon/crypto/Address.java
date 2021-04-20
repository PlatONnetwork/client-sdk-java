package com.platon.crypto;

public class Address {
    private String mainnet;
    private String testnet;
    public Address(){};
    public Address(String mainnetAddress, String testnetAddress) {
        this.mainnet=mainnetAddress;
        this.testnet=testnetAddress;
    }

    public String getMainnet() {
        return mainnet;
    }

    public void setMainnet(String mainnet) {
        this.mainnet = mainnet;
    }

    public String getTestnet() {
        return testnet;
    }

    public void setTestnet(String testnet) {
        this.testnet = testnet;
    }
}
