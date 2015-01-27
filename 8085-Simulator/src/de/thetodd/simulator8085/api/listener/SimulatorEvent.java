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
	private String event;
	
	public SimulatorEvent() {
		this("","",TYPE.ERROR);
	}

	public SimulatorEvent(String event, String message, SimulatorEvent.TYPE type) {
		super();
		this.message = message;
		this.type = type;
		this.event = event;
	}
	
	public String getMessage() {
		return message;
	}
	
	public SimulatorEvent.TYPE getType() {
		return type;
	}
	
	/**
	 * A String which identifies the SimulatorEvent.
	 * @return identifying constant String from {@link GlobalSimulatorEvents}
	 */
	public String getEvent() {
		return event;
	}
	
}
