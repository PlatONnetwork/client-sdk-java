package com.platon.codegen;

import com.platon.TempFileProvider;
import org.junit.Test;


public class AbiTypesMapperGeneratorTest extends TempFileProvider {

    @Test
    public void testGeneration() throws Exception {
        com.platon.codegen.AbiTypesMapperGenerator.main(new String[] { tempDirPath });
    }
}
