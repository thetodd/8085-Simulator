package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.Messages;
import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class SettingsView extends Composite {

	public SettingsView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		Label lblSpeicherbereich = new Label(this, SWT.NONE);
		lblSpeicherbereich
				.setText(Messages.SimulatorMainWindow_lblSpeicherbereich_text);

		Text txtMemoryStart = new Text(this, SWT.BORDER);
		txtMemoryStart.setText("0x1800");
		new Label(this, SWT.NONE);

		Label lblSpeicherbereichEnde = new Label(this, SWT.NONE);
		lblSpeicherbereichEnde
				.setText(Messages.SimulatorMainWindow_lblSpeicherbereichEnde_text);

		Text txtMemoryEnd = new Text(this, SWT.BORDER);
		txtMemoryEnd.setText("0x1BFF");
		new Label(this, SWT.NONE);

		Button btnSetMemory = new Button(this, SWT.NONE);
		btnSetMemory.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 2, 1));
		btnSetMemory.setText(Messages.SimulatorMainWindow_btnSetMemory_text);
		btnSetMemory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				short start = Integer.decode(txtMemoryStart.getText())
						.shortValue();
				short end = Integer.decode(txtMemoryEnd.getText()).shortValue();
				Memory.getInstance().setRange(start, end);
				txtMemoryStart.setText(String.format("0x%04X", Memory
						.getInstance().getMemoryStart()));
				txtMemoryEnd.setText(String.format("0x%04X", Memory
						.getInstance().getMemoryEnd()));

				Processor.getInstance().resetProcessor();
				Simulator.getInstance().fireMemoryChangeEvent();
			}
		});
		btnSetMemory
				.setToolTipText(Messages.SimulatorMainWindow_btnSetMemory_toolTipText);
		btnSetMemory.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive_edit.png"));
		new Label(this, SWT.NONE);

		Label lblClockrate = new Label(this, SWT.NONE);
		lblClockrate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblClockrate.setText(Messages.SimulatorMainWindow_lblClockrte_text);

		Text txtClock = new Text(this, SWT.BORDER);
		txtClock.setText("");
		GridData gd_txtClock = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1);
		gd_txtClock.widthHint = 96;
		txtClock.setLayoutData(gd_txtClock);
	}

	@Override
	protected void checkSubclass() {
	}
	
}
