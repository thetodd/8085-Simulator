package de.thetodd.simulator8085.api.actions;

import java.util.Collection;

import de.thetodd.simulator8085.api.Mnemonic;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.exceptions.ProcessorError;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;

public class OneStepAction implements Action {

	@Override
	public void run() {
		try {
		//System.out.println("OneStep");
		short pc = Processor.getInstance().getProgramcounter();
		byte opcode = Memory.getInstance().get(pc);
		Collection<Mnemonic> mnemonics = Simulator.getInstance().getUsableMnemonics();
		for (Mnemonic mnemonic : mnemonics) {
			if(mnemonic.validateOpcode(opcode)) {
				mnemonic.execute();
				break;
			}
		}
		} catch (ProcessorError ex) {
			ex.printStackTrace();
		}
	}

}
