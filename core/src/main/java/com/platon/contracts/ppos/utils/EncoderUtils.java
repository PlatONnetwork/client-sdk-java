package com.platon.contracts.ppos.utils;

import com.platon.abi.solidity.datatypes.BytesType;
import com.platon.abi.solidity.datatypes.IntType;
import com.platon.abi.solidity.datatypes.Type;
import com.platon.abi.solidity.datatypes.Utf8String;
import com.platon.contracts.ppos.abi.CustomStaticArray;
import com.platon.contracts.ppos.abi.CustomType;
import com.platon.contracts.ppos.abi.Function;
import com.platon.rlp.solidity.RlpEncoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import org.bouncycastle.util.encoders.Hex;

import java.util.ArrayList;
import java.util.List;

public class EncoderUtils {

    public static String functionEncoder(Function function){
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(RlpEncoder.encode(RlpString.create(function.getType()))));

        List<Type> parameters = function.getInputParameters();

        if (parameters != null && parameters.size() > 0) {

            for (Type parameter : parameters) {
                if (parameter == null) {
                    result.add(RlpString.EMPTY);
                }else if (parameter instanceof IntType) {
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
