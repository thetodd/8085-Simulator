package de.thetodd.simulator8085.api.actions;


/**
 * Simulates the code till the next breakpoint. After execution it calculates the time
 * the code would take on a real 8085 with a specific clockrate.
 * @author Florian Schleich
 * @since 1.0
 *
 */
public class SimulateAction implements Action {

	private double clockrate; //Clockrate in MHz
	
	public SimulateAction(double clockrate) {
		this.clockrate = clockrate;
	}

	@Override
	public void run() {
		SimulateThread th = new SimulateThread(clockrate);
		th.start();
	}

}
