package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorError;

public class HLTMnemonic implements Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulässig!");
		}
		opcode[0] = 0x76;

		return opcode;
	}

	@Override
	public void execute() throws ProcessorError {
		System.out.println("HLT");

		Processor.getInstance().setProgramcounter(
				Processor.getInstance().getProgramcounter()); // Processor
																// anhalten
		throw new ProcessorError("System Halt!");
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == 0x76);
	}

	@Override
	public byte size() {
		// TODO Auto-generated method stub
		return 1;
	}

}
