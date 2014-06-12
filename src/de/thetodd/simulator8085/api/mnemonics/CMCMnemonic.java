package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Processor;

public class CMCMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = 0x3F;

		return opcode;
	}

	@Override
	public void execute() {
		boolean cflag = Processor.getInstance().isCarryFlag();
		cflag = !cflag;
		Processor.getInstance().setCarryFlag(cflag);
		
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0x3F);
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
