package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class NOPMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulässig!");
		}
		opcode[0] = 0x00;

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == 0x00);
	}

	@Override
	public byte size() {
		// TODO Auto-generated method stub
		return 1;
	}
}
