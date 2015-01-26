package de.thetodd.simulator8085.api.actions;

import org.eclipse.jface.text.Position;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.AssemblerException;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.gui.sourceviewer.AssemblerSourceViewer;

public class AssembleAction implements Action {

	private String code;
	private AssemblerSourceViewer sv;

	public AssembleAction(AssemblerSourceViewer sv, String code) {
		this.code = code;
		this.sv = sv;
		sv.clearAnnotations();
	}

	public void run() {
		String[] codeLines = code.split("\r\n");

		Simulator.getInstance().setProgramSize(0);
		Simulator.getInstance().setCommandCount(0);
		Simulator.getInstance().getCodeMap().clear();
		Simulator.getInstance().getLabelMap().clear();
		Simulator.getInstance().getBreakpoints().clear();

		int linenumber = 0;
		// Scan for labeladresses
		short adr = 0x0000;
		for (String line : codeLines) {
			String[] command = line.split(" ");
			String mnemonic = command[0].toLowerCase();
			if (mnemonic.equals("org")) { // is Org
				adr = (short) Integer.decode(command[1]).intValue();
			} else if (line.endsWith(":")) { // is label
				String labelName = mnemonic.substring(0, mnemonic.length() - 1);
				Simulator.getInstance().addLabel(labelName, adr);
			} else if (line.startsWith(";") || line.equals("")) { // Comment-Line
				// Do nothing
			} else { // is mnemonic
				adr += Simulator.getInstance().getMnemonics().get(mnemonic)
						.size();
				// Add Mnemonic-Byte-Size to Programsize
				Simulator.getInstance().setProgramSize(
						Simulator.getInstance().getProgramSize()
								+ Simulator.getInstance().getMnemonics()
										.get(mnemonic).size());
				// Add 1 to Commandcount
				Simulator.getInstance().setCommandCount(
						Simulator.getInstance().getCommandCount() + 1);
			}
		}

		if (scanForSyntaxErrors()) {
			short adresse = 0x0000; // Adresse wird per ORG angepasst
			for (String line : codeLines) {
				String[] command = line.split(" ");
				String mnemonic = command[0].toLowerCase();

				// Pseudo ops
				if (mnemonic.equals("org")) {
					adresse = (short) Integer.decode(command[1]).intValue();
				} else if (mnemonic.equals("@:")) {
					Simulator.getInstance().addBreakpoint(adresse);
				} else if (mnemonic.endsWith(":")) {
					String labelName = mnemonic.substring(0,
							mnemonic.length() - 1);
					Simulator.getInstance().addLabel(labelName, adresse);
				} else if (mnemonic.startsWith(";") || line.equals("")) {
					// DO nothing
				} else {
					byte[] opcodes = new byte[0];
					if (command.length == 1) {
						opcodes = Simulator.getInstance().getOpcodes(mnemonic,
								new String[0]);
					} else {
						opcodes = Simulator.getInstance().getOpcodes(mnemonic,
								command[1].split(","));
					}
					Simulator.getInstance().getCodeMap()
							.put(adresse, linenumber);
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

	/**
	 * Scans sourcecode for syntaxerrors.
	 */
	private boolean scanForSyntaxErrors() {
		int linenum = 0;
		boolean isCorrect = true;
		try {
			String[] codeLines = code.split("\r\n");
			for (String line : codeLines) {
				String[] command = line.split(" ");
				if (command[0].equalsIgnoreCase("org")) { // PSEUDO-OP ORG
					if (command.length != 2) {
						throw new AssemblerException(
								"Wrong number of arguments", linenum);
					}
				} else if (Simulator.getInstance().getMnemonics()
						.containsKey(command[0].toLowerCase())) { // is Mnemonic
					String[] args;
					if (command.length < 2) { // No arguments available
						args = new String[0];
					} else {
						args = command[1].split(",");
					}
					if (!Simulator.getInstance().getMnemonics()
							.get(command[0].toLowerCase())
							.validateArguments(args)) {
						throw new AssemblerException("Wrong arguments", linenum);
					}
				}
				linenum++;
			}
		} catch (AssemblerException ex) {
			/*
			 * MessageBox messageBox = new MessageBox(Display.getDefault()
			 * .getActiveShell(), SWT.ICON_ERROR | SWT.OK);
			 * messageBox.setText("Assembling error");
			 * messageBox.setMessage(ex.getMessage()); messageBox.open();
			 */

			isCorrect = false;
			//ex.printStackTrace();

			String[] lines = sv.getText().split("\r\n");
			int pos = 0;
			for (int i = 0; i < ex.getLine(); i++) {
				pos += lines[i].length()+2;
			}
			
			System.out.println(pos);
			sv.addCompileError(ex.getLine(), new Position(pos, 3), ex.getMessage());
		}
		return isCorrect;
	}
}
