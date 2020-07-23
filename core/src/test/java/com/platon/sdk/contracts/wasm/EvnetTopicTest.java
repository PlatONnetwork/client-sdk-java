package com.platon.sdk.contracts.wasm;

import com.platon.rlp.datatypes.*;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.web3j.abi.WasmEventEncoder;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.PlatonFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Arrays;

import static org.junit.Assert.assertThat;

public class EvnetTopicTest extends BaseContractTest {

    private String address = "lax1sm0s7468yxscr9tpt3qqd4fepmlywn7rtm708k";

//    @Test
    public void deployByC() throws Exception {
        EventTopic contract = EventTopic.deploy(web3j, credentials, gasProvider, chainId).send();
        System.out.println("address="+contract.getContractAddress());
    }

    @Test
    public void setStringAndAddressAndBoolean() throws Exception {
        final String str = "你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc你好abc";
        WasmAddress address = new WasmAddress("lat1f7wp58h65lvphgw2hurl9sa943w0f7qc7gn7tq");
        final Boolean bool = true;

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setStringAndAddressAndBoolean(str,address,bool).send();
        contract.getStringAndAddrAndBooleanEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeString(str)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeAddress(address)));
            assertThat(log.topic3, CoreMatchers.is(WasmEventEncoder.encodeBoolean(bool)));
        });

        final String strShort = "你好abc";
        final Boolean boolFalse = false;
        transactionReceipt = contract.setStringAndAddressAndBoolean(strShort, address, boolFalse).send();
        contract.getStringAndAddrAndBooleanEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeString(strShort)));
            assertThat(log.topic3, CoreMatchers.is(WasmEventEncoder.encodeBoolean(boolFalse)));
        });

    }

    @Test
    public void setIntNumber() throws Exception {
        Int8 int8 = Int8.of(-100);
        Int16 int16= Int16.of(-1000);

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setIntNumber(int8, int16).send();

        contract.getIntNumberEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int8)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int16)));
        });

        Int8 int8z = Int8.of(100);
        Int16 int16z= Int16.of(10000);

        transactionReceipt = contract.setIntNumber(int8z, int16z).send();

        contract.getIntNumberEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int8z)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int16z)));
        });
    }


    @Test
    public void setUintNumber() throws Exception {
        Uint8 int8 = Uint8.of(100);
        Uint16 int16= Uint16.of(1000);

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setUintNumber(int8, int16).send();

        contract.getUintNumberEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int8)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int16)));
        });

        Uint8 int8z = Uint8.of(200);
        Uint16 int16z= Uint16.of(42767);

        transactionReceipt = contract.setUintNumber(int8z, int16z).send();

        contract.getUintNumberEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int8z)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeNumeric(int16z)));
        });
    }

    @Test
    public void setIntArray() throws Exception {
        Int8[] int8s = new Int8[]{Int8.of(-100), Int8.of(120)};
        Int16[] int16s = new Int16[]{Int16.of(-1000), Int16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setIntArray(int8s, int16s).send();

        contract.getIntArrayEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeArray(int8s)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16s)));
        });
    }

    @Test
    public void setUintArray() throws Exception {
        Uint8[] int8s = new Uint8[]{Uint8.of(100), Uint8.of(11)};
        Uint16[] int16s = new Uint16[]{Uint16.of(42767), Uint16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setUintArray(int8s, int16s).send();

        contract.getUintArrayEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeArray(int8s)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16s)));
        });
    }

    @Test
    public void setIntVector() throws Exception {
        Int8[] int8s = new Int8[]{Int8.of(-100), Int8.of(120)};
        Int16[] int16s = new Int16[]{Int16.of(-1000), Int16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setIntVector(int8s, int16s).send();

        contract.getIntVectorEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeArray(int8s)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16s)));
        });


        Int8[] int8sl = new Int8[]{ Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                                    Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                                    Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                                    Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                                    Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120)};
        Int16[] int16sl = new Int16[]{Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200)};

        transactionReceipt = contract.setIntVector(int8sl, int16sl).send();

        contract.getIntVectorEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeArray(int8sl)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16sl)));
        });
    }

    @Test
    public void setUintVector() throws Exception {
        byte[] int8s = new byte[]{100, 11};
        Uint16[] int16s = new Uint16[]{Uint16.of(42767), Uint16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setUintVector(int8s, int16s).send();

        contract.getUintVectorEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeBytes(int8s)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16s)));
        });

        byte[]  int8sl = new byte[]{ 100, 11, 100, 11, 100, 11, 100, 11,
                100, 11, 100, 11, 100, 11, 100, 11,
                100, 11, 100, 11, 100, 11, 100, 11,
                100, 11, 100, 11, 100, 11, 100, 11,
                100, 11, 100, 11, 100, 11, 100, 11};
        Uint16[] int16sl = new Uint16[]{Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200)};

        transactionReceipt = contract.setUintVector(int8sl, int16sl).send();

        contract.getUintVectorEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeBytes(int8sl)));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeArray(int16sl)));
        });
    }


    @Test
    public void setIntList() throws Exception {
        Int8[] int8s = new Int8[]{Int8.of(-100), Int8.of(120)};
        Int16[] int16s = new Int16[]{Int16.of(-1000), Int16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setIntList(Arrays.asList(int8s), Arrays.asList(int16s)).send();

        contract.getIntListEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeList(Arrays.asList(int8s))));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeList( Arrays.asList(int16s))));
        });


        Int8[] int8sl = new Int8[]{ Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),
                Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120),Int8.of(-100), Int8.of(120)};
        Int16[] int16sl = new Int16[]{Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),
                Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200),Int16.of(-1000), Int16.of(1200)};

        transactionReceipt = contract.setIntList(Arrays.asList(int8sl), Arrays.asList(int16sl)).send();

        contract.getIntListEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeList(Arrays.asList(int8sl))));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeList( Arrays.asList(int16sl))));
        });
    }

    @Test
    public void setUIintList() throws Exception {
        Uint8[] int8s = new Uint8[]{Uint8.of(100), Uint8.of(11)};
        Uint16[] int16s = new Uint16[]{Uint16.of(42767), Uint16.of(1200)};

        EventTopic contract = loadD();
        TransactionReceipt transactionReceipt = contract.setUintList(Arrays.asList(int8s), Arrays.asList(int16s)).send();

        contract.getUintListEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeList(Arrays.asList(int8s))));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeList( Arrays.asList(int16s))));
        });

        Uint8[] int8sl = new Uint8[]{ Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),
                Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),
                Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),
                Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),
                Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11),Uint8.of(100), Uint8.of(11)};
        Uint16[] int16sl = new Uint16[]{Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),
                Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200),Uint16.of(42767), Uint16.of(1200)};

        transactionReceipt = contract.setUintList(Arrays.asList(int8sl), Arrays.asList(int16sl)).send();

        contract.getUintListEvents(transactionReceipt).forEach(log ->{
            assertThat(log.topic1, CoreMatchers.is(WasmEventEncoder.encodeList(Arrays.asList(int8sl))));
            assertThat(log.topic2, CoreMatchers.is(WasmEventEncoder.encodeList(Arrays.asList(int16sl))));
        });
    }





    private EventTopic loadD(){
        return EventTopic.load(address, web3j, credentials, gasProvider, chainId);
    }
}
