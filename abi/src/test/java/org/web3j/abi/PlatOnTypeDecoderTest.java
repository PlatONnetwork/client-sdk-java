package org.web3j.abi;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigInteger;

import org.junit.Test;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.utils.Numeric;

public class PlatOnTypeDecoderTest {

    @Test
    public void testUintDecode() {

        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("0000000000000000"),
                Uint64.class
                ),
                is(new Uint64(BigInteger.ZERO)));

        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("7fffffffffffffff"),
                Uint64.class
                ),
                is(new Uint64(BigInteger.valueOf(Long.MAX_VALUE))));
    }

    @Test
    public void testIntDecode() {
        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("0000000000000000"),
                Int64.class
                ),
                is(new Int64(BigInteger.ZERO)));

        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("7fffffffffffffff"),
                Int64.class
                ),
                is(new Int64(BigInteger.valueOf(Long.MAX_VALUE))));

        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("8000000000000000"),
                Int64.class
                ),
                is(new Int64(BigInteger.valueOf(Long.MIN_VALUE))));

        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("ffffffffffffffff"),
                Int64.class
                ),
                is(new Int64(BigInteger.valueOf(-1))));
    }


    @Test
    public void testUtf8String() {
        assertThat(PlatOnTypeDecoder.decode(
        		Numeric.hexStringToByteArray("48656c6c6f2c20776f726c6421"), 
        		Utf8String.class),
                is(new Utf8String("Hello, world!")));
    }

}
