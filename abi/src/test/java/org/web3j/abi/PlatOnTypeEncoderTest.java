package org.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

import org.junit.Test;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.abi.datatypes.generated.Uint64;


public class PlatOnTypeEncoderTest {


    @Test
    public void testUintEncode() {
        Uint zero = new Uint64(BigInteger.ZERO);
        assertThat(PlatOnTypeEncoder.encode(zero),
                is("0000000000000000"));

        Uint maxLong = new Uint64(BigInteger.valueOf(Long.MAX_VALUE));
        assertThat(PlatOnTypeEncoder.encode(maxLong),
                is("7fffffffffffffff"));	
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testInvalidUintEncode() {
        new Uint64(BigInteger.valueOf(-1));
    }


    @Test
    public void testIntEncode() {
        Int zero = new Int64(BigInteger.ZERO);
        assertThat(PlatOnTypeEncoder.encode(zero),
                is("0000000000000000"));

        Int maxLong = new Int64(BigInteger.valueOf(Long.MAX_VALUE));
        assertThat(PlatOnTypeEncoder.encode(maxLong),
                is("7fffffffffffffff"));
        
        Int minLong = new Int64(BigInteger.valueOf(Long.MIN_VALUE));
        assertThat(PlatOnTypeEncoder.encode(minLong),
                is("8000000000000000"));

        Int minusOne = new Int64(BigInteger.valueOf(-1));
        assertThat(PlatOnTypeEncoder.encode(minusOne),
                is("ffffffffffffffff"));
    }

    @Test
    public void testUtf8String() {
        Utf8String string = new Utf8String("Hello, world!");
        assertThat(PlatOnTypeEncoder.encode(string),
                is("48656c6c6f2c20776f726c6421"));
    }
}
