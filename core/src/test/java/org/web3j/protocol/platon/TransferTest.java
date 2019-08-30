package org.web3j.protocol.platon;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonGetBalance;
import org.web3j.protocol.core.methods.response.PlatonGetTransactionCount;
import org.web3j.protocol.core.methods.response.PlatonGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TransferTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));
    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xc783df0e98baf34f2ed791f6087be8e3f55fe9c4e4687e0ddc30a37abc15b287");

    }

    @Test
    public void transfer() {

        String fromAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b")));
        String toAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79")));
//        String toAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("0ba4d3bbca0f664fa5230fe6e980927b093dd68892dc55283266962ffe8c03af")));
////
        String hash = sendTransaction("a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b", toAddress, new BigDecimal("6000000000000000000000000"), 50000000000000L, 210000L);
//
        try {
            PlatonGetTransactionReceipt platonGetTransactionReceipt =  web3j.platonGetTransactionReceipt(hash).send();
            PlatonGetBalance platonGetBalance = web3j.platonGetBalance("0x" + toAddress, DefaultBlockParameterName.LATEST).send();
            PlatonGetBalance platonGetBalance2 = web3j.platonGetBalance("0x" + fromAddress, DefaultBlockParameterName.LATEST).send();
//
            System.out.println(platonGetBalance.getBalance());
            System.out.println(platonGetBalance2.getBalance());
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        try {
//            PlatonBlock platonBlock = web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
//            System.out.println(platonBlock.getBlock().getNumberRaw());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public String sendTransaction(String privateKey, String toAddress, BigDecimal amount, long gasPrice, long gasLimit) {

        BigInteger GAS_PRICE = BigInteger.valueOf(gasPrice);
        BigInteger GAS_LIMIT = BigInteger.valueOf(gasLimit);

        Credentials credentials = Credentials.create(privateKey);

        try {

            List<RlpType> result = new ArrayList<>();
            result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Int64(0)))));
            String txType = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));

            RawTransaction rawTransaction = RawTransaction.createTransaction(getNonce(), GAS_PRICE, GAS_LIMIT, toAddress, amount.toBigInteger(),
                    txType);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, new Byte("100"), credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            PlatonSendTransaction transaction = web3j.platonSendRawTransaction(hexValue).send();

            return transaction.getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected BigInteger getNonce() throws IOException {
        PlatonGetTransactionCount ethGetTransactionCount = web3j.platonGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        if (ethGetTransactionCount.getTransactionCount().intValue() == 0) {
            ethGetTransactionCount = web3j.platonGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        }

        return ethGetTransactionCount.getTransactionCount().add(BigInteger.TEN);
    }
}
