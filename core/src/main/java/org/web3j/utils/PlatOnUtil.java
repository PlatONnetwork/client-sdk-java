package org.web3j.utils;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.PlatOnTypeDecoder;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.rlp.*;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PlatOnUtil {
    private PlatOnUtil(){}
    private static final String DEFAULT_ADDR = "0x0000000000000000000000000000000000000000";

    /**
     * 合约方法调用编码
     * @param function 合约函数
     * @param txType 交易类型
     * @return encoded data
     */
    public static String invokeEncode(Function function,long txType) {
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Int64(txType)))));
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Utf8String(function.getName())))));

        List<Type> parameters = function.getInputParameters();
        for (Type parameter:parameters) {
            String encodedValue = PlatOnTypeEncoder.encode(parameter);
            result.add(RlpString.create(Numeric.hexStringToByteArray(encodedValue)));
        }
        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }

    /**
     * 合约数据编码
     * @param contractBinary 合约数据
     * @param abi abi
     * @return encoded data
     */
    public static String deployEncode(String contractBinary,String abi) {
        // txType + bin + abi
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Int64(1)))));
        result.add(RlpString.create(Numeric.hexStringToByteArray(contractBinary)));
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Utf8String(abi)))));
        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }

    /**
     * 合约事件编码
     * @param data 数据
     * @param outputParameters 出参
     * @return results
     */
    public static List<Type> eventDecode(String data, List<TypeReference<Type>> outputParameters) {
        RlpList rlpList = RlpDecoder.decode(Numeric.hexStringToByteArray(data));
        List<RlpType> rlpTypeList = rlpList.getValues();
        RlpList rlpList2 = (RlpList)rlpTypeList.get(0);
        List<Type> results = new ArrayList<>();

        for (int i = 0; i < outputParameters.size(); i++) {
            RlpString rlpString = (RlpString) rlpList2.getValues().get(i);
            byte[] rlpBytes = rlpString.getBytes();
            TypeReference<Type> typeReference = outputParameters.get(i);
            Class<Type> type;
            try {
                type = typeReference.getClassType();
            } catch (ClassNotFoundException e) {
                throw new UnsupportedOperationException("class not found:",e );
            }
            Type result = PlatOnTypeDecoder.decode(rlpBytes, type);
            results.add(result);
        }
        return results;
    }

    /**
     * 估算GasLimit
     * @param web3j web3j
     * @param estimateGasFrom 发送者
     * @param estimateGasTo 接收者
     * @param encodedData 编码后的数据
     * @return gasLimit
     * @throws IOException exception
     */
    public static BigInteger estimateGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String encodedData) throws IOException {
        if(Strings.isEmpty(estimateGasTo)){
            estimateGasTo = DEFAULT_ADDR;
        }
        Transaction transaction = Transaction.createEthCallTransaction(estimateGasFrom,estimateGasTo, encodedData);
        Request<?, EthEstimateGas> ethEstimateGasReq = web3j.ethEstimateGas(transaction);
        if(ethEstimateGasReq==null){
            return DefaultGasProvider.GAS_LIMIT;
        }
        EthEstimateGas ethEstimateGasRes = ethEstimateGasReq.send();
        BigInteger ethEstimateGasLimit = ethEstimateGasRes.getAmountUsed();
        return ethEstimateGasLimit;
    }
}
