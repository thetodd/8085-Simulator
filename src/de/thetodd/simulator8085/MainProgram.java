package de.thetodd.simulator8085;

import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class MainProgram {

	public static void main(String[] args) {
		try {
			SimulatorMainWindow window = new SimulatorMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
