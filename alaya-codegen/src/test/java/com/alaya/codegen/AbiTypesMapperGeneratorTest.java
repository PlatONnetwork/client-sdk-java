package com.alaya.codegen;

import org.junit.Test;

import com.alaya.TempFileProvider;


public class AbiTypesMapperGeneratorTest extends TempFileProvider {

    @Test
    public void testGeneration() throws Exception {
        AbiTypesMapperGenerator.main(new String[] { tempDirPath });
    }
}
