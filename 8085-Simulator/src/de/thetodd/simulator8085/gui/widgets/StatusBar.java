package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.ISimulatorListener;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;
import de.thetodd.simulator8085.gui.Messages;

public class StatusBar extends Composite implements ISimulatorListener {

	private CLabel lblStatus;
	private final Image SUCCESS_IMG = SWTResourceManager.getImage(
			StatusBar.class, "/de/thetodd/simulator8085/gui/icons/accept.png");
	private final Image ERROR_IMG = SWTResourceManager.getImage(
			StatusBar.class, "/de/thetodd/simulator8085/gui/icons/error.png");
	private final Image WARNING_IMG = SWTResourceManager.getImage(
			StatusBar.class, "/de/thetodd/simulator8085/gui/icons/warning.png");
	private final Image INFORMATION_IMG = SWTResourceManager.getImage(
			StatusBar.class,
			"/de/thetodd/simulator8085/gui/icons/information.png");

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StatusBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		Label label = new Label(this, SWT.NONE);
		label.setText(Messages.SimulatorMainWindow_lblNewLabel_5_text);

		lblStatus = new CLabel(this, SWT.NONE);
		lblStatus.setAlignment(SWT.RIGHT);
		lblStatus.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		lblStatus.setText(Messages.StatusBar_lblStatus_text);

		Simulator.getInstance().registerSimulatorListener(this);
	}

	@Override
	protected void checkSubclass() {
	}

	@Override
	public void globalSimulatorEvent(SimulatorEvent evt) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				switch (evt.getType()) {
				case ERROR:
					lblStatus.setForeground(new Color(Display.getDefault(),
							255, 0, 0));
					lblStatus.setImage(ERROR_IMG);
					break;
				case INFORMATION:
					lblStatus.setForeground(new Color(Display.getDefault(), 0,
							50, 200));
					lblStatus.setImage(INFORMATION_IMG);
					break;
				case SUCCESS:
					lblStatus.setForeground(new Color(Display.getDefault(), 0,
							150, 0));
					lblStatus.setImage(SUCCESS_IMG);
					break;
				case WARNING:
					lblStatus.setForeground(new Color(Display.getDefault(),
							150, 150, 0));
					lblStatus.setImage(WARNING_IMG);
					break;
				default:
					lblStatus.setForeground(new Color(Display.getDefault(), 0,
							0, 0));
					lblStatus.setImage(null);
					break;
				}
				lblStatus.setText(evt.getMessage());
			}
		});
	}

}
