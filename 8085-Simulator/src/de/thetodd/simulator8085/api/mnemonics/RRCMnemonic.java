package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Processor;

public class RRCMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = 0x0F;

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		byte a = Processor.getInstance().getRegisterA();
		byte cf = (byte) (a & 0x01);
		a = (byte) ((a & 0xff) >> 1);
		if (cf != 0x00) { // Bit 1
			a = (byte) (a | 0x80);
			Processor.getInstance().setCarryFlag(true);
		} else { // Bit 0
			Processor.getInstance().setCarryFlag(false);
		}
		Processor.getInstance().setRegisterA(a);

		Processor.getInstance().incProgramcounter();

		return 4;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == 0x0F);
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
