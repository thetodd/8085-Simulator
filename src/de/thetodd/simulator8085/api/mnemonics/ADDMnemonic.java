package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class ADDMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x87;
			break;
		case 'm':
			opcode[0] = (byte) 0x86;
			break;
		case 'b':
			opcode[0] = (byte) 0x80;
			break;
		case 'c':
			opcode[0] = (byte) 0x81;
			break;
		case 'd':
			opcode[0] = (byte) 0x82;
			break;
		case 'e':
			opcode[0] = (byte) 0x83;
			break;
		case 'h':
			opcode[0] = (byte) 0x84;
			break;
		case 'l':
			opcode[0] = (byte) 0x85;
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
		byte b = 0;
		switch (opcode) {
		case (byte) 0x87:
			b = Processor.getInstance().getRegisterA();
			break;
		case (byte) 0x80:
			b = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0x81:
			b = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0x82:
			b = (Processor.getInstance().getRegisterD());
			break;
		case (byte) 0x83:
			b = (Processor.getInstance().getRegisterE());
			break;
		case (byte) 0x84:
			b = (Processor.getInstance().getRegisterH());
			break;
		case (byte) 0x85:
			b = (Processor.getInstance().getRegisterL());
			break;
		default:
			break;
		}

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 + b2;
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode >= (byte) 0x80) && (opcode <= (byte) 0x87);
	}

	@Override
	public byte size() {
		return 1;
	}
}
