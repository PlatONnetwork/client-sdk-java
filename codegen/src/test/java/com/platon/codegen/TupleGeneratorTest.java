package com.platon.codegen;

import com.platon.TempFileProvider;
import org.junit.Test;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.platon.codegen.TupleGenerator.CLASS_NAME;
import static com.platon.codegen.TupleGenerator.LIMIT;
import static org.junit.Assert.assertTrue;

public class TupleGeneratorTest extends TempFileProvider {

    @Test
    public void testTuplesGeneration() throws IOException {
        com.platon.codegen.TupleGenerator.main(new String[] { tempDirPath });

        String baseDir = tempDirPath + File.separatorChar
                + com.platon.codegen.TupleGenerator.PACKAGE_NAME.replace('.', File.separatorChar);

        String fileNameBase = baseDir + File.separator + CLASS_NAME;
        List<String> fileNames = new ArrayList<>(LIMIT);
        for (int i = 1; i <= LIMIT; i++) {
            fileNames.add(fileNameBase + i + ".java");
        }
        verifyGeneratedCode(fileNames);
    }

    private void verifyGeneratedCode(List<String> sourceFiles) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        try (StandardJavaFileManager fileManager =
                     compiler.getStandardFileManager(diagnostics, null, null)) {
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromStrings(sourceFiles);
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null, fileManager, diagnostics, null, null, compilationUnits);
            assertTrue("Generated code contains compile time error", task.call());
        }
    }
}
