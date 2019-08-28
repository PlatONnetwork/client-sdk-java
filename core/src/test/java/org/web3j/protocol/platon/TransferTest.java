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

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.112.120:8222"));
    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xc783df0e98baf34f2ed791f6087be8e3f55fe9c4e4687e0ddc30a37abc15b287");

    }

    @Test
    public void transfer() {

        String fromAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("c783df0e98baf34f2ed791f6087be8e3f55fe9c4e4687e0ddc30a37abc15b287")));
        String toAddress = Keys.getAddress(ECKeyPair.create(Numeric.toBigIntNoPrefix("59a9fac3bc8024169df74e6c0c861e1a5fdbe620b8a7a0c1dd0539d02c4e6add")));
////
        String hash = sendTransaction("c783df0e98baf34f2ed791f6087be8e3f55fe9c4e4687e0ddc30a37abc15b287", toAddress, new BigDecimal("6000000000000000000000000"), 5000000000000L, 210000L);
//
        try {
            PlatonGetBalance platonGetBalance = web3j.platonGetBalance("0x" + toAddress, DefaultBlockParameterName.LATEST).send();
            PlatonGetBalance platonGetBalance2 = web3j.platonGetBalance("0x" + fromAddress, DefaultBlockParameterName.LATEST).send();
//
            System.out.println(platonGetBalance.getBalance().longValue());
            System.out.println(platonGetBalance2.getBalance().longValue());
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

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, new Byte("102"), credentials);
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
