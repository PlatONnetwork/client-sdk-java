package org.web3j.codegen;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.WasmEventEncoder;
import org.web3j.abi.WasmFunctionEncoder;
import org.web3j.abi.datatypes.WasmEvent;
import org.web3j.abi.datatypes.WasmEventParameter;
import org.web3j.abi.datatypes.WasmFunction;
import org.web3j.abi.datatypes.generated.WasmAbiTypes;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.PlatonFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.WasmAbiDefinition;
import org.web3j.protocol.core.methods.response.WasmAbiDefinition.NamedType;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.WasmContract;
import org.web3j.tx.gas.GasProvider;
import org.web3j.utils.Collection;
import org.web3j.utils.Strings;
import org.web3j.utils.Version;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platon.rlp.datatypes.Pair;
import com.platon.rlp.datatypes.WasmAddress;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import rx.functions.Func1;

@SuppressWarnings("rawtypes")
public class WasmFunctionWrapper extends Generator {
	private static final String BINARY = "BINARY";
	private static final String WEB3J = "web3j";
	private static final String CREDENTIALS = "credentials";
	private static final String CONTRACT_GAS_PROVIDER = "contractGasProvider";
	private static final String TRANSACTION_MANAGER = "transactionManager";
	private static final String INITIAL_VALUE = "initialVonValue";
	private static final String CONTRACT_ADDRESS = "contractAddress";
	private static final String GAS_PRICE = "gasPrice";
	private static final String GAS_LIMIT = "gasLimit";
	private static final String FILTER = "filter";
	private static final String START_BLOCK = "startBlock";
	private static final String END_BLOCK = "endBlock";
	private static final String VON_VALUE = "vonValue";
	private static final String FUNC_NAME_PREFIX = "FUNC_";

	private static final ClassName LOG = ClassName.get(Log.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(WasmFunctionWrapper.class);

	private static final String CODEGEN_WARNING = "<p>Auto generated code.\n" + "<p><strong>Do not modify!</strong>\n" + "<p>Please use the "
			+ "<a href=\"https://github.com/PlatONnetwork/client-sdk-java/releases\">platon-web3j command line tools</a>,\n" + "or the "
			+ WasmFunctionWrapperGenerator.class.getName() + " in the \n"
			+ "<a href=\"https://github.com/PlatONnetwork/client-sdk-java/tree/master/codegen\">" + "codegen module</a> to update.\n";

	private static final String regex = "(\\w+)(?:\\[(.*?)\\])(?:\\[(.*?)\\])?";
	private static final String regex_map = "(map)(?:\\<(.*?)),(?:(.*?)\\>$)";
	private static final String regex_set = "(set)(?:\\<(.*?)\\>$)";
	private static final String regex_list = "(list)(?:\\<(.*?)\\>$)";
	private static final String regex_pair = "(pair)(?:\\<(.*?)),(?:(.*?)\\>$)";
	private static final String regex_fixedHash = "(FixedHash)(?:\\<(.*?)\\>)(?:\\[(.*?)\\])?";
	private static final Pattern pattern = Pattern.compile(regex);
	private static final Pattern pattern_map = Pattern.compile(regex_map);
	private static final Pattern pattern_set = Pattern.compile(regex_set);
	private static final Pattern pattern_list = Pattern.compile(regex_list);
	private static final Pattern pattern_pair = Pattern.compile(regex_pair);
	private static final Pattern pattern_fixedHash = Pattern.compile(regex_fixedHash);

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
		return "\n<p>Generated with platon-web3j version " + version + ".\n";
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

		fieldSpecs.add(FieldSpec.builder(String.class, BINARY).addModifiers(Modifier.PUBLIC, Modifier.STATIC).initializer("$L", code).build());
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

					methodSpecs.add(buildDeploy(className, functionDefinition, Credentials.class, CREDENTIALS, true, customTypes, false));
					methodSpecs
							.add(buildDeploy(className, functionDefinition, TransactionManager.class, TRANSACTION_MANAGER, true, customTypes, false));

					methodSpecs.add(buildDeploy(className, functionDefinition, Credentials.class, CREDENTIALS, true, customTypes, true));
					methodSpecs
							.add(buildDeploy(className, functionDefinition, TransactionManager.class, TRANSACTION_MANAGER, true, customTypes, true));
					continue;
				}

				methodSpecs.add(buildFunction(functionDefinition, customTypes, false));
				if (!functionDefinition.isConstant()) {
					methodSpecs.add(buildFunction(functionDefinition, customTypes, true));
				}
			} else if (functionDefinition.getType().equals("Event")) {
				methodSpecs.addAll(buildEventFunctions(functionDefinition, classBuilder, customTypes));
			} else if (functionDefinition.getType().equals("struct")) {
				classBuilder.addType(buildStruct(functionDefinition, customTypes));
			}
		}

		// no init method
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
			Set<String> customTypes, boolean isPayable) {
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
			fieldSpecs.add(FieldSpec.builder(buildTypeName(type, customTypes), name, Modifier.PUBLIC).build());
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
		String inputParams = Collection.join(inputParameterTypes, ",", parameterSpec -> parameterSpec.name);

		if (inputParameterTypes.size() == 1 && inputParameterTypes.get(0).type instanceof ArrayTypeName) {
			inputParams += ", Void.class";
		}
		return inputParams;
	}

	static List<ParameterSpec> buildParameterTypes(List<WasmAbiDefinition.NamedType> namedTypes, Set<String> customTypes) {
		List<ParameterSpec> result = new ArrayList<>(namedTypes.size());
		for (int i = 0; i < namedTypes.size(); i++) {
			WasmAbiDefinition.NamedType namedType = namedTypes.get(i);
			String name = createValidParamName(namedType.getName(), i);
			String type = namedTypes.get(i).getType();
			result.add(ParameterSpec.builder(buildTypeName(type, customTypes), name).build());
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

	MethodSpec buildFunction(WasmAbiDefinition functionDefinition, Set<String> customTypes, boolean isPayable) throws ClassNotFoundException {
		String functionName = functionDefinition.getName();
		MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(functionName).addModifiers(Modifier.PUBLIC);
		String inputParams = addParameters(methodBuilder, functionDefinition.getInput(), customTypes);

		List<TypeName> outputParameterTypes = new ArrayList<>();
		if (functionDefinition.hasOutputs()) {
			String type = functionDefinition.getOutput();
			outputParameterTypes.add(buildTypeName(type, customTypes));
		}

		if (functionDefinition.isConstant()) {
			buildConstantFunction(functionDefinition, methodBuilder, outputParameterTypes, inputParams, customTypes);
		} else {
			buildTransactionFunction(functionDefinition, methodBuilder, inputParams, isPayable);
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

			if (typeName instanceof ParameterizedTypeName) {
				ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
				methodBuilder.returns(buildRemoteCall(parameterizedTypeName.rawType));
				methodBuilder.addStatement("final $T function = new $T($N, $T.asList($L), $T.class, $L)", WasmFunction.class, WasmFunction.class,
						funcNameToConst(functionName), Arrays.class, inputParams, parameterizedTypeName.rawType,
						getParameterizedType(parameterizedTypeName));

				methodBuilder.addStatement("return executeRemoteCall(function, $T.class)", parameterizedTypeName.rawType);
			} else {
				methodBuilder.returns(buildRemoteCall(typeName));
				methodBuilder.addStatement("final $T function = new $T($N, $T.asList($L), $T.class)", WasmFunction.class, WasmFunction.class,
						funcNameToConst(functionName), Arrays.class, inputParams, typeName);
				methodBuilder.addStatement("return executeRemoteCall(function, $T.class)", typeName);
			}
		}
	}

	private static ParameterizedTypeName buildRemoteCall(TypeName typeName) {
		return ParameterizedTypeName.get(ClassName.get(RemoteCall.class), typeName);
	}

	private void buildTransactionFunction(WasmAbiDefinition functionDefinition, MethodSpec.Builder methodBuilder, String inputParams,
			boolean isPayable) throws ClassNotFoundException {

		if (functionDefinition.hasOutputs()) {
			// CHECKSTYLE:OFF
			reporter.report(String.format("Definition of the function %s returns a value but is not defined as a view function. "
					+ "Please ensure it contains the view modifier if you want to read the return value", functionDefinition.getName()));
			// CHECKSTYLE:ON
		}

		if (isPayable) {
			methodBuilder.addParameter(BigInteger.class, VON_VALUE);
		}
		String functionName = functionDefinition.getName();
		methodBuilder.returns(buildRemoteCall(TypeName.get(TransactionReceipt.class)));
		methodBuilder.addStatement("final $T function = new $T($N, $T.asList($L), Void.class)", WasmFunction.class, WasmFunction.class,
				funcNameToConst(functionName), Arrays.class, inputParams);
		if (isPayable) {
			methodBuilder.addStatement("return executeRemoteCallTransaction(function, $N)", VON_VALUE);
		} else {
			methodBuilder.addStatement("return executeRemoteCallTransaction(function)");
		}
	}

	List<MethodSpec> buildEventFunctions(WasmAbiDefinition functionDefinition, TypeSpec.Builder classBuilder, Set<String> customTypes) {
		String functionName = functionDefinition.getName();
		String responseClassName = Strings.capitaliseFirstLetter(functionName) + "EventResponse";

		List<NamedType> inputs = functionDefinition.getInput();
		int topic = functionDefinition.getTopic();
		List<NamedTypeName> indexedParameters = new ArrayList<>();
		List<NamedTypeName> nonIndexedParameters = new ArrayList<>();
		for (int i = 0; i < inputs.size(); i++) {
			NamedType namedType = inputs.get(i);
			String name = namedType.getName();
			String type = namedType.getType();

			TypeName typeName = buildTypeName(type, customTypes);
			boolean indexed = i < topic ? true : false;
			NamedTypeName parameter = new NamedTypeName(name, typeName, indexed);
			if (indexed) {
				indexedParameters.add(parameter);
			} else {
				nonIndexedParameters.add(parameter);
			}
		}

		classBuilder.addField(createEventDefinition(functionName, indexedParameters, nonIndexedParameters));
		classBuilder.addType(buildEventResponseObject(responseClassName, indexedParameters, nonIndexedParameters));

		List<MethodSpec> methods = new ArrayList<>();
		methods.add(buildEventTransactionReceiptFunction(responseClassName, functionName, indexedParameters, nonIndexedParameters));
		methods.add(buildEventObservableFunction(responseClassName, functionName, indexedParameters, nonIndexedParameters));
		methods.add(buildDefaultEventObservableFunction(responseClassName, functionName));
		return methods;
	}

	private FieldSpec createEventDefinition(String name, List<NamedTypeName> indexedParameters, List<NamedTypeName> nonIndexedParameters) {
		CodeBlock initializer = buildVariableLengthEventInitializer(name, indexedParameters, nonIndexedParameters);
		return FieldSpec.builder(WasmEvent.class, buildEventDefinitionName(name)).addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
				.initializer(initializer).build();
	}

	private static CodeBlock buildVariableLengthEventInitializer(String eventName, List<NamedTypeName> indexedParameters,
			List<NamedTypeName> nonIndexedParameters) {
		List<Object> objects = new ArrayList<>();
		objects.add(WasmEvent.class);
		objects.add(eventName);
		objects.add(Arrays.class);

		// indexed parameters
		String indexedParamStr = "";
		for (int i = 0; i < indexedParameters.size(); i++) {
			if (i > 0) {
				indexedParamStr += " , ";
			}
			TypeName typeName = indexedParameters.get(i).getTypeName();
			if (typeName instanceof ParameterizedTypeName) {
				ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
				String paramStr = getParameterizedType(parameterizedTypeName);

				indexedParamStr += "new $T($T.class, $L, true)";
				objects.add(WasmEventParameter.class);
				objects.add(parameterizedTypeName.rawType);
				objects.add(paramStr);
			} else {
				indexedParamStr += "new $T($T.class, true)";
				objects.add(WasmEventParameter.class);
				objects.add(typeName);
			}
		}

		// unindexed parameters
		objects.add(Arrays.class);
		String nonIndexedParamStr = "";
		for (int i = 0; i < nonIndexedParameters.size(); i++) {
			if (i > 0) {
				nonIndexedParamStr += " , ";
			}

			TypeName typeName = nonIndexedParameters.get(i).getTypeName();
			if (typeName instanceof ParameterizedTypeName) {
				ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;
				String paramStr = getParameterizedType(parameterizedTypeName);

				nonIndexedParamStr += "new $T($T.class, $L)";
				objects.add(WasmEventParameter.class);
				objects.add(parameterizedTypeName.rawType);
				objects.add(paramStr);
			} else {
				nonIndexedParamStr += "new $T($T.class)";
				objects.add(WasmEventParameter.class);
				objects.add(typeName);
			}
		}
		return CodeBlock.builder()
				.addStatement("new $T($S, $T.asList(" + indexedParamStr + "), $T.asList(" + nonIndexedParamStr + "))", objects.toArray()).build();
	}

	private String buildEventDefinitionName(String eventName) {
		return eventName.toUpperCase() + "_EVENT";
	}

	TypeSpec buildEventResponseObject(String className, List<NamedTypeName> indexedParameters, List<NamedTypeName> nonIndexedParameters) {
		TypeSpec.Builder builder = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC, Modifier.STATIC);
		builder.addField(LOG, "log", Modifier.PUBLIC);
		for (NamedTypeName namedType : indexedParameters) {
			builder.addField(ClassName.get(String.class), namedType.getName(), Modifier.PUBLIC);
		}

		for (NamedTypeName namedType : nonIndexedParameters) {
			builder.addField(namedType.getTypeName(), namedType.getName(), Modifier.PUBLIC);
		}
		return builder.build();
	}

	MethodSpec buildEventTransactionReceiptFunction(String responseClassName, String functionName, List<NamedTypeName> indexedParameters,
			List<NamedTypeName> nonIndexedParameters) {

		ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(List.class), ClassName.get("", responseClassName));

		String generatedFunctionName = "get" + Strings.capitaliseFirstLetter(functionName) + "Events";
		MethodSpec.Builder transactionMethodBuilder = MethodSpec.methodBuilder(generatedFunctionName).addModifiers(Modifier.PUBLIC)
				.addParameter(TransactionReceipt.class, "transactionReceipt").returns(parameterizedTypeName);

		transactionMethodBuilder
				.addStatement("$T valueList = extractEventParametersWithLog(" + buildEventDefinitionName(functionName) + ", " + "transactionReceipt)",
						ParameterizedTypeName.get(List.class, WasmContract.WasmEventValuesWithLog.class))
				.addStatement("$1T responses = new $1T(valueList.size())",
						ParameterizedTypeName.get(ClassName.get(ArrayList.class), ClassName.get("", responseClassName)))
				.beginControlFlow("for ($T eventValues : valueList)", WasmContract.WasmEventValuesWithLog.class)
				.addStatement("$1T typedResponse = new $1T()", ClassName.get("", responseClassName))
				.addCode(buildTypedResponse("typedResponse", indexedParameters, nonIndexedParameters, false))
				.addStatement("responses.add(typedResponse)").endControlFlow();

		transactionMethodBuilder.addStatement("return responses");
		return transactionMethodBuilder.build();
	}

	CodeBlock buildTypedResponse(String objectName, List<NamedTypeName> indexedParameters, List<NamedTypeName> nonIndexedParameters,
			boolean observable) {

		CodeBlock.Builder builder = CodeBlock.builder();
		if (observable) {
			builder.addStatement("$L.log = log", objectName);
		} else {
			builder.addStatement("$L.log = eventValues.getLog()", objectName);
		}
		for (int i = 0; i < indexedParameters.size(); i++) {
			builder.addStatement("$L.$L = ($T) eventValues.getIndexedValues().get($L)", objectName, indexedParameters.get(i).getName(), String.class,
					i);
		}
		for (int i = 0; i < nonIndexedParameters.size(); i++) {
			builder.addStatement("$L.$L = ($T) eventValues.getNonIndexedValues().get($L)", objectName, nonIndexedParameters.get(i).getName(),
					nonIndexedParameters.get(i).getTypeName(), i);
		}
		return builder.build();
	}

	MethodSpec buildEventObservableFunction(String responseClassName, String functionName, List<NamedTypeName> indexedParameters,
			List<NamedTypeName> nonIndexedParameters) {

		String generatedFunctionName = Strings.lowercaseFirstLetter(functionName) + "EventObservable";
		ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(rx.Observable.class),
				ClassName.get("", responseClassName));

		MethodSpec.Builder observableMethodBuilder = MethodSpec.methodBuilder(generatedFunctionName).addModifiers(Modifier.PUBLIC)
				.addParameter(PlatonFilter.class, FILTER).returns(parameterizedTypeName);

		TypeSpec converter = TypeSpec.anonymousClassBuilder("")
				.addSuperinterface(
						ParameterizedTypeName.get(ClassName.get(Func1.class), ClassName.get(Log.class), ClassName.get("", responseClassName)))
				.addMethod(MethodSpec.methodBuilder("call").addAnnotation(Override.class).addModifiers(Modifier.PUBLIC).addParameter(Log.class, "log")
						.returns(ClassName.get("", responseClassName))
						.addStatement("$T eventValues = extractEventParametersWithLog(" + buildEventDefinitionName(functionName) + ", log)",
								WasmContract.WasmEventValuesWithLog.class)
						.addStatement("$1T typedResponse = new $1T()", ClassName.get("", responseClassName))
						.addCode(buildTypedResponse("typedResponse", indexedParameters, nonIndexedParameters, true))
						.addStatement("return typedResponse").build())
				.build();

		observableMethodBuilder.addStatement("return web3j.platonLogObservable(filter).map($L)", converter);

		return observableMethodBuilder.build();
	}

	MethodSpec buildDefaultEventObservableFunction(String responseClassName, String functionName) {

		String generatedFunctionName = Strings.lowercaseFirstLetter(functionName) + "EventObservable";
		ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(ClassName.get(rx.Observable.class),
				ClassName.get("", responseClassName));

		MethodSpec.Builder observableMethodBuilder = MethodSpec.methodBuilder(generatedFunctionName).addModifiers(Modifier.PUBLIC)
				.addParameter(DefaultBlockParameter.class, START_BLOCK).addParameter(DefaultBlockParameter.class, END_BLOCK)
				.returns(parameterizedTypeName);

		observableMethodBuilder.addStatement("$1T filter = new $1T($2L, $3L, " + "getContractAddress())", PlatonFilter.class, START_BLOCK, END_BLOCK)
				.addStatement("filter.addSingleTopic($T.encode(" + buildEventDefinitionName(functionName) + "))", WasmEventEncoder.class)
				.addStatement("return " + generatedFunctionName + "(filter)");

		return observableMethodBuilder.build();
	}

	public static String getParameterizedType(ParameterizedTypeName parameterizedTypeName) {
		String rawType = parameterizedTypeName.rawType.toString();
		String argsType = getArgsType(parameterizedTypeName);
		return "\nnew com.platon.rlp.ParameterizedTypeImpl(\nnew java.lang.reflect.Type[] {" + argsType + "}, \n" + rawType + ".class" + ", \n"
				+ rawType + ".class)";
	}

	public static String getArgsType(ParameterizedTypeName parameterizedTypeName) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < parameterizedTypeName.typeArguments.size(); i++) {
			TypeName argType = parameterizedTypeName.typeArguments.get(i);
			if (argType instanceof ParameterizedTypeName) {
				builder.append(getParameterizedType((ParameterizedTypeName) argType));
			} else {
				if (argType instanceof ArrayTypeName) {
					builder.append(((ArrayTypeName) argType).toString() + ".class");
				} else {
					builder.append(((ClassName) argType).toString() + ".class");
				}
			}
			if (i < parameterizedTypeName.typeArguments.size() - 1) {
				builder.append(", ");
			}
		}
		return builder.toString();
	}

	static TypeName buildTypeName(String type, Set<String> customTypes) {

		// map
		Matcher matcherMap = pattern_map.matcher(type);
		if (matcherMap.find()) {
			String mapName = matcherMap.group(1);
			String keyName = matcherMap.group(2);
			String valueName = matcherMap.group(3);

			LOGGER.debug("buildTypeName >>> map >>> mapName:{},keyName:{},value:{}", mapName, keyName, valueName);

			TypeName keyTypeName = buildTypeName(keyName, customTypes);
			TypeName valueTypeName = buildTypeName(valueName, customTypes);
			return ParameterizedTypeName.get(ClassName.get(Map.class), keyTypeName, valueTypeName);
		}

		// set
		Matcher matcherSet = pattern_set.matcher(type);
		if (matcherSet.find()) {
			String setName = matcherSet.group(1);
			String parameterizedName = matcherSet.group(2);

			LOGGER.debug("buildTypeName >>> set >>> setName:{},parameterizedName:{}", setName, parameterizedName);

			TypeName parameterizedTypeName = buildTypeName(parameterizedName, customTypes);
			return ParameterizedTypeName.get(ClassName.get(Set.class), parameterizedTypeName);
		}

		// list
		Matcher matcherList = pattern_list.matcher(type);
		if (matcherList.find()) {
			String listName = matcherList.group(1);
			String parameterizedName = matcherList.group(2);

			LOGGER.debug("buildTypeName >>> list >>> listName:{},parameterizedName:{}", listName, parameterizedName);

			TypeName parameterizedTypeName = buildTypeName(parameterizedName, customTypes);
			return ParameterizedTypeName.get(ClassName.get(List.class), parameterizedTypeName);
		}

		// pair
		Matcher matcherPair = pattern_pair.matcher(type);
		if (matcherPair.find()) {
			String pairName = matcherPair.group(1);
			String keyName = matcherPair.group(2);
			String valueName = matcherPair.group(3);

			LOGGER.debug("buildTypeName >>> pair >>> pairName:{},keyName:{},value:{}", pairName, keyName, valueName);

			TypeName keyTypeName = buildTypeName(keyName, customTypes);
			TypeName valueTypeName = buildTypeName(valueName, customTypes);
			return ParameterizedTypeName.get(ClassName.get(Pair.class), keyTypeName, valueTypeName);
		}

		// FixedHash
		Matcher matcherFixedHash = pattern_fixedHash.matcher(type);
		if (matcherFixedHash.find()) {
			String name = matcherFixedHash.group(1);
			String size = matcherFixedHash.group(2);
			String arrayLen = matcherFixedHash.group(3);

			LOGGER.debug("buildTypeName >>> FixedHash >>> name:{},size:{},array length:{}", name, size, arrayLen);

			TypeName typeName;
			if (null != size && Integer.parseInt(size) == 20) {
				typeName = ClassName.get(WasmAddress.class);
			} else {
				typeName = ArrayTypeName.of(byte.class);
			}
			if (arrayLen != null) {
				typeName = ArrayTypeName.of(typeName);
			}
			return typeName;
		}

		// basic and custom types
		String baseTypeName;
		Matcher matcher = pattern.matcher(type);
		if (matcher.find()) { // array
			baseTypeName = matcher.group(1);
		} else {
			baseTypeName = type;
		}

		if (customTypes.contains(baseTypeName)) {
			matcher = pattern.matcher(type);
			if (matcher.find()) { // array
				String className = Strings.capitaliseFirstLetter(matcher.group(1));
				ClassName baseType = ClassName.get("", className);
				TypeName typeName = ArrayTypeName.of(baseType);

				// String firstArrayDimension = matcher.group(2);
				String secondArrayDimension = matcher.group(3);
				if (secondArrayDimension != null) {
					typeName = ArrayTypeName.of(typeName);
				}
				return typeName;
			} else {
				String className = Strings.capitaliseFirstLetter(type);
				return ClassName.get("", className);
			}
		} else {
			matcher = pattern.matcher(type);
			if (matcher.find()) { // array
				Class<?> baseType = WasmAbiTypes.getRawType(matcher.group(1));
				TypeName typeName = ArrayTypeName.of(baseType);

				// String firstArrayDimension = matcher.group(2);
				String secondArrayDimension = matcher.group(3);
				if (secondArrayDimension != null) {
					typeName = ArrayTypeName.of(typeName);
				}
				return typeName;
			} else {
				Class<?> cls = WasmAbiTypes.getType(type);
				return ClassName.get(cls);
			}
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

	public static class NamedTypeName {
		private final TypeName typeName;
		private final String name;
		private final boolean indexed;

		NamedTypeName(String name, TypeName typeName, boolean indexed) {
			this.name = name;
			this.typeName = typeName;
			this.indexed = indexed;
		}

		public String getName() {
			return name;
		}

		public TypeName getTypeName() {
			return typeName;
		}

		public boolean isIndexed() {
			return indexed;
		}
	}
}
