package de.thetodd.simulator8085.api.listener;

/**
 * A ISimulatorListener listens for a change in the
 * {@link de.thetodd.simulator8085.api.Simulator Simulator} state.
 * 
 * @author Florian Schleich <florian.schleich@informatik.hs-fulda.de>
 * @since 2.0.0
 */
public interface ISimulatorListener {

	/**
	 * A global {@link SimulatorEvent} should be fired when the simulator state
	 * changes.
	 * 
	 * @param evt a SimulatorEvent
	 */
	public void globalSimulatorEvent(SimulatorEvent evt);

}
