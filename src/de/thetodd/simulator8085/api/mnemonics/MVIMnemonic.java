package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class MVIMnemonic extends Mnemonic {

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[2];
		String arg = arguments[0].toLowerCase();
		if (arg.equals("a")) {
			opcode[0] = 0x3E;
		} else if (arg.equals("b")) {
			opcode[0] = 0x06;
		} else if (arg.equals("c")) {
			opcode[0] = 0x0E;
		} else if (arg.equals("d")) {
			opcode[0] = 0x16;
		} else if (arg.equals("e")) {
			opcode[0] = 0x1E;
		} else if (arg.equals("h")) {
			opcode[0] = 0x26;
		} else if (arg.equals("l")) {
			opcode[0] = 0x2E;
		} else if (arg.equals("m")) {
			opcode[0] = 0x36;
		} else {
			throw new IllegalArgumentException("Argument " + arguments[0]
					+ " ist nicht zulaessig!");
		}
		opcode[1] = (byte) Integer.decode(arguments[1]).intValue();

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		Processor.getInstance().incProgramcounter();
		if (opcode == 0x3E) {
			System.out.println("Set Register A");
			Processor.getInstance().setRegisterA(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x06) {
			Processor.getInstance().setRegisterB(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x0E) {
			Processor.getInstance().setRegisterC(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x16) {
			Processor.getInstance().setRegisterD(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x1E) {
			Processor.getInstance().setRegisterE(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x26) {
			Processor.getInstance().setRegisterH(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x2E) {
			Processor.getInstance().setRegisterL(
					Memory.getInstance().get(
							Processor.getInstance().getProgramcounter()));
		} else if (opcode == 0x36) {
			// TODO: b2 to Memory (HL)
			// Processor.getInstance().setRegisterA(Memory.getInstance().get(Processor.getInstance().getProgramcounter()));
		}
		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode == (byte) 0x3E) || (opcode == (byte) 0x36)
				|| (opcode == (byte) 0x06) || (opcode == (byte) 0x0E)
				|| (opcode == (byte) 0x16) || (opcode == (byte) 0x1E)
				|| (opcode == (byte) 0x26) || (opcode == (byte) 0x2E);
	}

	@Override
	public String toString() {
		return "MVI";
	}

	@Override
	public byte size() {
		return 2;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 2 && Simulator.isNumber(args[1])
				&& Simulator.isRegistername(args[0]);
	}

}
