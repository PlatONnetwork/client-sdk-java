package com.platon.abi.solidity;

import com.platon.abi.solidity.datatypes.Event;
import com.platon.crypto.Hash;
import com.platon.utils.Numeric;

public class PlatOnEventEncoder {

    private PlatOnEventEncoder() {
    }

    public static String encode(Event event) {
        String methodSignature = buildMethodSignature(event.getName());
        return buildEventSignature(methodSignature);
    }

    public static String encodeWithFunctionType(Event event) {
        String methodSignature = buildMethodSignature(event.getFunctionType());
        return buildEventSignature(methodSignature);
    }

    private static String buildMethodSignature(String methodName) {
        StringBuilder result = new StringBuilder();
        result.append(methodName);
        return result.toString();
    }

    private static String buildMethodSignature(int functionType) {
        StringBuilder result = new StringBuilder();
        result.append(functionType);
        return result.toString();
    }

    private static String buildEventSignature(String methodSignature) {
        byte[] input = methodSignature.getBytes();
        byte[] hash = Hash.sha3(input);
        return Numeric.toHexString(hash);
    }
}
