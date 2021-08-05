package com.platon.protocol.admin;

import com.platon.protocol.RequestTester;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.http.HttpService;
import org.junit.Test;

import java.math.BigInteger;

public class RequestTest extends RequestTester {
    
    private Admin web3j;

    @Override
    protected void initWeb3Client(HttpService httpService) {
        web3j = Admin.build(httpService);
    }
    
    @Test
    public void testPersonalListAccounts() throws Exception {
        web3j.personalListAccounts().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"personal_listAccounts\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testPersonalNewAccount() throws Exception {
        web3j.personalNewAccount("password").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"personal_newAccount\","
                + "\"params\":[\"password\"],\"id\":1}");
    } 

    @Test
    public void testPersonalSendTransaction() throws Exception {
        web3j.personalSendTransaction(
                new Transaction(
                        "FROM",
                        BigInteger.ONE,
                        BigInteger.TEN,
                        BigInteger.ONE,
                        "TO",
                        BigInteger.ZERO,
                        "DATA"
                ),
                "password"
        ).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"personal_sendTransaction\",\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0x1\",\"gasPrice\":\"0xa\",\"value\":\"0x0\",\"data\":\"0xDATA\",\"nonce\":\"0x1\"},\"password\"],\"id\":1}");
        //CHECKSTYLE:ON
    }   

    @Test
    public void testPersonalUnlockAccount() throws Exception {
        web3j.personalUnlockAccount(
                "0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2", BigInteger.ONE).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"personal_unlockAccount\","
                + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",1],"
                + "\"id\":1}");
    }

    @Test
    public void testPersonalUnlockAccountNoDuration() throws Exception {
        web3j.personalUnlockAccount("0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"personal_unlockAccount\","
                + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",null],"
                + "\"id\":1}");
    }

    @Test
    public void testTxPoolContent() throws Exception {
        web3j.txPoolContent().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"bech32\":true,\"method\":\"txpool_content\"," + "\"params\":[],\"id\":1}");
    }
}
