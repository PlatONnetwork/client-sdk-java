package org.web3j.abi;

import com.platon.rlp.datatypes.*;
import org.junit.Test;
import org.web3j.abi.datatypes.Array;
import org.web3j.abi.datatypes.WasmEvent;
import org.web3j.abi.datatypes.WasmEventParameter;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * FixedHash<N>
 *       FixedHash<20>   ->  WasmAddress
 *       FixedHash<!20>  ->  byte[]
 *       list<int8>      ->  List<Int8>
 *       int8[]          ->  Int8[]
 *
 */
public class WasmEventEncoderTest {

    @Test
    public void encodeNumber() {
        BigInteger number = BigInteger.valueOf(246567L);

        String result = WasmEventEncoder.encodeNumeric(Uint128.of(number));
        assertThat(result, is("0x000000000000000000000000000000000000000000000000000000000003c327"));

        number = BigInteger.valueOf(-246567L);
        result = WasmEventEncoder.encodeNumeric(Int128.of(number));
        assertThat(result, is("0x000000000000000000000000000000000000000000000000000000000007864d"));
    }

    @Test
    public void encodeBoolean() {
        Boolean bool =  true;
        String result = WasmEventEncoder.encodeBoolean(bool);
        assertThat(result, is("0x0000000000000000000000000000000000000000000000000000000000000001"));

        bool =  false;
        result = WasmEventEncoder.encodeBoolean(bool);
        assertThat(result, is("0x0000000000000000000000000000000000000000000000000000000000000000"));

    }

    @Test
    public void encodeString() {
        String string = "你好abc";
        String result = WasmEventEncoder.encodeString(string);
        assertThat(result, is("0x0000000000000000000000000000000000000000000000e4bda0e5a5bd616263"));

        string = "你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc";
        result = WasmEventEncoder.encodeString(string);
        assertThat(result, is("0xa294cd0e9eff651914801137f53e951abab16791a6a27a7314ac202e35f9e221"));
    }

    @Test
    public void encodeAddress() {
        WasmAddress address = new WasmAddress("lat1f7wp58h65lvphgw2hurl9sa943w0f7qc7gn7tq");

        String result = WasmEventEncoder.encodeAddress(address);
        assertThat(result, is("0x0000000000000000000000004f9c1a1efaa7d81ba1cabf07f2c3a5ac5cf4f818"));
    }

    @Test
    public void encodeIntUint8List() {
        List<Uint8> uintList = Arrays.asList(Uint8.of(240), Uint8.of(12));
        String result = WasmEventEncoder.encodeList(uintList);
        assertThat(result, is("0x000000000000000000000000000000000000000000000000000000000000f00c"));

        List<Int8> intList = Arrays.asList(Int8.of(120), Int8.of(-12));
        result = WasmEventEncoder.encodeList(intList);
        assertThat(result, is("0x00000000000000000000000000000000000000000000000000000000000078f4"));
    }

    @Test
    public void encodeWasmEvent() {
        WasmEvent event = new WasmEvent("nameAndAddr", Arrays.asList(), Arrays.asList(new WasmEventParameter(Int8[].class) , new WasmEventParameter(List.class,
                new com.platon.rlp.ParameterizedTypeImpl(
                        new java.lang.reflect.Type[] {com.platon.rlp.datatypes.Int8.class},
                        java.util.List.class,
                        java.util.List.class)) , new WasmEventParameter(Int8[].class)));

        String result = WasmEventEncoder.encode(event);
        assertThat(result, is("0x0000000000000000000000000000000000000000006e616d65416e6441646472"));
    }

    private void char2hex(){
        char[] bytes = {228, 189, 160, 229, 165, 189, 97, 98, 99, 228, 189, 160, 229, 165, 189, 97, 98, 99, 228, 189, 160, 229, 165, 189, 97, 98, 99, 228, 189, 160, 229, 165};
        System.out.println();
        for (int i = 0; i < bytes.length ; i++) {
            System.out.print(BigInteger.valueOf(bytes[i]).toString(16));
        }
        System.out.println();
    }
}
