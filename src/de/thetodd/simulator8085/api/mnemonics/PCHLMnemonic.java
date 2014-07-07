package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Processor;

public class PCHLMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xE9;

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().setProgramcounter(
				Processor.getInstance().getRegisterHL());
		Processor.getInstance().incProgramcounter();

		return 6;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xE9);
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
