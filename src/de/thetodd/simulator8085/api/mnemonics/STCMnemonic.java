package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class STCMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = 0x37;

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().setCarryFlag(true);
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == 0x37);
	}

	@Override
	public byte size() {
		return 1;
	}
}
