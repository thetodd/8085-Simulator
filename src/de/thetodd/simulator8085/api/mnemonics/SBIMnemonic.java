package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class SBIMnemonic extends Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length != 1) {
			throw new IllegalArgumentException(
					"Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xDE;
		opcode[1] = (byte) Integer.decode(arguments[0]).intValue();
		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		// Get Argument
		byte b = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();

		short a = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a - b2 - 1;
		if (Processor.getInstance().isCarryFlag()) {
			c -= 1;
		}
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);

		return 7;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xDE);
	}

	@Override
	public byte size() {
		return 2;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 1 && Simulator.isNumber(args[0]);
	}

}
