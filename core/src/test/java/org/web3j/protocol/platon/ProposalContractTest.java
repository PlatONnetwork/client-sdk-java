package org.web3j.protocol.platon;

import org.bouncycastle.util.encoders.Hex;
import org.junit.Before;
import org.junit.Test;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.FunctionType;
import org.web3j.platon.ProposalType;
import org.web3j.platon.VoteOption;
import org.web3j.platon.bean.ProgramVersion;
import org.web3j.platon.bean.Proposal;
import org.web3j.platon.bean.TallyResult;
import org.web3j.platon.contracts.ProposalContract;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.PlatonBlock;
import org.web3j.protocol.core.methods.response.PlatonGetTransactionCount;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 治理相关接口，包括，
 * 提交文本提案
 * 提交升级提案
 * 提交参数提案
 * 给提案投票
 * 版本声明
 * 查询提案
 * 查询提案结果
 * 查询提案列表
 * 查询生效版本
 * 查询节点代码版本
 * 查询可治理参数列表
 */
public class ProposalContractTest {

    private String collusionNodeId1 = "87e6f09c4fe2f59029b79a12e20659b18644dd0e48e352ea8759cd5bf6833614aae80930d7e240f4acf9fc1e679fbc814a4a32e33afa72d1a318cb700e6fac1d";
    private String collusionNodeId2 = "25a2407f1692febff715655d53912b6284d8672a411d39b250ec40530a7e36f0b7970ed1d413f9b079e104aba80e5cef25eaf299cbd6a01e8015b505cffebc2d";
    private String collusionNodeId3 = "c75f603bf2502822f4e4b45bdf27eeb1dd9b39642f636c97ff497d2c48457f3983aa77fd8adc6580bcc938dcedf48e9a10d6b50997f16c73b77ddd44e498dce0";
    private String collusionNodeId4 = "5de4975a1c60f47ae9bd630a054d3a336b938900ba5acbd8b96a3f5b646a6d9797fc32f8183721dc5f5acda085f3e0ee830b2041d51825426da0d7fc6405e6d2";

    private String collusionPrivatekey1 = "0x48bb84bc7659ff3c18d2ea53d1491b5535c2d573742b5270a76750bafc9b80bf";
    private String collusionPrivatekey2 = "0xd7f9e51e3a5be4a7af346c1fb6918cc9e78a308ef3db279c88a4c53c5e1a1d56";
    private String collusionPrivatekey3 = "0x04eb7e47c6eb269083a977c47bb6e23d8eb129f3f56e72d65f06f5203267c10e";
    private String collusionPrivatekey4 = "0x3cc53b6608313efdd666fba3150a64e0fcfa0af79cf31f07241e74fbc60f6a15";

    private String builtInPrivateKey = "a11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7";
    private String privateKey = "0x59a9fac3bc8024169df74e6c0c861e1a5fdbe620b8a7a0c1dd0539d02c4e6add";
    private String privateKey2 = "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b";

    private String nodeId = "47eddf1110e92262fd593df81307eca0cb544669986baa702fe11942fca14e20bd7436f2da1c4b23c1c72a4873bd6b322c8525e4324f8c85ed55ae98d5a115f2";
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.112.171:6789"));
    private Credentials credentials;
    private ProposalContract proposalContract;
    private String pIDID = null;

    @Before
    public void init() {

        credentials = Credentials.create(privateKey2);

        proposalContract = ProposalContract.load(web3j,
                credentials, "100");

        pIDID = String.valueOf(UUID.randomUUID().toString().hashCode());
    }

    @Test
    public void sendTransaction() {

//        sendTransaction("a11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7", toAddress, new BigDecimal("6000000000000000000000000"), 500000000000L, 210000L);

    }

    protected BigInteger getNonce() throws IOException {
        PlatonGetTransactionCount ethGetTransactionCount = web3j.platonGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        if (ethGetTransactionCount.getTransactionCount().intValue() == 0) {
            ethGetTransactionCount = web3j.platonGetTransactionCount(
                    credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
        }
        return ethGetTransactionCount.getTransactionCount();
    }

    public String sendTransaction(String privateKey, String toAddress, BigDecimal amount, long gasPrice, long gasLimit) {

        BigInteger GAS_PRICE = BigInteger.valueOf(gasPrice);
        BigInteger GAS_LIMIT = BigInteger.valueOf(gasLimit);

        Credentials credentials = Credentials.create(privateKey);

        try {

            List<RlpType> result = new ArrayList<>();
            result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Int64(0)))));
            String txType = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));

            RawTransaction rawTransaction = RawTransaction.createTransaction(getNonce(), GAS_PRICE, GAS_LIMIT, toAddress, amount.toBigInteger(),
                    txType);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, new Byte("101"), credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            PlatonSendTransaction transaction = web3j.platonSendRawTransaction(hexValue).send();

            return transaction.getTransactionHash();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询提案列表
     */
    @Test
    public void listProposal() {
        try {
            BaseResponse<List<Proposal>> baseResponse = proposalContract.getProposalList().send();
            List<Proposal> proposalList = baseResponse.data;
            System.out.println(proposalList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交文本提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     */
    @Test
    public void submitTextProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            System.out.println(blockNumber);
            BigInteger endVoltingBlock = blockNumber.divide(BigInteger.valueOf(200)).multiply(BigInteger.valueOf(200)).add(BigInteger.valueOf(200).multiply(BigInteger.valueOf(10))).subtract(BigInteger.valueOf(2));

            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitTextProposalParam(nodeId, pIDID)).send();
            BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_TEXT_FUNC_TYPE).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交升级提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * newVersion 升级版本
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     * activeBlock(endVotingBlock + 10 + 4*200 < 生效块高 <= endVotingBlock + 10 + 10*200)
     */
    @Test
    public void submitVersionProposal() {
        try {
            PlatonBlock ethBlock =
                    web3j.platonGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            BigInteger blockNumber = ethBlock.getBlock().getNumber();
            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitVersionProposalParam("0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7", "1177777335544552888200", BigInteger.valueOf(20000), BigInteger.valueOf(50))).send();
            BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_VERSION_FUNC_TYPE).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交参数提案
     * verifier 提交提案的验证人
     * url 提案URL，长度不超过512
     * newVersion 升级版本
     * endVotingBlock ((当前区块高度 / 200 )*200 + 200*10 - 10)
     * paramName 参数名称
     * currentValue 当前值
     * newValue 新的值
     */
    @Test
    public void submitCancelProposal() {

        try {
            PlatonSendTransaction platonSendTransaction = proposalContract.submitProposalReturnTransaction(Proposal.createSubmitCancelProposalParam(nodeId, pIDID, BigInteger.valueOf(5), "0x1178f6dcecd1731e2556d4a014d30ebe04cf5522c07776135e60f613e51af0c9")).send();
            BaseResponse baseResponse = proposalContract.getSubmitProposalResult(platonSendTransaction, FunctionType.SUBMIT_CANCEL_FUNC_TYPE).send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询节点的链生效版本
     */
    @Test
    public void getActiveVersion() {
        try {
            BaseResponse baseResponse = proposalContract.getActiveVersion().send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给提案投票
     * verifier 投票验证人
     * proposalID 提案ID
     * option 投票选项 Years(0x01)支持 Nays(0x02)反对 Abstentions(0x03)弃权
     */
    @Test
    public void vote() {
        try {
            BaseResponse baseResponse = proposalContract.vote(proposalContract.getProgramVersion(), VoteOption.YEAS, "0x1178f6dcecd1731e2556d4a014d30ebe04cf5522c07776135e60f613e51af0c9", "25a2407f1692febff715655d53912b6284d8672a411d39b250ec40530a7e36f0b7970ed1d413f9b079e104aba80e5cef25eaf299cbd6a01e8015b505cffebc2d").send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 版本声明
     * activeNode 声明的节点，只能是验证人/候选人
     */
    @Test
    public void declareVersion() {
        try {
            BaseResponse baseResponse = proposalContract.declareVersion(proposalContract.getProgramVersion(), "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c").send();
            System.out.println(baseResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询提案
     * proposalID 提案ID
     */
    @Test
    public void getProposal() {
        try {
            BaseResponse<Proposal> baseResponse = proposalContract.getProposal("0x389dfd3345351e56bf0e6fc11cdda2c1a23787bf3326ece3072e355d0065cf73").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询提案结果
     * proposalID 提案ID
     */
    @Test
    public void getTallyResult() {
        try {
            BaseResponse<TallyResult> baseResponse = proposalContract.getTallyResult("0x2ceea9176087f6fe64162b8efb2d71ffd0cc0c0326b24738bb644e71db0d5cc6").send();
            System.out.println(baseResponse.data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询节点代码版本
     */
    @Test
    public void getProgramVersion() {
        try {
            ProgramVersion programVersion = proposalContract.getProgramVersion();
            System.out.println(programVersion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
