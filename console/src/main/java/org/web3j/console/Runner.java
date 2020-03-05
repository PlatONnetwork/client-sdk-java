package org.web3j.console;

import static org.web3j.utils.Collection.tail;

import org.web3j.codegen.Console;
import org.web3j.codegen.SolidityFunctionWrapperGenerator;
import org.web3j.codegen.TruffleJsonFunctionWrapperGenerator;
import org.web3j.codegen.WasmFunctionWrapperGenerator;

/**
 * Main entry point for running command line utilities.
 */
public class Runner {

	private static String USAGE = "Usage: platon-web3j version|wallet|solidity|truffle|wasm ...";

	// generate by http://patorjk.com/software/taag
	private static String LOGO = " ____  _        _  _____ ___  _   _\n" 
			                   + "|  _ \\| |      / \\|_   _/ _ \\| \\ | |\n"
			                   + "| |_) | |     / _ \\ | || | | |  \\| |\n" 
			                   + "|  __/| |___ / ___ \\| || |_| | |\\  |\n" 
			                   + "|_|   |_____/_/   \\_\\_| \\___/|_| \\_|\n";

	public static void main(String[] args) throws Exception {
		System.out.println(LOGO);

		if (args.length < 1) {
			Console.exitError(USAGE);
		} else {
			switch (args[0]) {
			case "wallet":
				WalletRunner.run(tail(args));
				break;
			case "solidity":
				SolidityFunctionWrapperGenerator.run(tail(args));
				break;
			case "truffle":
				TruffleJsonFunctionWrapperGenerator.run(tail(args));
				break;
			case "wasm":
				WasmFunctionWrapperGenerator.run(tail(args));
				break;
			default:
				Console.exitError(USAGE);
			}
		}
	}
}
