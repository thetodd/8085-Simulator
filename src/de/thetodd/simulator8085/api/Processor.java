package de.thetodd.simulator8085.api;

public class Processor {

	private static Processor processorInstance;

	// Register
	private byte a;
	private byte b;
	private byte c;
	private byte d;
	private byte e;
	private byte h;
	private byte l;
	private short pc;
	private short sp;

	// Flags
	private boolean zeroFlag;
	private boolean parityFlag;
	private boolean carryFlag;
	private boolean signFlag;
	private boolean auxiliaryCarryFlag;

	public static Processor getInstance() {
		if (processorInstance == null) {
			processorInstance = new Processor();
		}
		return processorInstance;
	}

	public Processor() {
		resetProcessor();
	}

	/**
	 * @return the a
	 */
	public byte getRegisterA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setRegisterA(byte a) {
		this.a = a;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_A));
	}

	/**
	 * @return the b
	 */
	public byte getRegisterB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setRegisterB(byte b) {
		this.b = b;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_B));
	}

	/**
	 * @return the c
	 */
	public byte getRegisterC() {
		return c;
	}

	/**
	 * @param c
	 *            the c to set
	 */
	public void setRegisterC(byte c) {
		this.c = c;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_C));
	}

	/**
	 * @return the d
	 */
	public byte getRegisterD() {
		return d;
	}

	/**
	 * @param d
	 *            the d to set
	 */
	public void setRegisterD(byte d) {
		this.d = d;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_D));
	}

	/**
	 * @return the e
	 */
	public byte getRegisterE() {
		return e;
	}

	/**
	 * @param e
	 *            the e to set
	 */
	public void setRegisterE(byte e) {
		this.e = e;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_E));
	}

	/**
	 * @return the f
	 */
	public byte getRegisterF() {
		byte flags = 0x00;
		if (isSignFlag()) {
			flags |= 0x80;
		}
		if (isZeroFlag()) {
			flags |= 0x40;
		}
		if (isAuxiliaryCarryFlag()) {
			flags |= 0x10;
		}
		if (isParityFlag()) {
			flags |= 0x04;
		}
		if (isCarryFlag()) {
			flags |= 0x01;
		}
		return flags;
	}

	/**
	 * @return the h
	 */
	public byte getRegisterH() {
		return h;
	}

	/**
	 * @param h
	 *            the h to set
	 */
	public void setRegisterH(byte h) {
		this.h = h;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_H));
	}

	/**
	 * @return the l
	 */
	public byte getRegisterL() {
		return l;
	}

	/**
	 * @param l
	 *            the l to set
	 */
	public void setRegisterL(byte l) {
		this.l = l;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_L));
	}

	/**
	 * @return the pc
	 */
	public short getProgramcounter() {
		return pc;
	}

	/**
	 * @param pc
	 *            the pc to set
	 */
	public void setProgramcounter(short pc) {
		this.pc = pc;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_PC));
	}

	// Inkrementiert den PCum 1
	public void incProgramcounter() {
		this.pc += 1;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_PC));
	}

	/**
	 * @return the sp
	 */
	public short getStackpointer() {
		return sp;
	}

	/**
	 * @param sp
	 *            the sp to set
	 */
	public void setStackpointer(short sp) {
		this.sp = sp;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_SP));
	}

	/**
	 * Resets Registers, Flags, Programcounter and Stackpointer. Doesn't reset
	 * any label or breakpoints!
	 */
	public void resetProcessor() {
		a = (byte) (Math.random() * 0xff);
		b = (byte) (Math.random() * 0xff);
		c = (byte) (Math.random() * 0xff);
		d = (byte) (Math.random() * 0xff);
		e = (byte) (Math.random() * 0xff);
		h = (byte) (Math.random() * 0xff);
		l = (byte) (Math.random() * 0xff);
		pc = Memory.getInstance().getMemoryStart();
		sp = Memory.getInstance().getMemoryEnd();

		zeroFlag = false;
		parityFlag = false;
		carryFlag = false;
		signFlag = false;
		auxiliaryCarryFlag = false;
		
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(RegisterChangeEvent.getBCTemplate()));
	}

	/**
	 * @return the zeroFlag
	 */
	public boolean isZeroFlag() {
		return zeroFlag;
	}

	/**
	 * @param zeroFlag
	 *            the zeroFlag to set
	 */
	public void setZeroFlag(boolean zeroFlag) {
		this.zeroFlag = zeroFlag;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_F));
	}

	/**
	 * @return the parityFlag
	 */
	public boolean isParityFlag() {
		return parityFlag;
	}

	/**
	 * @param parityFlag
	 *            the parityFlag to set
	 */
	public void setParityFlag(boolean parityFlag) {
		this.parityFlag = parityFlag;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_F));
	}

	/**
	 * @return the carryFlag
	 */
	public boolean isCarryFlag() {
		return carryFlag;
	}

	/**
	 * @param carryFlag
	 *            the carryFlag to set
	 */
	public void setCarryFlag(boolean carryFlag) {
		this.carryFlag = carryFlag;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_F));
	}

	/**
	 * @return the signFlag
	 */
	public boolean isSignFlag() {
		return signFlag;
	}

	/**
	 * @param signFlag
	 *            the signFlag to set
	 */
	public void setSignFlag(boolean signFlag) {
		this.signFlag = signFlag;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_F));
	}

	/**
	 * @return the auxiliaryCarryFlag
	 */
	public boolean isAuxiliaryCarryFlag() {
		return auxiliaryCarryFlag;
	}

	/**
	 * @param auxiliaryCarryFlag
	 *            the auxiliaryCarryFlag to set
	 */
	public void setAuxiliaryCarryFlag(boolean auxiliaryCarryFlag) {
		this.auxiliaryCarryFlag = auxiliaryCarryFlag;
		//Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(Register.REGISTER_F));
	}

	public void setFlags(byte b) {
		if (b == 0) {
			Processor.getInstance().setZeroFlag(true);
		} else {
			Processor.getInstance().setZeroFlag(false);
		}

		int p_count = 0; // Counter for Parity
		for (byte i = 0; i < 8; i++) { // Cycle thru any bit of byte
			byte n = (byte) (0x01 << i);
			if ((b & n) == n) { // Bit is 1
				p_count++;
			}
		}
		if ((p_count % 2) == 0) {
			Processor.getInstance().setParityFlag(true);
		} else {
			Processor.getInstance().setParityFlag(false);
		}
		
		if((b&0x80) == 0x80) {
			Processor.getInstance().setSignFlag(true);
		} else {
			Processor.getInstance().setSignFlag(false);
		}
	}
	
}
