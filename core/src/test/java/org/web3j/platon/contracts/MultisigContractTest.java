package org.web3j.platon.contracts;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.StaticGasProvider;
import rx.Subscription;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class MultisigContractTest extends TestBase {

    private Logger logger = LoggerFactory.getLogger(MultisigContractTest.class);

    public String deploy() throws Exception {
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        Multisig contract = Multisig.deploy(web3j,adminCredentials,multisigBinary,new StaticGasProvider(gasPrice, BigInteger.valueOf(250000000))).send();
        String contractAddress = contract.getContractAddress();
        logger.info("Contract Address: {}",contractAddress);
        return contractAddress;
    }

    // 部署合约
    @Test
    public void deployContract() {
        try {
            deploy();
        } catch (Exception e){

        }
    }

    /**
     * Get contract's owners
     * @throws Exception
     */
    @Test
    public void getOwners() throws Exception {
        String contractAddress = deploy();
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        Multisig contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
        String owners = "0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf0:0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf1";
        contract.initWallet(owners, new BigInteger("2")).send();
        String result = contract.getOwners().send();
        result = result.startsWith("0x")?result:"0x"+result;
        result = result.contains(":0x")?result:result.replaceAll(":",":0x");
        Assert.assertEquals(owners,result);
    }

    /**
     * Get contract's owners
     * @throws Exception
     */
    @Test
    public void getListSize() throws Exception {
        String contractAddress = deploy();
        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        Multisig contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
        logger.info("Multisig Before init: {}", JSON.toJSONString(contract,true));
        TransactionReceipt receipt =  contract.initWallet("0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf0:0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf1", new BigInteger("2")).send();
        logger.info("TransactionReceipt: {}", JSON.toJSONString(receipt,true));
        contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
        logger.info("Multisig After init: {}", JSON.toJSONString(contract,true));
        BigInteger listSize = contract.getListSize().send();
        logger.info("ListSize:{}",listSize);
        Assert.assertTrue(BigInteger.ZERO.compareTo(listSize)<=0);
    }


}
