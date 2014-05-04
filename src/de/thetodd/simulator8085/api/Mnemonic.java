package de.thetodd.simulator8085.api;

public abstract interface Mnemonic {

	public byte[] getOpcode(String[] arguments) throws IllegalArgumentException;
	public void execute() throws ProcessorError;
	public boolean hasOpcode(byte opcode);
	public byte size();
	
}
