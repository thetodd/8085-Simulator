package de.thetodd.simulator8085.api;

import de.thetodd.simulator8085.api.exceptions.ProcessorError;

public class Mnemonic {

	public byte[] getOpcode(String[] arguments) throws IllegalArgumentException {
		return null;
	}

	public void execute() throws ProcessorError {

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
