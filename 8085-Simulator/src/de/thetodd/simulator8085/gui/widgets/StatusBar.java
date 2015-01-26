package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

import de.thetodd.simulator8085.gui.Messages;
import org.eclipse.swt.layout.GridData;

public class StatusBar extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public StatusBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Label label = new Label(this, SWT.NONE);
		label.setText(Messages.SimulatorMainWindow_lblNewLabel_5_text);
		
		Label lblStatus = new Label(this, SWT.NONE);
		lblStatus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblStatus.setText(Messages.StatusBar_lblStatus_text);
	}

	@Override
	protected void checkSubclass() {
	}

}
