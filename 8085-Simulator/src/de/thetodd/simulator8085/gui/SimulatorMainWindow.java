package de.thetodd.simulator8085.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFileChooser;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
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
import de.thetodd.simulator8085.api.platform.Memory;
import de.thetodd.simulator8085.api.platform.Processor;
import de.thetodd.simulator8085.gui.outviews.LEDBar;
import de.thetodd.simulator8085.gui.outviews.ListView;
import de.thetodd.simulator8085.gui.sourceviewer.AssemblerSourceViewer;
import de.thetodd.simulator8085.gui.widgets.FlagsView;
import de.thetodd.simulator8085.gui.widgets.MemoryTable;
import de.thetodd.simulator8085.gui.widgets.ProgramInfoView;
import de.thetodd.simulator8085.gui.widgets.RegistersView;
import de.thetodd.simulator8085.gui.widgets.SettingsView;
import de.thetodd.simulator8085.gui.widgets.StatusBar;

public class SimulatorMainWindow implements ProcessorChangedListener {

	protected Shell shlSimulator;
	private File document;
	private SimulatorMainWindow window;
	private SimulatorThread simThread;
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
		shlSimulator.setSize(800, 600);
		if (Simulator.getInstance().isDebugMode()) {
			shlSimulator.setText("8085 Simulator [DEBUG-MODE]");
		} else {
			shlSimulator.setText("8085 Simulator");
		}
		shlSimulator.setLayout(new GridLayout(2, false));

		addMenu();

		new RegistersView(shlSimulator, SWT.NONE);
		
		FlagsView flagsView = new FlagsView(shlSimulator, SWT.NONE);
		flagsView.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));

		SashForm sashForm = new SashForm(shlSimulator, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));

		CTabFolder tabsLeft = new CTabFolder(sashForm, SWT.BORDER);
		tabsLeft.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmProgramm = new CTabItem(tabsLeft, SWT.NONE);
		tbtmProgramm
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/application_xp_terminal.png"));
		tbtmProgramm.setText(Messages.SimulatorMainWindow_tbtmProgramm_text);
		tabsLeft.setSelection(tbtmProgramm);

		sv = new AssemblerSourceViewer(tabsLeft);
		tbtmProgramm.setControl(sv.getControl());

		CTabItem tbtmSpeicher = new CTabItem(tabsLeft, SWT.NONE);
		tbtmSpeicher.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive.png"));
		tbtmSpeicher.setText(Messages.SimulatorMainWindow_tbtmSpeicher_text);

		MemoryTable mTable = new MemoryTable(tabsLeft);
		tbtmSpeicher.setControl(mTable.getTable());

		CTabFolder tabsRight = new CTabFolder(sashForm, SWT.BORDER);
		tabsRight.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmNewItem = new CTabItem(tabsRight, SWT.NONE);
		tbtmNewItem.setText(Messages.SimulatorMainWindow_tbtmNewItem_text);
		tabsRight.setSelection(tbtmNewItem);

		SettingsView settings = new SettingsView(tabsRight, SWT.NONE);
		tbtmNewItem.setControl(settings);
		
		CTabItem tbtmProgramminformationen = new CTabItem(tabsRight, SWT.NONE);
		tbtmProgramminformationen.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/chart_bar.png"));
		tbtmProgramminformationen
				.setText(Messages.SimulatorMainWindow_tbtmProgramminformationen_text);

		ProgramInfoView infoView = new ProgramInfoView(tabsRight, SWT.NONE);
		tbtmProgramminformationen.setControl(infoView);
		
		sashForm.setWeights(new int[] { 3, 1 });

		StatusBar status = new StatusBar(shlSimulator,SWT.NONE);
		status.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));
	}

	/**
	 * Adds the menu to the shell
	 */
	private void addMenu() {
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
				//lblProgramSize.setText(Simulator.getInstance().getProgramSize()
				//		+ " Byte");
				//lblCommandCount.setText(Simulator.getInstance()
				//		.getCommandCount() + " Anweisungen");
				//short memSize = Memory.getInstance().getMemorySize();
				//double load = Simulator.getInstance().getProgramSize()
				//		/ memSize * 100;
				//pbLoad.setSelection((int) load);
				//if (load > memSize) {
				//	pbLoad.setState(SWT.ERROR);
				//} else {
				//	pbLoad.setState(SWT.NORMAL);
				//}

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
	}

	@Override
	public void memoryChanged() {

	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {

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
				//lblStatusLine.setText("8085 Simulator - " + statustext);
			}
		});
	}

	private void clearStatus() {
		//this.lblStatusLine.setText("8085 Simulator");
	}
}
