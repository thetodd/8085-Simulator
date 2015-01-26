package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.gui.Messages;

public class ProgramInfoView extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ProgramInfoView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		Label lblProgrammgre = new Label(this, SWT.NONE);
		lblProgrammgre
				.setText(Messages.SimulatorMainWindow_lblProgrammgre_text);

		Text lblProgramSize = new Text(this, SWT.NONE);
		lblProgramSize
				.setText(Messages.SimulatorMainWindow_lblProgramSize_text);
		lblProgramSize.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblProgramSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAnzahlAnweisungen = new Label(this, SWT.NONE);
		lblAnzahlAnweisungen
				.setText(Messages.SimulatorMainWindow_lblAnzahlAnweisungen_text);

		Text lblCommandCount = new Text(this, SWT.NONE);
		lblCommandCount
				.setText(Messages.SimulatorMainWindow_lblCommandCount_text);
		lblCommandCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblCommandCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label lblAuslastungSpeicherbereich = new Label(this, SWT.NONE);
		lblAuslastungSpeicherbereich
				.setText(Messages.SimulatorMainWindow_lblAuslastungSpeicherbereich_text);

		Text lblPercent = new Text(this, SWT.NONE);
		lblPercent.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblPercent.setText(Messages.SimulatorMainWindow_lblPercent_text);
		lblPercent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		ProgressBar pbLoad = new ProgressBar(this, SWT.NONE);
		pbLoad.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
				2, 1));
	}

	@Override
	protected void checkSubclass() {
	}

}
