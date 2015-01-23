package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class SBBMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x9F;
			break;
		case 'm':
			opcode[0] = (byte) 0x9E;
			break;
		case 'b':
			opcode[0] = (byte) 0x98;
			break;
		case 'c':
			opcode[0] = (byte) 0x99;
			break;
		case 'd':
			opcode[0] = (byte) 0x9A;
			break;
		case 'e':
			opcode[0] = (byte) 0x9B;
			break;
		case 'h':
			opcode[0] = (byte) 0x9C;
			break;
		case 'l':
			opcode[0] = (byte) 0x9D;
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
		byte b = 0;
		int clock = 4;
		switch (opcode) {
		case (byte) 0x9F:
			b = Processor.getInstance().getRegisterA();
			break;
		case (byte) 0x98:
			b = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0x99:
			b = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0x9A:
			b = (Processor.getInstance().getRegisterD());
			break;
		case (byte) 0x9B:
			b = (Processor.getInstance().getRegisterE());
			break;
		case (byte) 0x9C:
			b = (Processor.getInstance().getRegisterH());
			break;
		case (byte) 0x9D:
			b = (Processor.getInstance().getRegisterL());
			break;
		case (byte) 0x9E:
			b = Memory.getInstance().get(
					Processor.getInstance().getRegisterHL());
			clock = 7;
			break;
		default:
			break;
		}

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 - b2;
		if (Processor.getInstance().isCarryFlag()) {
			c -= 1;
		}
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);

		return clock;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0x98) && (opcode <= (byte) 0x9F);
	}

	@Override
	public byte size() {
		return 1;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 1 && Simulator.isRegistername(args[0]);
	}
}
