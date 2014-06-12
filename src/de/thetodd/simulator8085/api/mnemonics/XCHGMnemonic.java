package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Processor;

public class XCHGMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xEB;

		return opcode;
	}

	@Override
	public void execute() {
		short de = Processor.getInstance().getRegisterDE();
		Processor.getInstance().setRegisterDE(Processor.getInstance().getRegisterHL());
		Processor.getInstance().setRegisterHL(de);
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xEB);
	}

	@Override
	public byte size() {
		return 1;
	}
	
	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 0;
	}
}
