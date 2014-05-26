package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class ORIMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length > 1) {
			throw new IllegalArgumentException("Falsche Anzahl von Argumenten!");
		}
		opcode[0] = (byte) 0xF6;
		opcode[1] = Integer.decode(arguments[0]).byteValue();

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().incProgramcounter();
		byte b2 = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		byte a = Processor.getInstance().getRegisterA();
		byte c = (byte) (a | b2);
		Processor.getInstance().setRegisterA(c);

		Processor.getInstance().incProgramcounter();

		// Set flags
		Processor.getInstance().setFlags(c);
		Processor.getInstance().setAuxiliaryCarryFlag(false);
		Processor.getInstance().setCarryFlag(false);		
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xF6);
	}

	@Override
	public byte size() {
		return 2;
	}
}
