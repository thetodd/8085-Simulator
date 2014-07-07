package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class INRMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x3c;
			break;
		case 'm':
			opcode[0] = (byte) 0x34;
			break;
		case 'b':
			opcode[0] = (byte) 0x04;
			break;
		case 'c':
			opcode[0] = (byte) 0x0c;
			break;
		case 'd':
			opcode[0] = (byte) 0x14;
			break;
		case 'e':
			opcode[0] = (byte) 0x1c;
			break;
		case 'h':
			opcode[0] = (byte) 0x24;
			break;
		case 'l':
			opcode[0] = (byte) 0x2c;
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

		int clock = 4;
		switch (opcode) {
		case (byte) 0x3c:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + 1));
			Processor.getInstance().setFlags(
					Processor.getInstance().getRegisterA());
			break;
		case (byte) 0x04:
			Processor.getInstance().setRegisterB(
					(byte) (Processor.getInstance().getRegisterB() + 1));
			break;
		case (byte) 0x0c:
			Processor.getInstance().setRegisterC(
					(byte) (Processor.getInstance().getRegisterC() + 1));
			break;
		case (byte) 0x14:
			Processor.getInstance().setRegisterD(
					(byte) (Processor.getInstance().getRegisterD() + 1));
			break;
		case (byte) 0x1c:
			Processor.getInstance().setRegisterE(
					(byte) (Processor.getInstance().getRegisterE() + 1));
			break;
		case (byte) 0x24:
			Processor.getInstance().setRegisterH(
					(byte) (Processor.getInstance().getRegisterH() + 1));
			break;
		case (byte) 0x2c:
			Processor.getInstance().setRegisterL(
					(byte) (Processor.getInstance().getRegisterL() + 1));
			break;
		case (byte) 0x34:
			byte m = Memory.getInstance().get(
					Processor.getInstance().getRegisterHL());
			m += 1;
			Memory.getInstance()
					.put(Processor.getInstance().getRegisterHL(), m);
			clock = 10;
			break;
		default:
			break;
		}

		return clock;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0x04) || (opcode == (byte) 0x0c)
				|| (opcode == (byte) 0x14) || (opcode == (byte) 0x1c)
				|| (opcode == (byte) 0x24) || (opcode == (byte) 0x2c)
				|| (opcode == (byte) 0x34) || (opcode == (byte) 0x3c);
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
