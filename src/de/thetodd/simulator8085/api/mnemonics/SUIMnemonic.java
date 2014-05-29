package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorError;

public class SUIMnemonic implements Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length != 1) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xD6;
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

		short a = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a - b2;
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xD6);
	}

	@Override
	public byte size() {
		return 2;
	}

}
