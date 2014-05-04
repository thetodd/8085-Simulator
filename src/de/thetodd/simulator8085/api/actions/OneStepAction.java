package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorError;
import de.thetodd.simulator8085.api.Simulator;

public class OneStepAction implements Action {

	@Override
	public void run() {
		try {
		//System.out.println("OneStep");
		short pc = Processor.getInstance().getProgramcounter();
		byte opcode = Memory.getInstance().get(pc);
		Collection<Mnemonic> mnemonics = Simulator.getInstance().getUsableMnemonics();
		for (Mnemonic mnemonic : mnemonics) {
			if(mnemonic.hasOpcode(opcode)) {
				mnemonic.execute();
				break;
			}
		}
		} catch (ProcessorError ex) {
			ex.printStackTrace();
		}
	}

}
