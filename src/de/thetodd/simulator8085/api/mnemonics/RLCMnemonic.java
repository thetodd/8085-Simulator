package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Processor;

public class RLCMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = 0x07;

		return opcode;
	}

	@Override
	public void execute() {
		byte a = Processor.getInstance().getRegisterA();
		byte cf = (byte) (a & 0x80);
		a = (byte) ((a & 0xff) << 1);
		if (cf != 0x00) { // Bit 1
			a = (byte) (a | 0x01);
			Processor.getInstance().setCarryFlag(true);
		} else { // Bit 0
			Processor.getInstance().setCarryFlag(false);
		}
		Processor.getInstance().setRegisterA(a);

		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == 0x07);
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
