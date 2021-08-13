package com.platon.crypto;

import com.platon.parameters.NetworkParameters;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.platon.crypto.Bip32Test.*;
import static com.platon.crypto.SampleKeys.PASSWORD;
import static com.platon.crypto.WalletUtilsTest.createTempDir;
import static org.junit.Assert.assertEquals;

import java.io.File;

public class Bip44WalletUtilsTest {

    private File tempDir;

    @Before
    public void setUp() throws Exception {
        tempDir = createTempDir();
    }

    @After
    public void tearDown() throws Exception {
        for (File file:tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
    }

    @SuppressWarnings("checkstyle:LineLength")
    @Test
    public void generateBip44KeyPair() {
        String mnemonic = "spider elbow fossil truck deal circle divert sleep safe report laundry above";
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);
        String seedStr = bytesToHex(seed);
        assertEquals("f0d2ab78b96acd147119abad1cd70eb4fec4f0e0a95744cf532e6a09347b08101213b4cbf50eada0eb89cba444525fe28e69707e52aa301c6b47ce1c5ef82eb5",
                seedStr);

        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        assertEquals("xprv9s21ZrQH143K2yA9Cdad5gjqHRC7apVUgEyYq5jXeXigDZ3PfEnps44tJprtMXr7PZivEsin6Qrbad7PuiEy4tn5jAEK6A3U46f9KvfRCmD",
                Base58.encode(addChecksum(serializePrivate(masterKeypair))));

        Bip32ECKeyPair bip44Keypair = Bip44WalletUtils.generateBip44KeyPair(masterKeypair);

        assertEquals("xprvA21iQhYkgksD4fXqSAvbJxqhzcULByCqRPLciwEBstGYTTEbEhUJp4cn3foK7xbMiGMfhFKvKmauQ5SjoqvkTE7XKFqHaa6F8XBx78N9CRh",
                Base58.encode(addChecksum(serializePrivate(bip44Keypair))));
        assertEquals("xpub6F14pD5eX8RWH9cJYCTbg6nSYeJpbRvgncGDXKdoSDoXLFZjnEnZMrwFtv8TTxJ3s4e9oxDFcfQXSRyuzbaovNmKFJogGp3vGH7EwUCncei",
                Base58.encode(addChecksum(serializePublic(bip44Keypair))));
    }

    @SuppressWarnings("checkstyle:LineLength")
    @Test
    public void deriveChildKey() {
        NetworkParameters.selectPlatON();
        String mnemonic = "spider elbow fossil truck deal circle divert sleep safe report laundry above";
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        Bip32ECKeyPair bip44Keypair = Bip44WalletUtils.generateBip44KeyPair(masterKeypair);
        for (int i = 0; i < 1; i++) {
            Bip32ECKeyPair bip32ECKeyPair = bip44Keypair.deriveChildKey(i);
            String address = Credentials.create(bip32ECKeyPair).getAddress();
            Assert.assertEquals("lat134h3492x5lfrukdweagu7d77udn5xeje54hdck",address);
        }
    }

    @SuppressWarnings("checkstyle:LineLength")
    @Test
    public void generateBip44KeyPairTestNet() {
        String mnemonic = "spider elbow fossil truck deal circle divert sleep safe report laundry above";
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);
        String seedStr = bytesToHex(seed);
        assertEquals("f0d2ab78b96acd147119abad1cd70eb4fec4f0e0a95744cf532e6a09347b08101213b4cbf50eada0eb89cba444525fe28e69707e52aa301c6b47ce1c5ef82eb5",
                seedStr);

        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        assertEquals("xprv9s21ZrQH143K2yA9Cdad5gjqHRC7apVUgEyYq5jXeXigDZ3PfEnps44tJprtMXr7PZivEsin6Qrbad7PuiEy4tn5jAEK6A3U46f9KvfRCmD",
                Base58.encode(addChecksum(serializePrivate(masterKeypair))));

        Bip32ECKeyPair bip44Keypair = Bip44WalletUtils.generateBip44KeyPair(masterKeypair, true);

        assertEquals("xprv9zhLxq63By3SX5hAMKnxjGy7L18bnn7GzDQv53eYYqeRX9M82riC1dqovamttwFpk2ZkDQxgcikBQzs1DTu2KShJJqnqgx83EftUB3k39uc",
                Base58.encode(addChecksum(serializePrivate(bip44Keypair))));
        assertEquals("xpub6DghNLcw2LbjjZmdTMKy6Quqt2y6CEq8MSLWsS4A7BBQPwgGaQ2SZSAHmsrqBVxLegjW2mBfcvDBhpeEqCmucTTPJiNLHQkiDuKwHs9gEtk",
                Base58.encode(addChecksum(serializePublic(bip44Keypair))));
    }

    @Test
    public void testGenerateBip44Wallets() throws Exception {
        Bip39Wallet wallet = Bip44WalletUtils.generateBip44Wallet(PASSWORD, tempDir);
        byte[] seed = MnemonicUtils.generateSeed(wallet.getMnemonic(), PASSWORD);
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        Bip32ECKeyPair bip44Keypair = Bip44WalletUtils.generateBip44KeyPair(masterKeypair);
        Credentials credentials = Credentials.create(bip44Keypair);

        assertEquals(credentials,
                Bip44WalletUtils.loadBip44Credentials(PASSWORD, wallet.getMnemonic()));
    }

    private String bytesToHex(byte[] bytes) {
        final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

        char[] chars = new char[2 * bytes.length];
        for (int i = 0; i < bytes.length; ++i) {
            chars[2 * i] = HEX_CHARS[(bytes[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[bytes[i] & 0x0F];
        }
        return new String(chars);
    }
}
