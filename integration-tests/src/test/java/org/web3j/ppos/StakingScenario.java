//package org.web3j.ppos;
//
//import com.platon.sdk.contracts.ppos.dto.CallResponse;
//import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
//import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;
//import com.platon.sdk.contracts.ppos.dto.req.StakingParam;
//import com.platon.sdk.contracts.ppos.dto.req.UpdateStakingParam;
//import com.platon.sdk.contracts.ppos.dto.resp.Delegation;
//import com.platon.sdk.contracts.ppos.dto.resp.DelegationIdInfo;
//import com.platon.sdk.contracts.ppos.dto.resp.Node;
//import org.junit.Test;
//import org.web3j.Scenario;
//import org.web3j.protocol.core.DefaultBlockParameterName;
//import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
//import org.web3j.tx.Transfer;
//import org.web3j.utils.Convert;
//import org.web3j.utils.Convert.Unit;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.List;
//
//import static org.junit.Assert.assertTrue;
//
//public class StakingScenario extends Scenario {
//
//    private BigDecimal transferValue = new BigDecimal("10000000");
//
//    /**
//     * 正常的场景:
//     * 初始化账户余额
//     * 创建质押信息(1000)
//     * 修改质押信息(1001)
//     * 修改质押金额(1002)
//     * 对质押委托(1004)
//     * 查询当前结算周期的验证人队列(1100)
//     * 查询当前共识周期的验证人列表(1101)
//     * 查询所有实时的候选人列表(1102)
//     * 查询当前账户地址所委托的节点的NodeID和质押Id(1103)
//     * 查询当前单个委托信息(1104)
//     * 查询当前节点的质押信息(1105)
//     * 对质押解除委托(1005)
//     * 退出质押(1003)
//     */
//    @Test
//    public void executeScenario() throws Exception {
//        //初始化账户余额
//        transfer();
//        BigInteger stakingBalance = web3j.platonGetBalance(stakingCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
//        BigInteger delegateBalance = web3j.platonGetBalance(delegateCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
//
//        assertTrue(new BigDecimal(stakingBalance).compareTo(Convert.fromVon(transferValue, Unit.VON)) >= 0);
//        assertTrue(new BigDecimal(delegateBalance).compareTo(Convert.fromVon(transferValue, Unit.VON)) >= 0);
//
//        //创建质押信息(1000)
//        TransactionResponse createStakingResponse = staking();
//        assertTrue(createStakingResponse.toString(), createStakingResponse.isStatusOk());
//
//        //修改质押信息(1001)
//        TransactionResponse updateStakingResponse = updateStakingInfo();
//        assertTrue(updateStakingResponse.toString(), updateStakingResponse.isStatusOk());
//
//        //修改质押金额(1002)
//        TransactionResponse addStakingResponse = addStaking();
//        assertTrue(addStakingResponse.toString(), addStakingResponse.isStatusOk());
//
//        //对质押委托(1004)
//        TransactionResponse delegateResponse = delegate();
//        assertTrue(delegateResponse.toString(), delegateResponse.isStatusOk());
//
//        //查询当前账户地址所委托的节点的NodeID和质押Id(1103)
//        CallResponse<List<DelegationIdInfo>> getRelatedListByDelAddrResponse = getRelatedListByDelAddr();
//        assertTrue(getRelatedListByDelAddrResponse.toString(), getRelatedListByDelAddrResponse.isStatusOk());
//
//        List<DelegationIdInfo> delegationIdInfos = getRelatedListByDelAddrResponse.getData();
//        assertTrue(delegationIdInfos != null && !delegationIdInfos.isEmpty());
//
//        //查询当前单个委托信息(1104)
//        CallResponse<Delegation> getDelegateResponse = getDelegateInfo(delegationIdInfos.get(0).getStakingBlockNum());
//        assertTrue(getDelegateResponse.toString(), getDelegateResponse.isStatusOk());
//
//        //查询当前节点的质押信息(1105)
//        CallResponse<Node> getStakingInfoResponse = getStakingInfo();
//        assertTrue(getStakingInfoResponse.toString(), getStakingInfoResponse.isStatusOk());
//
//        //对质押解除委托(1005)
//        TransactionResponse unDelegateResponse = unDelegate(delegationIdInfos.get(0).getStakingBlockNum());
//        assertTrue(unDelegateResponse.toString(), unDelegateResponse.isStatusOk());
//
//        //退出质押(1003)
//        TransactionResponse unStakingResponse = unStaking();
//        assertTrue(unStakingResponse.toString(), unStakingResponse.isStatusOk());
//    }
//
//
//    public TransactionResponse unStaking() throws Exception {
//        PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId).send();
//        TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public TransactionResponse unDelegate(BigInteger stakingBlockNum) throws Exception {
//        BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
//        PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingBlockNum, stakingAmount.toBigInteger()).send();
//        TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//
//    public CallResponse<Node> getStakingInfo() throws Exception {
//    	CallResponse<Node> baseResponse = stakingContract.getStakingInfo(nodeId).send();
//        return baseResponse;
//    }
//
//
//    public CallResponse<Delegation> getDelegateInfo(BigInteger stakingBlockNum) throws Exception {
//    	CallResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, delegateCredentials.getAddress(), stakingBlockNum).send();
//        return baseResponse;
//    }
//
//
//    public CallResponse<List<DelegationIdInfo>> getRelatedListByDelAddr() throws Exception {
//    	CallResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(delegateCredentials.getAddress()).send();
//        return baseResponse;
//    }
//
//    public TransactionResponse delegate() throws Exception {
//        StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
//        BigDecimal stakingAmount = Convert.toVon("500000", Unit.LAT);
//
//        PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, stakingAmountType, stakingAmount.toBigInteger()).send();
//        TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public TransactionResponse addStaking() throws Exception {
//        StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
//        BigDecimal addStakingAmount = Convert.toVon("4000000", Unit.LAT).add(new BigDecimal("999999999999999998"));
//
//        PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId, stakingAmountType, addStakingAmount.toBigInteger()).send();
//        TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public TransactionResponse updateStakingInfo() throws Exception {
//        String benifitAddress = benefitCredentials.getAddress();
//        String externalId = "";
//        String nodeName = "integration-node1-u";
//        String webSite = "https://www.platon.network/#/";
//        String details = "integration-node1-details-u";
//        BigInteger rewardPer = BigInteger.valueOf(2000L);
//
//        PlatonSendTransaction platonSendTransaction = stakingContract.updateStakingInfoReturnTransaction(new UpdateStakingParam.Builder()
//                .setBenifitAddress(benifitAddress)
//                .setExternalId(externalId)
//                .setNodeId(nodeId)
//                .setNodeName(nodeName)
//                .setWebSite(webSite)
//                .setDetails(details)
//                .setRewardPer(rewardPer)
//                .build()).send();
//
//        TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public TransactionResponse staking() throws Exception {
//        StakingAmountType stakingAmountType = StakingAmountType.FREE_AMOUNT_TYPE;
//        String benifitAddress = benefitCredentials.getAddress();
//        String externalId = "";
//        String nodeName = "integration-node1";
//        String webSite = "https://www.platon.network/#/";
//        String details = "integration-node1-details";
//        BigDecimal stakingAmount = Convert.toVon("5000000", Unit.LAT).add(BigDecimal.valueOf(1L));
//        BigInteger rewardPer = BigInteger.valueOf(1000L);
//
//        PlatonSendTransaction platonSendTransaction = stakingContract.stakingReturnTransaction(new StakingParam.Builder()
//                .setNodeId(nodeId)
//                .setAmount(stakingAmount.toBigInteger())
//                .setStakingAmountType(stakingAmountType)
//                .setBenifitAddress(benifitAddress)
//                .setExternalId(externalId)
//                .setNodeName(nodeName)
//                .setWebSite(webSite)
//                .setDetails(details)
//                .setBlsPubKey(blsPubKey)
//                .setProcessVersion(web3j.getProgramVersion().send().getAdminProgramVersion())
//                .setBlsProof(web3j.getSchnorrNIZKProve().send().getAdminSchnorrNIZKProve())
//                .setRewardPer(rewardPer)
//                .build()).send();
//        TransactionResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public void transfer() throws Exception {
//        Transfer.sendFunds(web3j, superCredentials, chainId, stakingCredentials.getAddress(), transferValue, Unit.LAT).send();
//        Transfer.sendFunds(web3j, superCredentials, chainId, delegateCredentials.getAddress(), transferValue, Unit.LAT).send();
//    }
//
//}
