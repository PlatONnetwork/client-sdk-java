package com.platon.sdk.contracts.ppos.utils;

import com.platon.sdk.contracts.ppos.abi.CustomStaticArray;
import com.platon.sdk.contracts.ppos.abi.CustomType;
import com.platon.sdk.contracts.ppos.abi.Function;
import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.datatypes.*;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.rlp.RlpType;

import java.util.ArrayList;
import java.util.List;

public class EncoderUtils {

    public static String functionEncoder(Function function){
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(RlpEncoder.encode(RlpString.create(function.getType()))));

        List<Type> parameters = function.getInputParameters();

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
                }
            }
        }

        String data = Hex.toHexString(RlpEncoder.encode(new RlpList(result)));
        return data;
    }
}
