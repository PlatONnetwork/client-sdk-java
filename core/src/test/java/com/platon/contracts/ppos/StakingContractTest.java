package com.platon.contracts.ppos;

import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.contracts.ppos.dto.req.StakingParam;
import com.platon.contracts.ppos.dto.req.UpdateStakingParam;
import com.platon.contracts.ppos.dto.resp.Node;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.http.HttpService;
import com.platon.tx.Transfer;
import com.platon.utils.Convert;
import com.platon.utils.Convert.Unit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

public class StakingContractTest {

    private String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
    String blsPubKey = "5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811";
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.148:6789"));
    long chainId = 2022041902;
    String hrp = "lat";

    private Credentials superCredentials;
    private Credentials stakingCredentials;
    private Credentials benefitCredentials;
    private StakingContract stakingContract;

    @Before
    public void init() throws Exception {
        NetworkParameters.init(chainId, hrp);

    	superCredentials = Credentials.create("0x3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668");
    	System.out.println("superCredentials balance="+ web3j.platonGetBalance(superCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());

    	stakingCredentials = Credentials.create("0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
    	System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(stakingCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());

    	benefitCredentials = Credentials.create("0x3581985348bffd03b286b37712165f7addf3a8d907b25efc44addf54117e9b91");
    	System.out.println("benefitCredentials balance="+ web3j.platonGetBalance(benefitCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());

        stakingContract = StakingContract.load(web3j, stakingCredentials);
    }

    @Test
    public void transfer() throws Exception {
    	Transfer.sendFunds(web3j, superCredentials, stakingCredentials.getAddress(), new BigDecimal("10000000"), Unit.KPVON).send();
    	System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(stakingCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());
    }

    @Test
    public void staking() {
        try {
        	StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
        	String benefitAddress = benefitCredentials.getAddress();
        	String externalId = "";
            String nodeName = "node5";
            String webSite = "www.platon.network";
            String details = "node5-details";
            BigDecimal stakingAmount = Convert.toVon("5000000", Unit.KPVON);
            BigInteger rewardPer = BigInteger.valueOf(1000L);

            PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
                    .setNodeId(nodeId)
                    .setAmount(stakingAmount.toBigInteger())
                    .setStakingAmountType(stakingAmountType)
                    .setBenifitAddress(benefitAddress)
                    .setExternalId(externalId)
                    .setNodeName(nodeName)
                    .setWebSite(webSite)
                    .setDetails(details)
                    .setBlsPubKey(blsPubKey)
                    .setProcessVersion(web3j.getProgramVersion().send().getAdminProgramVersion())
                    .setBlsProof(web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve())
                    .setRewardPer(rewardPer)
                    .build()).send();

            TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse.toString());  //14431
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getStakingInfo() {
    	try {
    		CallResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
    		System.out.println(baseResponse);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Test
    public void getPackageReward() {
    	try {
    		CallResponse<BigInteger> baseResponse = stakingContract.getPackageReward().send();
    		System.out.println(baseResponse);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Test
    public void getStakingReward() {
    	try {
    		CallResponse<BigInteger> baseResponse = stakingContract.getStakingReward().send();
    		System.out.println(baseResponse);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Test
    public void getAvgPackTime() {
    	try {
    		CallResponse<BigInteger> baseResponse = stakingContract.getAvgPackTime().send();
    		System.out.println(baseResponse);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Test
    public void benefitAddress() {
        try {
        	String benefitAddress = benefitCredentials.getAddress();
        	String externalId = "";
            String nodeName = "node5-u";
            String webSite = "www.platon.network";
            String details = "node5-details-u";
            BigInteger rewardPer = BigInteger.valueOf(2000L);

            PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
            		.setBenifitAddress(benefitAddress)
            		.setExternalId(externalId)
            		.setNodeId(nodeId)
            		.setNodeName(nodeName)
            		.setWebSite(webSite)
            		.setDetails(details)
                    .setRewardPer(rewardPer)
            		.build()).send();

            TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addStaking() {
        try {
        	StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
            BigDecimal addStakingAmount = Convert.toVon("4000000", Unit.KPVON).add(new BigDecimal("999999999999999998"));

            PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, stakingAmountType, addStakingAmount.toBigInteger()).send();
            TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unStaking() {
        try {
            PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
            TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
