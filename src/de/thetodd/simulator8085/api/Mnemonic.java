package de.thetodd.simulator8085.api;

import de.thetodd.simulator8085.api.exceptions.ProcessorError;

public class Mnemonic {

	public byte[] getOpcode(String[] arguments) throws IllegalArgumentException {
		return null;
	}

	/**
	 * Executes the mnemonic according to the opcode in the memory.
	 * @return clocks needed for execution <i>defaults to 1</i>
	 * @throws ProcessorError
	 */
	public int execute() throws ProcessorError {
		return 1;
	}

	public boolean validateOpcode(byte opcode) {
		return false;
	}

	public byte size() {
		return 0;
	}

	public boolean validateArguments(String[] args) {
		return true;
	}

}
