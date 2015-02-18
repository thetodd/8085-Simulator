package de.thetodd.simulator8085.gui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.thetodd.simulator8085.api.platform.Processor;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class SetPCDialog extends Dialog {
	private Text text;
	private String adrText;

	/**
	 * Create the dialog.
	 * @param parentShell
	 */
	public SetPCDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = (GridLayout) container.getLayout();
		gridLayout.horizontalSpacing = 2;
		gridLayout.numColumns = 2;
		
		Label lblAddress = new Label(container, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAddress.setText(Messages.SetPCDialog_lblAddress_text);
		
		text = new Text(container, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				adrText = text.getText();
			}
		});
		text.setText(String.format("%04X", Processor
				.getInstance().getProgramcounter()));
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return container;
	}

	/**
	 * Create contents of the button bar.
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Return the initial size of the dialog.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(216, 123);
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText(Messages.SimulatorMainWindow_mntmSetProgramcounter_text);
		// TODO Auto-generated method stub
		super.configureShell(newShell);
	}

	public short getSelectedAddress() {
		try {
			short adr = (short) Integer.parseInt(adrText, 16);
			return adr;
		} catch (NumberFormatException ex) {
			MessageDialog.openConfirm(new Shell(), "Wrong number format", ex.getLocalizedMessage());
			return Processor.getInstance().getProgramcounter();
		}
	}

}
