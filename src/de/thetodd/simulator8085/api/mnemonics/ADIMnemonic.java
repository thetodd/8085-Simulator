package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class ADIMnemonic implements Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length != 1) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xC6;
		opcode[1] = (byte) Integer.decode(arguments[0]).intValue();
		return opcode;
	}

	@Override
	public void execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		// Get Argument
		byte b = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 + b2;
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xC6);
	}

	@Override
	public byte size() {
		return 2;
	}

}
