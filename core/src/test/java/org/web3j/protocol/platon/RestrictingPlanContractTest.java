package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.RestrictingItem;
import org.web3j.platon.bean.RestrictingPlan;
import org.web3j.platon.contracts.RestrictingPlanContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RestrictingPlanContractTest {


    private Web3j web3j = Web3j.build(new HttpService("http://10.10.8.157:6789"));

    private String address = "0x493301712671Ada506ba6Ca7891F436D29185821";
    private String benifitAddress = "0x12c171900f010b17e969702efa044d077e868082";

    private RestrictingPlanContract restrictingPlanContract;

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xa11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7");

//        BigInteger gasPrice = BigInteger.valueOf(1_000_000_000L);
//        BigInteger deployGasLimit = BigInteger.valueOf(250_000_000L);
//        BigInteger invokeGasLimit = BigInteger.valueOf(2_000_000L);
//        BigInteger.valueOf(3355440),BigInteger.valueOf(3355440),BigInteger.valueOf(3355440)

        restrictingPlanContract = RestrictingPlanContract.load(
                web3j,
                credentials,
                new DefaultWasmGasProvider(BigInteger.valueOf(3355440),BigInteger.valueOf(3355440),BigInteger.valueOf(3355440)), "102");
    }

    @Test
    public void createRestrictingPlan() {

        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(5), new BigInteger("1000000000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(6), new BigInteger("1000000000000000000000000")));

        try {
            BaseResponse baseResponse = restrictingPlanContract.createRestrictingPlan(benifitAddress, restrictingPlans).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRestrictingPlanInfo(){
        try {
            BaseResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(benifitAddress).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
