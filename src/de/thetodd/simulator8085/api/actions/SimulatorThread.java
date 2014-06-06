package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class SimulatorThread extends Thread {

	private SimulatorMainWindow mainwindow;

	private boolean run;

	public SimulatorThread(SimulatorMainWindow mw) {
		this.mainwindow = mw;
	}
	
	public void stopRunning() {
		this.run = false;
	}
	
	public void startRunning() {
		this.run = true;
		this.start();
	}
	
	public boolean isRunning() {
		return this.run;
	}

	@Override
	public void run() {
		Collection<Mnemonic> mnemonics = Simulator.getInstance()
				.getUsableMnemonics();
		while (run) {
			try {
				short pc = Processor.getInstance().getProgramcounter();
				byte opcode = Memory.getInstance().get(pc);
				System.out.printf("Opcode: %02Xh\n", opcode);
				for (Mnemonic mnemonic : mnemonics) {
					if (mnemonic.hasOpcode(opcode)) {
						mnemonic.execute();
						break;
					}
				}
				if (Simulator.getInstance().isBreakpoint(
						Processor.getInstance().getProgramcounter())) {
					run = false;
				}
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
						mainwindow.updateLineHighlighting();
					}
				});
				
				Thread.sleep(1000);
			} catch (ProcessorError | InterruptedException ex) {
				if (!ex.getMessage().equals("System Halt!")) {
					ex.printStackTrace();
				}
				run = false;
			}
		}
	}
}
