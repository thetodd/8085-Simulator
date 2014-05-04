package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorError;
import de.thetodd.simulator8085.api.RegisterChangeEvent;
import de.thetodd.simulator8085.api.Simulator;

public class SimulateAction implements Action {

	private boolean run = true;
	
	public SimulateAction() {
	}

	@Override
	public void run() {
		//System.out.println("Simulate");
		Collection<Mnemonic> mnemonics = Simulator.getInstance()
				.getUsableMnemonics();
		while (run) {
			try {
				short pc = Processor.getInstance().getProgramcounter();
				byte opcode = Memory.getInstance().get(pc);
				for (Mnemonic mnemonic : mnemonics) {
					if (mnemonic.hasOpcode(opcode)) {
						//System.out.println(mnemonic.toString());
						mnemonic.execute();
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
	}

}
