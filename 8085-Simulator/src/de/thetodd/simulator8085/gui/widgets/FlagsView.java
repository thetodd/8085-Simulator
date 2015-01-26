package de.thetodd.simulator8085.gui.widgets;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.ProcessorChangedListener;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent.Register;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.Messages;
import de.thetodd.simulator8085.gui.SimulatorMainWindow;

public class FlagsView extends Group implements ProcessorChangedListener {

	private Label lblSignFlag;
	private Label lblZeroFlag;
	private Label lblACarryFlag;
	private Label lblParityFlag;
	private Label lblCarryFlag;

	public FlagsView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(5, true));
		setText(Messages.SimulatorMainWindow_grpFlags_text);

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("S");

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setText("Z");

		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setText("AC");

		Label lblNewLabel_3 = new Label(this, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_3.setAlignment(SWT.CENTER);
		lblNewLabel_3.setText("P");

		Label lblNewLabel_4 = new Label(this, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_4.setAlignment(SWT.CENTER);
		lblNewLabel_4.setText("C");

		lblSignFlag = new Label(this, SWT.NONE);
		lblSignFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSignFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblZeroFlag = new Label(this, SWT.NONE);
		lblZeroFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblZeroFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblACarryFlag = new Label(this, SWT.NONE);
		lblACarryFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblACarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblParityFlag = new Label(this, SWT.NONE);
		lblParityFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblParityFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblCarryFlag = new Label(this, SWT.NONE);
		lblCarryFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblCarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));
		
		Simulator.getInstance().registerChangeListener(this);
	}
	
	@Override
	protected void checkSubclass() {
	}

	@Override
	public void memoryChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {
		List<Register> regs = Arrays.asList(evt.getRegister());
		if (regs.contains(Register.REGISTER_F)) {
			byte f = Processor.getInstance().getRegisterF();
			Image imgSet = SWTResourceManager.getImage(
					SimulatorMainWindow.class,
					"/de/thetodd/simulator8085/gui/icons/accept.png");
			Image imgNotSet = SWTResourceManager.getImage(
					SimulatorMainWindow.class,
					"/de/thetodd/simulator8085/gui/icons/delete.png");
			if ((f & 0x80) == 0x80) {
				lblSignFlag.setImage(imgSet);
			} else {
				lblSignFlag.setImage(imgNotSet);
			}
			if ((f & 0x40) == 0x40) {
				lblZeroFlag.setImage(imgSet);
			} else {
				lblZeroFlag.setImage(imgNotSet);
			}
			if ((f & 0x10) == 0x10) {
				lblACarryFlag.setImage(imgSet);
			} else {
				lblACarryFlag.setImage(imgNotSet);
			}
			if ((f & 0x04) == 0x04) {
				lblParityFlag.setImage(imgSet);
			} else {
				lblParityFlag.setImage(imgNotSet);
			}
			if ((f & 0x01) == 0x01) {
				lblCarryFlag.setImage(imgSet);
			} else {
				lblCarryFlag.setImage(imgNotSet);
			}
		}
	}

	@Override
	public void outChanged(byte adr, byte value) {
		// TODO Auto-generated method stub
		
	}

}
