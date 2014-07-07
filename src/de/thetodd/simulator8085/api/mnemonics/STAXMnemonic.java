package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class STAXMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		char arg0 = arguments[0].toLowerCase().charAt(0);

		if (arg0 == 'b') {
			opcode[0] = (byte) 0x02;
		} else if (arg0 == 'd') {
			opcode[0] = (byte) 0x12;
		}

		return opcode;
	}

	@Override
	public int execute() throws ProcessorError {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();

		if (opcode == 0x02) {
			Memory.getInstance().put(Processor.getInstance().getRegisterBC(),
					Processor.getInstance().getRegisterA());
		} else if (opcode == 0x12) {
			Memory.getInstance().put(Processor.getInstance().getRegisterDE(),
					Processor.getInstance().getRegisterA());
		}

		return 7;
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == 0x02) || (opcode == 0x12);
	}

	@Override
	public String toString() {
		return "STAX";
	}

	@Override
	public byte size() {
		return 1;
	}

}
