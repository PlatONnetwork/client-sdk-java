package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Delegation;
import org.web3j.platon.bean.DelegationIdInfo;
import org.web3j.platon.contracts.DelegateContract;
import org.web3j.platon.contracts.StakingContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import java.math.BigInteger;
import java.util.List;

public class DelegateContractTest {

    private String nodeId = "1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429";
    private String delegateAddress = "0x493301712671Ada506ba6Ca7891F436D29185821";

    private Web3j web3j = Web3j.build(new HttpService("http://10.10.8.56:6789"));

    private Credentials credentials;

    private DelegateContract delegateContract;

    @Before
    public void init() {

        credentials = Credentials.create("0xa11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7");

        delegateContract = DelegateContract.load(web3j,
                credentials,
                new DefaultWasmGasProvider(), "100");
    }

    @Test
    public void delegate() {

        try {
            BaseResponse baseResponse = delegateContract.delegate(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, new BigInteger("1000000000000000000000000")).send();
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
            BaseResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, delegateAddress, BigInteger.valueOf(2360)).send();
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
