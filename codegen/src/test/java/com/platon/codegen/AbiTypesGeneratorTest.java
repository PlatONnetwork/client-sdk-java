package com.platon.codegen;

import com.platon.TempFileProvider;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class AbiTypesGeneratorTest extends TempFileProvider {

    @Test
    public void testGetPackageName() {
        assertThat(com.platon.codegen.AbiTypesGenerator.getPackageName(String.class), is("java.lang"));
    }

    @Test
    public void testCreatePackageName() {
        assertThat(com.platon.codegen.AbiTypesGenerator.createPackageName(String.class), is("java.lang.generated"));
    }

    @Test
    public void testGeneration() throws Exception {
        com.platon.codegen.AbiTypesGenerator.main(new String[] { tempDirPath });
    }
}
