package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;

public class LHLDMnemonic implements Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[3];
		short adr = Integer.decode(arguments[0]).shortValue();
		
		opcode[0] = (byte) 0x2A;
		opcode[1] = (byte) (adr);
		opcode[2] = (byte) (adr>>8);

		return opcode;
	}

	@Override
	public void execute() {
		Processor.getInstance().incProgramcounter();
		byte b3 = Memory.getInstance().get(Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		byte b2 = Memory.getInstance().get(Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		
		short adr = (short) ((b2<<8)|b3);
		
		Processor.getInstance().setRegisterHL(Memory.getInstance().get(adr));
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == 0x2A);
	}

	@Override
	public String toString() {
		return "LHLD";
	}

	@Override
	public byte size() {
		return 3;
	}

}