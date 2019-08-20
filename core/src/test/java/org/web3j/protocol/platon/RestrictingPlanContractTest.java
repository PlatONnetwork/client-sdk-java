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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RestrictingPlanContractTest {


    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private String address = "0x493301712671Ada506ba6Ca7891F436D29185821";
    private String benifitAddress = "0x12c171900f010b17e969702efa044d077e868082";

    private RestrictingPlanContract restrictingPlanContract;

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0xe1eb63c6f8d4d2b131b12ea4d06dd690c719afbe703bf9c152346317b0794d57");

        restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials, "100");
    }

    @Test
    public void createRestrictingPlan() {

        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(5), new BigInteger("200000000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(6), new BigInteger("21000000000000000000000")));
        try {
            BaseResponse baseResponse = restrictingPlanContract.createRestrictingPlan(benifitAddress, restrictingPlans).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRestrictingPlanInfo() {
        try {
            BaseResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(benifitAddress).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
