package org.web3j.ppos;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.web3j.Scenario;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.alibaba.fastjson.JSON;

public class StakingScenario extends Scenario {
	
	private BigDecimal transferValue = new BigDecimal("10000000");

	public void transfer() throws Exception {
		Transfer.sendFunds(web3j, superCredentials, chainId, stakingCredentials.getAddress(), transferValue, Unit.LAT).send();
		Transfer.sendFunds(web3j, superCredentials, chainId, delegateCredentials.getAddress(), transferValue, Unit.LAT).send();
	}
	

	/**
	 * 1. 初始化账户余额
	 * 2. 创建质押信息
	 * 3. 修改质押信息
	 * 4. 修改质押金额
	 * 5. 对质押委托
	 * 6. 对质押解除委托
	 * 7. 退出质押
	 */
	@Test
	public void executeScenario() throws Exception {
		//1. 初始化账户余额
		transfer();
		BigInteger stakingBalance = web3j.platonGetBalance(stakingCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		BigInteger delegateBalance = web3j.platonGetBalance(delegateCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		
		assertTrue(new BigDecimal(stakingBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		assertTrue(new BigDecimal(delegateBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		
		//2. 创建质押信息
		BaseResponse stakingResponse = staking();
		assertTrue(stakingResponse.toString(),stakingResponse.status);
		BigInteger stakingBlockNum = stakingResponse.transactionReceipt.getBlockNumber();
		
		//3. 修改质押信息
		
		
		
		
		
//		
//		getCandidateList();

	}
	

    public BaseResponse staking() throws Exception {   	
    	StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
    	String benifitAddress = benefitCredentials.getAddress();
    	String externalId = "";
        String nodeName = "integration-node1";
        String webSite = "https://www.platon.network/#/";
        String details = "integration-node1-details";
        BigDecimal stakingAmount = Convert.toVon("5000000", Unit.LAT).add(BigDecimal.valueOf(1L));
    	
        PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
                .setNodeId(nodeId)
                .setAmount(stakingAmount.toBigInteger())  
                .setStakingAmountType(stakingAmountType)
                .setBenifitAddress(benifitAddress)
                .setExternalId(externalId)
                .setNodeName(nodeName)
                .setWebSite(webSite)
                .setDetails(details)
                .setBlsPubKey(blsPubKey)
                .build()).send(); 
        BaseResponse baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
        return baseResponse;
    }
    
    
	
    public void getCandidateList() {
        try {
            BaseResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
            List<Node> nodeList = baseResponse.data;
            System.out.println(JSON.toJSONString(nodeList,true));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	
	

}
