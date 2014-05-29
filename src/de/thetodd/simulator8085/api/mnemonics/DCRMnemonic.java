package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class DCRMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg = arguments[0].toLowerCase().charAt(0);
		switch (arg) {
		case 'a':
			opcode[0] = (byte) 0x3D;
			break;
		case 'm':
			opcode[0] = (byte) 0x35;
			break;
		case 'b':
			opcode[0] = (byte) 0x05;
			break;
		case 'c':
			opcode[0] = (byte) 0x0d;
			break;
		case 'd':
			opcode[0] = (byte) 0x15;
			break;
		case 'e':
			opcode[0] = (byte) 0x1d;
			break;
		case 'h':
			opcode[0] = (byte) 0x25;
			break;
		case 'l':
			opcode[0] = (byte) 0x2d;
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
		case (byte) 0x3D:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() - 1));
			Processor.getInstance().setFlags(Processor.getInstance().getRegisterA());
			break;
		case (byte) 0x05:
			Processor.getInstance().setRegisterB(
					(byte) (Processor.getInstance().getRegisterB() - 1));
			break;
		case (byte) 0x0D:
			Processor.getInstance().setRegisterC(
					(byte) (Processor.getInstance().getRegisterC() - 1));
			break;
		case (byte) 0x15:
			Processor.getInstance().setRegisterD(
					(byte) (Processor.getInstance().getRegisterD() - 1));
			break;
		case (byte) 0x1D:
			Processor.getInstance().setRegisterE(
					(byte) (Processor.getInstance().getRegisterE() - 1));
			break;
		case (byte) 0x25:
			Processor.getInstance().setRegisterH(
					(byte) (Processor.getInstance().getRegisterH() - 1));
			break;
		case (byte) 0x2D:
			Processor.getInstance().setRegisterL(
					(byte) (Processor.getInstance().getRegisterL() - 1));
			break;
		case (byte) 0x35:
			byte m = Memory.getInstance().get(Processor.getInstance().getRegisterHL());
			m -= 1;
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(), m);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode >= (byte) 0x05) || (opcode >= (byte) 0x0d)
				|| (opcode >= (byte) 0x15) || (opcode >= (byte) 0x1D)
				|| (opcode >= (byte) 0x25) || (opcode >= (byte) 0x2d)
				|| (opcode >= (byte) 0x35) || (opcode >= (byte) 0x3d);
	}

	@Override
	public byte size() {
		return 1;
	}
}
