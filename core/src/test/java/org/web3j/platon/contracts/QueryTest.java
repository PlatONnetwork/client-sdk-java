package org.web3j.platon.contracts;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultWasmGasProvider;

import java.io.IOException;
import java.io.OptionalDataException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: Chendongming
 * @Date: 2019/6/3 10:27
 * @Description:
 */
public class QueryTest {
    private Logger logger = LoggerFactory.getLogger(MultisigContractTest.class);

    private TicketContract ticketContract;
    private CandidateContract candidateContract;
    private Web3j web3j;

    private String walletAddr = "0x87a2FFFcB66C0798383b7C0C264FF447C27B73A5";
    private String privateKey = "c359429aa195e528ac0bfe9ce74c7067359180a395d5f2b3017d99b098eb483f";
    @Before
    public void init() throws ConnectException {
//        web3j = Web3j.build(new HttpService("http://10.10.8.21:6789"));
//        web3j = Web3j.build(new HttpService("http://192.168.120.84:6789"));
        WebSocketService ws = new WebSocketService("ws://10.10.8.200:6789",true);
        ws.connect();
        web3j = Web3j.build(ws);
        ticketContract = TicketContract.load(web3j,new ReadonlyTransactionManager(web3j, TicketContract.CONTRACT_ADDRESS),new DefaultWasmGasProvider());
        candidateContract = CandidateContract.load(web3j,new ReadonlyTransactionManager(web3j, CandidateContract.CONTRACT_ADDRESS), new DefaultWasmGasProvider());
    }

    @Test
    public void getBalance() throws IOException {
        BigInteger balance = web3j.ethGetBalance(walletAddr, DefaultBlockParameterName.LATEST).send().getBalance();
        logger.info("Balance:{}",balance);
    }

    @Test
    public void blockNumber() throws IOException, InterruptedException {
        while (true){
            BigInteger number = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST,true).send().getBlock().getNumber();
            logger.info("Block Number:{}",number);
            TimeUnit.SECONDS.sleep(1);
        }

    }

    @Test
    public void getTransactionReceipt() throws IOException {
        web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(DefaultBlockParameterName.EARLIEST).subscribe((block)->{
            String json = JSON.toJSONString(block,true);
            logger.info("tx: {}",json);
        });
        //web3j.ethGetTransactionReceipt();

        Optional<TransactionReceipt> receipt = web3j
                .ethGetTransactionReceipt("0x06f324c40e82fe70dd4573a0fcb744998bc45cb6550ba7bec966e1778dadd376")
                .send().getTransactionReceipt();

        logger.info("Block Receipt:{}",receipt);
    }
}
