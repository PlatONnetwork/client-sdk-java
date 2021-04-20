package com.platon.codegen;

import com.platon.TempFileProvider;
import com.platon.utils.Strings;
import org.junit.Test;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import static com.platon.codegen.FunctionWrapperGenerator.JAVA_TYPES_ARG;
import static org.junit.Assert.assertTrue;

public class WasmFunctionWrapperGeneratorTest extends TempFileProvider {

    private String solidityBaseDir;

    @Override
    public void setUp() throws Exception {
        super.setUp();

        URL url = WasmFunctionWrapperGeneratorTest.class.getClassLoader().getResource("wasm");
        solidityBaseDir = url.getPath();
    }

    @Test
    public void testTweetAccount() throws Exception {
        testCodeGenerationJvmTypes("tweetAccount", "TweetAccount");
    }

    @Test
    public void testContractEmitEventWithAddr() throws Exception {
        testCodeGenerationJvmTypes("contractEmitEventWithAddr", "ContractEmitEventWithAddr");
    }

    @Test
    public void testEventTopic() throws Exception {
        testCodeGenerationJvmTypes("eventTopic", "EventTopic");
    }

    private void testCodeGenerationJvmTypes(String contractName, String inputFileName) throws Exception {
        testCodeGeneration(contractName, inputFileName, "org.web3j.contract.java", JAVA_TYPES_ARG);
    }

    private void testCodeGeneration(String contractName, String inputFileName, String packageName, String types)
            throws Exception {

        com.platon.codegen.WasmFunctionWrapperGenerator.main(Arrays.asList(
                types,
                solidityBaseDir + File.separator + contractName + File.separator
                        + "build" + File.separator + inputFileName + ".wasm",
                solidityBaseDir + File.separator + contractName + File.separator
                        + "build" + File.separator + inputFileName + ".abi.json",
                "-p", packageName,
                "-o", tempDirPath
        ).toArray(new String[0])); // https://shipilev.net/blog/2016/arrays-wisdom-ancients/

        verifyGeneratedCode(tempDirPath + File.separator
                + packageName.replace('.', File.separatorChar) + File.separator
                + Strings.capitaliseFirstLetter(inputFileName) + ".java");
    }

    private void verifyGeneratedCode(String sourceFile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager =
                     compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromStrings(Arrays.asList(sourceFile));
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null, fileManager, diagnostics, null, null, compilationUnits);
            boolean result = task.call();

            System.out.println(diagnostics.getDiagnostics());
            assertTrue("Generated contract contains compile time error", result);
        }
    }
}
