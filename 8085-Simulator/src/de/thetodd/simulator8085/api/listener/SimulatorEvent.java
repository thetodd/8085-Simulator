package de.thetodd.simulator8085.api.listener;

/**
 * A SimulatorEvent is fired when the state of the
 * {@link de.thetodd.simulator8085.api.Simulator Simulator} is changed.
 * 
 * @author Florian Schleich <florian.schleich@informatik.hs-fulda.de>
 * @since 2.0
 *
 */
public class SimulatorEvent {

	/**
	 * A SimulatorEvent can have one of these types.
	 */
	public enum TYPE {
		ERROR, WARNING, INFORMATION, SUCCESS
	}
	
	private String message;
	private SimulatorEvent.TYPE type;
	
	public SimulatorEvent() {
		this.message = "";
	}

	public SimulatorEvent(String message, SimulatorEvent.TYPE type) {
		super();
		this.message = message;
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public SimulatorEvent.TYPE getType() {
		return type;
	}
	
}
