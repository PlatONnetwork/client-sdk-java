package com.platon.sdk.contracts.wasm;

import com.platon.rlp.datatypes.WasmAddress;
import org.junit.Test;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContractEmitEventWithAddrTest extends BaseContractTest {


    private String address = "lax1yr0mnwz58c5da2erhxch0ruf68swusu9hwjp20";

    @Test
    public void deployByT() throws Exception {
        ContractEmitEventWithAddr contract =  ContractEmitEventWithAddr.deploy(web3j,transactionManager,gasProvider,chainId).send();
        System.out.println("address="+contract.getContractAddress());
    }

    @Test
    public void deployByC() throws Exception {
        ContractEmitEventWithAddr contract =  ContractEmitEventWithAddr.deploy(web3j,credentials,gasProvider,chainId).send();
        System.out.println("address="+contract.getContractAddress());
    }


    @Test
    public void setNameAndEmitAddress() throws Exception {

        ContractEmitEventWithAddr contract =  loadT();

        TransactionReceipt transactionReceipt = contract.setNameAndEmitAddress("x", new WasmAddress(address)).send();
        System.out.println("hash = " + transactionReceipt.getTransactionHash());
        List<ContractEmitEventWithAddr.TransferEventResponse> tokenAddress = contract.getTransferEvents(transactionReceipt);

        assertThat(tokenAddress.size(), is(1));

        tokenAddress.stream().forEach(
                event -> {
                    assertThat(event.arg1, is("x"));
                    assertThat(event.arg3.getAddress(), is(address));
                    System.out.println(event.log + "  "  + event.arg1 + "  " + event.arg2 + "  " + event.arg3.getAddress());
                }
        );

        contract =  loadD();
        transactionReceipt = contract.setNameAndEmitAddress("y", new WasmAddress(credentialsAddress)).send();
        tokenAddress = contract.getTransferEvents(transactionReceipt);

        assertThat(tokenAddress.size(), is(1));

        tokenAddress.stream().forEach(
                event -> {
                    assertThat(event.arg1, is("y"));
                    assertThat(event.arg3.getAddress(), is(credentialsAddress));
                    System.out.println(event.log + "  "  + event.arg1 + "  " + event.arg2 + "  " + event.arg3.getAddress());
                }
        );
    }


    @Test
    public void getAddress() throws Exception {

        ContractEmitEventWithAddr contract =  loadT();

        WasmAddress result = contract.getAddress().send();
        assertThat(result.getAddress(), is(credentialsAddress));
        System.out.println("address = " + result.getAddress());

        contract =  loadD();
        result = contract.getAddress().send();
        assertThat(result.getAddress(), is(credentialsAddress));
        System.out.println("address = " + result.getAddress());

    }

    @Test
    public void getName() throws Exception {

        ContractEmitEventWithAddr contract =  loadT();

        String result = contract.getName().send();
        assertThat(result, is("y"));
        System.out.println("name = " + result);

        contract =  loadD();
        result = contract.getName().send();
        assertThat(result, is("y"));
        System.out.println("name = " + result);

    }


    private ContractEmitEventWithAddr loadT(){
        return ContractEmitEventWithAddr.load(address, web3j, transactionManager, gasProvider, chainId);
    }

    private ContractEmitEventWithAddr loadD(){
        return ContractEmitEventWithAddr.load(address, web3j, credentials, gasProvider, chainId);
    }

}
