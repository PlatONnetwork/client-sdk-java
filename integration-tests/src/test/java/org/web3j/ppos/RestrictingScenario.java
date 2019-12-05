package org.web3j.ppos;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.web3j.Scenario;
import org.web3j.platon.bean.RestrictingItem;
import org.web3j.platon.bean.RestrictingPlan;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.platon.sdk.contracts.inner.dto.BaseResponse;

public class RestrictingScenario extends Scenario {
	
	private BigDecimal transferValue = new BigDecimal("10000000");
	
	/**
	 *  正常的场景:
	 *  初始化账户余额
	 *  创建锁仓计划(4000)
	 *  获取锁仓信息(4100)
	 */
	@Test
	public void executeScenario() throws Exception {
		//初始化账户余额
		transfer();
		BigInteger restrictingBalance = web3j.platonGetBalance(restrictingSendCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		assertTrue(new BigDecimal(restrictingBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		
		
		//创建锁仓计划(4000)
		BaseResponse createRestrictingPlanResponse = createRestrictingPlan();
		assertTrue(createRestrictingPlanResponse.toString(),createRestrictingPlanResponse.isStatusOk());

		//获取锁仓信息(4100)
		BaseResponse<RestrictingItem> getRestrictingPlanInfoResponse = getRestrictingPlanInfo();
		assertTrue(getRestrictingPlanInfoResponse.toString(),getRestrictingPlanInfoResponse.isStatusOk());
	}
    
	    
	public BaseResponse<RestrictingItem> getRestrictingPlanInfo() throws Exception {
	    BaseResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(restrictingRecvCredentials.getAddress()).send();
		return baseResponse;
	}


    public BaseResponse createRestrictingPlan() throws Exception {   
        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("100000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000000")));
        PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(restrictingRecvCredentials.getAddress(), restrictingPlans).send();
        BaseResponse baseResponse = restrictingPlanContract.getCreateRestrictingPlanResult(platonSendTransaction).send();
        return baseResponse;
    }

	public void transfer() throws Exception {
		Transfer.sendFunds(web3j, superCredentials, chainId, restrictingSendCredentials.getAddress(), transferValue, Unit.LAT).send();
	}	
}
