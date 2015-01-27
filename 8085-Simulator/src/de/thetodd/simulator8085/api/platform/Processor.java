package de.thetodd.simulator8085.api.platform;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.GlobalSimulatorEvents;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;

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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_A_CHANGED, Byte.toString(a),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_B_CHANGED, Byte.toString(b),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_C_CHANGED, Byte.toString(c),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_D_CHANGED, Byte.toString(d),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_E_CHANGED, Byte.toString(e),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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

	public void setRegisterF(byte flags) {
		// Reset flags
		setSignFlag(false);
		setAuxiliaryCarryFlag(false);
		setCarryFlag(false);
		setParityFlag(false);
		setZeroFlag(false);

		if ((flags &= 0x80) == 0x80) {
			setSignFlag(true);
		}
		if ((flags &= 0x40) == 0x40) {
			setZeroFlag(true);
		}
		if ((flags &= 0x10) == 0x10) {
			setAuxiliaryCarryFlag(true);
		}
		if ((flags &= 0x04) == 0x04) {
			setParityFlag(true);
		}
		if ((flags &= 0x01) == 0x01) {
			setCarryFlag(true);
		}
		

		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(flags),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_H_CHANGED, Byte.toString(h),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_L_CHANGED, Byte.toString(l),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
	}

	public short getRegisterBC() {
		short bc = (short) ((short) (getRegisterB()) << 8);
		bc |= 0x00FF & (short) getRegisterC();
		return bc;
	}

	public short getRegisterDE() {
		short de = (short) ((short) (getRegisterD()) << 8);
		de |= 0x00ff & (short) getRegisterE();
		return de;
	}

	public short getRegisterHL() {
		short hl = (short) ((short) (getRegisterH()) << 8);
		hl |= 0x00ff & (short) getRegisterL();
		return hl;
	}

	public void setRegisterBC(short bc) {
		setRegisterB((byte) (bc >> 8));
		setRegisterC((byte) (bc));
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_B_CHANGED, Short.toString(bc),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
		SimulatorEvent evt2 = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_C_CHANGED, Short.toString(bc),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt2);
	}

	public void setRegisterDE(short de) {
		setRegisterD((byte) (de >> 8));
		setRegisterE((byte) (de));
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_D_CHANGED, Short.toString(de),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
		SimulatorEvent evt2 = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_E_CHANGED, Short.toString(de),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt2);
	}

	public void setRegisterHL(short hl) {
		setRegisterH((byte) (hl >> 8));
		setRegisterL((byte) (hl));
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_H_CHANGED, Short.toString(hl),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
		SimulatorEvent evt2 = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_L_CHANGED, Short.toString(hl),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt2);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_PC_CHANGED, Short.toString(pc),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
	}

	// Inkrementiert den PCum 1
	public void incProgramcounter() {
		this.pc += 1;
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_PC_CHANGED, Short.toString(pc),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_SP_CHANGED, Short.toString(sp),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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


		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.RESET_PROCESSOR, "Processor reset.",
				SimulatorEvent.TYPE.INFORMATION);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(getRegisterF()),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(getRegisterF()),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(getRegisterF()),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(getRegisterF()),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
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
		SimulatorEvent evt = new SimulatorEvent(
				GlobalSimulatorEvents.REGISTER_F_CHANGED, Byte.toString(getRegisterF()),
				SimulatorEvent.TYPE.SUCCESS);
		Simulator.getInstance().fireSimulatorEvent(evt);
	}

	/**
	 * Sets the flags by value.
	 * 
	 * @param b
	 */
	public void setFlags(short b) {
		Processor.getInstance().setZeroFlag((b == 0));
		int p_count = 0; // Counter for Parity
		for (byte i = 0; i < 8; i++) { // Cycle thru any bit of byte
			byte n = (byte) (0x01 << i);
			if ((b & n) == n) { // Bit is 1
				p_count++;
			}
		}
		Processor.getInstance().setParityFlag(((p_count % 2) == 0));
		Processor.getInstance().setSignFlag(((b & 0x80) == 0x80));
		Processor.getInstance().setCarryFlag(((b & 0xff00) != 0x00));
	}

}
