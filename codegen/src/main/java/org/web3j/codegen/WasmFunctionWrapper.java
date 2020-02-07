package org.web3j.codegen;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.WasmFunctionEncoder;
// import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.WasmFunction;
import org.web3j.abi.datatypes.generated.WasmAbiTypes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.WasmAbiDefinition;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.WasmContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Collection;
import org.web3j.utils.Strings;
import org.web3j.utils.Version;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

@SuppressWarnings("rawtypes")
public class WasmFunctionWrapper extends Generator {
	private static final String BINARY = "BINARY";
	private static final String WEB3J = "web3j";
	private static final String CREDENTIALS = "credentials";
	private static final String CONTRACT_GAS_PROVIDER = "contractGasProvider";
	private static final String TRANSACTION_MANAGER = "transactionManager";
	private static final String INITIAL_VALUE = "initialWeiValue";
	private static final String CONTRACT_ADDRESS = "contractAddress";
	private static final String GAS_PRICE = "gasPrice";
	private static final String GAS_LIMIT = "gasLimit";
	// private static final String FILTER = "filter";
	// private static final String START_BLOCK = "startBlock";
	// private static final String END_BLOCK = "endBlock";
	private static final String WEI_VALUE = "weiValue";
	private static final String FUNC_NAME_PREFIX = "FUNC_";

	// private static final ClassName LOG = ClassName.get(Log.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(WasmFunctionWrapper.class);

	private static final String CODEGEN_WARNING = "<p>Auto generated code.\n" + "<p><strong>Do not modify!</strong>\n" + "<p>Please use the "
			+ "<a href=\"https://docs.web3j.io/command_line.html\">web3j command line tools</a>,\n" + "or the "
			+ SolidityFunctionWrapperGenerator.class.getName() + " in the \n" + "<a href=\"https://github.com/web3j/web3j/tree/master/codegen\">"
			+ "codegen module</a> to update.\n";

	private static final String regex = "(\\w+)(?:\\[(.*?)\\])(?:\\[(.*?)\\])?";
	private static final Pattern pattern = Pattern.compile(regex);
	private final GenerationReporter reporter;

	public WasmFunctionWrapper() {
		this(new LogGenerationReporter(LOGGER));
	}

	WasmFunctionWrapper(GenerationReporter reporter) {
		this.reporter = reporter;
	}

	public void generateJavaFiles(String contractName, String bin, String abi, String destinationDir, String basePackageName)
			throws IOException, ClassNotFoundException {
		generateJavaFiles(contractName, bin, loadContractDefinition(abi), destinationDir, basePackageName);
	}

	void generateJavaFiles(String contractName, String bin, List<WasmAbiDefinition> abi, String destinationDir, String basePackageName)
			throws IOException, ClassNotFoundException {
		String className = Strings.capitaliseFirstLetter(contractName);

		TypeSpec.Builder classBuilder = createClassBuilder(className, bin);

		classBuilder.addMethod(buildConstructor(Credentials.class, CREDENTIALS));
		classBuilder.addMethod(buildConstructor(TransactionManager.class, TRANSACTION_MANAGER));

		classBuilder.addFields(buildFuncNameConstants(abi));

		classBuilder.addMethods(buildFunctionDefinitions(className, classBuilder, abi));

		classBuilder.addMethod(buildLoad(className, Credentials.class, CREDENTIALS));
		classBuilder.addMethod(buildLoad(className, TransactionManager.class, TRANSACTION_MANAGER));

		write(basePackageName, classBuilder.build(), destinationDir);
	}

	private TypeSpec.Builder createClassBuilder(String className, String binary) {
		String javadoc = CODEGEN_WARNING + getWeb3jVersion();
		return TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC).addJavadoc(javadoc).superclass(WasmContract.class)
				.addFields(createBinaryDefinition(binary));
	}

	private String getWeb3jVersion() {
		String version;
		try {
			// This only works if run as part of the web3j command line tools which contains
			// a version.properties file
			version = Version.getVersion();
		} catch (IOException | NullPointerException e) {
			version = Version.DEFAULT;
		}
		return "\n<p>Generated with web3j version " + version + ".\n";
	}

	private List<FieldSpec> createBinaryDefinition(String binary) {
		List<FieldSpec> fieldSpecs = new ArrayList<>();

		int len = binary.length();
		int size = 65534;
		int num = len % size == 0 ? len / size : len / size + 1;

		String code = "";
		for (int i = 0; i < num; i++) {
			String content = binary.substring(i * size, (i + 1) * size > len ? len : (i + 1) * size);
			String name = BINARY + "_" + i;

			fieldSpecs.add(FieldSpec.builder(String.class, name).addModifiers(Modifier.PRIVATE, Modifier.STATIC).initializer("$S", content).build());

			if (i > 0) {
				code += " + ";
			}
			code += name;
		}

		fieldSpecs.add(FieldSpec.builder(String.class, BINARY).addModifiers(Modifier.PRIVATE, Modifier.STATIC).initializer("$L", code).build());
		return fieldSpecs;
	}

	private static MethodSpec buildConstructor(Class authType, String authName) {
		MethodSpec.Builder toReturn = MethodSpec.constructorBuilder().addModifiers(Modifier.PROTECTED).addParameter(String.class, CONTRACT_ADDRESS)
				.addParameter(Web3j.class, WEB3J).addParameter(authType, authName);

		toReturn.addParameter(GasProvider.class, CONTRACT_GAS_PROVIDER).addStatement("super($N, $N, $N, $N, $N)", BINARY, CONTRACT_ADDRESS, WEB3J,
				authName, CONTRACT_GAS_PROVIDER);

		return toReturn.build();
	}

	private Iterable<FieldSpec> buildFuncNameConstants(List<WasmAbiDefinition> functionDefinitions) {
		List<FieldSpec> fields = new ArrayList<>();
		Set<String> fieldNames = new HashSet<>();
		fieldNames.add(WasmContract.FUNC_DEPLOY);

		for (WasmAbiDefinition functionDefinition : functionDefinitions) {
			if (functionDefinition.getType().equals("Action")) {
				String funcName = functionDefinition.getName();
				if (funcName.equals("init")) {
					continue;
				}

				if (!fieldNames.contains(funcName)) {
					FieldSpec field = FieldSpec.builder(String.class, funcNameToConst(funcName), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
							.initializer("$S", funcName).build();
					fields.add(field);
					fieldNames.add(funcName);
				}
			}
		}
		return fields;
	}

	private List<MethodSpec> buildFunctionDefinitions(String className, TypeSpec.Builder classBuilder, List<WasmAbiDefinition> functionDefinitions)
			throws ClassNotFoundException {

		List<MethodSpec> methodSpecs = new ArrayList<>();
		Set<String> customTypes = getCustomType(functionDefinitions);
		boolean constructor = false;

		for (WasmAbiDefinition functionDefinition : functionDefinitions) {
			if (functionDefinition.getType().equals("Action")) {
				if (functionDefinition.getName().equals("init")) {
					constructor = true;

					methodSpecs.add(buildDeploy(className, functionDefinition, Credentials.class, CREDENTIALS, true, customTypes));
					methodSpecs.add(buildDeploy(className, functionDefinition, TransactionManager.class, TRANSACTION_MANAGER, true, customTypes));
					continue;
				}

				MethodSpec ms = buildFunction(functionDefinition, customTypes);
				methodSpecs.add(ms);
			} else if (functionDefinition.getType().equals("Event")) {
				// TODO
				// methodSpecs.addAll(buildEventFunctions(functionDefinition, classBuilder, customTypes));
			} else if (functionDefinition.getType().equals("struct")) {
				classBuilder.addType(buildStruct(functionDefinition, customTypes));
			}
		}

		if (!constructor) {
			MethodSpec.Builder credentialsMethodBuilder = getDeployMethodSpec(className, Credentials.class, CREDENTIALS, false, true);
			methodSpecs.add(buildDeployNoParams(credentialsMethodBuilder, className, CREDENTIALS, false, true));

			MethodSpec.Builder transactionManagerMethodBuilder = getDeployMethodSpec(className, TransactionManager.class, TRANSACTION_MANAGER, false,
					true);
			methodSpecs.add(buildDeployNoParams(transactionManagerMethodBuilder, className, TRANSACTION_MANAGER, false, true));
		}

		return methodSpecs;
	}

	private Set<String> getCustomType(List<WasmAbiDefinition> functionDefinitions) {
		Set<String> structs = new HashSet<>();
		for (WasmAbiDefinition functionDefinition : functionDefinitions) {
			if (functionDefinition.getType().equals("struct")) {
				structs.add(functionDefinition.getName());
			}
		}
		return structs;
	}

	private MethodSpec buildDeploy(String className, WasmAbiDefinition functionDefinition, Class authType, String authName, boolean withGasProvider,
			Set<String> customTypes) {

		boolean isPayable = functionDefinition.isPayable();

		MethodSpec.Builder methodBuilder = getDeployMethodSpec(className, authType, authName, isPayable, withGasProvider);
		String inputParams = addParameters(methodBuilder, functionDefinition.getInput(), customTypes);

		if (!inputParams.isEmpty()) {
			return buildDeployWithParams(methodBuilder, className, inputParams, authName, isPayable, withGasProvider);
		} else {
			return buildDeployNoParams(methodBuilder, className, authName, isPayable, withGasProvider);
		}
	}

	private static MethodSpec.Builder getDeployMethodSpec(String className, Class authType, String authName, boolean isPayable,
			boolean withGasProvider) {

		MethodSpec.Builder builder = MethodSpec.methodBuilder("deploy").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(buildRemoteCall(ClassName.get("", className))).addParameter(Web3j.class, WEB3J).addParameter(authType, authName);

		if (isPayable && !withGasProvider) {
			return builder.addParameter(BigInteger.class, GAS_PRICE).addParameter(BigInteger.class, GAS_LIMIT).addParameter(BigInteger.class,
					INITIAL_VALUE);
		} else if (isPayable && withGasProvider) {
			return builder.addParameter(GasProvider.class, CONTRACT_GAS_PROVIDER).addParameter(BigInteger.class, INITIAL_VALUE);
		} else if (!isPayable && withGasProvider) {
			return builder.addParameter(GasProvider.class, CONTRACT_GAS_PROVIDER);
		} else {
			return builder.addParameter(BigInteger.class, GAS_PRICE).addParameter(BigInteger.class, GAS_LIMIT);
		}
	}

	private static MethodSpec buildDeployNoParams(MethodSpec.Builder methodBuilder, String className, String authName, boolean isPayable,
			boolean withGasPRovider) {
		methodBuilder.addStatement("$T encodedConstructor = $T.encodeConstructor($L, $T.asList())", String.class, WasmFunctionEncoder.class, BINARY,
				Arrays.class);

		if (isPayable && !withGasPRovider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, $L, encodedConstructor, $L)", className, WEB3J, authName,
					GAS_PRICE, GAS_LIMIT, INITIAL_VALUE);
			methodBuilder.addAnnotation(Deprecated.class);
		} else if (isPayable && withGasPRovider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, encodedConstructor, $L)", className, WEB3J, authName,
					CONTRACT_GAS_PROVIDER, INITIAL_VALUE);
		} else if (!isPayable && !withGasPRovider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, $L, encodedConstructor)", className, WEB3J, authName, GAS_PRICE,
					GAS_LIMIT);
			methodBuilder.addAnnotation(Deprecated.class);
		} else {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, encodedConstructor)", className, WEB3J, authName,
					CONTRACT_GAS_PROVIDER);
		}

		return methodBuilder.build();
	}

	private static MethodSpec buildDeployWithParams(MethodSpec.Builder methodBuilder, String className, String inputParams, String authName,
			boolean isPayable, boolean withGasProvider) {

		methodBuilder.addStatement("$T encodedConstructor = $T.encodeConstructor($L, $T.asList($L))", String.class, WasmFunctionEncoder.class, BINARY,
				Arrays.class, inputParams);

		if (isPayable && !withGasProvider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, $L, encodedConstructor, $L)", className, WEB3J, authName,
					GAS_PRICE, GAS_LIMIT, INITIAL_VALUE);
			methodBuilder.addAnnotation(Deprecated.class);
		} else if (isPayable && withGasProvider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, encodedConstructor, $L)", className, WEB3J, authName,
					CONTRACT_GAS_PROVIDER, INITIAL_VALUE);
		} else if (!isPayable && !withGasProvider) {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, $L, encodedConstructor)", className, WEB3J, authName, GAS_PRICE,
					GAS_LIMIT);
			methodBuilder.addAnnotation(Deprecated.class);
		} else {
			methodBuilder.addStatement("return deployRemoteCall($L.class, $L, $L, $L, encodedConstructor)", className, WEB3J, authName,
					CONTRACT_GAS_PROVIDER);
		}

		return methodBuilder.build();
	}

	public TypeSpec buildStruct(WasmAbiDefinition functionDefinition, Set<String> customTypes) {
		String className = Strings.capitaliseFirstLetter(functionDefinition.getName());
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.STATIC);

		List<FieldSpec> fieldSpecs = new ArrayList<>();

		if (null != functionDefinition.getBaseclass() && !functionDefinition.getBaseclass().isEmpty()) {
			String baseClass = Strings.capitaliseFirstLetter(functionDefinition.getBaseclass().get(0));
			FieldSpec field = FieldSpec.builder(ClassName.get("", baseClass), "baseClass", Modifier.PUBLIC).build();
			fieldSpecs.add(field);
		}

		for (int i = 0; i < functionDefinition.getFields().size(); i++) {
			WasmAbiDefinition.NamedType namedType = functionDefinition.getFields().get(i);
			String name = namedType.getName();
			String type = namedType.getType();

			String tmpType = null;
			Matcher matcher = pattern.matcher(type);
			if (matcher.find()) {
				tmpType = matcher.group(1);
			} else {
				tmpType = type;
			}

			if (null != tmpType && customTypes.contains(tmpType)) {
				fieldSpecs.add(FieldSpec.builder(buildCustomTypeName(type), name, Modifier.PUBLIC).build());
			} else {
				fieldSpecs.add(FieldSpec.builder(buildTypeName(type), name, Modifier.PUBLIC).build());
			}
		}
		typeBuilder.addFields(fieldSpecs);

		return typeBuilder.build();
	}

	private static MethodSpec buildLoad(String className, Class authType, String authName) {
		MethodSpec.Builder toReturn = MethodSpec.methodBuilder("load").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(ClassName.get("", className)).addParameter(String.class, CONTRACT_ADDRESS).addParameter(Web3j.class, WEB3J)
				.addParameter(authType, authName);

		toReturn.addParameter(GasProvider.class, CONTRACT_GAS_PROVIDER).addStatement("return new $L($L, $L, $L, $L)", className, CONTRACT_ADDRESS,
				WEB3J, authName, CONTRACT_GAS_PROVIDER);

		return toReturn.build();
	}

	String addParameters(MethodSpec.Builder methodBuilder, List<WasmAbiDefinition.NamedType> namedTypes, Set<String> customTypes) {
		List<ParameterSpec> inputParameterTypes = buildParameterTypes(namedTypes, customTypes);
		methodBuilder.addParameters(inputParameterTypes);
		return Collection.join(inputParameterTypes, ",", parameterSpec -> parameterSpec.name);
	}

	static List<ParameterSpec> buildParameterTypes(List<WasmAbiDefinition.NamedType> namedTypes, Set<String> customTypes) {
		List<ParameterSpec> result = new ArrayList<>(namedTypes.size());
		for (int i = 0; i < namedTypes.size(); i++) {
			WasmAbiDefinition.NamedType namedType = namedTypes.get(i);
			String name = createValidParamName(namedType.getName(), i);
			String type = namedTypes.get(i).getType();

			String tmpType = null;
			Matcher matcher = pattern.matcher(type);
			if (matcher.find()) {
				tmpType = matcher.group(1);
			} else {
				tmpType = type;
			}

			if (null != tmpType && customTypes.contains(tmpType)) {
				result.add(ParameterSpec.builder(buildCustomTypeName(type), name).build());
			} else {
				result.add(ParameterSpec.builder(buildTypeName(type), name).build());
			}
		}
		return result;
	}

	static String createValidParamName(String name, int idx) {
		if (name.equals("")) {
			return "param" + idx;
		} else {
			return name;
		}
	}

	MethodSpec buildFunction(WasmAbiDefinition functionDefinition, Set<String> customTypes) throws ClassNotFoundException {
		String functionName = functionDefinition.getName();

		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(functionName).addModifiers(Modifier.PUBLIC);

		String inputParams = addParameters(methodBuilder, functionDefinition.getInput(), customTypes);

		List<TypeName> outputParameterTypes = new ArrayList<>();
		if (functionDefinition.hasOutputs()) {
			String type = functionDefinition.getOutput();
			String tmpType = null;
			Matcher matcher = pattern.matcher(type);
			if (matcher.find()) {
				tmpType = matcher.group(1);
			} else {
				tmpType = type;
			}

			if (null != tmpType && customTypes.contains(tmpType)) {
				outputParameterTypes.add(buildCustomTypeName(type));
			} else {
				outputParameterTypes.add(buildTypeName(type));
			}
		}

		if (functionDefinition.isConstant()) {
			buildConstantFunction(functionDefinition, methodBuilder, outputParameterTypes, inputParams, customTypes);
		} else {
			buildTransactionFunction(functionDefinition, methodBuilder, inputParams);
		}

		return methodBuilder.build();
	}

	private void buildConstantFunction(WasmAbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, List<TypeName> outputParameterTypes,
			String inputParams, Set<String> customTypes) throws ClassNotFoundException {

		String functionName = functionDefinition.getName();

		if (outputParameterTypes.isEmpty()) {
			methodBuilder.addStatement("throw new RuntimeException" + "(\"cannot call constant function with void return type\")");
		} else {
			TypeName typeName = outputParameterTypes.get(0);
			methodBuilder.returns(buildRemoteCall(typeName));

			methodBuilder.addStatement("final $T function = new $T($N, $T.asList($L), $T.class)", WasmFunction.class, WasmFunction.class,
					funcNameToConst(functionName), Arrays.class, inputParams, typeName);

			methodBuilder.addStatement("return executeRemoteCall(function, $T.class)", typeName);

		}
	}

	private static ParameterizedTypeName buildRemoteCall(TypeName typeName) {
		return ParameterizedTypeName.get(ClassName.get(RemoteCall.class), typeName);
	}

	private void buildTransactionFunction(WasmAbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, String inputParams)
			throws ClassNotFoundException {

		if (functionDefinition.hasOutputs()) {
			// CHECKSTYLE:OFF
			reporter.report(String.format("Definition of the function %s returns a value but is not defined as a view function. "
					+ "Please ensure it contains the view modifier if you want to read the return value", functionDefinition.getName()));
			// CHECKSTYLE:ON
		}

		if (functionDefinition.isPayable()) {
			methodBuilder.addParameter(BigInteger.class, WEI_VALUE);
		}

		String functionName = functionDefinition.getName();

		methodBuilder.returns(buildRemoteCall(TypeName.get(TransactionReceipt.class)));

		methodBuilder.addStatement("final $T function = new $T($N, $T.asList($L), Void.class)", WasmFunction.class, WasmFunction.class,
				funcNameToConst(functionName), Arrays.class, inputParams);

		if (functionDefinition.isPayable()) {
			methodBuilder.addStatement("return executeRemoteCallTransaction(function, $N)", WEI_VALUE);
		} else {
			methodBuilder.addStatement("return executeRemoteCallTransaction(function)");
		}
	}

	static TypeName buildCustomTypeName(String type) {
		Matcher matcher = pattern.matcher(type);
		if (matcher.find()) {
			String className = Strings.capitaliseFirstLetter(matcher.group(1));
			ClassName baseType = ClassName.get("", className);
			String firstArrayDimension = matcher.group(2);
			String secondArrayDimension = matcher.group(3);

			LOGGER.debug("baseType:{},firstArrayDimension:{},secondArrayDimension:{}", baseType, firstArrayDimension, secondArrayDimension);

			TypeName typeName = ArrayTypeName.of(baseType);
			if (secondArrayDimension != null) {
				typeName = ArrayTypeName.of(typeName);
			}
			return typeName;
		} else {
			String className = Strings.capitaliseFirstLetter(type);
			return ClassName.get("", className);
		}
	}

	static TypeName buildTypeName(String type) {
		Matcher matcher = pattern.matcher(type);
		if (matcher.find()) {
			Class<?> baseType = WasmAbiTypes.getType(matcher.group(1));
			String firstArrayDimension = matcher.group(2);
			String secondArrayDimension = matcher.group(3);

			LOGGER.debug("baseType:{},firstArrayDimension:{},secondArrayDimension:{}", baseType, firstArrayDimension, secondArrayDimension);

			TypeName typeName = ArrayTypeName.of(baseType);
			if (secondArrayDimension != null) {
				typeName = ArrayTypeName.of(typeName);
			}
			return typeName;
		} else if (type.startsWith("bytes")) {
			return ArrayTypeName.of(Byte.class);
		} else {
			Class<?> cls = WasmAbiTypes.getType(type);
			return ClassName.get(cls);
		}
	}

	private List<WasmAbiDefinition> loadContractDefinition(String abi) throws IOException {
		ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
		WasmAbiDefinition[] abiDefinition = objectMapper.readValue(abi, WasmAbiDefinition[].class);
		return Arrays.asList(abiDefinition);
	}

	private static String funcNameToConst(String funcName) {
		return FUNC_NAME_PREFIX + funcName.toUpperCase();
	}
}
