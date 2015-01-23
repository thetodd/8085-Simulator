package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class LXISPMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[3];
		short adr = Integer.decode(arguments[0]).shortValue();
		
		opcode[0] = (byte) 0x31;
		opcode[1] = (byte) (adr);
		opcode[2] = (byte) (adr>>8);

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		byte b3 = Memory.getInstance().get(Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		byte b2 = Memory.getInstance().get(Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		
		short adr = (short) ((b2<<8)|b3);
		
		Processor.getInstance().setStackpointer(adr);
		
		return 10;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == 0x31);
	}

	@Override
	public String toString() {
		return "LXISP";
	}

	@Override
	public byte size() {
		return 3;
	}
	
	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 1 && Simulator.isNumber(args[0]);
	}

}
