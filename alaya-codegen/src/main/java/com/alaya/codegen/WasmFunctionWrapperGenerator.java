package com.alaya.codegen;

import static com.alaya.codegen.Console.exitError;
import static com.alaya.utils.Collection.tail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.alaya.protocol.ObjectMapperFactory;
import com.alaya.protocol.core.methods.response.WasmAbiDefinition;
import com.alaya.utils.Files;
import com.alaya.utils.Numeric;
import com.alaya.utils.Strings;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WasmFunctionWrapperGenerator extends FunctionWrapperGenerator {
	private static final String USAGE = "wasm generate <input binary file>.bin <input abi file>.abi.json " + "-p|--package <base package name> "
			+ "-o|--output <destination base directory>";

	private final String binaryFileLocation;
	private final String absFileLocation;

	private WasmFunctionWrapperGenerator(String binaryFileLocation, String absFileLocation, String destinationDirLocation, String basePackageName,
			boolean useJavaNativeTypes) {

		super(destinationDirLocation, basePackageName, useJavaNativeTypes);
		this.binaryFileLocation = binaryFileLocation;
		this.absFileLocation = absFileLocation;
	}

	public static void run(String[] args) throws Exception {
		if (args.length < 1 || !args[0].equals("generate")) {
			exitError(USAGE);
		} else {
			main(tail(args));
		}
	}

	public static void main(String[] args) throws Exception {

		String[] fullArgs;
		if (args.length == 6) {
			fullArgs = new String[args.length + 1];
			fullArgs[0] = JAVA_TYPES_ARG;
			System.arraycopy(args, 0, fullArgs, 1, args.length);
		} else {
			fullArgs = args;
		}

		if (fullArgs.length != 7) {
			exitError(USAGE);
		}

		boolean useJavaNativeTypes = useJavaNativeTypes(fullArgs[0], USAGE);

		String binaryFileLocation = parsePositionalArg(fullArgs, 1);
		String absFileLocation = parsePositionalArg(fullArgs, 2);
		String destinationDirLocation = parseParameterArgument(fullArgs, "-o", "--outputDir");
		String basePackageName = parseParameterArgument(fullArgs, "-p", "--package");

		if (binaryFileLocation.equals("") || absFileLocation.equals("") || destinationDirLocation.equals("") || basePackageName.equals("")) {
			exitError(USAGE);
		}

		new WasmFunctionWrapperGenerator(binaryFileLocation, absFileLocation, destinationDirLocation, basePackageName, useJavaNativeTypes).generate();
	}

	static List<WasmAbiDefinition> loadContractDefinition(File absFile) throws IOException {
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		WasmAbiDefinition[] abiDefinition = objectMapper.readValue(absFile, WasmAbiDefinition[].class);
		return Arrays.asList(abiDefinition);
	}

	private void generate() throws IOException, ClassNotFoundException {

		File binaryFile = new File(binaryFileLocation);
		if (!binaryFile.exists()) {
			exitError("Invalid input binary file specified: " + binaryFileLocation);
		}

		byte[] bytes = Files.readBytes(new File(binaryFile.toURI()));
		// String binary = new String(bytes);
		String binary = Numeric.toHexString(bytes);

		File absFile = new File(absFileLocation);
		if (!absFile.exists() || !absFile.canRead()) {
			exitError("Invalid input ABI file specified: " + absFileLocation);
		}
		String fileName = absFile.getName();
		String contractName = getFileNameNoExtension(getFileNameNoExtension(fileName));
		bytes = Files.readBytes(new File(absFile.toURI()));
		String abi = new String(bytes);

		List<WasmAbiDefinition> functionDefinitions = loadContractDefinition(absFile);

		if (functionDefinitions.isEmpty()) {
			exitError("Unable to parse input ABI file");
		} else {
			String className = Strings.capitaliseFirstLetter(contractName);

			System.out.println("Generating " + basePackageName + "." + className + " ... ");

			new WasmFunctionWrapper().generateJavaFiles(contractName, binary, abi, destinationDirLocation.toString(), basePackageName);

			System.out.println("File written to " + destinationDirLocation.toString() + "\n");
		}
	}
}
