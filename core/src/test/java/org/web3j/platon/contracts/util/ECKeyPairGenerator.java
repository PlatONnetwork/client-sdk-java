package org.web3j.platon.contracts.util;

import lombok.Data;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;

public class ECKeyPairGenerator {
    @Data
    static class KeyPair{
        private String privateKey,publicKey;
        public KeyPair(String privateKey,String publicKey){
          this.privateKey="0x"+privateKey;
          this.publicKey="0x"+publicKey;
        }
    }
    public static List<KeyPair> randomKeyPairs(int count){
        List<KeyPair> keyPairs = new ArrayList<>();
       while (keyPairs.size()<count){
           try {
               ECKeyPair keyPair = Keys.createEcKeyPair();
               keyPairs.add(new KeyPair(keyPair.getPrivateKey().toString(16),keyPair.getPublicKey().toString(16)));
           } catch (InvalidAlgorithmParameterException e) {
               e.printStackTrace();
           } catch (NoSuchAlgorithmException e) {
               e.printStackTrace();
           } catch (NoSuchProviderException e) {
               e.printStackTrace();
           }
       }
        return keyPairs;
    }
}
