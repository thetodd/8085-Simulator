package de.thetodd.simulator8085.api.actions;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Simulator;

public class AssembleAction implements Action {

	private String code;

	public AssembleAction(String code) {
		this.code = code;
	}

	public void run() {
		String[] codeLines = code.split("\r\n");

		Simulator.getInstance().setProgramSize(0);
		Simulator.getInstance().setCommandCount(0);
		Simulator.getInstance().getCodeMap().clear();
		Simulator.getInstance().getLabelMap().clear();
		Simulator.getInstance().getBreakpoints().clear();
		
		// Scan for labels
		short adr = 0x0000;
		for (String line : codeLines) {
			String[] command = line.split(" ");
			String mnemonic = command[0].toLowerCase();
			// System.out.println(mnemonic);
			if (mnemonic.equals("org")) { // is Org
				adr = (short) Integer.decode(command[1]).intValue();
			} else if (line.endsWith(":")) { // is label
				String labelName = mnemonic.substring(0, mnemonic.length() - 1);
				Simulator.getInstance().addLabel(labelName, adr);
				// System.out.println("Label: "+labelName+" - "+String.format("0x%04X",adr));
			} else if(line.startsWith(";")) { //Comment-Line
				//Do nothing
			} else { // is mnemonic
				adr += Simulator.getInstance().getMnemonics().get(mnemonic)
						.size();
				//Add Mnemonic-Byte-Size to Programsize
				Simulator.getInstance().setProgramSize(
						Simulator.getInstance().getProgramSize()
								+ Simulator.getInstance().getMnemonics()
										.get(mnemonic).size());
				//Add 1 to Commandcount
				Simulator.getInstance().setCommandCount(Simulator.getInstance().getCommandCount()+1);
			}
		}

		short adresse = 0x0000; // Adresse wird per ORG angepasst
		int linenumber = 0;
		for (String line : codeLines) {
			String[] command = line.split(" ");
			String mnemonic = command[0].toLowerCase();

			// Pseudo ops
			if (mnemonic.equals("org")) {
				adresse = (short) Integer.decode(command[1]).intValue();
			} else if (mnemonic.equals("@:")) {
				Simulator.getInstance().addBreakpoint(adresse);
			} else if (mnemonic.endsWith(":")) {
				String labelName = mnemonic.substring(0, mnemonic.length() - 1);
				Simulator.getInstance().addLabel(labelName, adresse);
			} else if(mnemonic.startsWith(";")) {
				//DO nothing
			} else {
				byte[] opcodes = new byte[0];
				if (command.length == 1) {
					opcodes = Simulator.getInstance().getOpcodes(mnemonic,
							new String[0]);
				} else {
					opcodes = Simulator.getInstance().getOpcodes(mnemonic,
							command[1].split(","));
				}
				Simulator.getInstance().getCodeMap().put(adresse, linenumber);
				for (byte c : opcodes) {
					Memory.getInstance().put(adresse, c);
					adresse++;
				}
			}
			
			linenumber++;
		}
		Simulator.getInstance().fireMemoryChangeEvent();
		Simulator.getInstance().setAssembled(true);
	}

}
