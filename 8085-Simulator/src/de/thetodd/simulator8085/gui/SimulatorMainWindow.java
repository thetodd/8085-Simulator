package de.thetodd.simulator8085.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.actions.Action;
import de.thetodd.simulator8085.api.actions.AssembleAction;
import de.thetodd.simulator8085.api.actions.OneStepAction;
import de.thetodd.simulator8085.api.actions.PrintAction;
import de.thetodd.simulator8085.api.actions.SimulateAction;
import de.thetodd.simulator8085.api.actions.SimulatorThread;
import de.thetodd.simulator8085.api.listener.ProcessorChangedListener;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent.Register;
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.outviews.LEDBar;
import de.thetodd.simulator8085.gui.outviews.ListView;
import de.thetodd.simulator8085.gui.sourceviewer.AssemblerSourceViewer;
import de.thetodd.simulator8085.gui.widgets.MemoryTable;

public class SimulatorMainWindow implements ProcessorChangedListener {

	protected Shell shlSimulator;
	private Text txtRegisterA;
	private Text txtRegisterB;
	private Text txtRegisterC;
	private Text txtRegisterD;
	private Text txtRegisterE;
	private Text txtRegisterH;
	private Text txtRegisterL;
	private Text txtMemoryEnd;
	private Text txtRegisterSP;
	private Text txtRegisterPC;
	private Text lblProgramSize;
	private Text lblCommandCount;
	private Text lblPercent;
	private Text txtMemoryStart;
	private ProgressBar pbLoad;
	private Label lblSignFlag;
	private Label lblZeroFlag;
	private Label lblACarryFlag;
	private Label lblParityFlag;
	private Label lblCarryFlag;

	private File document;
	private SimulatorMainWindow window;
	private SimulatorThread simThread;
	private CLabel lblStatusLine; // a rudimental statusline
	private Label lblClockrate;
	private Text txtClock;
	private AssemblerSourceViewer sv;

	public SimulatorMainWindow() {

	}

	/**
	 * Open the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		final Display display = Display.getDefault();

		createContents();
		shlSimulator.open();
		shlSimulator.layout();

		Simulator.getInstance().registerChangeListener(this);
		Simulator.getInstance().fireMemoryChangeEvent();
		Simulator.getInstance().fireRegisterChangeEvent(
				new RegisterChangeEvent(RegisterChangeEvent.getAllTemplate()));

		this.window = this;

		while (!shlSimulator.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlSimulator = new Shell();
		shlSimulator
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/application_xp_terminal.png"));
		shlSimulator.setSize(737, 462);
		if (Simulator.getInstance().isDebugMode()) {
			shlSimulator.setText("8085 Simulator [DEBUG-MODE]");
		} else {
			shlSimulator.setText("8085 Simulator");
		}
		shlSimulator.setLayout(new GridLayout(2, false));

		Menu menu = new Menu(shlSimulator, SWT.BAR);
		shlSimulator.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText(Messages.SimulatorMainWindow_mntmFile_text);

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNewFile = new MenuItem(menu_1, SWT.NONE);
		mntmNewFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				document = null;
				Processor.getInstance().resetProcessor();
				Memory.getInstance().resetMemory();
				Simulator.getInstance().fireMemoryChangeEvent();
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
				sv.setText("");
			}
		});
		mntmNewFile.setText(Messages.SimulatorMainWindow_mntmNewFile_text);
		mntmNewFile.setAccelerator(SWT.MOD1 + 'N');

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					document = fc.getSelectedFile();
					try {
						String text = new String(Files.readAllBytes(document
								.toPath()));
						sv.getDocument().set(text);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		mntmOpen.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/folder_explore.png"));
		mntmOpen.setText(Messages.SimulatorMainWindow_mntmOpen_text);
		mntmOpen.setAccelerator(SWT.MOD1 + 'O');

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (document == null) { // We have a new file, ask for save path
					JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(null);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						document = fc.getSelectedFile();
					}
				}
				if (document != null) {
					try {
						// Create file
						FileWriter fstream = new FileWriter(document);
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(sv.getDocument().get());
						// Close the output stream
						out.close();
					} catch (Exception e) {// Catch exception if any
						System.err.println("Error: " + e.getMessage());
					}
				}
			}
		});
		mntmSave.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/disk.png"));
		mntmSave.setText(Messages.SimulatorMainWindow_mntmSave_text);
		mntmSave.setAccelerator(SWT.MOD1 + 'S');

		MenuItem mntmPrint = new MenuItem(menu_1, SWT.NONE);
		mntmPrint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PrintAction print = new PrintAction(sv.getDocument().get(),
						"Filename");
				print.run();
			}
		});
		mntmPrint.setText(Messages.SimulatorMainWindow_mntmPrint_text);
		mntmPrint.setAccelerator(SWT.F10);

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmClose = new MenuItem(menu_1, SWT.NONE);
		mntmClose.setText(Messages.SimulatorMainWindow_mntmClose_text);
		mntmClose.setAccelerator(SWT.MOD2 + SWT.F4);

		MenuItem mntmSimulate = new MenuItem(menu, SWT.CASCADE);
		mntmSimulate.setText(Messages.SimulatorMainWindow_mntmSimulate_text);

		Menu menu_2 = new Menu(mntmSimulate);
		mntmSimulate.setMenu(menu_2);

		MenuItem mntmAsseble = new MenuItem(menu_2, SWT.NONE);
		mntmAsseble.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Action assemble = new AssembleAction(sv, sv.getText());
				setStatus("Assembling...");
				assemble.run();

				// Reload counters
				lblProgramSize.setText(Simulator.getInstance().getProgramSize()
						+ " Byte");
				lblCommandCount.setText(Simulator.getInstance()
						.getCommandCount() + " Anweisungen");
				short memSize = Memory.getInstance().getMemorySize();
				double load = Simulator.getInstance().getProgramSize()
						/ memSize * 100;
				pbLoad.setSelection((int) load);
				if (load > memSize) {
					pbLoad.setState(SWT.ERROR);
				} else {
					pbLoad.setState(SWT.NORMAL);
				}

				updateLineHighlighting(); // maybe to soon
				clearStatus();
			}
		});
		mntmAsseble.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/wrench.png"));
		mntmAsseble.setText(Messages.SimulatorMainWindow_mntmAsseble_text);
		mntmAsseble.setAccelerator(SWT.F6);

		MenuItem mntmResetProcessor = new MenuItem(menu_2, SWT.NONE);
		mntmResetProcessor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Memory.getInstance().resetMemory();
				Processor.getInstance().resetProcessor();
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
				setStatus("Processor has been reset");
			}
		});
		mntmResetProcessor
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/arrow_rotate_anticlockwise.png"));
		mntmResetProcessor
				.setText(Messages.SimulatorMainWindow_mntmResetProcessor_text);
		mntmResetProcessor.setAccelerator(SWT.MOD1 + 'R');

		MenuItem mntmSimulate_1 = new MenuItem(menu_2, SWT.NONE);
		mntmSimulate_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (simThread != null && simThread.isRunning()) {
					simThread.stopRunning();
				}
				simThread = new SimulatorThread(window);
				setStatus("Simulating...");
				simThread.startRunning();
				clearStatus();
			}
		});
		mntmSimulate_1
				.setText(Messages.SimulatorMainWindow_mntmSimulate_1_text);

		MenuItem mntmStopSim = new MenuItem(menu_2, SWT.NONE);
		mntmStopSim.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (simThread != null && simThread.isRunning()) {
					simThread.stopRunning();
					clearStatus();
				}
			}
		});
		mntmStopSim.setText(Messages.SimulatorMainWindow_mntmStopSim_text);

		MenuItem mntmNexBreakpoint = new MenuItem(menu_2, SWT.NONE);
		mntmNexBreakpoint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					double clockrate = Double.valueOf(txtClock.getText());
					SimulateAction simulate = new SimulateAction(clockrate);
					setStatus("Simulating till next breakpoint...");
					simulate.run();
					updateLineHighlighting();
					clearStatus();
				} catch (NumberFormatException ex) {
					MessageDialog.openError(shlSimulator, "Wrong Clockrate",
							"The clockrate \"" + txtClock.getText()
									+ "\" has the wrong format.");
				}
			}
		});
		mntmNexBreakpoint.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/control_end_blue.png"));
		mntmNexBreakpoint
				.setText(Messages.SimulatorMainWindow_mntmNexBreakpoint_text);
		mntmNexBreakpoint.setAccelerator(SWT.F8);

		MenuItem mntmOneStep = new MenuItem(menu_2, SWT.NONE);
		mntmOneStep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Action einzel = new OneStepAction();
				einzel.run();
				updateLineHighlighting();
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
			}
		});
		mntmOneStep.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/control_play.png"));
		mntmOneStep.setText(Messages.SimulatorMainWindow_mntmOneStep_text);
		mntmOneStep.setAccelerator(SWT.F7);

		MenuItem mntmViewers = new MenuItem(menu, SWT.CASCADE);
		mntmViewers.setText(Messages.SimulatorMainWindow_mntmViewers_text);

		Menu menu_4 = new Menu(mntmViewers);
		mntmViewers.setMenu(menu_4);

		MenuItem mntmList = new MenuItem(menu_4, SWT.NONE);
		mntmList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ListView lv = new ListView(Display.getDefault());
				lv.open();
				lv.layout();
			}
		});
		mntmList.setText(Messages.SimulatorMainWindow_mntmList_text);

		MenuItem mntmIntersection = new MenuItem(menu_4, SWT.NONE);
		mntmIntersection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				LEDBar insect = new LEDBar(Display.getDefault());
				insect.open();
				insect.layout();
			}
		});
		mntmIntersection
				.setText(Messages.SimulatorMainWindow_mntmIntersection_text);

		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu
				.setText(Messages.SimulatorMainWindow_mntmNewSubmenu_text);

		Menu menu_3 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_3);

		MenuItem mntmHelpContents = new MenuItem(menu_3, SWT.NONE);
		mntmHelpContents
				.setText(Messages.SimulatorMainWindow_mntmHelpContents_text);
		mntmHelpContents.setAccelerator(SWT.F1);

		MenuItem mntmAbout = new MenuItem(menu_3, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AboutDialog about = new AboutDialog(shlSimulator);
				about.open();
			}
		});
		mntmAbout.setText(Messages.SimulatorMainWindow_mntmAbout_text);

		Group grpRegister = new Group(shlSimulator, SWT.NONE);
		grpRegister.setLayout(new GridLayout(7, false));
		grpRegister.setText(Messages.SimulatorMainWindow_grpRegister_text);

		Label lblA = new Label(grpRegister, SWT.NONE);
		lblA.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblA.setAlignment(SWT.CENTER);
		lblA.setText("A");

		Label lblB = new Label(grpRegister, SWT.NONE);
		lblB.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblB.setText("B");
		lblB.setAlignment(SWT.CENTER);

		Label lblC = new Label(grpRegister, SWT.NONE);
		lblC.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblC.setText("C");
		lblC.setAlignment(SWT.CENTER);

		Label lblD = new Label(grpRegister, SWT.NONE);
		lblD.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblD.setText("D");
		lblD.setAlignment(SWT.CENTER);

		Label lblE = new Label(grpRegister, SWT.NONE);
		lblE.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblE.setText("E");
		lblE.setAlignment(SWT.CENTER);

		Label lblH = new Label(grpRegister, SWT.NONE);
		lblH.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblH.setText("H");
		lblH.setAlignment(SWT.CENTER);

		Label lblL = new Label(grpRegister, SWT.NONE);
		lblL.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblL.setText("L");
		lblL.setAlignment(SWT.CENTER);

		txtRegisterA = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterA
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterA.setForeground(SWTResourceManager.getColor(220, 20, 60));
		txtRegisterA.setText("0x00");
		txtRegisterA.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));

		txtRegisterB = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterB.setText("0x00");
		txtRegisterB.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterB.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterB
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterC = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterC.setText("0x00");
		txtRegisterC.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterC
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterD = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterD.setText("0x00");
		txtRegisterD.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterD.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterD
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterE = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterE.setText("0x00");
		txtRegisterE.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterE.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterE
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterH = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterH.setText("0x00");
		txtRegisterH.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterH.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterH
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		txtRegisterL = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterL.setText("0x00");
		txtRegisterL.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterL.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterL
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label lblSp = new Label(grpRegister, SWT.NONE);
		lblSp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				2, 1));
		lblSp.setText(Messages.SimulatorMainWindow_lblSp_text);
		lblSp.setAlignment(SWT.CENTER);

		Label lblPc = new Label(grpRegister, SWT.NONE);
		lblPc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				2, 1));
		lblPc.setText(Messages.SimulatorMainWindow_lblPc_text);
		lblPc.setAlignment(SWT.CENTER);
		new Label(grpRegister, SWT.NONE);
		new Label(grpRegister, SWT.NONE);
		new Label(grpRegister, SWT.NONE);

		txtRegisterSP = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY
				| SWT.CENTER);
		txtRegisterSP.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false, 2, 1));
		txtRegisterSP.setText("0x0000");
		txtRegisterSP.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterSP.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterSP.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));

		txtRegisterPC = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY
				| SWT.CENTER);
		txtRegisterPC.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false,
				false, 2, 1));
		txtRegisterPC.setText("0x0000");
		txtRegisterPC.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterPC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterPC.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));
		new Label(grpRegister, SWT.NONE);
		new Label(grpRegister, SWT.NONE);
		new Label(grpRegister, SWT.NONE);

		Group grpFlags = new Group(shlSimulator, SWT.NONE);
		grpFlags.setLayout(new GridLayout(5, false));
		grpFlags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		grpFlags.setText(Messages.SimulatorMainWindow_grpFlags_text);

		Label lblNewLabel = new Label(grpFlags, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel.widthHint = 20;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("S");

		Label lblNewLabel_1 = new Label(grpFlags, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel_1 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel_1.widthHint = 20;
		lblNewLabel_1.setLayoutData(gd_lblNewLabel_1);
		lblNewLabel_1.setText("Z");

		Label lblNewLabel_2 = new Label(grpFlags, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel_2 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel_2.widthHint = 20;
		lblNewLabel_2.setLayoutData(gd_lblNewLabel_2);
		lblNewLabel_2.setText("AC");

		Label lblNewLabel_3 = new Label(grpFlags, SWT.NONE);
		lblNewLabel_3.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel_3 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel_3.widthHint = 20;
		lblNewLabel_3.setLayoutData(gd_lblNewLabel_3);
		lblNewLabel_3.setText("P");

		Label lblNewLabel_4 = new Label(grpFlags, SWT.NONE);
		lblNewLabel_4.setAlignment(SWT.CENTER);
		GridData gd_lblNewLabel_4 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel_4.widthHint = 20;
		lblNewLabel_4.setLayoutData(gd_lblNewLabel_4);
		lblNewLabel_4.setText("C");

		lblSignFlag = new Label(grpFlags, SWT.NONE);
		lblSignFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSignFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblZeroFlag = new Label(grpFlags, SWT.NONE);
		lblZeroFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblZeroFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblACarryFlag = new Label(grpFlags, SWT.NONE);
		lblACarryFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblACarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblParityFlag = new Label(grpFlags, SWT.NONE);
		lblParityFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblParityFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblCarryFlag = new Label(grpFlags, SWT.NONE);
		lblCarryFlag.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblCarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		CTabFolder tabFolder = new CTabFolder(shlSimulator, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1,
				1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmProgramm = new CTabItem(tabFolder, SWT.NONE);
		tbtmProgramm
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/application_xp_terminal.png"));
		tbtmProgramm.setText(Messages.SimulatorMainWindow_tbtmProgramm_text);
		tabFolder.setSelection(tbtmProgramm);

		sv = new AssemblerSourceViewer(tabFolder);
		tbtmProgramm.setControl(sv.getControl());

		CTabItem tbtmSpeicher = new CTabItem(tabFolder, SWT.NONE);
		tbtmSpeicher.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive.png"));
		tbtmSpeicher.setText(Messages.SimulatorMainWindow_tbtmSpeicher_text);

		MemoryTable mTable = new MemoryTable(tabFolder);
		tbtmSpeicher.setControl(mTable.getTable());

		CTabFolder tabFolder_1 = new CTabFolder(shlSimulator, SWT.BORDER);
		GridData gd_tabFolder_1 = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd_tabFolder_1.widthHint = 250;
		tabFolder_1.setLayoutData(gd_tabFolder_1);
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmNewItem = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmNewItem.setText(Messages.SimulatorMainWindow_tbtmNewItem_text);
		tabFolder_1.setSelection(tbtmNewItem);

		Composite composite_1 = new Composite(tabFolder_1, SWT.NONE);
		tbtmNewItem.setControl(composite_1);
		composite_1.setLayout(new GridLayout(3, false));

		Label lblSpeicherbereich = new Label(composite_1, SWT.NONE);
		lblSpeicherbereich
				.setText(Messages.SimulatorMainWindow_lblSpeicherbereich_text);

		txtMemoryStart = new Text(composite_1, SWT.BORDER);
		txtMemoryStart.setText("0x1800");
		new Label(composite_1, SWT.NONE);

		Label lblSpeicherbereichEnde = new Label(composite_1, SWT.NONE);
		lblSpeicherbereichEnde
				.setText(Messages.SimulatorMainWindow_lblSpeicherbereichEnde_text);

		txtMemoryEnd = new Text(composite_1, SWT.BORDER);
		txtMemoryEnd.setText("0x1BFF");
		new Label(composite_1, SWT.NONE);

		Button btnSetMemory = new Button(composite_1, SWT.NONE);
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
		new Label(composite_1, SWT.NONE);

		lblClockrate = new Label(composite_1, SWT.NONE);
		lblClockrate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblClockrate.setText(Messages.SimulatorMainWindow_lblClockrte_text);

		txtClock = new Text(composite_1, SWT.BORDER);
		txtClock.setText("");
		GridData gd_txtClock = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				2, 1);
		gd_txtClock.widthHint = 96;
		txtClock.setLayoutData(gd_txtClock);

		CTabItem tbtmProgramminformationen = new CTabItem(tabFolder_1, SWT.NONE);
		tbtmProgramminformationen.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/chart_bar.png"));
		tbtmProgramminformationen
				.setText(Messages.SimulatorMainWindow_tbtmProgramminformationen_text);

		Composite composite_2 = new Composite(tabFolder_1, SWT.NONE);
		tbtmProgramminformationen.setControl(composite_2);
		composite_2.setLayout(new GridLayout(2, false));

		Label lblProgrammgre = new Label(composite_2, SWT.NONE);
		lblProgrammgre
				.setText(Messages.SimulatorMainWindow_lblProgrammgre_text);

		lblProgramSize = new Text(composite_2, SWT.NONE);
		lblProgramSize
				.setText(Messages.SimulatorMainWindow_lblProgramSize_text);
		lblProgramSize.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblProgramSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblAnzahlAnweisungen = new Label(composite_2, SWT.NONE);
		lblAnzahlAnweisungen
				.setText(Messages.SimulatorMainWindow_lblAnzahlAnweisungen_text);

		lblCommandCount = new Text(composite_2, SWT.NONE);
		lblCommandCount
				.setText(Messages.SimulatorMainWindow_lblCommandCount_text);
		lblCommandCount.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblCommandCount.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		Label lblAuslastungSpeicherbereich = new Label(composite_2, SWT.NONE);
		lblAuslastungSpeicherbereich
				.setText(Messages.SimulatorMainWindow_lblAuslastungSpeicherbereich_text);

		lblPercent = new Text(composite_2, SWT.NONE);
		lblPercent.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblPercent.setText(Messages.SimulatorMainWindow_lblPercent_text);
		lblPercent.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));

		pbLoad = new ProgressBar(composite_2, SWT.NONE);
		pbLoad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				2, 1));

		lblStatusLine = new CLabel(shlSimulator, SWT.NONE);
		lblStatusLine.setText(Messages.SimulatorMainWindow_lblNewLabel_5_text);
		new Label(shlSimulator, SWT.NONE);
	}

	@Override
	public void memoryChanged() {

	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {
		// Reload Registers
		List<Register> regs = Arrays.asList(evt.getRegister());
		if (regs.contains(Register.REGISTER_A)) {
			txtRegisterA.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterA()));
		}
		if (regs.contains(Register.REGISTER_B)) {
			txtRegisterB.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterB()));
		}
		if (regs.contains(Register.REGISTER_C)) {
			txtRegisterC.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterC()));
		}
		if (regs.contains(Register.REGISTER_D)) {
			txtRegisterD.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterD()));
		}
		if (regs.contains(Register.REGISTER_E)) {
			txtRegisterE.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterE()));
		}
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
		if (regs.contains(Register.REGISTER_H)) {
			txtRegisterH.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterH()));
		}
		if (regs.contains(Register.REGISTER_L)) {
			txtRegisterL.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterL()));
		}
		if (regs.contains(Register.REGISTER_SP)) {
			txtRegisterSP.setText(String.format("0x%04X", Processor
					.getInstance().getStackpointer()));
		}
		if (regs.contains(Register.REGISTER_PC)) {
			txtRegisterPC.setText(String.format("0x%04X", Processor
					.getInstance().getProgramcounter()));
		}
	}

	public void updateLineHighlighting() {
		// TODO: has to be adapted to the new SourceViewer, perhaps by
		// annotations?!
		/*
		 * if (Simulator.getInstance().getCodeMap()
		 * .containsKey(Processor.getInstance().getProgramcounter())) { int
		 * linenumber = Simulator.getInstance().getCodeMap()
		 * .get(Processor.getInstance().getProgramcounter());
		 * codeText.setLineBackground(0, codeText.getLineCount(), null);
		 * codeText.setLineBackground(linenumber, 1, new
		 * Color(Display.getDefault(), 0xFF, 0xFF, 0x99)); }
		 */
	}

	@Override
	public void outChanged(byte adr, byte value) {
	}

	private void setStatus(final String statustext) {
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				lblStatusLine.setText("8085 Simulator - " + statustext);
			}
		});
	}

	private void clearStatus() {
		this.lblStatusLine.setText("8085 Simulator");
	}
}
