package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

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
					+ " wird nicht unterstützt!");
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		int a = Processor.getInstance().getRegisterA();
		if (Processor.getInstance().isSignFlag()) {
			a = -a;
		}
		switch (opcode) {
		case (byte) 0x87:
			int b = (a + Processor.getInstance().getRegisterA());
			Processor.getInstance().setRegisterA((byte) b);
			setFlags(b);
			break;
		// case (byte) 0x86: Processor.getInstance().setRegisterA((byte)
		// (Processor.getInstance().getRegisterA()+arg)); break;
		case (byte) 0x80:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterB()));
			break;
		case (byte) 0x81:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterC()));
			break;
		case (byte) 0x82:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterD()));
			break;
		case (byte) 0x83:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterE()));
			break;
		case (byte) 0x84:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterH()));
			break;
		case (byte) 0x85:
			Processor.getInstance().setRegisterA(
					(byte) (Processor.getInstance().getRegisterA() + Processor
							.getInstance().getRegisterL()));
			break;
		default:
			break;
		}
	}

	private void setFlags(int b) {
		if (b == 0) {
			Processor.getInstance().setZeroFlag(true);
		} else {
			Processor.getInstance().setZeroFlag(false);
		}

		int p_count = 0; // Counter for Parity
		for (byte i = 0; i < 8; i++) { // Cycle thru any bit of byte
			byte n = (byte) (0x01 << i);
			if ((b & n) == n) { // Bit is 1
				p_count++;
			}
		}
		if ((p_count % 2) == 0) {
			Processor.getInstance().setParityFlag(true);
		} else {
			Processor.getInstance().setParityFlag(false);
		}
		
		if(b<0) {
			Processor.getInstance().setSignFlag(true);
		} else {
			Processor.getInstance().setSignFlag(false);
		}
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
