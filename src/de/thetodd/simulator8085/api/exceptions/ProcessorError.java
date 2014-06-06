package de.thetodd.simulator8085.api.exceptions;

public class ProcessorError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3679044524290872391L;

	private String msg;
	
	public ProcessorError(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
}
