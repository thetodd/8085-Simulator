package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.Simulator;

public class OUTMnemonic implements Mnemonic {

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
	public void execute() {
		Processor.getInstance().incProgramcounter();
		byte adr = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		Simulator.getInstance().setOutEntry(adr,
				Processor.getInstance().getRegisterA());
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xD3);
	}

	@Override
	public byte size() {
		return 2;
	}
}
