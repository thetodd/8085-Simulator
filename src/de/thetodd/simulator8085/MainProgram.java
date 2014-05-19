package de.thetodd.simulator8085;

import java.util.Arrays;
import java.util.List;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class MainProgram {

	public static void main(String[] args) {
		try {
			List<String> argList = Arrays.asList(args);
			if (argList.contains("-DEBUG")) {
				Simulator.getInstance().setDebugMode(true);
			}
			SimulatorMainWindow window = new SimulatorMainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
