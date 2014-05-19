package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class ADCMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x8F;
			break;
		case 'm':
			opcode[0] = (byte) 0x8E;
			break;
		case 'b':
			opcode[0] = (byte) 0x88;
			break;
		case 'c':
			opcode[0] = (byte) 0x89;
			break;
		case 'd':
			opcode[0] = (byte) 0x8A;
			break;
		case 'e':
			opcode[0] = (byte) 0x8B;
			break;
		case 'h':
			opcode[0] = (byte) 0x8C;
			break;
		case 'l':
			opcode[0] = (byte) 0x8D;
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
		case (byte) 0x8F:
			b = Processor.getInstance().getRegisterA();
			break;
		case (byte) 0x88:
			b = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0x89:
			b = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0x8A:
			b = (Processor.getInstance().getRegisterD());
			break;
		case (byte) 0x8B:
			b = (Processor.getInstance().getRegisterE());
			break;
		case (byte) 0x8C:
			b = (Processor.getInstance().getRegisterH());
			break;
		case (byte) 0x8D:
			b = (Processor.getInstance().getRegisterL());
			break;
		default:
			break;
		}

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 + b2;
		if(Processor.getInstance().isCarryFlag()) {
			c++;
		}
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode >= (byte) 0x88) && (opcode <= (byte) 0x8F);
	}

	@Override
	public byte size() {
		return 1;
	}
}