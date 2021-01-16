package com.platon.utils;

import org.junit.Test;

import static com.platon.utils.Assertions.verifyPrecondition;

public class AssertionsTest {

    @Test
    public void testVerifyPrecondition() {
        verifyPrecondition(true, "");
    }

    @Test(expected = RuntimeException.class)
    public void testVerifyPreconditionFailure() {
        verifyPrecondition(false, "");
    }
}
