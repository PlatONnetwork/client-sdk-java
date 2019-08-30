package org.web3j.protocol.platon;

import org.junit.Before;
import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.bean.RestrictingItem;
import org.web3j.platon.bean.RestrictingPlan;
import org.web3j.platon.contracts.RestrictingPlanContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 锁仓计划接口，包括，
 * 创建锁仓计划
 * 获取锁仓信息
 */
public class RestrictingPlanContractTest {

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.76:6794"));

    private String benifitAddress = "0x493301712671Ada586ba6Ca7891F436D29185889";

    private RestrictingPlanContract restrictingPlanContract;

    private Credentials credentials;

    @Before
    public void init() {

        credentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");

        restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials, "100");
    }

    /**
     * 创建锁仓计划
     * account 锁仓释放到账账户
     * plan plan 为 RestrictingPlan 类型的列表（数组），RestrictingPlan 定义如下：type RestrictingPlan struct {     Epoch uint64    Amount：*big.Int}其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。Epoch * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     */
    @Test
    public void createRestrictingPlan() {

        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("1000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000")));
        try {
            PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(benifitAddress, restrictingPlans).send();
            BaseResponse baseResponse = restrictingPlanContract.getCreateRestrictingPlanResult(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取锁仓信息。
     * account 锁仓释放到账账户
     */
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
