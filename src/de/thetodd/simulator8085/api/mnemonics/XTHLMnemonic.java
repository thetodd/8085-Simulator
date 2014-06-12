package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class XTHLMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length > 0) {
			throw new IllegalArgumentException("Argumente sind nicht zulaessig!");
		}
		opcode[0] = (byte) 0xE3;

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().incProgramcounter();
		
		byte h = Processor.getInstance().getRegisterH();
		byte l = Processor.getInstance().getRegisterL();
		byte s1 = Memory.getInstance().popStack();
		byte s2 = Memory.getInstance().popStack();
		
		Processor.getInstance().setRegisterH(s1);
		Processor.getInstance().setRegisterL(s2);
		Memory.getInstance().pushStack(l);
		Memory.getInstance().pushStack(h);
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xE3);
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
