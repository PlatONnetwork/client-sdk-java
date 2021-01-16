package com.platon.codegen;

import com.platon.protocol.core.methods.response.AbiDefinition;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Test that we can parse Truffle Contract from JSON file.
 */
public class ContractJsonParseTest {

    static final String BUILD_CONTRACTS = "build" + File.separator + "contracts";
    private String contractBaseDir;

    static String jsonFileLocation(String baseDir,
            String contractName, String inputFileName) {
        return baseDir + File.separator + contractName + File.separator + BUILD_CONTRACTS
                + File.separator + inputFileName + ".json";
    }

    @SuppressWarnings("SameParameterValue")
    static com.platon.codegen.TruffleJsonFunctionWrapperGenerator.Contract parseContractJson(String baseDir,
                                                                                             String contractName, String inputFileName)
            throws Exception {

        String fileLocation = jsonFileLocation(baseDir, contractName, inputFileName);
        return com.platon.codegen.TruffleJsonFunctionWrapperGenerator.loadContractDefinition(new File(fileLocation));
    }

    @Before
    public void setUp() throws Exception {
        URL url = SolidityFunctionWrapperGeneratorTest.class.getClassLoader().getResource("truffle");
        contractBaseDir = url.getPath();
    }

    @Test
    public void testParseMetaCoin() throws Exception {
        com.platon.codegen.TruffleJsonFunctionWrapperGenerator.Contract mc = parseContractJson(contractBaseDir, "MetaCoin", "MetaCoin");

        assertEquals("Unexpected contract name", "MetaCoin", mc.getContractName());
    }

    @Test
    public void testParseConvertLib() throws Exception {
        com.platon.codegen.TruffleJsonFunctionWrapperGenerator.Contract mc = parseContractJson(contractBaseDir, "MetaCoin", "ConvertLib");

        assertEquals("Unexpected contract name", "ConvertLib", mc.getContractName());
        assertEquals("Unexpected number of functions", 1, mc.abi.size());
        AbiDefinition abi = mc.abi.get(0);
        assertEquals("Unexpected function name", "convert", abi.getName());
        assertTrue("Expected function to be 'constant'", abi.isConstant());
        assertFalse("Expected function to not be 'payable'", abi.isPayable());
        assertEquals("Expected abi to represent a function", "function", abi.getType());
        assertEquals("Expected the 'pure' for the state mutability setting", "pure",
                abi.getStateMutability());

    }
}