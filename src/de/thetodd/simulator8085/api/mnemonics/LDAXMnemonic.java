package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class LDAXMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg0 = arguments[0].toLowerCase().charAt(0);

		if (arg0 == 'b') {
			opcode[0] = (byte) 0x0A;
		} else if (arg0 == 'd') {
			opcode[0] = (byte) 0x1A;
		} else {
			throw new IllegalArgumentException("Argument ist nicht zulaessig!");
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		if (opcode == 0x0A) {
			Processor.getInstance().setRegisterA(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterBC()));
		} else if (opcode == 0x1A) {
			Processor.getInstance().setRegisterA(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterDE()));
		}
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == 0x0A) || (opcode == 0x1A);
	}

	@Override
	public String toString() {
		return "LDAX";
	}

	@Override
	public byte size() {
		return 1;
	}

}
