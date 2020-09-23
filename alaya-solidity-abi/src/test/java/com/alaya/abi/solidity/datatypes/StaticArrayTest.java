package com.alaya.abi.solidity.datatypes;

import java.util.stream.IntStream;

import com.alaya.abi.solidity.datatypes.generated.StaticArray3;
import com.alaya.abi.solidity.datatypes.generated.StaticArray32;
import com.alaya.abi.solidity.datatypes.generated.Uint8;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class StaticArrayTest {

    @Test
    public void canBeInstantiatedWithLessThan32Elements() {
        final StaticArray<Uint> array = new StaticArray32<>(arrayOfUints(32));

        assertThat(array.getValue().size(), equalTo(32));
    }

    @Test
    public void canBeInstantiatedWithSizeMatchingType() {
        final StaticArray<Uint> array = new StaticArray3<>(arrayOfUints(3));

        assertThat(array.getValue().size(), equalTo(3));
    }

    @Test
    public void throwsIfSizeDoesntMatchType() {
        try {
            new StaticArray3<>(arrayOfUints(4));
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo(
                    "Expected array of type [StaticArray3] to have [3] elements."));
        }
    }

    @Test
    public void throwsIfSizeIsAboveMaxOf32() {
        try {
            new StaticArray32<>(arrayOfUints(33));
            fail();
        } catch (UnsupportedOperationException e) {
            assertThat(e.getMessage(), equalTo(
                    "Static arrays with a length greater than 32 are not supported."));
        }
    }

    private Uint[] arrayOfUints(int length) {
        return IntStream.rangeClosed(1, length).mapToObj(Uint8::new).toArray(Uint[]::new);
    }
}