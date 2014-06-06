package de.thetodd.simulator8085.api.actions;

import de.thetodd.simulator8085.gui.SimulatorMainWindow;


/**
 * Simulates the 8085 processor.
 * @author Florian
 *
 */
public class RunSimulateAction implements Action {
	
	private SimulatorMainWindow mw;

	public RunSimulateAction(SimulatorMainWindow mw) {
		this.mw = mw;
	}

	@Override
	public void run() {
		SimulatorThread st = new SimulatorThread(mw);
		st.start();
	}

}
