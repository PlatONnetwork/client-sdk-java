package com.platon.crypto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platon.bech32.Bech32;
import com.platon.utils.Files;
import com.platon.utils.Numeric;
import org.bouncycastle.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.platon.crypto.Hash.sha256;
import static com.platon.crypto.Keys.ADDRESS_LENGTH_IN_HEX;
import static com.platon.crypto.Keys.PRIVATE_KEY_LENGTH_IN_HEX;

/**
 * Utility functions for working with Wallet files.
 */
public class WalletUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String generateFullNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {

        return generateNewWalletFile(password, destinationDirectory, true);
    }

    public static String generateLightNewWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {

        return generateNewWalletFile(password, destinationDirectory, false);
    }

    /**
     * create a platON standard wallet
     *
     * @param password pwd
     * @param destinationDirectory dest dir
     * @return file
     * @throws NoSuchAlgorithmException e
     * @throws NoSuchProviderException e
     * @throws InvalidAlgorithmParameterException e
     * @throws CipherException e
     * @throws IOException error
     */
    public static String generatePlatONWalletFile(String password, File destinationDirectory)
            throws NoSuchAlgorithmException, NoSuchProviderException,
            InvalidAlgorithmParameterException, CipherException, IOException {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();

        String fileName  = generatePlatONWalletFile(password, ecKeyPair, destinationDirectory);
        return fileName;
    }

    /**
     * create a platON standard Bip39 wallet
     *
     * @param password
     * @param destinationDirectory
     * @return
     * @throws CipherException
     * @throws IOException
     */
    public static Bip39Wallet generatePlatONBip39Wallet(String password, File destinationDirectory)
            throws CipherException, IOException {
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
        ECKeyPair ecKeyPair = ECKeyPair.create(sha256(seed));

        String fileName  = generatePlatONWalletFile(password, ecKeyPair, destinationDirectory);

        return new Bip39Wallet(fileName, mnemonic);
    }

    /**
     * Create platON standard wallet with ecKeyPair
     *
     * @param password
     * @param ecKeyPair
     * @param destinationDirectory
     * @return
     * @throws CipherException
     * @throws IOException
     */
    public static String generatePlatONWalletFile(String password, ECKeyPair ecKeyPair, File destinationDirectory)
            throws CipherException, IOException {

        WalletFile walletFile = Wallet.createPlatON(password,ecKeyPair);

        String fileName = getWalletFileName(walletFile);
        File destination = new File(destinationDirectory, fileName);

        objectMapper.writeValue(destination, walletFile);

        return fileName;
    }

    public static String generateNewWalletFile(String password, File destinationDirectory)
            throws CipherException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException, IOException {
        return generateFullNewWalletFile(password, destinationDirectory);
    }

    public static String generateNewWalletFile(
            String password, File destinationDirectory, boolean useFullScrypt)
            throws CipherException, IOException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException, NoSuchProviderException {

        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        return generateWalletFile(password, ecKeyPair, destinationDirectory, useFullScrypt);
    }

    public static String generateWalletFile(
            String password, ECKeyPair ecKeyPair, File destinationDirectory, boolean useFullScrypt)
            throws CipherException, IOException {

        WalletFile walletFile;
        if (useFullScrypt) {
            walletFile = Wallet.createStandard(password, ecKeyPair);
        } else {
            walletFile = Wallet.createLight(password, ecKeyPair);
        }

        String fileName = getWalletFileName(walletFile);
        File destination = new File(destinationDirectory, fileName);

        objectMapper.writeValue(destination, walletFile);

        return fileName;
    }

    /**
     * Generates a BIP-39 compatible Ethereum wallet. The private key for the wallet can
     * be calculated using following algorithm:
     * <pre>
     *     Key = SHA-256(BIP_39_SEED(mnemonic, password))
     * </pre>
     *
     * @param password Will be used for both wallet encryption and passphrase for BIP-39 seed
     * @param destinationDirectory The directory containing the wallet
     * @return A BIP-39 compatible Ethereum wallet
     * @throws CipherException if the underlying cipher is not available
     * @throws IOException if the destination cannot be written to
     */
    public static Bip39Wallet generateBip39Wallet(String password, File destinationDirectory)
            throws CipherException, IOException {
        byte[] initialEntropy = new byte[16];
        secureRandom.nextBytes(initialEntropy);

        String mnemonic = MnemonicUtils.generateMnemonic(initialEntropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
        ECKeyPair privateKey = ECKeyPair.create(sha256(seed));

        String walletFile = generateWalletFile(password, privateKey, destinationDirectory, false);

        return new Bip39Wallet(walletFile, mnemonic);
    }

    public static Credentials loadCredentials(String password, String source)
            throws IOException, CipherException {
        return loadCredentials(password, new File(source));
    }

    //private static final Pattern OLD_ADDRESS_PATTERN = Pattern.compile(".*address\":[\\s]*\".*");
    public static Credentials loadCredentials(String password, File source) throws IOException, CipherException {
        WalletFile walletFile = loadWalletFile(source);
        Credentials credentials = Credentials.create(Wallet.decrypt(password, loadWalletFile(source)));
        if (!walletFile.getAddress().equalsIgnoreCase(credentials.getAddress())){
            throw new CipherException("wallet file's content is cracked.");
        }
        return credentials;
    }

    private static final String MAIN_TEST_ADDRESS_REGEX = "\\\"address\\\"\\s*:\\s*\\{\\s*\\\"mainnet\\\"\\s*:\\s*\\\"([A-Za-z0-9]+)\\\"[^}]*\\}";
    public static WalletFile loadWalletFile(File source) throws IOException{
        // 统一把source文件中的address值替换为“{}”，兼容新旧格式钱包文件的加载
        String fileContent = Files.readString(source);
        fileContent = fileContent.replaceAll(MAIN_TEST_ADDRESS_REGEX, "\"address\": \"$1\"");
        WalletFile walletFile = objectMapper.readValue(fileContent, WalletFile.class);

        //eth钱包文件中的地址，是0x开头的，转成Bech32格式
        /*if(Numeric.containsHexPrefix(walletFile.getAddress())){
            walletFile.setAddress(Bech32.addressEncode(NetworkParameters.getHrp(), walletFile.getAddress()));
        }else{
            walletFile.setAddress(Bech32.changeHrp(walletFile.getAddress(), NetworkParameters.getHrp()));
        }*/
        walletFile.setAddress(Bech32.convertToUnifiedAddress(walletFile.getAddress()));
        return walletFile;
    }

    public static Credentials loadBip39Credentials(String password, String mnemonic) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, password);
        return Credentials.create(ECKeyPair.create(sha256(seed)));
    }

    private static String getWalletFileName(WalletFile walletFile) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(
                "'UTC--'yyyy-MM-dd'T'HH-mm-ss.nVV'--'");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        return now.format(format) + walletFile.getAddress() + ".json";
    }

    public static String getDefaultKeyDirectory() {
        return getDefaultKeyDirectory(System.getProperty("os.name"));
    }

    static String getDefaultKeyDirectory(String osName1) {
        String osName = osName1.toLowerCase();

        if (osName.startsWith("mac")) {
            return String.format(
                    "%s%sLibrary%sEthereum", System.getProperty("user.home"), File.separator,
                    File.separator);
        } else if (osName.startsWith("win")) {
            return String.format("%s%sEthereum", System.getenv("APPDATA"), File.separator);
        } else {
            return String.format("%s%s.ethereum", System.getProperty("user.home"), File.separator);
        }
    }

    public static String getTestnetKeyDirectory() {
        return String.format(
                "%s%stestnet%skeystore", getDefaultKeyDirectory(), File.separator, File.separator);
    }

    public static String getMainnetKeyDirectory() {
        return String.format("%s%skeystore", getDefaultKeyDirectory(), File.separator);
    }
    
    /**
     * Get keystore destination directory for a Rinkeby network.
     * @return a String containing destination directory
     */
    public static String getRinkebyKeyDirectory() {
        return String.format(
                "%s%srinkeby%skeystore", getDefaultKeyDirectory(), File.separator, File.separator);
    }

    public static boolean isValidPrivateKey(String privateKey) {
        String cleanPrivateKey = Numeric.cleanHexPrefix(privateKey);
        return cleanPrivateKey.length() == PRIVATE_KEY_LENGTH_IN_HEX;
    }

    public static boolean isValidAddress(String input) {
        //exclude blank characters and uppercase letters
        Pattern pattern = Pattern.compile("^[a-z0-9]+$");
        Matcher matcher = pattern.matcher(input);
        if(!matcher.find()){
            return false;
        }
        String cleanInput;
        try{
            byte [] bytes = Bech32.addressDecode(input);
            String hexAddress = Hex.toHexString(bytes);
            cleanInput = Numeric.cleanHexPrefix(hexAddress);
        }catch (Exception e){
            return false;
        }

        try {
            Numeric.toBigIntNoPrefix(cleanInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return cleanInput.length() == ADDRESS_LENGTH_IN_HEX;
    }
}
