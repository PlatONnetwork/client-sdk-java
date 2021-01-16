package com.platon.crypto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platon.parameters.NetworkParameters;
import com.platon.utils.Numeric;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.platon.crypto.Hash.sha256;
import static com.platon.crypto.SampleKeys.*;
import static com.platon.crypto.WalletUtils.isValidAddress;
import static com.platon.crypto.WalletUtils.isValidPrivateKey;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class WalletUtilsTest {

    private File tempDir;

    @Before
    public void setUp() throws Exception {
        tempDir = createTempDir();
        NetworkParameters.init(2021,"lxy");
    }

    /*@After
    public void tearDown() throws Exception {
        for (File file:tempDir.listFiles()) {
            file.delete();
        }
        tempDir.delete();
    }*/

    @Test
    public void testGenerateBip39Wallets() throws Exception {
        Bip39Wallet wallet = WalletUtils.generateBip39Wallet(PASSWORD, tempDir);
        byte[] seed = MnemonicUtils.generateSeed(wallet.getMnemonic(), PASSWORD);
        Credentials credentials = Credentials.create(ECKeyPair.create(sha256(seed)));

        assertEquals(credentials, WalletUtils.loadBip39Credentials(PASSWORD, wallet.getMnemonic()));
    }

    @Test
    public void testGenerateFullNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateFullNewWalletFile(PASSWORD, tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGeneratedNewWalletFile(fileName);
    }

    @Test
    public void testGenerateNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateNewWalletFile(PASSWORD, tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGeneratedNewWalletFile(fileName);
    }


    @Test
    public void testGenerateLightNewWalletFile() throws Exception {
        String fileName = WalletUtils.generateLightNewWalletFile(PASSWORD, tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGeneratedNewWalletFile(fileName);
    }

    @Test
    public void testGeneratePlatONWalletFileByKeyPair() throws Exception {
        String fileName = WalletUtils.generatePlatONWalletFile(PASSWORD,KEY_PAIR,tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGeneratedNewWalletFile(fileName);
    }

    @Test
    public void testGeneratePlatONWalletFile() throws Exception {
        String fileName = WalletUtils.generatePlatONWalletFile(PASSWORD, tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGeneratedNewWalletFile(fileName);
    }

    @Test
    public void testGeneratePlatONBip39Wallet() throws Exception {
        Bip39Wallet wallet = WalletUtils.generatePlatONBip39Wallet(PASSWORD, tempDir);
        System.out.println("fileName:" + tempDir.getAbsolutePath());
        byte[] seed = MnemonicUtils.generateSeed(wallet.getMnemonic(), PASSWORD);
        Credentials credentials = Credentials.create(ECKeyPair.create(sha256(seed)));

        assertEquals(credentials, WalletUtils.loadBip39Credentials(PASSWORD, wallet.getMnemonic()));
    }

    private void testGeneratedNewWalletFile(String fileName) throws Exception {
        Credentials c = WalletUtils.loadCredentials(PASSWORD, new File(tempDir, fileName));
        System.out.println("wallet address: " + c.getAddress());
    }

    @Test
    public void testGenerateFullWalletFile() throws Exception {
        String fileName = WalletUtils.generateWalletFile(PASSWORD, KEY_PAIR, tempDir, true);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);

        testGenerateWalletFile(fileName);
    }

    @Test
    public void testGenerateLightWalletFile() throws Exception {
        String fileName = WalletUtils.generateWalletFile(PASSWORD, KEY_PAIR, tempDir, false);
        System.out.println("fileName:" + tempDir.getAbsolutePath() + "\\" +fileName);
        testGenerateWalletFile(fileName);
    }

    private void testGenerateWalletFile(String fileName) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD, new File(tempDir, fileName));

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Test
    public void testLoadCredentialsFromFile() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                new File(WalletUtilsTest.class.getResource(
                        "/keyfiles/"
                                + "UTC--2016-11-03T05-55-06."
                                + "340672473Z--ef678007d18427e6022059dbc264f27507cd1ffc")
                        .getFile()));

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    /**
     * 加载旧格式钱包文件
     * @throws Exception
     */
    @Test
    public void testLoadCredentialsFromOldFormatFile() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                new File(WalletUtilsTest.class.getResource(
                        "/keyfiles/old-format-wallet--ef678007d18427e6022059dbc264f27507cd1ffc.json")
                        .getFile()));

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Test
    public void testLoadCredentialsFromString() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                WalletUtilsTest.class.getResource(
                        "/keyfiles/"
                        + "UTC--2016-11-03T05-55-06."
                        + "340672473Z--ef678007d18427e6022059dbc264f27507cd1ffc").getFile());

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Test
    public void testLoadCredentialsFromOldFormatString() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                WalletUtilsTest.class.getResource(
                        "/keyfiles/old-format-wallet--ef678007d18427e6022059dbc264f27507cd1ffc.json").getFile());

        assertThat(credentials, equalTo(CREDENTIALS));
    }

    @Ignore  // enable if users need to work with MyEtherWallet
    @Test
    public void testLoadCredentialsMyEtherWallet() throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(
                PASSWORD,
                new File(WalletUtilsTest.class.getResource(
                        "/keyfiles/"
                        + "UTC--2016-11-03T07-47-45."
                        + "988Z--4f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818").getFile()));

        assertThat(credentials, equalTo(
                Credentials.create(
                        "6ca4203d715e693279d6cd9742ad2fb7a3f6f4abe27a64da92e0a70ae5d859c9")));
    }

    @Test
    public void testGetDefaultKeyDirectory() {
        assertTrue(WalletUtils.getDefaultKeyDirectory("Mac OS X")
                .endsWith(String.format("%sLibrary%sEthereum", File.separator, File.separator)));
        assertTrue(WalletUtils.getDefaultKeyDirectory("Windows")
                .endsWith(String.format("%sEthereum", File.separator)));
        assertTrue(WalletUtils.getDefaultKeyDirectory("Linux")
                .endsWith(String.format("%s.ethereum", File.separator)));
    }

    @Test
    public void testGetTestnetKeyDirectory() {
        assertTrue(WalletUtils.getMainnetKeyDirectory()
                .endsWith(String.format("%skeystore", File.separator)));
        assertTrue(WalletUtils.getTestnetKeyDirectory()
                .endsWith(String.format("%stestnet%skeystore", File.separator, File.separator)));
        assertTrue(WalletUtils.getRinkebyKeyDirectory()
                .endsWith(String.format("%srinkeby%skeystore", File.separator, File.separator)));
        
    }
 
    
    private static File createTempDir() throws Exception {
        return Files.createTempDirectory(
                WalletUtilsTest.class.getSimpleName() + "-testkeys").toFile();
    }

    @Test
    public void testIsValidPrivateKey() {
        assertTrue(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING));
        assertTrue(isValidPrivateKey(Numeric.prependHexPrefix(SampleKeys.PRIVATE_KEY_STRING)));

        assertFalse(isValidPrivateKey(""));
        assertFalse(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING + "a"));
        assertFalse(isValidPrivateKey(SampleKeys.PRIVATE_KEY_STRING.substring(1)));
    }

    @Test
    public void testIsValidAddress() {
        assertTrue(isValidAddress(BECH32_ADDRESS));

        assertFalse(isValidAddress(""));
        assertFalse(isValidAddress(BECH32_ADDRESS + 'a'));
        assertFalse(isValidAddress(BECH32_ADDRESS.substring(1)));
    }

    @Test
    public void testLoadCredentialsCurrentFormat() throws Exception {
        Credentials e = WalletUtils.loadCredentials("123456", new File("D:\\swap\\eth.json"));
        System.out.println("eth wallet address: " + e.getAddress());


        Credentials c = WalletUtils.loadCredentials("123456", new File("D:\\swap\\current.json"));
        System.out.println("current wallet address: " + c.getAddress());

        Credentials n = WalletUtils.loadCredentials("123456", new File("D:\\swap\\new.json"));
        System.out.println("new version wallet address: " + n.getAddress());


    }

    @Test
    public void testExp() throws IOException {
        String regex = "\\\"address\\\"\\s*:\\s*\\{\\s*\\\"mainnet\\\"\\s*:\\s*\\\"(\\S*)\\\"[^}]*\\}";
        Pattern main_test_ADDRESS_PATTERN = Pattern.compile(regex);

        String fileContent = "{\n" +
                "\t\"version\": 3,\n" +
                "\t\"id\": \"a1741eec-3252-4e0e-b0cf-d506631be3e3\",\n" +
                "\t\"crypto\": {\n" +
                "\t\t\"ciphertext\": \"c73370daf300641796e4d5faff7f16a0aeaf1cad97ef1a4b3ac2eaa3e92fc0de\",\n" +
                "\t\t\"cipherparams\": {\n" +
                "\t\t\t\"iv\": \"6b7c2ea412fe341a768b105a84c54aba\"\n" +
                "\t\t},\n" +
                "\t\t\"kdf\": \"scrypt\",\n" +
                "\t\t\"kdfparams\": {\n" +
                "\t\t\t\"r\": 8,\n" +
                "\t\t\t\"p\": 1,\n" +
                "\t\t\t\"n\": 16384,\n" +
                "\t\t\t\"dklen\": 32,\n" +
                "\t\t\t\"salt\": \"c5b8ffbca4a3f107f55fbbe277ad7a24cb978514d6b45f257f026e78f5f560d9\"\n" +
                "\t\t},\n" +
                "\t\t\"mac\": \"091a8f80f552e7284110c9b6b3dba324d49a434890bad427dcb2bcc96a964216\",\n" +
                "\t\t\"cipher\": \"aes-128-ctr\"\n" +
                "\t},\n" +
                "\t\"address\": {\n" +
                "\t\t\"mainnet\": \"atp1ttx0um8capdsrkfy45yka9f8d39sfqqrklwg98\",\n" +
                "\t\t\"testnet\": \"atx1ttx0um8capdsrkfy45yka9f8d39sfqqruejzkd\"\n" +
                "\t}\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();
        Matcher matcher = main_test_ADDRESS_PATTERN.matcher(fileContent);
        if(matcher.find()){
            fileContent = fileContent.replaceAll(regex, "\"address\": \"$1\"");
        }
        System.out.println(fileContent);
        WalletFile walletFile = objectMapper.readValue(fileContent, WalletFile.class);
        System.out.println(walletFile);

    }

    @Test
    public void testLoadWalletFile() throws IOException {
        File file = new File("src/test/resources/eth.json");
        WalletFile walletFile = WalletUtils.loadWalletFile(file);
        System.out.println(walletFile.getAddress() + " : " + walletFile.getCrypto().getCiphertext());

        file = new File("src/test/resources/main_test.json");
        walletFile = WalletUtils.loadWalletFile(file);
        System.out.println(walletFile.getAddress() + " : " + walletFile.getCrypto().getCiphertext());

        file = new File("src/test/resources/new.json");
        walletFile = WalletUtils.loadWalletFile(file);
        System.out.println(walletFile.getAddress() + " : " + walletFile.getCrypto().getCiphertext());
    }

}
