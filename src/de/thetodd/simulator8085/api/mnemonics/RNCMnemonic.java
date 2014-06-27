package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class RNCMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xD0;

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		if (!Processor.getInstance().isCarryFlag()) {
			// get return address from stack
			byte retHigh = Memory.getInstance().popStack();
			byte retLow = Memory.getInstance().popStack();
			short retAdr = (short) ((retHigh << 8) | retLow);

			Processor.getInstance().setProgramcounter(retAdr);
			return 12;
		} else {
			Processor.getInstance().incProgramcounter();
			return 6;
		}
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xD0);
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
