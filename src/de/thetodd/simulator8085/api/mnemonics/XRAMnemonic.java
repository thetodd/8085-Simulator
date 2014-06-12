package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class XRAMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 1) {
			throw new IllegalArgumentException("Falsche Anzahl von Argumenten!");
		}

		String arg0 = arguments[0].toLowerCase();
		if (arg0.equals("a")) {
			opcode[0] = (byte) 0xaf;
		} else if (arg0.equals("b")) {
			opcode[0] = (byte) 0xa8;
		} else if (arg0.equals("c")) {
			opcode[0] = (byte) 0xa9;
		} else if (arg0.equals("d")) {
			opcode[0] = (byte) 0xaa;
		} else if (arg0.equals("e")) {
			opcode[0] = (byte) 0xab;
		} else if (arg0.equals("h")) {
			opcode[0] = (byte) 0xac;
		} else if (arg0.equals("l")) {
			opcode[0] = (byte) 0xad;
		} else if (arg0.equals("m")) {
			opcode[0] = (byte) 0xae;
		}

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		byte a = Processor.getInstance().getRegisterA();

		byte b2 = 0x00;
		switch (opcode) {
		case (byte) 0xa8:
			b2 = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0xa9:
			b2 = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0xaa:
			b2 = Processor.getInstance().getRegisterD();
			break;
		case (byte) 0xab:
			b2 = Processor.getInstance().getRegisterE();
			break;
		case (byte) 0xac:
			b2 = Processor.getInstance().getRegisterH();
			break;
		case (byte) 0xad:
			b2 = Processor.getInstance().getRegisterL();
			break;
		case (byte) 0xae:
			b2 = Memory.getInstance().get(
					Processor.getInstance().getRegisterHL());
			break;
		case (byte) 0xaf:
			b2 = Processor.getInstance().getRegisterA();
			break;
		}

		byte c = (byte) (a ^ b2);
		Processor.getInstance().setRegisterA(c);

		// Set flags
		Processor.getInstance().setFlags(c);
		Processor.getInstance().setAuxiliaryCarryFlag(false);
		Processor.getInstance().setCarryFlag(false);
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0xa8) && (opcode <= (byte) 0xaf);
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
