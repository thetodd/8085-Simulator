package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Processor;

public class HLTMnemonic extends Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = 0x76;

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		System.out.println("HLT");

		Processor.getInstance().setProgramcounter(
				Processor.getInstance().getProgramcounter()); // Processor
																// anhalten
		throw new ProcessorError("System Halt!");
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == 0x76);
	}

	@Override
	public byte size() {
		return 1;
	}
	
	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 0;
	}

}
