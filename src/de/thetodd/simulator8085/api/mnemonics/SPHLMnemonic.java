package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Processor;

public class SPHLMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xF9;

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().setStackpointer(
				Processor.getInstance().getRegisterHL());
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xF9);
	}

	@Override
	public byte size() {
		return 1;
	}
}
