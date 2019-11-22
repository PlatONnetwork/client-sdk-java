package org.web3j.utils;

import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.PlatOnTypeDecoder;
import org.web3j.abi.PlatOnTypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.BytesType;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.IntType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.platon.BaseResponse;
import org.web3j.platon.CustomStaticArray;
import org.web3j.platon.CustomType;
import org.web3j.platon.ErrorCode;
import org.web3j.platon.bean.Response;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.PlatonEstimateGas;
import org.web3j.rlp.*;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PlatOnUtil {

    private static final Logger logger = LoggerFactory.getLogger("PlatOnUtil");

    private static final int MAX_DEPTH = 16;

    /**
     * Allow for content up to size of 2^64 bytes *
     */
    private static final double MAX_ITEM_LENGTH = Math.pow(256, 8);

    /**
     * Reason for threshold according to Vitalik Buterin:
     * - 56 bytes maximizes the benefit of both options
     * - if we went with 60 then we would have only had 4 slots for long strings
     * so RLP would not have been able to store objects above 4gb
     * - if we went with 48 then RLP would be fine for 2^128 space, but that's way too much
     * - so 56 and 2^64 space seems like the right place to put the cutoff
     * - also, that's where Bitcoin's varint does the cutof
     */
    private static final int SIZE_THRESHOLD = 56;

    /** RLP encoding rules are defined as follows: */

    /*
     * For a single byte whose value is in the [0x00, 0x7f] range, that byte is
     * its own RLP encoding.
     */

    /**
     * [0x80]
     * If a string is 0-55 bytes long, the RLP encoding consists of a single
     * byte with value 0x80 plus the length of the string followed by the
     * string. The range of the first byte is thus [0x80, 0xb7].
     */
    private static final int OFFSET_SHORT_ITEM = 0x80;

    /**
     * [0xb7]
     * If a string is more than 55 bytes long, the RLP encoding consists of a
     * single byte with value 0xb7 plus the length of the length of the string
     * in binary form, followed by the length of the string, followed by the
     * string. For example, a length-1024 string would be encoded as
     * \xb9\x04\x00 followed by the string. The range of the first byte is thus
     * [0xb8, 0xbf].
     */
    private static final int OFFSET_LONG_ITEM = 0xb7;

    /**
     * [0xc0]
     * If the total payload of a list (i.e. the combined length of all its
     * items) is 0-55 bytes long, the RLP encoding consists of a single byte
     * with value 0xc0 plus the length of the list followed by the concatenation
     * of the RLP encodings of the items. The range of the first byte is thus
     * [0xc0, 0xf7].
     */
    private static final int OFFSET_SHORT_LIST = 0xc0;

    /**
     * [0xf7]
     * If the total payload of a list is more than 55 bytes long, the RLP
     * encoding consists of a single byte with value 0xf7 plus the length of the
     * length of the list in binary form, followed by the length of the list,
     * followed by the concatenation of the RLP encodings of the items. The
     * range of the first byte is thus [0xf8, 0xff].
     */
    private static final int OFFSET_LONG_LIST = 0xf7;

    private PlatOnUtil() {

    }

    private static final String DEFAULT_ADDR = "0x0000000000000000000000000000000000000000";

    /**
     * 合约方法调用编码
     *
     * @param function 合约函数
     * @param txType   交易类型
     * @return encoded data
     */
    public static String invokeEncode(Function function, long txType) {
        List<RlpType> result = new ArrayList<>();
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Int64(txType)))));
        result.add(RlpString.create(Numeric.hexStringToByteArray(PlatOnTypeEncoder.encode(new Utf8String(function.getName())))));

        List<Type> parameters = function.getInputParameters();
        for (Type parameter : parameters) {
            String encodedValue = PlatOnTypeEncoder.encode(parameter);
            result.add(RlpString.create(Numeric.hexStringToByteArray(encodedValue)));
        }
        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }

    /**
     * 合约方法调用编码
     *
     * @param function 合约函数
     * @return encoded data
     */
    public static String invokeEncode(Function function) {

        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(RlpEncoder.encode(RlpString.create(function.getType()))));

        List<Type> parameters = function.getInputParameters();
        for (Type parameter : parameters) {
            if (parameter instanceof IntType) {
                result.add(RlpString.create(RlpEncoder.encode(RlpString.create(((IntType) parameter).getValue()))));
            } else if (parameter instanceof BytesType) {
                result.add(RlpString.create(RlpEncoder.encode(RlpString.create(((BytesType) parameter).getValue()))));
            } else if (parameter instanceof Utf8String) {
                result.add(RlpString.create(RlpEncoder.encode(RlpString.create(((Utf8String) parameter).getValue()))));
            } else if (parameter instanceof CustomStaticArray) {
                result.add(((CustomStaticArray) parameter).getRlpEncodeData());
            } else if (parameter instanceof CustomType) {
                result.add(((CustomType) parameter).getRlpEncodeData());
            }
        }
        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }

    public static BaseResponse invokeDecode(String result) {

        if (result == null) {
            return new BaseResponse();
        }

        RlpList rlpList = RlpDecoder.decode(Hex.decode(Numeric.cleanHexPrefix(result)));

        List<RlpType> rlpTypeList = rlpList.getValues();

        StringBuilder sb = new StringBuilder();

        for (RlpType rlpType : rlpTypeList) {
            byte[] bytes = RlpEncoder.encode(rlpType);
            sb.append(new String(bytes));
        }

        BaseResponse baseResponse = JSONUtil.parseObject(sb.toString(), BaseResponse.class);

        if (baseResponse == null) {
            return new BaseResponse();
        }

        if (baseResponse.isStatusOk()) {
            baseResponse.errMsg = ErrorCode.getErrorMsg(ErrorCode.SUCCESS);
        } else {
            baseResponse.errMsg = (String) baseResponse.data;
            baseResponse.data = null;
        }
        return baseResponse;
    }

    /**
     * 合约数据编码
     *
     * @param contractBinary 合约数据
     * @param abi            abi
     * @return encoded data
     */
    public static String deployEncode(String contractBinary, String abi) {
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
     *
     * @param data             数据
     * @param outputParameters 出参
     * @return results
     */
    public static List<Type> eventDecode(String data, List<TypeReference<Type>> outputParameters) {
        RlpList rlpList = RlpDecoder.decode(Numeric.hexStringToByteArray(data));
        List<RlpType> rlpTypeList = rlpList.getValues();
        RlpList rlpList2 = (RlpList) rlpTypeList.get(0);
        List<Type> results = new ArrayList<>();

        for (int i = 0; i < outputParameters.size(); i++) {
            RlpString rlpString = (RlpString) rlpList2.getValues().get(i);
            byte[] rlpBytes = rlpString.getBytes();
            TypeReference<Type> typeReference = outputParameters.get(i);
            Class<Type> type;
            try {
                type = typeReference.getClassType();
            } catch (ClassNotFoundException e) {
                throw new UnsupportedOperationException("class not found:", e);
            }
            Type result = PlatOnTypeDecoder.decode(rlpBytes, type);
            results.add(result);
        }
        return results;
    }

    /**
     * 估算GasLimit
     *
     * @param web3j           web3j
     * @param estimateGasFrom 发送者
     * @param estimateGasTo   接收者
     * @param encodedData     编码后的数据
     * @return gasLimit
     * @throws IOException exception
     */
    public static BigInteger estimateGasLimit(Web3j web3j, String estimateGasFrom, String estimateGasTo, String encodedData) throws IOException {
        if (Strings.isEmpty(estimateGasTo)) {
            estimateGasTo = DEFAULT_ADDR;
        }
        Transaction transaction = Transaction.createEthCallTransaction(estimateGasFrom, estimateGasTo, encodedData);
        Request<?, PlatonEstimateGas> ethEstimateGasReq = web3j.platonEstimateGas(transaction);
        if (ethEstimateGasReq == null) {
            return DefaultGasProvider.GAS_LIMIT;
        }
        PlatonEstimateGas ethEstimateGasRes = ethEstimateGasReq.send();
        BigInteger ethEstimateGasLimit = ethEstimateGasRes.getAmountUsed();
        return ethEstimateGasLimit;
    }
}
