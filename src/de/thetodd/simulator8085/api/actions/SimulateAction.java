package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import org.eclipse.jface.dialogs.MessageDialog;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

/**
 * Simulates the code till the next breakpoint. After execution it calculates the time
 * the code would take on a real 8085 with a specific clockrate.
 * @author Florian Schleich
 * @since 1.0
 *
 */
public class SimulateAction implements Action {

	private boolean run = true;
	
	private double clockrate; //Clockrate in MHz
	
	public SimulateAction(double clockrate) {
		this.clockrate = clockrate;
	}

	@Override
	public void run() {
		//System.out.println("Simulate");
		Collection<Mnemonic> mnemonics = Simulator.getInstance()
				.getUsableMnemonics();
		int clocks = 0; //Counter for clocks needed for running
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
				if(Simulator.getInstance().isBreakpoint(Processor.getInstance().getProgramcounter())) {
					//System.out.println("Breakpoint");
					run = false;
				}
				Simulator.getInstance().fireRegisterChangeEvent(new RegisterChangeEvent(RegisterChangeEvent.getAllTemplate()));
			} catch (ProcessorError ex) {
				if(!ex.getMessage().equals("System Halt!")) {
					ex.printStackTrace();
				}
				run = false;
				//System.out.println("Simulation fertig!");
			}
		}
		double quarz = clockrate; //Quarz in MHz
		int q = (int) (quarz*1000000); //Umrechnung in Hz
		double taktzeit = 1.0/q; //Umrechnung in Sekunden
		double zeit = taktzeit * clocks; //Ausführungszeit
		zeit = (float) zeit;
		MessageDialog.openInformation(null, "Clocks needed", String.format("%d clocks needed for running.\nIt will take %fs @ %fMHz", clocks,zeit,quarz));
	}

}
