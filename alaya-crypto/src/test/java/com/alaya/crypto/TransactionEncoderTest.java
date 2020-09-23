package com.alaya.crypto;

import java.math.BigInteger;
import java.util.List;

import com.alaya.parameters.NetworkParameters;
import org.junit.Test;

import com.alaya.rlp.solidity.RlpString;
import com.alaya.rlp.solidity.RlpType;
import com.alaya.utils.Numeric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class TransactionEncoderTest {

    @Test
    public void testEtherTransactionAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(createEtherTransaction(),
                new Sign.SignatureData((byte) 0, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(9));
        assertThat(rlpStrings.get(3), equalTo(RlpString.create(new BigInteger("33c98f20dd73d7bb1d533c4aa3371f2b30c6ebde", 16))));
    }

    @Test
    public void testContractAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(
                createContractTransaction(), null);
        assertThat(rlpStrings.size(), is(6));
        assertThat(rlpStrings.get(3), is(RlpString.create("")));
    }

    @Test
    public void testEip155Encode() {
        assertThat(TransactionEncoder.encode(createEip155RawTransaction(), NetworkParameters.MainNetParams.getChainId()),
                is(Numeric.hexStringToByteArray("0xef098504a817c8008252089433c98f20dd73d7bb1d533c4aa3371f2b30c6ebde880de0b6b3a7640000808303113a8080")));
    }

    @Test
    public void testEip155Transaction() {
        // https://github.com/ethereum/EIPs/issues/155
        Credentials credentials = Credentials.create("0x4646464646464646464646464646464646464646464646464646464646464646");
        assertThat(TransactionEncoder.signMessage(createEip155RawTransaction(),  NetworkParameters.MainNetParams.getChainId(), credentials),
                is(Numeric.hexStringToByteArray("0xf86f098504a817c8008252089433c98f20dd73d7bb1d533c4aa3371f2b30c6ebde880de0b6b3a76400008083062297a0045a8bbf804cc2c6a59f5f6acb544bc60efee31648581d00ebe58dfad67bb10aa01653665e957167922c95385d64590c1c56c7d225ef2ee214add692103a659514")));
    }

    private static RawTransaction createEtherTransaction() {
        return RawTransaction.createEtherTransaction(
                BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, "atp1x0yc7gxaw0tmk82n8392xdcl9vcvd677g57j0q",
                BigInteger.valueOf(Long.MAX_VALUE));
    }

    static RawTransaction createContractTransaction() {
        return RawTransaction.createContractTransaction(
                BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, BigInteger.valueOf(Long.MAX_VALUE),
                "01234566789");
    }

    private static RawTransaction createEip155RawTransaction() {
        return RawTransaction.createEtherTransaction(
                BigInteger.valueOf(9), BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000), "atp1x0yc7gxaw0tmk82n8392xdcl9vcvd677g57j0q",
                BigInteger.valueOf(1000000000000000000L));
    }
}
