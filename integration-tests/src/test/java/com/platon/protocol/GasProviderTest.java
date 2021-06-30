package com.platon.protocol;

import com.alibaba.fastjson.JSONObject;
import com.platon.contracts.ppos.ProposalContract;
import com.platon.contracts.ppos.abi.Function;
import com.platon.contracts.ppos.dto.resp.Proposal;
import com.platon.contracts.ppos.utils.EncoderUtils;
import com.platon.crypto.Credentials;
import com.platon.parameters.NetworkParameters;
import com.platon.protocol.core.methods.request.Transaction;
import com.platon.protocol.core.methods.response.PlatonChainId;
import com.platon.protocol.core.methods.response.PlatonEstimateGas;
import com.platon.protocol.http.HttpService;
import com.platon.tx.gas.GasProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @Author liushuyu
 * @Date 2021/6/4 11:01
 * @Version
 * @Desc
 */
public class GasProviderTest {

    @Before
    public void init(){
        NetworkParameters.selectAlaya();
    }

    @Test
    public void testGetProviderWithGasPrice_version0_15_0() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.120.143:6788"));
        String fromAddr = Credentials.create("3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668").getAddress();
        String contractAddr = NetworkParameters.getPposContractAddressOfProposal();

        String nodePublicKey = "8d4967ba21c46d63a352b7bccc47810ebdda7869861fbd341d50bfdeb4318e6e429749a3595102392921e434f8d46720af90d018da3a616f931597f57990ded0";
        String pipId = "111";
        String module = "staking";
        String paramName = "stakeThreshold";
        String paramValue = "1";
        Proposal proposal =
                Proposal.createSubmitParamProposalParam(
                        nodePublicKey,
                        pipId,
                        module,
                        paramName,
                        paramValue);
        Function function = new Function(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters());
        //BigInteger gasLimit = web3j.platonEstimateGas(transaction).send().getAmountUsed();
        //gasPrice必须首先获得，在estimateGas的时候，治理合约就需要gasPrice。
        //estimateGas的时候，交易的所有参数，除了gasLimit，应该和真正发送时的参数一样。
        BigInteger gasPrice = BigInteger.valueOf(100000000000000L);

        BigInteger gasLimit = null;
        BigInteger nonce = null;

        //String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data
        Transaction transaction = Transaction.createFunctionCallTransaction(fromAddr, nonce, gasPrice, gasLimit, contractAddr, EncoderUtils.functionEncoder(function));

        PlatonEstimateGas platonEstimateGas = web3j.platonEstimateGas(transaction).send();

        //不带gasprice 估算一次
        Transaction transaction_noGasPrice = Transaction.createEthCallTransaction(fromAddr, contractAddr, EncoderUtils.functionEncoder(function));
        PlatonEstimateGas platonEstimateGas_noGasPrice = web3j.platonEstimateGas(transaction_noGasPrice).send();

        System.out.println("带gas Price：" + JSONObject.toJSONString(platonEstimateGas));
        System.out.println("不带gas Price：" + JSONObject.toJSONString(platonEstimateGas_noGasPrice));
        Assert.assertTrue(platonEstimateGas.getError() == null);
        Assert.assertTrue(platonEstimateGas_noGasPrice.getError() != null);
    }

    @Test
    public void testChainId_version0_15_0() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.120.143:6788"));
        PlatonChainId send = web3j.getChainId().send();
        Assert.assertTrue(send.getError() != null);
    }

    @Test
    public void testGetProviderWithGasPrice_version0_16_0() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.120.141:6789"));
        String fromAddr = Credentials.create("3a4130e4abb887a296eb38c15bbd83253ab09492a505b10a54b008b7dcc1668").getAddress();
        String contractAddr = NetworkParameters.getPposContractAddressOfProposal();

        String nodePublicKey = "0abaf3219f454f3d07b6cbcf3c10b6b4ccf605202868e2043b6f5db12b745df0604ef01ef4cb523adc6d9e14b83a76dd09f862e3fe77205d8ac83df707969b47";
        String pipId = "111";
        String module = "staking";
        String paramName = "stakeThreshold";
        String paramValue = "1";
        Proposal proposal =
                Proposal.createSubmitParamProposalParam(
                        nodePublicKey,
                        pipId,
                        module,
                        paramName,
                        paramValue);
        Function function = new Function(proposal.getSubmitFunctionType(),
                proposal.getSubmitInputParameters());
        //BigInteger gasLimit = web3j.platonEstimateGas(transaction).send().getAmountUsed();
        //gasPrice必须首先获得，在estimateGas的时候，治理合约就需要gasPrice。
        //estimateGas的时候，交易的所有参数，除了gasLimit，应该和真正发送时的参数一样。
        BigInteger gasPrice = BigInteger.valueOf(100000000000000L);

        BigInteger gasLimit = null;
        BigInteger nonce = null;

        //String from, BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, String data
        Transaction transaction = Transaction.createFunctionCallTransaction(fromAddr, nonce, gasPrice, gasLimit, contractAddr, EncoderUtils.functionEncoder(function));

        PlatonEstimateGas platonEstimateGas = web3j.platonEstimateGas(transaction).send();

        //不带gasprice 估算一次
        Transaction transaction_noGasPrice = Transaction.createEthCallTransaction(fromAddr, contractAddr, EncoderUtils.functionEncoder(function));
        PlatonEstimateGas platonEstimateGas_noGasPrice = web3j.platonEstimateGas(transaction_noGasPrice).send();

        System.out.println("带gas Price：" + JSONObject.toJSONString(platonEstimateGas));
        System.out.println("不带gas Price：" + JSONObject.toJSONString(platonEstimateGas_noGasPrice));
        Assert.assertTrue(platonEstimateGas.getError() == null);
        Assert.assertTrue(platonEstimateGas_noGasPrice.getError() == null);
    }

    @Test
    public void testChainId_version0_16_0() throws IOException {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.120.141:6789"));
        PlatonChainId send = web3j.getChainId().send();
        Assert.assertTrue(send.getError() == null);
    }
}
