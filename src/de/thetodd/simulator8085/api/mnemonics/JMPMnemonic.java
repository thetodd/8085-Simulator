package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorError;
import de.thetodd.simulator8085.api.Simulator;

public class JMPMnemonic implements Mnemonic {

	//TODO: Unterstützung von hardcoded Adressen
	@Override
	public byte[] getOpcode(String[] arguments) throws IllegalArgumentException {
		byte[] opcode = new byte[3];
		opcode[0] = (byte) 0xC3;
		short adr = Simulator.getInstance().getLabelAdress(arguments[0]);
		opcode[2] = (byte) ((adr & 0xFF00) >> 8);
		opcode[1] = (byte) ((adr & 0x00FF));
		return opcode;
	}

	@Override
	public void execute() throws ProcessorError {
		Processor.getInstance().incProgramcounter();
		short[] adresse = new short[2];
		adresse[1] = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		adresse[0] = (short) (Memory.getInstance().get(
				Processor.getInstance().getProgramcounter()) << 8);
		short jmpAdr = (short) ((adresse[0]) + (adresse[1]));
		Processor.getInstance().setProgramcounter(jmpAdr);
	}

	@Override
	public boolean hasOpcode(byte opcode) {
		return (opcode == (byte) 0xC3);
	}

	@Override
	public byte size() {
		return 3;
	}

	@Override
	public String toString() {
		return "JUMP TO";
	}

}
