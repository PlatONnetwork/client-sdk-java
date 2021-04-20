package com.platon.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ConvertTest {

    @Test
    public void testFromWei() {
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.VON),
                is(new BigDecimal("21000000000000")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.KVON),
                is(new BigDecimal("21000000000")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.MVON),
                is(new BigDecimal("21000000")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.GVON),
                is(new BigDecimal("21000")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.TVON),
                is(new BigDecimal("21")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.PVON),
                is(new BigDecimal("0.021")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.KPVON),
                is(new BigDecimal("0.000021")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.MPVON),
                is(new BigDecimal("0.000000021")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.GPVON),
                is(new BigDecimal("0.000000000021")));
        assertThat(Convert.fromVon("21000000000000", Convert.Unit.TPVON),
                is(new BigDecimal("0.000000000000021")));
    }

    @Test
    public void testToWei() {
        assertThat(Convert.toVon("21", Convert.Unit.VON), is(new BigDecimal("21")));
        assertThat(Convert.toVon("21", Convert.Unit.KVON), is(new BigDecimal("21000")));
        assertThat(Convert.toVon("21", Convert.Unit.MVON), is(new BigDecimal("21000000")));
        assertThat(Convert.toVon("21", Convert.Unit.GVON), is(new BigDecimal("21000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.TVON), is(new BigDecimal("21000000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.PVON),
                is(new BigDecimal("21000000000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.KPVON),
                is(new BigDecimal("21000000000000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.MPVON),
                is(new BigDecimal("21000000000000000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.GPVON),
                is(new BigDecimal("21000000000000000000000000")));
        assertThat(Convert.toVon("21", Convert.Unit.TPVON),
                is(new BigDecimal("21000000000000000000000000000")));
    }

    @Test
    public void testUnit() {
        assertThat(Convert.Unit.fromString("atp"), is(Convert.Unit.KPVON));
        assertThat(Convert.Unit.fromString("atp"), is(Convert.Unit.KPVON));
        assertThat(Convert.Unit.fromString("von"), is(Convert.Unit.VON));
    }
}
