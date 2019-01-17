package org.web3j.contract.test;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.platon.contracts.TicketContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ReadonlyTransactionManager;
import org.web3j.tx.gas.DefaultWasmGasProvider;

public class TicketContractTest {

    private Logger logger = LoggerFactory.getLogger(TicketContractTest.class);

    private static final Credentials CREDENTIALS = loadCredentials();

    private static Credentials loadCredentials() {
        Credentials credentials = null;
        try {
            URL url = TicketContractTest.class.getClassLoader().getResource("sophia/contracts/build/admin.json");
            String path = url.getPath();
            credentials = WalletUtils.loadCredentials("88888888",path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    private Web3j web3j = Web3j.build(new HttpService("http://10.10.8.209:6789"));

    @Test
    public void getPrice() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String price = contract.GetTicketPrice().send();
        logger.debug("Ticket price: {}",price);
    }


    @Test
    public void VoteTicket() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                CREDENTIALS,
                new DefaultWasmGasProvider()
        );

        TransactionReceipt receipt = contract
                .VoteTicket(BigInteger.TEN,BigInteger.ONE,"0x1f3a8672348ff6b789e416762ad53e69063138b8eb4d8780101658f24b2369f1a8e09499226b467d8bc0c4e03e1dc903df857eeb3c67733d21b6aaee2840e429")
                .send();

        List<TicketContract.VoteTicketEventEventResponse> eventEvents = contract.getVoteTicketEventEvents(receipt);
        eventEvents.forEach(e->logger.debug("param1:{}",e.param1));

        /*String a = receipt.getLogs().get(0).getData();
        RlpList rl = RlpDecoder.decode(Hex.decode(a.replace("0x","")));

        List<RlpType> rlpTypes = rl.getValues();
        RlpList rlpList1 = (RlpList) rlpTypes.get(0);
        RlpString rlpString = (RlpString) rlpList1.getValues().get(0);
        String typecode = Hex.toHexString(rlpString.getBytes());
        byte[] hexByte = Numeric.hexStringToByteArray(typecode);
        Type result = PlatOnTypeDecoder.decode(hexByte, Utf8String.class);
        logger.debug("{}",result.getValue());*/

        Assert.assertEquals("0x1",receipt.getStatus());
    }

    @Test
    public void GetCandidateTicketIds() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String ids = contract
                .GetBatchCandidateTicketIds("0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47")
                .send();
        logger.debug("CandidateTicketIds: {}",ids);
    }

    @Test
    public void GetBatchCandidateTicketIds() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String detail = contract.GetBatchCandidateTicketIds("0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47").send();

        logger.debug("{}",detail);

    }

    @Test
    public void GetTicketDetail() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String detail = contract.GetTicketDetail("0x34bdecd0fa6b8d85b1fa436eac6066aca2f51cc5e84fec278bff7df781310982").send();

        logger.debug("{}",detail);

    }

    @Test
    public void GetBatchTicketDetail() throws Exception {
        String ticketIds = "0x34bdecd0fa6b8d85b1fa436eac6066aca2f51cc5e84fec278bff7df781310982:0x29d376241e85cba191406c7a698cb4aa322ec7971c89e9c7f7123c81b64fc7ba:0x8156589c8f71e981a3835362542ebf10bfbf93bc01cebc3b2c7185416f209508";
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String detail = contract.GetBatchTicketDetail(ticketIds).send();

        logger.debug("{}",detail);

    }

    @Test
    public void GetCandidateEpoch() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String detail = contract.GetCandidateEpoch("0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47").send();

        logger.debug("{}",detail);

    }

    @Test
    public void GetPoolRemainder() throws Exception {
        TicketContract contract = TicketContract.load(
                web3j,
                new ReadonlyTransactionManager(web3j,TicketContract.CONTRACT_ADDRESS),
                new DefaultWasmGasProvider()
        );

        String detail = contract.GetPoolRemainder().send();

        logger.debug("{}",detail);

    }

}
