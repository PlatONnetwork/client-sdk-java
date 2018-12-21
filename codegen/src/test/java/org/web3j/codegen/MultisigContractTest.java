package org.web3j.codegen;

import org.junit.Test;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Files;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class MultisigContractTest {

    private static final File CONTRACT_BINARY_FILE = new File("D:\\Workspace\\client-sdk-java\\core\\src\\test\\resources\\multisig.wasm");

    private static String getBinary() throws IOException {
        byte [] bytes = Files.readBytes(CONTRACT_BINARY_FILE);
        String binary = new String(bytes);
        return binary;
    }

    @Test
    public void testContract() throws Exception {
        Web3j web3 = Web3j.build(new HttpService("http://192.168.9.76:6788"));

        String data = getBinary();
        BigInteger gasLimit = Multisig.getDeployGasLimit(web3,"0x0000000000000000000000000000000000000000",
                "0x0000000000000000000000000000000000000000",data);

        ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigIntNoPrefix("a11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7"));
        Credentials credentials = Credentials.create(ecKeyPair);
        BigInteger gasPrice = web3.ethGasPrice().send().getGasPrice();

        Multisig contract = Multisig.deploy(web3,credentials,data,new StaticGasProvider(gasPrice,gasLimit)).send();
        System.out.println(contract);

    }
}
