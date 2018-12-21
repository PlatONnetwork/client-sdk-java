package org.web3j.abi;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.web3j.abi.datatypes.NumericType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;

public class PlatOnTypeDecoder {

    @SuppressWarnings("unchecked")
    public static <T extends Type> T decode(byte[] data, Class<T> type) {
        if (NumericType.class.isAssignableFrom(type)) {
            return (T) decodeNumeric(data, (Class<NumericType>) type);
        } else if (Utf8String.class.isAssignableFrom(type)) {
            return (T)decodeUtf8String(data); 
        } else {
            throw new UnsupportedOperationException(
                    "Type cannot be encoded: " + type.getClass());
        }
    }
    
    
    private static <T extends NumericType> T decodeNumeric(byte[] input, Class<T> type) {
        try {
        	BigInteger value;
        	if (input.length == 0) {
        		value = BigInteger.ZERO;
            }else {
            	value = new BigInteger(input);
            }
            return type.getConstructor(BigInteger.class).newInstance(value);
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new UnsupportedOperationException(
                    "Unable to create instance of " + type.getName(), e);
        }
    }
    

    private static Utf8String decodeUtf8String(byte[] input) {
        return new Utf8String(new String(input, StandardCharsets.UTF_8));
    }
}
