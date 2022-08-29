package com.platon.contracts.ppos;

import com.platon.contracts.ppos.dto.BaseResponse;
import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.RestrictingPlan;
import com.platon.contracts.ppos.dto.resp.RestrictingItem;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.http.HttpService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 锁仓计划接口，包括，
 * 创建锁仓计划
 * 获取锁仓信息
 */
public class RestrictingPlanContractTest {

    private RestrictingPlanContract restrictingPlanContract;

    private Credentials credentials;
    private Credentials deleteCredentials;

    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.148:6789"));
    long chainId = 2022041902;
    String hrp = "lat";

    @Before
    public void init() {
        NetworkParameters.init(chainId, hrp);

        credentials = Credentials.create("0x3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668");
        deleteCredentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");
        restrictingPlanContract = RestrictingPlanContract.load(web3j, credentials);
    }

    /**
     * 创建锁仓计划
     * account 锁仓释放到账账户
     * plan plan 为 RestrictingPlan 类型的列表（数组），RestrictingPlan 定义如下：type RestrictingPlan struct {     Epoch uint64    Amount：*big.Int}其中，Epoch：表示结算周期的倍数。与每个结算周期出块数的乘积表示在目标区块高度上释放锁定的资金。Epoch * 每周期的区块数至少要大于最高不可逆区块高度。Amount：表示目标区块上待释放的金额。
     */
    @Test
    public void createRestrictingPlan() {

        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(1000), new BigInteger("1000000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(2000), new BigInteger("1000000000000000000000")));
        try {
            PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(deleteCredentials.getAddress(), restrictingPlans).send();
            BaseResponse baseResponse = restrictingPlanContract.getTransactionResponse(platonSendTransaction).send();
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
            CallResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(deleteCredentials.getAddress()).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
