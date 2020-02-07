package com.platon.sdk.contracts.ppos.utils;

import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
import com.platon.sdk.contracts.ppos.abi.Function;
import com.platon.sdk.contracts.ppos.dto.common.FunctionType;
import com.platon.sdk.contracts.ppos.exception.NoSupportFunctionType;
import org.web3j.abi.datatypes.Type;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.List;

public class EstimateGasUtil {
    /**
     * 默认gasLimit固定值
     */
    private static final BigInteger BASE_DEFAULT_GAS_LIMIT = BigInteger.valueOf(21000);
    /**
     * 非0值gasLimit基数
     */
    private static final BigInteger BASE_NON_ZERO_GAS_LIMIT = BigInteger.valueOf(68);
    /**
     * 0值gasLimit基数
     */
    private static final BigInteger BASE_ZERO_GAS_LIMIT = BigInteger.valueOf(4);

    /**
     * 本地估算gasLimit
     *
     * @param function
     * @return
     */
    public static BigInteger getGasLimit(Function function) throws  NoSupportFunctionType {
        if(isSupportLocal(function.getType())){
            return  BASE_DEFAULT_GAS_LIMIT
                    .add(getContractGasLimit(function.getType()))
                    .add(getFunctionGasLimit(function.getType()))
                    .add(getInterfaceDynamicGasLimit(function.getType(),function.getInputParameters()))
                    .add(getDataGasLimit(EncoderUtils.functionEncoder(function)));
        } else {
            throw new NoSupportFunctionType();
        }
    }

    /**
     * 获取合约调用消耗
     *
     * @param type
     * @return
     */
    public static boolean isSupportLocal(int type) {
        switch (type) {
            case FunctionType.STAKING_FUNC_TYPE:
            case FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE:
            case FunctionType.ADD_STAKING_FUNC_TYPE:
            case FunctionType.WITHDREW_STAKING_FUNC_TYPE:
            case FunctionType.SUBMIT_TEXT_FUNC_TYPE:
            case FunctionType.SUBMIT_VERSION_FUNC_TYPE:
            case FunctionType.SUBMIR_PARAM_FUNCTION_TYPE:
            case FunctionType.SUBMIT_CANCEL_FUNC_TYPE:
            case FunctionType.VOTE_FUNC_TYPE:
            case FunctionType.DECLARE_VERSION_FUNC_TYPE:
            case FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE:
            case FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE:
                return true;
            default:
                return false;
        }
    }

    /**
     * 获取合约调用消耗
     *
     * @param type
     * @return
     */
    private static  BigInteger getContractGasLimit(int type) {
        switch (type) {
            case FunctionType.STAKING_FUNC_TYPE:
            case FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE:
            case FunctionType.ADD_STAKING_FUNC_TYPE:
            case FunctionType.WITHDREW_STAKING_FUNC_TYPE:
            case FunctionType.DELEGATE_FUNC_TYPE:
            case FunctionType.WITHDREW_DELEGATE_FUNC_TYPE:
                return BigInteger.valueOf(6000);
            case FunctionType.SUBMIT_TEXT_FUNC_TYPE:
            case FunctionType.SUBMIT_VERSION_FUNC_TYPE:
            case FunctionType.SUBMIR_PARAM_FUNCTION_TYPE:
            case FunctionType.SUBMIT_CANCEL_FUNC_TYPE:
            case FunctionType.VOTE_FUNC_TYPE:
            case FunctionType.DECLARE_VERSION_FUNC_TYPE:
                return BigInteger.valueOf(9000);
            case FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE:
                return BigInteger.valueOf(21000);
            case FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE:
                return BigInteger.valueOf(18000);
            default:
                return BigInteger.valueOf(0);
        }
    }

    /**
     * 根据接口类型，获取接口固定的gas消耗
     *
     * @param type
     * @return
     */
    private static  BigInteger getFunctionGasLimit(int type) {
        switch (type) {
            case FunctionType.SUBMIR_PARAM_FUNCTION_TYPE:
            case FunctionType.SUBMIT_CANCEL_FUNC_TYPE:
                return BigInteger.valueOf(500000);
            case FunctionType.SUBMIT_VERSION_FUNC_TYPE:
                return BigInteger.valueOf(450000);
            case FunctionType.SUBMIT_TEXT_FUNC_TYPE:
                return BigInteger.valueOf(320000);
            case FunctionType.STAKING_FUNC_TYPE:
                return BigInteger.valueOf(32000);
            case FunctionType.UPDATE_STAKING_INFO_FUNC_TYPE:
                return BigInteger.valueOf(12000);
            case FunctionType.ADD_STAKING_FUNC_TYPE:
            case FunctionType.WITHDREW_STAKING_FUNC_TYPE:
                return BigInteger.valueOf(20000);
            case FunctionType.DELEGATE_FUNC_TYPE:
                return BigInteger.valueOf(16000);
            case FunctionType.WITHDREW_DELEGATE_FUNC_TYPE:
            case FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE:
                return BigInteger.valueOf(8000);
            case FunctionType.DECLARE_VERSION_FUNC_TYPE:
                return BigInteger.valueOf(3000);
            case FunctionType.VOTE_FUNC_TYPE:
                return BigInteger.valueOf(2000);
            case FunctionType.REPORT_DOUBLESIGN_FUNC_TYPE:
                return BigInteger.valueOf(42000);
            default:
                return BigInteger.valueOf(0);
        }
    }

    /**
     * 获取接口动态gas消耗
     *
     * @param type
     * @param inputParameters
     * @return
     */
    private static BigInteger getInterfaceDynamicGasLimit(int type, List<Type> inputParameters) {
        if (type == FunctionType.CREATE_RESTRICTINGPLAN_FUNC_TYPE) {
            if (inputParameters.size() > 1 && inputParameters.get(1) instanceof CustomStaticArray) {
                return BigInteger.valueOf(((CustomStaticArray) inputParameters.get(1)).getValue().size()).multiply(BASE_DEFAULT_GAS_LIMIT);
            }
        }
        return BigInteger.ZERO;
    }

    /**
     * 获取data中的gas消耗
     *
     * @param rlpData
     * @return
     */
    private static BigInteger getDataGasLimit(String rlpData) {
        byte[] bytes = Numeric.hexStringToByteArray(rlpData);
        int nonZeroSize = 0;
        int zeroSize = 0;

        for (byte b : bytes) {
            if (b != 0) {
                nonZeroSize++;
            } else {
                zeroSize++;
            }
        }
        return BigInteger.valueOf(nonZeroSize).multiply(BASE_NON_ZERO_GAS_LIMIT).add(BigInteger.valueOf(zeroSize).multiply(BASE_ZERO_GAS_LIMIT));
    }
}
