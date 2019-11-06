package org.web3j.ppos;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.web3j.Scenario;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.StakingAmountType;
import org.web3j.platon.bean.Delegation;
import org.web3j.platon.bean.DelegationIdInfo;
import org.web3j.platon.bean.Node;
import org.web3j.platon.bean.StakingParam;
import org.web3j.platon.bean.UpdateStakingParam;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

public class StakingScenario extends Scenario {
	
	private BigDecimal transferValue = new BigDecimal("10000000");
	
	/**
	 * 正常的场景:
	 * 初始化账户余额
	 * 创建质押信息(1000)
	 * 修改质押信息(1001)
	 * 修改质押金额(1002)
	 * 对质押委托(1004)
	 * 查询当前结算周期的验证人队列(1100)
	 * 查询当前共识周期的验证人列表(1101)
	 * 查询所有实时的候选人列表(1102)
	 * 查询当前账户地址所委托的节点的NodeID和质押Id(1103)
	 * 查询当前单个委托信息(1104)
	 * 查询当前节点的质押信息(1105)
	 * 对质押解除委托(1005)
	 * 退出质押(1003)
	 */
	@Test
	public void executeScenario() throws Exception {
		//初始化账户余额
		transfer();
		BigInteger stakingBalance = web3j.platonGetBalance(stakingCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		BigInteger delegateBalance = web3j.platonGetBalance(delegateCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
		
		assertTrue(new BigDecimal(stakingBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		assertTrue(new BigDecimal(delegateBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
		
		//创建质押信息(1000)
		BaseResponse createStakingResponse = staking();
		assertTrue(createStakingResponse.toString(),createStakingResponse.isStatusOk());
		BigInteger stakingBlockNum = createStakingResponse.transactionReceipt.getBlockNumber();
		
		//修改质押信息(1001)
		BaseResponse updateStakingResponse = updateStakingInfo();
		assertTrue(updateStakingResponse.toString(),updateStakingResponse.isStatusOk());
		
		//修改质押金额(1002)
		BaseResponse addStakingResponse = addStaking();
		assertTrue(addStakingResponse.toString(),addStakingResponse.isStatusOk());
		
		//对质押委托(1004)
		BaseResponse delegateResponse = delegate();
		assertTrue(delegateResponse.toString(),delegateResponse.isStatusOk());
		
		//查询当前结算周期的验证人队列(1100)
		BaseResponse<List<Node>> getVerifierListResponse = getVerifierList();
		assertTrue(getVerifierListResponse.toString(),getVerifierListResponse.isStatusOk());
		
		//查询当前共识周期的验证人列表(1101)
		BaseResponse<List<Node>> getValidatorListResponse = getValidatorList();
		assertTrue(getValidatorListResponse.toString(),getValidatorListResponse.isStatusOk());
		
		//查询所有实时的候选人列表(1102)
		BaseResponse<List<Node>> getCandidateListResponse = getCandidateList();
		assertTrue(getCandidateListResponse.toString(),getCandidateListResponse.isStatusOk());
		
		//查询当前账户地址所委托的节点的NodeID和质押Id(1103)
		BaseResponse<List<DelegationIdInfo>> getRelatedListByDelAddrResponse = getRelatedListByDelAddr();
		assertTrue(getRelatedListByDelAddrResponse.toString(),getRelatedListByDelAddrResponse.isStatusOk());
		
		//查询当前单个委托信息(1104)
		BaseResponse<Delegation> getDelegateResponse = getDelegateInfo(stakingBlockNum);
		assertTrue(getDelegateResponse.toString(),getDelegateResponse.isStatusOk());
		
		//查询当前节点的质押信息(1105)
		BaseResponse<Node> getStakingInfoResponse = getStakingInfo();
		assertTrue(getStakingInfoResponse.toString(),getStakingInfoResponse.isStatusOk());
		
		//对质押解除委托(1005)
		BaseResponse unDelegateResponse = unDelegate(stakingBlockNum);
		assertTrue(unDelegateResponse.toString(),unDelegateResponse.isStatusOk());
		
		//退出质押(1003)
		BaseResponse unStakingResponse = unStaking();
		assertTrue(unStakingResponse.toString(),unStakingResponse.isStatusOk());
	}
	
	
    public BaseResponse unStaking() throws Exception{
        PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
        BaseResponse baseResponse = stakingContract.getUnStakingResult(platonSendTransaction).send();
        return baseResponse;
    }

    public BaseResponse unDelegate(BigInteger stakingBlockNum) throws Exception  {
        BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
        PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingBlockNum, stakingAmount.toBigInteger()).send();
        BaseResponse baseResponse = delegateContract.getUnDelegateResult(platonSendTransaction).send();
        return baseResponse;
    }
	
	
    public  BaseResponse<Node> getStakingInfo() throws Exception {
    	BaseResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
    	return baseResponse;
    }
	

    public BaseResponse<Delegation> getDelegateInfo(BigInteger stakingBlockNum) throws Exception{
        BaseResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, delegateCredentials.getAddress(), stakingBlockNum).send();
        return baseResponse;
    }
    
	    
	public BaseResponse<List<DelegationIdInfo>> getRelatedListByDelAddr() throws Exception {
		BaseResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateCredentials.getAddress()).send();
		return baseResponse;
	}
    
    public BaseResponse<List<Node>> getCandidateList() throws Exception {
        BaseResponse<List<Node>> baseResponse = nodeContract.getCandidateList().send();
        return baseResponse;
    }
    
    public  BaseResponse<List<Node>> getValidatorList() throws Exception {
    	 BaseResponse<List<Node>> baseResponse = nodeContract.getValidatorList().send();
         return baseResponse;
    }
    
    public BaseResponse<List<Node>> getVerifierList() throws Exception {
    	 BaseResponse<List<Node>> baseResponse = nodeContract.getVerifierList().send();
         return baseResponse;
    }

    public BaseResponse delegate()  throws Exception  {
    	StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
        BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
    	
        PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, stakingAmountType, stakingAmount.toBigInteger()).send();
        BaseResponse baseResponse = delegateContract.getDelegateResult(platonSendTransaction).send();
        return baseResponse;
    }

    public BaseResponse addStaking() throws Exception {
    	StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
        BigDecimal addStakingAmount = Convert.toVon("4000000", Unit.LAT).add(new BigDecimal("999999999999999998"));
    	
        PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, StakingAmountType.FREE_AMOUNT_TYPE, addStakingAmount.toBigInteger()).send();
        BaseResponse baseResponse = stakingContract.getAddStakingResult(platonSendTransaction).send();
        return baseResponse;
    }

    public BaseResponse updateStakingInfo() throws Exception {
    	String benifitAddress = benefitCredentials.getAddress();
    	String externalId = "";
        String nodeName = "integration-node1-u";
        String webSite = "https://www.platon.network/#/";
        String details = "integration-node1-details-u";
    
        PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
        		.setBenifitAddress(benifitAddress)
        		.setExternalId(externalId)
        		.setNodeId(nodeId)
        		.setNodeName(nodeName)
        		.setWebSite(webSite)
        		.setDetails(details)
        		.build()).send();

        BaseResponse baseResponse = stakingContract.getUpdateStakingInfoResult(platonSendTransaction).send();
        return baseResponse;
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
				.setProcessVersion(stakingContract.getProgramVersion())
				.setBlsProof(stakingContract.getAdminSchnorrNIZKProve())
                .build()).send(); 
        BaseResponse baseResponse = stakingContract.getStakingResult(platonSendTransaction).send();
        return baseResponse;
    }

	public void transfer() throws Exception {
		Transfer.sendFunds(web3j, superCredentials, chainId, stakingCredentials.getAddress(), transferValue, Unit.LAT).send();
		Transfer.sendFunds(web3j, superCredentials, chainId, delegateCredentials.getAddress(), transferValue, Unit.LAT).send();
	}	

}
