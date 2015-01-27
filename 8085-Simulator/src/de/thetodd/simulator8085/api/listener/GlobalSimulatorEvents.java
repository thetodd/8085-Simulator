package de.thetodd.simulator8085.api.listener;

/**
 * The containing class of possible global simulator events.
 * @see {@link ISimulatorListener}, {@link SimulatorEvent}
 * @author Florian Schleich <florian.schleich@informatik.hs-fulda.de>
 *
 */
public class GlobalSimulatorEvents {

	public static final String RESET_PROCESSOR = "reset.processor";
	public static final String ASSEMBLED = "assembled";
	public static final String REGISTER_ALL_CHANGED = "register.changed.all";
	public static final String REGISTER_A_CHANGED = "register.changed.a";
	public static final String REGISTER_B_CHANGED = "register.changed.b";
	public static final String REGISTER_C_CHANGED = "register.changed.c";
	public static final String REGISTER_D_CHANGED = "register.changed.d";
	public static final String REGISTER_E_CHANGED = "register.changed.e";
	public static final String REGISTER_F_CHANGED = "register.changed.f";
	public static final String REGISTER_H_CHANGED = "register.changed.h";
	public static final String REGISTER_L_CHANGED = "register.changed.l";
	public static final String REGISTER_SP_CHANGED = "register.changed.sp";
	public static final String REGISTER_PC_CHANGED = "register.changed.pc";
	public static final String PORT_WRITE = "port.write";
	public static final String MEMORY_CHANGE = "memory.change";
	public static final String STATUS = "status";
	
}
