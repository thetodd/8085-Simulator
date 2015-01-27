package de.thetodd.simulator8085.gui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.listener.GlobalSimulatorEvents;
import de.thetodd.simulator8085.api.listener.ISimulatorListener;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.Messages;

public class RegistersView extends Group implements ISimulatorListener {

	private Text txtRegisterA;
	private Text txtRegisterB;
	private Text txtRegisterC;
	private Text txtRegisterD;
	private Text txtRegisterE;
	private Text txtRegisterH;
	private Text txtRegisterL;
	private Text txtRegisterSP;
	private Text txtRegisterPC;

	public RegistersView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(7, false));
		setText(Messages.SimulatorMainWindow_grpRegister_text);

		Label lblA = new Label(this, SWT.NONE);
		lblA.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblA.setAlignment(SWT.CENTER);
		lblA.setText("A");

		Label lblB = new Label(this, SWT.NONE);
		lblB.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblB.setText("B");
		lblB.setAlignment(SWT.CENTER);

		Label lblC = new Label(this, SWT.NONE);
		lblC.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblC.setText("C");
		lblC.setAlignment(SWT.CENTER);

		Label lblD = new Label(this, SWT.NONE);
		lblD.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblD.setText("D");
		lblD.setAlignment(SWT.CENTER);

		Label lblE = new Label(this, SWT.NONE);
		lblE.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblE.setText("E");
		lblE.setAlignment(SWT.CENTER);

		Label lblH = new Label(this, SWT.NONE);
		lblH.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblH.setText("H");
		lblH.setAlignment(SWT.CENTER);

		Label lblL = new Label(this, SWT.NONE);
		lblL.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblL.setText("L");
		lblL.setAlignment(SWT.CENTER);

		txtRegisterA = new Text(this, SWT.BORDER);
		txtRegisterA
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterA.setForeground(SWTResourceManager.getColor(220, 20, 60));
		txtRegisterA.setText("0x00");
		txtRegisterA.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));

		txtRegisterB = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterB.setText("0x00");
		txtRegisterB.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterB.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterB
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterC = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterC.setText("0x00");
		txtRegisterC.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterC
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterD = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterD.setText("0x00");
		txtRegisterD.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterD.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterD
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterE = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterE.setText("0x00");
		txtRegisterE.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterE.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterE
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterH = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterH.setText("0x00");
		txtRegisterH.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterH.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterH
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterL = new Text(this, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterL.setText("0x00");
		txtRegisterL.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterL.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterL
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label lblSp = new Label(this, SWT.NONE);
		lblSp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				2, 1));
		lblSp.setText(Messages.SimulatorMainWindow_lblSp_text);
		lblSp.setAlignment(SWT.CENTER);

		Label lblPc = new Label(this, SWT.NONE);
		lblPc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				2, 1));
		lblPc.setText(Messages.SimulatorMainWindow_lblPc_text);
		lblPc.setAlignment(SWT.CENTER);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		txtRegisterSP = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		txtRegisterSP.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false, 2, 1));
		txtRegisterSP.setText("0x0000");
		txtRegisterSP.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterSP.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterSP.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));

		txtRegisterPC = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.CENTER);
		txtRegisterPC.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false, 2, 1));
		txtRegisterPC.setText("0x0000");
		txtRegisterPC.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterPC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterPC.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		// Simulator.getInstance().registerChangeListener(this);
		Simulator.getInstance().registerSimulatorListener(this);
	}

	@Override
	protected void checkSubclass() {

	}

	@Override
	public void globalSimulatorEvent(SimulatorEvent evt) {
		if (evt.getEvent().startsWith("register.changed")) {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_A_CHANGED)) {
						txtRegisterA.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterA()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_B_CHANGED)) {
						txtRegisterB.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterB()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_C_CHANGED)) {
						txtRegisterC.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterC()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_D_CHANGED)) {
						txtRegisterD.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterD()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_E_CHANGED)) {
						txtRegisterE.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterE()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_H_CHANGED)) {
						txtRegisterH.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterH()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_L_CHANGED)) {
						txtRegisterL.setText(String.format("0x%02X", Processor
								.getInstance().getRegisterL()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_SP_CHANGED)) {
						txtRegisterSP.setText(String.format("0x%04X", Processor
								.getInstance().getStackpointer()));
					} else if (evt.getEvent().equals(
							GlobalSimulatorEvents.REGISTER_PC_CHANGED)) {
						txtRegisterPC.setText(String.format("0x%04X", Processor
								.getInstance().getProgramcounter()));
					}
				}
			});
		}
	}

}
