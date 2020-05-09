package com.platon.sdk.contracts.wasm;

import java.math.BigDecimal;

import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert.Unit;

/**
 * @title 合约销毁
 * @description:
 * @author: hudenian
 * @create: 2020/02/07
 */
public class WASMDestructTest extends BaseContractTest {
    @Test
    public void testDistoryContract() {

        String name = "hudenian";
        try {
//        	/**
//        	 * 会因gas失败的交易场景
//        	 */
//        	Destory_contract failContract = Destory_contract.deploy(web3j, transactionManager, gasProvider,"").send();
//
//            System.out.println("contractAddress " + failContract.getContractAddress());
        	
            /**
             * 交易成功的合约场景
             */
            ContractDistory contractDistory = ContractDistory.deploy(web3j, transactionManager, gasProvider).send();
            String contractAddress = contractDistory.getContractAddress();
            String transactionHash = contractDistory.getTransactionReceipt().get().getTransactionHash();

            System.out.println("contractAddress " + contractAddress);
            System.out.println("tx hash " + transactionHash);
            /**
             * 合约转账的场景
             */
            Transfer transfer = new Transfer(web3j, transactionManager);
            transfer.sendFunds(contractAddress, BigDecimal.TEN, Unit.LAT,GAS_PRICE,GAS_LIMIT).send();
            
            //合约设置值
            TransactionReceipt transactionReceipt = contractDistory.set_string(name).send();
            
            System.out.println("tx status " + transactionReceipt.getStatus());
            

            //合约销毁前查询合约上的数据
            String chainName = contractDistory.get_string().send();
            System.out.println("chainName " + chainName);

            //合约销毁
            transactionReceipt = contractDistory.distory_contract().send();

//            //合约销毁后查询合约上的数据
//            String chainName1 = contractDistory.get_string().send();
//            
//            System.out.println("chainName1 " + chainName1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
