package de.thetodd.simulator8085.api.exceptions;

public class AssemblerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6113054233907090808L;

	private String message;
	private int line;

	public AssemblerException(String message, int line) {
		this.message = message;
		this.line = line;
	}

	@Override
	public String getMessage() {
		return this.message + " in line " + (this.line+1);
	}

	public int getLine() {
		return line;
	}
	
}
