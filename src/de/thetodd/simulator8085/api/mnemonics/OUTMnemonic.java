package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class OUTMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		if (arguments.length != 1) {
			throw new IllegalArgumentException("Falsche Anzahl Argumente!");
		}
		opcode[0] = (byte) 0xd3;
		opcode[1] = (byte) Integer.decode(arguments[0]).intValue();

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		byte adr = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		Simulator.getInstance().setOutEntry(adr,
				Processor.getInstance().getRegisterA());

		return 10;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xD3);
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
