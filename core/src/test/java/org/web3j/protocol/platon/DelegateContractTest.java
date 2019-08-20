package org.web3j.protocol.platon;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Delegation;
import org.web3j.platon.bean.DelegationIdInfo;
import org.web3j.platon.contracts.DelegateContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpDecoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.tx.gas.DefaultWasmGasProvider;
import org.web3j.utils.Numeric;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.List;

public class DelegateContractTest {

    private String nodeId = "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c";
    private String delegateAddress = "0xbfCAEc5286822434D59310E03B2F4F162A35CBDd";

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private Credentials credentials;

    private DelegateContract delegateContract;

    @Before
    public void init() {
        credentials = Credentials.create("0xce4f875efc9d21d06f8607de170d0011e79be86325bacc9639f57a437c65ce8c");

        delegateContract = DelegateContract.load(web3j,
                credentials,
                new DefaultWasmGasProvider(), "100");
    }

    @Test
    public void decode() throws UnsupportedEncodingException {

        String text = "f856838203ec8180b842b8401f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e4298b8ad3c21bcecceda1000000";

        RlpList rlpList = RlpDecoder.decode(Hex.decode(text));

        RlpList rl = (RlpList) rlpList.getValues().get(0);

        RlpString rlpType = (RlpString) rl.getValues().get(0);

        RlpString rlpTyp1 = (RlpString) rl.getValues().get(1);
        RlpString rlpTyp2 = (RlpString) rl.getValues().get(2);
        RlpString rlpTyp3 = (RlpString) rl.getValues().get(3);

        RlpList rlps = RlpDecoder.decode(rlpType.getBytes());
        BigInteger bigInteger = new BigInteger(((RlpString) rlps.getValues().get(0)).getBytes());

        RlpList rlps1 = RlpDecoder.decode(rlpTyp1.getBytes());
        BigInteger bigInteger1 = new BigInteger(1, ((RlpString) rlps1.getValues().get(0)).getBytes());

        RlpList rlps2 = RlpDecoder.decode(rlpTyp2.getBytes());
        String nodeId = Numeric.toHexString(((RlpString) rlps2.getValues().get(0)).getBytes());

        System.out.println(nodeId);

    }

    @Test
    public void delegate() {

        try {
            BaseResponse baseResponse = delegateContract.delegate(nodeId, StakingAmountType.RESTRICTING_AMOUNT_TYPE, new BigInteger("1000000000000000000000000")).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unDelegate() {

        try {
            BaseResponse baseResponse = delegateContract.unDelegate(nodeId, BigInteger.valueOf(2360), new BigInteger("1000000000000000000000000")).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDelegateInfo() {

        try {
            BaseResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, delegateAddress, BigInteger.valueOf(1038)).send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRelatedListByDelAddr() {
        try {
            BaseResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateAddress).send();
            DelegationIdInfo delegationIdInfo = baseResponse.data.get(0);
            System.out.println(delegationIdInfo.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
