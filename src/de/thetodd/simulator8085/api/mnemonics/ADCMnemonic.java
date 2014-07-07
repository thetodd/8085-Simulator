package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class ADCMnemonic extends Mnemonic {

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
	public int execute() throws ProcessorError {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		byte b = 0;
		int clock = 0;
		
		//TODO: Needs support for memory
		switch (opcode) {
		case (byte) 0x8F:
			b = Processor.getInstance().getRegisterA();
			clock = 4;
			break;
		case (byte) 0x88:
			b = Processor.getInstance().getRegisterB();
			clock = 4;
			break;
		case (byte) 0x89:
			b = Processor.getInstance().getRegisterC();
			clock = 4;
			break;
		case (byte) 0x8A:
			b = (Processor.getInstance().getRegisterD());
			clock = 4;
			break;
		case (byte) 0x8B:
			b = (Processor.getInstance().getRegisterE());
			clock = 4;
			break;
		case (byte) 0x8C:
			b = (Processor.getInstance().getRegisterH());
			clock = 4;
			break;
		case (byte) 0x8D:
			b = (Processor.getInstance().getRegisterL());
			clock = 4;
			break;
		default:
			break;
		}

		short a2 = (short) (Processor.getInstance().getRegisterA() & 0xFF);
		short b2 = (short) (b & 0xFF);
		int c = a2 + b2;
		if (Processor.getInstance().isCarryFlag()) {
			c++;
		}
		Processor.getInstance().setRegisterA((byte) c);
		Processor.getInstance().setFlags((short) c);

		return clock;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0x88) && (opcode <= (byte) 0x8F);
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
