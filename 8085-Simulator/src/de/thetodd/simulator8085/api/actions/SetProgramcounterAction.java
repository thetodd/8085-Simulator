package de.thetodd.simulator8085.api.actions;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.GlobalSimulatorEvents;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.SetPCDialog;

public class SetProgramcounterAction implements Action {

	@Override
	public void run() {
		SetPCDialog dialog = new SetPCDialog(new Shell());
		int result = dialog.open();
		if (result == Window.OK) {
			Processor.getInstance().setProgramcounter(dialog.getSelectedAddress());
			SimulatorEvent evt = new SimulatorEvent(
					GlobalSimulatorEvents.REGISTER_PC_CHANGED, "",
					SimulatorEvent.TYPE.INFORMATION);
			Simulator.getInstance().fireSimulatorEvent(evt);
		}

	}

}
