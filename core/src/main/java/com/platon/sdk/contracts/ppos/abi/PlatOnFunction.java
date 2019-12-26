package com.platon.sdk.contracts.ppos.abi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.datatypes.*;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Numeric;

import com.platon.sdk.contracts.ppos.dto.common.FunctionType;

public class PlatOnFunction {

    /**
     * 默认的gasPrice
     */
    private static final BigInteger BASE_DEFAULT_GAS_PRICE = BigInteger.valueOf(500000000000L);
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

    private int type;

    private List<Type> inputParameters;

    protected GasProvider gasProvider;

    public PlatOnFunction(int type) {
        this.type = type;
    }

    public PlatOnFunction(int type, GasProvider gasProvider) {
        this.type = type;
        this.gasProvider = gasProvider;
    }

    public PlatOnFunction(int type, List<Type> inputParameters) {
        this.type = type;
        this.inputParameters = inputParameters;
    }

    public PlatOnFunction(int type, List<Type> inputParameters, GasProvider gasProvider) {
        this.type = type;
        this.inputParameters = inputParameters;
        this.gasProvider = gasProvider;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Type> getInputParameters() {
        return inputParameters;
    }

    public void setInputParameters(List<Type> inputParameters) {
        this.inputParameters = inputParameters;
    }


    public GasProvider getGasProvider() {
        if (gasProvider == null) {
            BigInteger gasLimit = BASE_DEFAULT_GAS_LIMIT.add(getContractGasLimit())
                    .add(getFunctionGasLimit()).add(getInterfaceDynamicGasLimit()).add(getDataGasLimit());
            return new ContractGasProvider(getGasPrice(), gasLimit);
        }
        return gasProvider;
    }

    public BigInteger getFeeAmount(BigInteger gasPrice) {
        BigInteger gasLimit = BASE_DEFAULT_GAS_LIMIT.add(getContractGasLimit())
                .add(getFunctionGasLimit()).add(getInterfaceDynamicGasLimit()).add(getDataGasLimit());
        return gasLimit.multiply(gasPrice == null || gasPrice.compareTo(BigInteger.ZERO) != 1 ? getGasPrice() : gasPrice);
    }

    private BigInteger getGasPrice() {
        switch (type) {
            case FunctionType.SUBMIT_TEXT_FUNC_TYPE:
                return BigInteger.valueOf(1500000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIT_VERSION_FUNC_TYPE:
                return BigInteger.valueOf(2100000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIR_PARAM_FUNCTION_TYPE:
                return BigInteger.valueOf(2000000).multiply(BigInteger.valueOf(1000000000));
            case FunctionType.SUBMIT_CANCEL_FUNC_TYPE:
                return BigInteger.valueOf(3000000).multiply(BigInteger.valueOf(1000000000));
            default:
                return BASE_DEFAULT_GAS_PRICE;
        }
    }

    /**
     * 获取合约调用消耗
     *
     * @return
     */
    private BigInteger getContractGasLimit() {
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
     * @return
     */
    private BigInteger getFunctionGasLimit() {
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
     * @return
     */
    private BigInteger getInterfaceDynamicGasLimit() {
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
     * @return
     */
    private BigInteger getDataGasLimit() {
        byte[] bytes = Numeric.hexStringToByteArray(getEncodeData());  
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

    public String getEncodeData() {

        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(RlpEncoder.encode(RlpString.create(type))));

        List<Type> parameters = inputParameters;

        if (parameters != null && parameters.size() > 0) {

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
                } else if (parameter instanceof DynamicArray) {
                    //TODO
                }
            }
        }

        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }
}
