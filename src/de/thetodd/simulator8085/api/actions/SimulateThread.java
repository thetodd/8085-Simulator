package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class SimulateThread extends Thread {

	private boolean run = true;
	private double clockrate;

	public SimulateThread(double clockrate) {
		this.clockrate = clockrate;
	}

	@Override
	public void run() {
		// System.out.println("Simulate");
		Collection<Mnemonic> mnemonics = Simulator.getInstance()
				.getUsableMnemonics();
		int clocks = 0; // Counter for clocks needed for running
		while (run) {
			try {
				short pc = Processor.getInstance().getProgramcounter();
				byte opcode = Memory.getInstance().get(pc);
				for (Mnemonic mnemonic : mnemonics) {
					if (mnemonic.validateOpcode(opcode)) {
						clocks += mnemonic.execute();
						break;
					}
				}
				if (Simulator.getInstance().isBreakpoint(
						Processor.getInstance().getProgramcounter())) {
					run = false;
				}

			} catch (ProcessorError ex) {
				if (!ex.getMessage().equals("System Halt!")) {
					ex.printStackTrace();
				}
				run = false;
			}
		}
		final double quarz = clockrate; // Quarz in MHz
		int q = (int) (quarz * 1000000); // Umrechnung in Hz
		double taktzeit = 1.0 / q; // Umrechnung in Sekunden
		double zeit = taktzeit * clocks; // Ausführungszeit
		zeit = (float) zeit;
		final int c = clocks;
		final double z = zeit;
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
				MessageDialog.openInformation(
						null,
						"Clocks needed",
						String.format(
								"%d clocks needed for running.\nIt will take %fs @ %fMHz",
								c, z, quarz));
			}
		});
	}

}
