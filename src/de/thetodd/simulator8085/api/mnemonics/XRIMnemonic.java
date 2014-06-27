package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class XRIMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length > 1) {
			throw new IllegalArgumentException("Falsche Anzahl von Argumenten!");
		}
		opcode[0] = (byte) 0xEE;
		opcode[1] = Integer.decode(arguments[0]).byteValue();

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		byte b2 = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		byte a = Processor.getInstance().getRegisterA();
		byte c = (byte) (a ^ b2);
		Processor.getInstance().setRegisterA(c);

		Processor.getInstance().incProgramcounter();

		// Set flags
		Processor.getInstance().setFlags(c);
		Processor.getInstance().setAuxiliaryCarryFlag(false);
		Processor.getInstance().setCarryFlag(false);

		return 7;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xEE);
	}

	@Override
	public byte size() {
		return 2;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 1 && Simulator.isNumber(args[0]);
	}
}
