package com.platon.contracts.ppos;

import com.platon.contracts.ppos.dto.CallResponse;
import com.platon.contracts.ppos.dto.TransactionResponse;
import com.platon.contracts.ppos.dto.enums.DelegateAmountType;
import com.platon.contracts.ppos.dto.enums.StakingAmountType;
import com.platon.contracts.ppos.dto.resp.*;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.Web3j;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.core.methods.response.PlatonSendTransaction;
import com.platon.protocol.http.HttpService;
import com.platon.tx.Transfer;
import com.platon.tx.gas.GasProvider;
import com.platon.utils.Convert.Unit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 委托相关接口，包括，
 * 发起委托
 * 减持/撤销委托
 * 查询当前单个委托信息
 */
public class DelegateContractTest {
    private String nodeId = "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050";
    private BigInteger stakingNb = BigInteger.valueOf(14431L);
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.120.148:6789"));
    long chainId = 2022041902;
    String hrp = "lat";

    private Credentials deleteCredentials;
    private Credentials superCredentials;
    private DelegateContract delegateContract;

    @Before
    public void init() {
        NetworkParameters.init(chainId, hrp);
        superCredentials = Credentials.create("0x3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668");
        deleteCredentials = Credentials.create("0x6fe419582271a4dcf01c51b89195b77b228377fde4bde6e04ef126a0b4373f79");
        delegateContract = DelegateContract.load(web3j, deleteCredentials);
    }

    @Test
    public void transfer() throws Exception {
    	Transfer.sendFunds(web3j, superCredentials, deleteCredentials.getAddress(), new BigDecimal("10000000"), Unit.KPVON).send();
    	System.out.println("stakingCredentials balance="+ web3j.platonGetBalance(deleteCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance());
    }

    @Test
    public void delegate() {
        try {
            PlatonSendTransaction platonSendTransaction = delegateContract.delegateReturnTransaction(nodeId, DelegateAmountType.DELEGATE_LOCK_AMOUNT_TYPE, new BigInteger("100000000000000000000")).send();
            TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void estimateDelegateGasLimit() {
        try {
            GasProvider gasProvider = delegateContract.getDelegateGasProvider(nodeId, DelegateAmountType.FREE_AMOUNT_TYPE, new BigInteger("200000000000000000000"));
            System.out.println("gasProvider.getGasLimit():" + gasProvider.getGasLimit());
            TransactionResponse response = delegateContract.delegate(nodeId, DelegateAmountType.FREE_AMOUNT_TYPE, new BigInteger("200000000000000000000")).send();
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unDelegate() {
        try {
            PlatonSendTransaction platonSendTransaction = delegateContract.unDelegateReturnTransaction(nodeId, stakingNb, new BigInteger("200000000000000000000")).send();
            TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();

            if(baseResponse.isStatusOk()){
                UnDelegation unDelegation = delegateContract.decodeUnDelegateLogOfNew(baseResponse.getTransactionReceipt());
                System.out.println(unDelegation);
            }

            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redeemDelegation() {
        try {
            PlatonSendTransaction platonSendTransaction = delegateContract.redeemDelegationReturnTransaction().send();
            TransactionResponse baseResponse = delegateContract.getTransactionResponse(platonSendTransaction).send();

            if(baseResponse.isStatusOk()){
                RedeemDelegation redeemDelegation = delegateContract.decodeRedeemDelegateLog(baseResponse.getTransactionReceipt());
                System.out.println(redeemDelegation);
            }
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDelegateInfo() {
        try {
            CallResponse<Delegation> baseResponse = delegateContract.getDelegateInfo(nodeId, deleteCredentials.getAddress(), stakingNb).send();
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRelatedListByDelAddr() {
        try {
        	CallResponse<List<DelegationIdInfo>> baseResponse = delegateContract.getRelatedListByDelAddr(deleteCredentials.getAddress()).send();
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getDelegateLockInfo() {
        try {
            CallResponse<DelegationLockInfo> baseResponse = delegateContract.getDelegationLockInfo(deleteCredentials.getAddress()).send();
            System.out.println(baseResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
