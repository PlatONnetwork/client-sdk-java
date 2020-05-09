//package org.web3j.ppos;
//
//import static org.junit.Assert.assertTrue;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//
//import org.junit.Test;
//import org.web3j.Scenario;
//import org.web3j.protocol.core.DefaultBlockParameterName;
//import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
//import org.web3j.tx.Transfer;
//import org.web3j.utils.Convert;
//import org.web3j.utils.Convert.Unit;
//
//import com.platon.sdk.contracts.ppos.dto.BaseResponse;
//import com.platon.sdk.contracts.ppos.dto.CallResponse;
//import com.platon.sdk.contracts.ppos.dto.TransactionResponse;
//import com.platon.sdk.contracts.ppos.dto.common.DuplicateSignType;
//
//public class SlashScenario extends Scenario {
//
//	private BigDecimal transferValue = new BigDecimal("1000");
//    private String data = "{\"prepare_a\":{\"epoch\":0,\"view_number\":0,\"block_hash\":\"0xf41006b64e9109098723a37f9246a76c236cd97c67a334cfb4d54bc36a3f1306\",\"block_number\":500,\"block_index\":0,\"validate_node\":{\"index\":0,\"address\":\"0x0550184a50db8162c0cfe9296f06b2b1db019331\",\"NodeID\":\"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050\",\"blsPubKey\":\"5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811\"},\"signature\":\"0xa7205d571b16696b3a9b68e4b9ccef001c751d860d0427760f650853fe563f5191f2292dd67ccd6c89ed050182f19b9200000000000000000000000000000000\"},\"prepare_b\":{\"epoch\":0,\"view_number\":0,\"block_hash\":\"0x5c57b8daf9b52978aa0cd79c7783330461fc7978139d4301782484ce9e7231c5\",\"block_number\":500,\"block_index\":0,\"validate_node\":{\"index\":0,\"address\":\"0x0550184a50db8162c0cfe9296f06b2b1db019331\",\"NodeID\":\"77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050\",\"blsPubKey\":\"5ccd6b8c32f2713faa6c9a46e5fb61ad7b7400e53fabcbc56bdc0c16fbfffe09ad6256982c7059e7383a9187ad93a002a7cda7a75d569f591730481a8b91b5fad52ac26ac495522a069686df1061fc184c31771008c1fedfafd50ae794778811\"},\"signature\":\"0x85f423d5f6332ac0f63f0e089f09b066d525062d671f923ac658ce0773741d505ae36b5f74f93a7b13866a0c5d76029100000000000000000000000000000000\"}}";
//
//	/**
//	 * 正常的场景:
//	 * 初始化账户余额
//	 * 举报双签(3000)
//	 * 查询节点是否已被举报过多签(3001)
//	 */
//	@Test
//	public void executeScenario() throws Exception {
//		//初始化账户余额
//		transfer();
//
//		BigInteger slashBalance = web3j.platonGetBalance(slashCredentials.getAddress(), DefaultBlockParameterName.LATEST).send().getBalance();
//		assertTrue(new BigDecimal(slashBalance).compareTo(Convert.fromVon(transferValue, Unit.VON))>=0);
//
//		//举报双签(3000)
//		BaseResponse reportDuplicateSignResponse = reportDuplicateSign();
//		assertTrue(reportDuplicateSignResponse.toString(),reportDuplicateSignResponse.getCode()>=0);
//
//		//查询节点是否已被举报过多签(3001)
//		BaseResponse checkDuplicateSignResponse = checkDuplicateSign();
//		assertTrue(checkDuplicateSignResponse.toString(),checkDuplicateSignResponse.isStatusOk());
//
//	}
//
//    public TransactionResponse reportDuplicateSign() throws Exception {
//        PlatonSendTransaction platonSendTransaction = slashContract.reportDoubleSignReturnTransaction(DuplicateSignType.PREPARE_BLOCK, data).send();
//        TransactionResponse baseResponse = slashContract.getTransactionResponse(platonSendTransaction).send();
//        return baseResponse;
//    }
//
//    public CallResponse<String> checkDuplicateSign() throws Exception {
//    	CallResponse<String> baseResponse = slashContract.checkDoubleSign(DuplicateSignType.PREPARE_BLOCK, "0x4F8eb0B21eb8F16C80A9B7D728EA473b8676Cbb3", BigInteger.valueOf(500L)).send();
//    	 return baseResponse;
//    }
//
//	public void transfer() throws Exception {
//		Transfer.sendFunds(web3j, superCredentials, chainId, slashCredentials.getAddress(), transferValue, Unit.LAT).send();
//	}
//
//}
