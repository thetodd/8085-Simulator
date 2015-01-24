package de.thetodd.simulator8085.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;

public class AboutDialog extends Dialog {

	/**
	 * Create the dialog.
	 * 
	 * @param parentShell
	 */
	public AboutDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.numColumns = 2;
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;

		Label lblSimulator = new Label(container, SWT.NONE);
		lblSimulator.setFont(SWTResourceManager.getFont("Segoe UI", 14,
				SWT.NORMAL));
		lblSimulator.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				false, 2, 1));
		lblSimulator.setText(Messages.AboutDialog_lblSimulator_text);

		Label lblVersion = new Label(container, SWT.NONE);
		lblVersion.setText(Messages.AboutDialog_lblVersion_text);

		Label lblVersionString = new Label(container, SWT.NONE);
		lblVersionString.setText(Messages.ProgramVersion);
		
		StyledText styledText = new StyledText(container, SWT.BORDER | SWT.WRAP);
		styledText.setText(Messages.AboutDialog_styledText_text);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		return container;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(450, 300);
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText(Messages.AboutDialog_newShell_text);
		// TODO Auto-generated method stub
		super.configureShell(newShell);
	}

}
