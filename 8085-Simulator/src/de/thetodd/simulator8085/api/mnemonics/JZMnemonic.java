package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class JZMnemonic extends Mnemonic {

	@Override
	public byte[] getOpcode(String[] arguments) throws IllegalArgumentException {
		byte[] opcode = new byte[3];
		opcode[0] = (byte) 0xCA;
		short adr = 0;
		try {
			adr = Integer.decode(arguments[0]).shortValue(); // support for hard
																// coded
																// addresses
		} catch (Exception ex) {
			adr = Simulator.getInstance().getLabelAdress(arguments[0]);
		}
		opcode[2] = (byte) ((adr & 0xFF00) >> 8);
		opcode[1] = (byte) ((adr & 0x00FF));
		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		short[] adresse = new short[2];
		adresse[1] = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		adresse[0] = (short) (Memory.getInstance().get(
				Processor.getInstance().getProgramcounter()) << 8);
		short jmpAdr = (short) ((adresse[0]) + (adresse[1]));
		Processor.getInstance().incProgramcounter();
		// check if zero flag is set
		if (Processor.getInstance().isZeroFlag()) {
			Processor.getInstance().setProgramcounter(jmpAdr);
			return 10;
		}
		return 7;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0xCA);
	}

	@Override
	public byte size() {
		return 3;
	}

	@Override
	public String toString() {
		return "JNZ";
	}

}
