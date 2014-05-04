package de.thetodd.simulator8085.api;

import java.util.Date;

public class RegisterChangeEvent {

	public static enum Register {
		REGISTER_A, REGISTER_B, REGISTER_C, REGISTER_D, REGISTER_E, REGISTER_F, REGISTER_H, REGISTER_L, REGISTER_PC, REGISTER_SP
	}
	
	/**
	 * Template for BC-Register.
	 * @return
	 */
	public static Register[] getBCTemplate() {
		Register[] r = {Register.REGISTER_B,Register.REGISTER_C};
		return r;
	}
	
	/**
	 * Template for DE-Register.
	 * @return
	 */
	public static Register[] getDETemplate() {
		Register[] r = {Register.REGISTER_D,Register.REGISTER_E};
		return r;
	}
	
	/**
	 * Template for HL-Register.
	 * @return
	 */
	public static Register[] getHLTemplate() {
		Register[] r = {Register.REGISTER_H,Register.REGISTER_L};
		return r;
	}
	
	/**
	 * Template for All-Register.
	 * @return
	 */
	public static Register[] getAllTemplate() {
		Register[] regs = {Register.REGISTER_A,
				Register.REGISTER_B,
				Register.REGISTER_C,
				Register.REGISTER_D,
				Register.REGISTER_E,
				Register.REGISTER_F,
				Register.REGISTER_H,
				Register.REGISTER_L,
				Register.REGISTER_PC,
				Register.REGISTER_SP};
		return regs;
	}
	
	private Register[] register;
	private Date evtTime;

	public RegisterChangeEvent(Register[] r) {
		evtTime = new Date();
		register = r;
	}
	
	public RegisterChangeEvent(Register r) {
		Register[] regs = {r};
		evtTime = new Date();
		register = regs;
	}

	/**
	 * @return the register
	 */
	public Register[] getRegister() {
		return register;
	}

	/**
	 * @param register the register to set
	 */
	public void setRegister(Register[] register) {
		this.register = register;
	}

	/**
	 * @return the evtTime
	 */
	public Date getEvtTime() {
		return evtTime;
	}

	/**
	 * @param evtTime the evtTime to set
	 */
	public void setEvtTime(Date evtTime) {
		this.evtTime = evtTime;
	}
	
	
	
}
