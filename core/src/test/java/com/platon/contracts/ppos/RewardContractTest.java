package com.platon.contracts.ppos;

import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.resp.Reward;
import com.platon.crypto.Credentials;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.http.HttpService;
import com.platon.tx.Transfer;
import com.platon.utils.Convert.Unit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 委托相关接口，包括，
 * 发起委托
 * 减持/撤销委托
 * 查询当前单个委托信息
 */
public class RewardContractTest {

    private String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";

    private long chainId = 103;
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.145:6789"));

    private Credentials deleteCredentials;
    private Credentials superCredentials;
    private RewardContract rewardContract;

    @Before
    public void init() {
        superCredentials = Credentials.create("0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
        deleteCredentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");
        rewardContract = RewardContract.load(web3j, deleteCredentials);
    }

    @Test
    public void transfer() throws Exception {
        Transfer.sendFunds(web3j, superCredentials, deleteCredentials.getAddress(), new BigDecimal("10000000"), Unit.KPVON).send();
        System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(deleteCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());
    }

    @Test
    public void withdrawDelegateReward() {
        try {
            PlatonSendTransaction platonSendTransaction = rewardContract.withdrawDelegateRewardReturnTransaction().send();
            TransactionResponse baseResponse = rewardContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse);

            if(baseResponse.isStatusOk()){
                List<Reward> rewardList = rewardContract.decodeWithdrawDelegateRewardLog(baseResponse.getTransactionReceipt());
                System.out.println(rewardList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDelegateReward() {
        try {
            List<String> nodeList = new ArrayList<>();
            nodeList.add(nodeId);
            CallResponse<List<Reward>> baseResponse = rewardContract.getDelegateReward(deleteCredentials.getAddress(), nodeList).send();
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
