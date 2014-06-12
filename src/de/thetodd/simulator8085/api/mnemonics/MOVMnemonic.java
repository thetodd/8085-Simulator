package de.thetodd.simulator8085.api.mnemonics;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class MOVMnemonic extends Mnemonic {

	private byte[][] opcodeMatrix;

	public MOVMnemonic() {
		opcodeMatrix = new byte[255][255];
		opcodeMatrix['a']['m'] = 0x7E;
		opcodeMatrix['b']['m'] = 0x46;
		opcodeMatrix['c']['m'] = 0x4E;
		opcodeMatrix['d']['m'] = 0x56;
		opcodeMatrix['e']['m'] = 0x5E;
		opcodeMatrix['h']['m'] = 0x66;
		opcodeMatrix['l']['m'] = 0x6E;

		opcodeMatrix['a']['a'] = 0x7F;
		opcodeMatrix['b']['a'] = 0x47;
		opcodeMatrix['c']['a'] = 0x4F;
		opcodeMatrix['d']['a'] = 0x57;
		opcodeMatrix['e']['a'] = 0x5F;
		opcodeMatrix['h']['a'] = 0x67;
		opcodeMatrix['l']['a'] = 0x6F;

		opcodeMatrix['a']['b'] = 0x78;
		opcodeMatrix['b']['b'] = 0x40;
		opcodeMatrix['c']['b'] = 0x48;
		opcodeMatrix['d']['b'] = 0x50;
		opcodeMatrix['e']['b'] = 0x58;
		opcodeMatrix['h']['b'] = 0x60;
		opcodeMatrix['l']['b'] = 0x68;

		opcodeMatrix['a']['c'] = 0x79;
		opcodeMatrix['b']['c'] = 0x41;
		opcodeMatrix['c']['c'] = 0x49;
		opcodeMatrix['d']['c'] = 0x51;
		opcodeMatrix['e']['c'] = 0x59;
		opcodeMatrix['h']['c'] = 0x61;
		opcodeMatrix['l']['c'] = 0x69;

		opcodeMatrix['a']['d'] = 0x7A;
		opcodeMatrix['b']['d'] = 0x42;
		opcodeMatrix['c']['d'] = 0x4A;
		opcodeMatrix['d']['d'] = 0x52;
		opcodeMatrix['e']['d'] = 0x5A;
		opcodeMatrix['h']['d'] = 0x62;
		opcodeMatrix['l']['d'] = 0x6A;

		opcodeMatrix['a']['e'] = 0x7b;
		opcodeMatrix['b']['e'] = 0x43;
		opcodeMatrix['c']['e'] = 0x4b;
		opcodeMatrix['d']['e'] = 0x53;
		opcodeMatrix['e']['e'] = 0x5b;
		opcodeMatrix['h']['e'] = 0x63;
		opcodeMatrix['l']['e'] = 0x6b;

		opcodeMatrix['a']['h'] = 0x7c;
		opcodeMatrix['b']['h'] = 0x44;
		opcodeMatrix['c']['h'] = 0x4c;
		opcodeMatrix['d']['h'] = 0x54;
		opcodeMatrix['e']['h'] = 0x5c;
		opcodeMatrix['h']['h'] = 0x64;
		opcodeMatrix['l']['h'] = 0x6c;

		opcodeMatrix['a']['l'] = 0x7d;
		opcodeMatrix['b']['l'] = 0x45;
		opcodeMatrix['c']['l'] = 0x4d;
		opcodeMatrix['d']['l'] = 0x55;
		opcodeMatrix['e']['l'] = 0x5d;
		opcodeMatrix['h']['l'] = 0x65;
		opcodeMatrix['l']['l'] = 0x6d;

		opcodeMatrix['m']['a'] = 0x77;
		opcodeMatrix['m']['b'] = 0x70;
		opcodeMatrix['m']['c'] = 0x71;
		opcodeMatrix['m']['d'] = 0x72;
		opcodeMatrix['m']['e'] = 0x73;
		opcodeMatrix['m']['h'] = 0x74;
		opcodeMatrix['m']['l'] = 0x75;
	}

	public byte[] getOpcode(String[] arguments) {
		byte[] opcode = new byte[1];
		if (arguments.length != 2) {
			throw new IllegalArgumentException("Falsche Anzahl von Argumenten!");
		}
		char arg0 = arguments[0].toLowerCase().charAt(0);
		char arg1 = arguments[1].toLowerCase().charAt(0);

		opcode[0] = opcodeMatrix[arg0][arg1];

		return opcode;
	}

	@Override
	public void execute() {
		byte opcode = Memory.getInstance().get(
				Processor.getInstance().getProgramcounter());
		switch (opcode) {
		case 0x40:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterB());
			break;
		case 0x41:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterC());
			break;
		case 0x42:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterD());
			break;
		case 0x43:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterE());
			break;
		case 0x44:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterH());
			break;
		case 0x45:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterL());
			break;
		case 0x46:
			Processor.getInstance().setRegisterB(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x47:
			Processor.getInstance().setRegisterB(
					Processor.getInstance().getRegisterA());
			break;
		case 0x48:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterB());
			break;
		case 0x49:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterC());
			break;
		case 0x4a:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterD());
			break;
		case 0x4b:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterE());
			break;
		case 0x4c:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterH());
			break;
		case 0x4d:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterL());
			break;
		case 0x4e:
			Processor.getInstance().setRegisterC(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x4f:
			Processor.getInstance().setRegisterC(
					Processor.getInstance().getRegisterA());
			break;
		case 0x50:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterB());
			break;
		case 0x51:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterC());
			break;
		case 0x52:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterD());
			break;
		case 0x53:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterE());
			break;
		case 0x54:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterH());
			break;
		case 0x55:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterL());
			break;
		case 0x56:
			Processor.getInstance().setRegisterD(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x57:
			Processor.getInstance().setRegisterD(
					Processor.getInstance().getRegisterA());
			break;
		case 0x58:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterB());
			break;
		case 0x59:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterC());
			break;
		case 0x5a:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterD());
			break;
		case 0x5b:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterE());
			break;
		case 0x5c:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterH());
			break;
		case 0x5d:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterL());
			break;
		case 0x5e:
			Processor.getInstance().setRegisterE(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x5f:
			Processor.getInstance().setRegisterE(
					Processor.getInstance().getRegisterA());
			break;
		case 0x60:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterB());
			break;
		case 0x61:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterC());
			break;
		case 0x62:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterD());
			break;
		case 0x63:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterE());
			break;
		case 0x64:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterH());
			break;
		case 0x65:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterL());
			break;
		case 0x66:
			Processor.getInstance().setRegisterH(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x67:
			Processor.getInstance().setRegisterH(
					Processor.getInstance().getRegisterA());
			break;
		case 0x68:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterB());
			break;
		case 0x69:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterC());
			break;
		case 0x6a:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterD());
			break;
		case 0x6b:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterE());
			break;
		case 0x6c:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterH());
			break;
		case 0x6d:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterL());
			break;
		case 0x6e:
			Processor.getInstance().setRegisterL(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x6f:
			Processor.getInstance().setRegisterL(
					Processor.getInstance().getRegisterA());
			break;
		case 0x70:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterB());
			break;
		case 0x71:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterC());
			break;
		case 0x72:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterD());
			break;
		case 0x73:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterE());
			break;
		case 0x74:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterH());
			break;
		case 0x75:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterL());
			break;
		case 0x77:
			Memory.getInstance().put(Processor.getInstance().getRegisterHL(),
					Processor.getInstance().getRegisterA());
			break;
		case 0x78:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterB());
			break;
		case 0x79:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterC());
			break;
		case 0x7a:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterD());
			break;
		case 0x7b:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterE());
			break;
		case 0x7c:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterH());
			break;
		case 0x7d:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterL());
			break;
		case 0x7e:
			Processor.getInstance().setRegisterA(
					Memory.getInstance().get(
							Processor.getInstance().getRegisterHL()));
			break;
		case 0x7f:
			Processor.getInstance().setRegisterA(
					Processor.getInstance().getRegisterA());
			break;
		}

		Processor.getInstance().incProgramcounter();
	}

	@Override
	public boolean validateOpcode(byte opcode) {
		return (opcode >= (byte) 0x40) && (opcode <= (byte) 0x7F)
				&& (opcode != (byte) 0x76);
	}

	@Override
	public byte size() {
		return 1;
	}

	@Override
	public boolean validateArguments(String[] args) {
		return args.length == 2 && Simulator.isRegistername(args[0])
				&& Simulator.isRegistername(args[1]);
	}
}
