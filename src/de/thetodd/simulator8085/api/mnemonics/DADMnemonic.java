package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class DADMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'b':
			opcode[0] = (byte) 0x09;
			break;
		case 'd':
			opcode[0] = (byte) 0x19;
			break;
		case 'h':
			opcode[0] = (byte) 0x29;
			break;
		case 's':
			opcode[0] = (byte) 0x39;
			break;
		default:
			throw new IllegalArgumentException("Argument " + arguments[0]
					+ " wird nicht unterstuetzt!");
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		switch (opcode) {
		case (byte) 0x09:
			Processor
					.getInstance()
					.setRegisterHL(
							(short) (Processor.getInstance().getRegisterBC() + Processor
									.getInstance().getRegisterHL()));
			break;
		case (byte) 0x19:
			Processor
					.getInstance()
					.setRegisterHL(
							(short) (Processor.getInstance().getRegisterDE() + Processor
									.getInstance().getRegisterHL()));
			break;
		case (byte) 0x29:
			Processor
					.getInstance()
					.setRegisterHL(
							(short) (Processor.getInstance().getRegisterHL() + Processor
									.getInstance().getRegisterHL()));
			break;
		case (byte) 0x39:
			Processor
					.getInstance()
					.setRegisterHL(
							(short) (Processor.getInstance().getStackpointer() + Processor
									.getInstance().getRegisterHL()));
			break;
		default:
			break;
		}
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0x09) || (opcode == (byte) 0x19)
				|| (opcode == (byte) 0x29) || (opcode == (byte) 0x39);
	}

	@Override
	public byte size() {
		return 1;
	}
}
