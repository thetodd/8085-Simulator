package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class PUSHMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length != 1) {
			throw new IllegalArgumentException("Falsche Anzahl Argumente!");
		}
		String arg = arguments[0].toLowerCase();
		if (arg.equals("b")) {
			opcode[0] = (byte) 0xC5;
		} else if (arg.equals("d")) {
			opcode[0] = (byte) 0xD5;
		} else if (arg.equals("h")) {
			opcode[0] = (byte) 0xE5;
		} else if (arg.equals("psw")) {
			opcode[0] = (byte) 0xF5;
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		switch (opcode) {
		case (byte) 0xC5:
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterB());
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterC());
			break;
		case (byte) 0xD5:
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterD());
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterE());
			break;
		case (byte) 0xE5:
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterH());
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterL());
			break;
		case (byte) 0xF5:
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterA());
			Memory.getInstance().pushStack(
					Processor.getInstance().getRegisterF());
			break;
		default:
			break;
		}
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xC5) || (opcode == (byte) 0xD5)
				|| (opcode == (byte) 0xE5) || (opcode == (byte) 0xF5);
	}

	@Override
	public byte size() {
		return 1;
	}
}
