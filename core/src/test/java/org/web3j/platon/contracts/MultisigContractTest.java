package org.web3j.platon.contracts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultisigContractTest extends TestBase {

    private Logger logger = LoggerFactory.getLogger(MultisigContractTest.class);

//    public String deploy() throws Exception {
//        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
//        Multisig contract = Multisig.deploy(web3j,adminCredentials,multisigBinary,new StaticGasProvider(gasPrice, BigInteger.valueOf(250000000))).send();
//        String contractAddress = contract.getContractAddress();
//        logger.info("Contract Address: {}",contractAddress);
//        return contractAddress;
//    }
//
//    // 部署合约
//    @Test
//    public void deployContract() {
//        try {
//            deploy();
//        } catch (Exception e){
//
//        }
//    }
//
//    /**
//     * Get contract's owners
//     * @throws Exception
//     */
//    @Test
//    public void getOwners() throws Exception {
//        String contractAddress = deploy();
//        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
//        Multisig contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
//        String owners = "0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf0:0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf1";
//        contract.initWallet(owners, new BigInteger("2")).send();
//        String result = contract.getOwners().send();
//        Assert.assertEquals(owners,result);
//    }
//
//    /**
//     * Get contract's owners
//     * @throws Exception
//     */
//    @Test
//    public void getListSize() throws Exception {
//        String contractAddress = deploy();
//        BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
//        Multisig contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
//        logger.info("Multisig Before init: {}", JSON.toJSONString(contract,true));
//        TransactionReceipt receipt =  contract.initWallet("0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf0:0xfc6268a75664df3a1f50d77fc95bbfd8faeecaf1", new BigInteger("2")).send();
//        logger.info("TransactionReceipt: {}", JSON.toJSONString(receipt,true));
//        contract = Multisig.load(multisigBinary,contractAddress,web3j,adminCredentials,new StaticGasProvider(gasPrice,new BigInteger("2000000")));
//        logger.info("Multisig After init: {}", JSON.toJSONString(contract,true));
//        BigInteger listSize = contract.getListSize().send();
//        logger.info("ListSize:{}",listSize);
//        Assert.assertTrue(BigInteger.ZERO.compareTo(listSize)<=0);
//    }
//
//    @Test
//    public void sync() throws IOException, InterruptedException {
//        // 活动开始时，订阅链上的区块信息
//        // 查询当前最高块
////        BigInteger currentHeight = web3j.ethBlockNumber().send().getBlockNumber();
//        BigInteger currentHeight = BigInteger.valueOf(703185);
//        Subscription subscription = web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
//                        DefaultBlockParameter.valueOf(currentHeight), // 起始区块号，活动开始时，查一下链上的区块高度，作为此参数的值
//                        true)
//                .subscribe(block -> {
//                    EthBlock.Block blockData = block.getBlock();
//
//                    // 交易时间
//                    blockData.getTimestamp();
//
//                    logger.error("区块 : {}",blockData);
//                    List<EthBlock.TransactionResult> transactionResults = blockData.getTransactions();
//                    logger.error("区块交易列表 : {}",transactionResults);
//
//                    if(transactionResults.size()!=0){
//                        // 存储入库
//                        logger.info("交易：{}",transactionResults.size());
//                    }
//                });
//
//        /*while (true){
//            if(subscription.isUnsubscribed()){
//                logger.error("Unsubscribed ********** ");
//            }
//            TimeUnit.SECONDS.sleep(2);
//        }*/
//    }
}
