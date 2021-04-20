package com.platon.utils;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VersionTest {

    @Test
    public void testGetVersion() throws IOException {
        assertThat(Version.getVersion(), Is.is(Version.DEFAULT));
    }

    @Test
    public void testGetTimestamp() throws IOException {
        assertThat(Version.getTimestamp(), is("2017-01-31 01:21:09.843 UTC"));
    }
}
