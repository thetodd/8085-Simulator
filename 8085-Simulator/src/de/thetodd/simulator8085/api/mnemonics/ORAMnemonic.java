package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class ORAMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 1) {
			throw new IllegalArgumentException("Falsche Anzahl von Argumenten!");
		}

		String arg0 = arguments[0].toLowerCase();
		if (arg0.equals("a")) {
			opcode[0] = (byte) 0xb7;
		} else if (arg0.equals("b")) {
			opcode[0] = (byte) 0xb0;
		} else if (arg0.equals("c")) {
			opcode[0] = (byte) 0xb1;
		} else if (arg0.equals("d")) {
			opcode[0] = (byte) 0xb2;
		} else if (arg0.equals("e")) {
			opcode[0] = (byte) 0xb3;
		} else if (arg0.equals("h")) {
			opcode[0] = (byte) 0xb4;
		} else if (arg0.equals("l")) {
			opcode[0] = (byte) 0xb5;
		} else if (arg0.equals("m")) {
			opcode[0] = (byte) 0xb6;
		}

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		byte a = Processor.getInstance().getRegisterA();
		Processor.getInstance().incProgramcounter();

		int clock = 4;
		byte b2 = 0x00;
		switch (opcode) {
		case (byte) 0xb0:
			b2 = Processor.getInstance().getRegisterB();
			break;
		case (byte) 0xb1:
			b2 = Processor.getInstance().getRegisterC();
			break;
		case (byte) 0xb2:
			b2 = Processor.getInstance().getRegisterD();
			break;
		case (byte) 0xb3:
			b2 = Processor.getInstance().getRegisterE();
			break;
		case (byte) 0xb4:
			b2 = Processor.getInstance().getRegisterH();
			break;
		case (byte) 0xb5:
			b2 = Processor.getInstance().getRegisterL();
			break;
		case (byte) 0xb6:
			b2 = Memory.getInstance().get(
					Processor.getInstance().getRegisterHL());
			clock = 7;
			break;
		case (byte) 0xb7:
			b2 = Processor.getInstance().getRegisterA();
			break;
		}

		byte c = (byte) (a | b2);
		Processor.getInstance().setRegisterA(c);

		// Set flags
		Processor.getInstance().setFlags(c);
		Processor.getInstance().setAuxiliaryCarryFlag(false);
		Processor.getInstance().setCarryFlag(false);
		
		return clock;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0xB0) && (opcode <= (byte) 0xB7);
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
