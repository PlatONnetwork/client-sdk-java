package com.platon.contracts.ppos;

import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.RestrictingPlan;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.resp.RestrictingItem;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.tx.Transfer;
import com.platon.utils.Convert;
import com.platon.utils.Convert.Unit;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

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
		TransactionResponse createRestrictingPlanResponse = createRestrictingPlan();
		assertTrue(createRestrictingPlanResponse.toString(),createRestrictingPlanResponse.isStatusOk());

		//获取锁仓信息(4100)
		CallResponse<RestrictingItem> getRestrictingPlanInfoResponse = getRestrictingPlanInfo();
		assertTrue(getRestrictingPlanInfoResponse.toString(),getRestrictingPlanInfoResponse.isStatusOk());
	}


	public CallResponse<RestrictingItem> getRestrictingPlanInfo() throws Exception {
		CallResponse<RestrictingItem> baseResponse = restrictingPlanContract.getRestrictingInfo(restrictingRecvCredentials.getAddress()).send();
		return baseResponse;
	}


    public TransactionResponse createRestrictingPlan() throws Exception {
        List<RestrictingPlan> restrictingPlans = new ArrayList<>();
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(100), new BigInteger("100000000000000000000")));
        restrictingPlans.add(new RestrictingPlan(BigInteger.valueOf(200), new BigInteger("200000000000000000000")));
        PlatonSendTransaction platonSendTransaction = restrictingPlanContract.createRestrictingPlanReturnTransaction(restrictingRecvCredentials.getAddress(), restrictingPlans).send();
        TransactionResponse baseResponse = restrictingPlanContract.getTransactionResponse(platonSendTransaction).send();
        return baseResponse;
    }

	public void transfer() throws Exception {
		Transfer.sendFunds(web3j, superCredentials, restrictingSendCredentials.getAddress(), transferValue, Unit.KPVON).send();
	}
}
