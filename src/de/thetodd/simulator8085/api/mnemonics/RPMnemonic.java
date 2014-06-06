package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class RPMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xF0;

		return opcode;
	}

	@Override
	public void execute() {
		if (!Processor.getInstance().isSignFlag()) {
			// get return address from stack
			byte retHigh = Memory.getInstance().popStack();
			byte retLow = Memory.getInstance().popStack();
			short retAdr = (short) ((retHigh << 8) | retLow);

			Processor.getInstance().setProgramcounter(retAdr);
		} else {
			Processor.getInstance().incProgramcounter();
		}
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xF0);
	}

	@Override
	public byte size() {
		return 1;
	}
}
