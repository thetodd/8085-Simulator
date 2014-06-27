package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class DCXMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'b':
			opcode[0] = (byte) 0x0B;
			break;
		case 'd':
			opcode[0] = (byte) 0x1B;
			break;
		case 'h':
			opcode[0] = (byte) 0x2B;
			break;
		case 's':
			opcode[0] = (byte) 0x3B;
			break;
		default:
			throw new IllegalArgumentException("Argument " + arguments[0]
					+ " wird nicht unterstuetzt!");
		}

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		switch (opcode) {
		case (byte) 0x0B:
			Processor.getInstance().setRegisterBC(
					(short) (Processor.getInstance().getRegisterBC() - 1));
			break;
		case (byte) 0x1B:
			Processor.getInstance().setRegisterDE(
					(short) (Processor.getInstance().getRegisterDE() - 1));
			break;
		case (byte) 0x2B:
			Processor.getInstance().setRegisterHL(
					(short) (Processor.getInstance().getRegisterHL() - 1));
			break;
		case (byte) 0x3B:
			Processor.getInstance().setStackpointer(
					(short) (Processor.getInstance().getStackpointer() - 1));
			break;
		default:
			break;
		}

		return 6;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0x0B) || (opcode == (byte) 0x1B)
				|| (opcode == (byte) 0x2B) || (opcode == (byte) 0x3B);
	}

	@Override
	public byte size() {
		return 1;
	}
}
