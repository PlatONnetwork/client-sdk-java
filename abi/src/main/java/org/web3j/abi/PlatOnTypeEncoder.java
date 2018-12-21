package org.web3j.abi;

import static org.web3j.abi.datatypes.Type.MAX_BIT_LENGTH;
import static org.web3j.abi.datatypes.Type.MAX_BYTE_LENGTH;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

import org.bouncycastle.util.encoders.Hex;
import org.web3j.abi.datatypes.IntType;
import org.web3j.abi.datatypes.NumericType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Ufixed;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.utils.Numeric;

/**
 * <p>Ethereum Contract Application Binary Interface (ABI) encoding for types.
 * Further details are available
 * <a href="https://github.com/ethereum/wiki/wiki/Ethereum-Contract-ABI">here</a>.
 * </p>
 */
public class PlatOnTypeEncoder {

    private PlatOnTypeEncoder() { }

    @SuppressWarnings("unchecked")
    public static String encode(Type parameter) {
    	if (parameter instanceof IntType) {
    		return encodeInt(((IntType) parameter));
    	}else if (parameter instanceof Utf8String) {
            return encodeString((Utf8String) parameter);
        }else {
            throw new UnsupportedOperationException(
                    "Type cannot be encoded: " + parameter.getClass());
        }
    }

    private static String encodeInt(IntType intType) {
    	byte[] rawValue = toByteArray(intType);
        byte paddingValue = getPaddingValue(intType);
        byte[] paddedRawValue = new byte[intType.getBitSize()/8];
        if (paddingValue != 0) {
            for (int i = 0; i < paddedRawValue.length; i++) {
                paddedRawValue[i] = paddingValue;
            }
        }

        System.arraycopy(
                rawValue, 0,
                paddedRawValue, paddedRawValue.length - rawValue.length,
                rawValue.length);
        return Numeric.toHexStringNoPrefix(paddedRawValue);
	}
    

    private static String encodeString(Utf8String string) {
        byte[] utfEncoded = string.getValue().getBytes(StandardCharsets.UTF_8);
        return Hex.toHexString(utfEncoded);
    }


    private static byte getPaddingValue(NumericType numericType) {
        if (numericType.getValue().signum() == -1) {
            return (byte) 0xff;
        } else {
            return 0;
        }
    }

    private static byte[] toByteArray(NumericType numericType) {
        BigInteger value = numericType.getValue();
        if (numericType instanceof Ufixed || numericType instanceof Uint) {
            if (value.bitLength() == MAX_BIT_LENGTH) {
                // As BigInteger is signed, if we have a 256 bit value, the resultant byte array
                // will contain a sign byte in it's MSB, which we should ignore for this unsigned
                // integer type.
                byte[] byteArray = new byte[MAX_BYTE_LENGTH];
                System.arraycopy(value.toByteArray(), 1, byteArray, 0, MAX_BYTE_LENGTH);
                return byteArray;
            }
        }
        return value.toByteArray();
    }
}
