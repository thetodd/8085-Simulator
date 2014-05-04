package de.thetodd.simulator8085.gui;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorChangedListener;
import de.thetodd.simulator8085.api.RegisterChangeEvent;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.SyntaxHighlighter;
import de.thetodd.simulator8085.api.RegisterChangeEvent.Register;
import de.thetodd.simulator8085.api.actions.Action;
import de.thetodd.simulator8085.api.actions.AssembleAction;
import de.thetodd.simulator8085.api.actions.OneStepAction;
import de.thetodd.simulator8085.api.actions.SimulateAction;

public class SimulatorMainWindow implements ProcessorChangedListener {

	protected Shell shlSimulator;
	private Text txtRegisterA;
	private Text txtRegisterB;
	private Text txtRegisterC;
	private Text txtRegisterD;
	private Text txtRegisterE;
	private Text txtRegisterH;
	private Text txtRegisterL;
	private Text txtRegisterF;
	private Text txtMemoryEnd;
	private Table table;
	private Text txtRegisterSP;
	private Text txtRegisterPC;
	private StyledText codeText;
	private Text lblProgramSize;
	private Text lblCommandCount;
	private Label lblPercent;
	private Text txtMemoryStart;
	private ProgressBar pbLoad;

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
		shlSimulator.setSize(732, 462);
		shlSimulator.setText("8085 Simulator - Hochschule Fulda");
		shlSimulator.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shlSimulator, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Group grpRegister = new Group(composite, SWT.NONE);
		grpRegister.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 2));
		grpRegister.setText("Register");

		Label lblA = new Label(grpRegister, SWT.NONE);
		lblA.setBounds(10, 34, 8, 15);
		lblA.setText("A");

		txtRegisterA = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterA
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterA.setForeground(SWTResourceManager.getColor(220, 20, 60));
		txtRegisterA.setText("0x00");
		txtRegisterA.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterA.setBounds(24, 27, 56, 28);

		txtRegisterB = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterB.setText("0x00");
		txtRegisterB.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterB.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterB
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterB.setBounds(100, 27, 56, 28);

		Label lblB = new Label(grpRegister, SWT.NONE);
		lblB.setText("B");
		lblB.setBounds(86, 34, 8, 15);

		txtRegisterC = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterC.setText("0x00");
		txtRegisterC.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterC
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterC.setBounds(176, 27, 56, 28);

		Label lblC = new Label(grpRegister, SWT.NONE);
		lblC.setText("C");
		lblC.setBounds(162, 34, 8, 15);

		txtRegisterD = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterD.setText("0x00");
		txtRegisterD.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterD.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterD
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterD.setBounds(252, 27, 56, 28);

		Label lblD = new Label(grpRegister, SWT.NONE);
		lblD.setText("D");
		lblD.setBounds(238, 34, 8, 15);

		txtRegisterE = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterE.setText("0x00");
		txtRegisterE.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterE.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterE
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterE.setBounds(328, 27, 56, 28);

		Label lblE = new Label(grpRegister, SWT.NONE);
		lblE.setText("E");
		lblE.setBounds(314, 34, 8, 15);

		txtRegisterH = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterH.setText("0x00");
		txtRegisterH.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterH.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterH
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterH.setBounds(24, 61, 56, 28);

		Label lblH = new Label(grpRegister, SWT.NONE);
		lblH.setText("H");
		lblH.setBounds(10, 68, 8, 15);

		txtRegisterL = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterL.setText("0x00");
		txtRegisterL.setForeground(SWTResourceManager.getColor(50, 205, 50));
		txtRegisterL.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterL
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterL.setBounds(100, 61, 56, 28);

		Label lblL = new Label(grpRegister, SWT.NONE);
		lblL.setText("L");
		lblL.setBounds(86, 68, 8, 15);

		txtRegisterF = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterF.setText("0x00");
		txtRegisterF.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterF.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterF
				.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		txtRegisterF.setBounds(176, 61, 56, 28);

		Label lblF = new Label(grpRegister, SWT.NONE);
		lblF.setText("F");
		lblF.setBounds(162, 68, 8, 15);

		txtRegisterSP = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterSP.setText("0x0000");
		txtRegisterSP.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterSP.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterSP.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));
		txtRegisterSP.setBounds(252, 61, 83, 28);

		Label lblSp = new Label(grpRegister, SWT.NONE);
		lblSp.setText("SP");
		lblSp.setBounds(238, 68, 13, 15);

		txtRegisterPC = new Text(grpRegister, SWT.BORDER | SWT.READ_ONLY);
		txtRegisterPC.setText("0x0000");
		txtRegisterPC.setForeground(SWTResourceManager.getColor(255, 215, 0));
		txtRegisterPC.setFont(SWTResourceManager.getFont("Courier New", 14,
				SWT.BOLD));
		txtRegisterPC.setBackground(SWTResourceManager
				.getColor(SWT.COLOR_BLACK));
		txtRegisterPC.setBounds(360, 61, 83, 28);

		Label lblPc = new Label(grpRegister, SWT.NONE);
		lblPc.setText("PC");
		lblPc.setBounds(341, 68, 17, 15);

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 1));
		composite_1.setLayout(new GridLayout(3, false));

		Label lblSpeicherbereich = new Label(composite_1, SWT.NONE);
		lblSpeicherbereich.setText("Speicherbereich Anfang:");

		txtMemoryStart = new Text(composite_1, SWT.BORDER);
		txtMemoryStart.setText("0x1800");

		Button button = new Button(composite_1, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
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
		button.setToolTipText("Speicher anpassen");
		button.setImage(SWTResourceManager.getImage(SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive_edit.png"));
		button.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 2));

		Label lblSpeicherbereichEnde = new Label(composite_1, SWT.NONE);
		lblSpeicherbereichEnde.setText("Speicherbereich Ende:");

		txtMemoryEnd = new Text(composite_1, SWT.BORDER);
		txtMemoryEnd.setText("0x1BFF");

		ToolBar toolBar = new ToolBar(composite, SWT.FLAT | SWT.RIGHT);

		ToolItem toolItem = new ToolItem(toolBar, SWT.NONE);
		toolItem.setToolTipText("Datei \u00F6ffnen");
		toolItem.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/folder_explore.png"));

		ToolItem toolItem_1 = new ToolItem(toolBar, SWT.NONE);
		toolItem_1.setToolTipText("Datei speichern");
		toolItem_1.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/disk.png"));

		ToolItem tltmNewItem = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Action assemble = new AssembleAction(codeText.getText());
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

				updateLineHighlighting();
			}
		});
		tltmNewItem.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/wrench.png"));
		tltmNewItem.setToolTipText("Assemblieren");

		ToolItem tltmNewItem_1 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// SwingWorker<Boolean, Object> simulate = new SimulateAction();

				SimulateAction simulate = new SimulateAction();
				simulate.run();
				updateLineHighlighting();
			}
		});
		tltmNewItem_1.setToolTipText("Simulieren");
		tltmNewItem_1.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/control_play.png"));

		ToolItem tltmNewItem_2 = new ToolItem(toolBar, SWT.NONE);
		tltmNewItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Action einzel = new OneStepAction();
				einzel.run();
				updateLineHighlighting();
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
			}
		});
		tltmNewItem_2.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/control_end_blue.png"));
		tltmNewItem_2.setToolTipText("Einzelschritt");

		ToolItem tltmReset = new ToolItem(toolBar, SWT.NONE);
		tltmReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Memory.getInstance().resetMemory();
				Processor.getInstance().resetProcessor();
				Simulator.getInstance().fireRegisterChangeEvent(
						new RegisterChangeEvent(RegisterChangeEvent
								.getAllTemplate()));
			}
		});
		tltmReset.setToolTipText("Reset");
		tltmReset
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/arrow_rotate_anticlockwise.png"));

		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmProgramm = new CTabItem(tabFolder, SWT.NONE);
		tbtmProgramm
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/application_xp_terminal.png"));
		tbtmProgramm.setText("Programm");
		tabFolder.setSelection(tbtmProgramm);

		codeText = new StyledText(tabFolder, SWT.BORDER);
		codeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SyntaxHighlighter highlighter = new SyntaxHighlighter();
				highlighter.highlight(codeText);
			}
		});
		codeText.setText("ORG 0x1800\r\nJMP start\r\nstart:\r\nMVI A,0x33\r\nJMP ende\r\nORG 0x1900\r\n@:\r\nende:\r\nMVI A,0x44\r\nMVI A,0x55\r\nJMP start\r\nHLT");
		codeText.setTopMargin(5);
		codeText.setBottomMargin(5);
		codeText.setRightMargin(5);
		codeText.setFont(SWTResourceManager.getFont("Courier New", 10,
				SWT.NORMAL));
		codeText.setLeftMargin(5);
		tbtmProgramm.setControl(codeText);

		CTabItem tbtmSpeicher = new CTabItem(tabFolder, SWT.NONE);
		tbtmSpeicher.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive.png"));
		tbtmSpeicher.setText("Speicher");

		TableViewer tableViewer = new TableViewer(tabFolder, SWT.BORDER
				| SWT.FULL_SELECTION);
		table = tableViewer.getTable();
		table.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		table.setHeaderVisible(true);
		tbtmSpeicher.setControl(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnAdresse = tableViewerColumn.getColumn();
		tblclmnAdresse.setWidth(64);
		tblclmnAdresse.setText("Adresse");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn = tableViewerColumn_1.getColumn();
		tableColumn.setWidth(30);
		tableColumn.setText("0");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_1 = tableViewerColumn_2.getColumn();
		tableColumn_1.setWidth(30);
		tableColumn_1.setText("1");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_2 = tableViewerColumn_3.getColumn();
		tableColumn_2.setWidth(30);
		tableColumn_2.setText("2");

		TableViewerColumn tableViewerColumn_16 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_9 = tableViewerColumn_16.getColumn();
		tableColumn_9.setWidth(30);
		tableColumn_9.setText("3");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_3 = tableViewerColumn_4.getColumn();
		tableColumn_3.setWidth(30);
		tableColumn_3.setText("4");

		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_4 = tableViewerColumn_5.getColumn();
		tableColumn_4.setWidth(30);
		tableColumn_4.setText("5");

		TableViewerColumn tableViewerColumn_6 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_5 = tableViewerColumn_6.getColumn();
		tableColumn_5.setWidth(30);
		tableColumn_5.setText("6");

		TableViewerColumn tableViewerColumn_7 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_6 = tableViewerColumn_7.getColumn();
		tableColumn_6.setWidth(30);
		tableColumn_6.setText("7");

		TableViewerColumn tableViewerColumn_8 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_7 = tableViewerColumn_8.getColumn();
		tableColumn_7.setWidth(30);
		tableColumn_7.setText("8");

		TableViewerColumn tableViewerColumn_9 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tableColumn_8 = tableViewerColumn_9.getColumn();
		tableColumn_8.setWidth(30);
		tableColumn_8.setText("9");

		TableViewerColumn tableViewerColumn_10 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnA = tableViewerColumn_10.getColumn();
		tblclmnA.setWidth(30);
		tblclmnA.setText("A");

		TableViewerColumn tableViewerColumn_11 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnB = tableViewerColumn_11.getColumn();
		tblclmnB.setWidth(30);
		tblclmnB.setText("B");

		TableViewerColumn tableViewerColumn_12 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnC = tableViewerColumn_12.getColumn();
		tblclmnC.setWidth(30);
		tblclmnC.setText("C");

		TableViewerColumn tableViewerColumn_13 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnD = tableViewerColumn_13.getColumn();
		tblclmnD.setWidth(30);
		tblclmnD.setText("D");

		TableViewerColumn tableViewerColumn_14 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnE = tableViewerColumn_14.getColumn();
		tblclmnE.setWidth(30);
		tblclmnE.setText("E");

		TableViewerColumn tableViewerColumn_15 = new TableViewerColumn(
				tableViewer, SWT.NONE);
		TableColumn tblclmnF = tableViewerColumn_15.getColumn();
		tblclmnF.setWidth(30);
		tblclmnF.setText("F");

		CTabItem tbtmProgramminformationen = new CTabItem(tabFolder, SWT.NONE);
		tbtmProgramminformationen.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/chart_bar.png"));
		tbtmProgramminformationen.setText("Programminformationen");

		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmProgramminformationen.setControl(composite_2);
		composite_2.setLayout(new FormLayout());

		Label lblProgrammgre = new Label(composite_2, SWT.NONE);
		FormData fd_lblProgrammgre = new FormData();
		fd_lblProgrammgre.right = new FormAttachment(0, 103);
		fd_lblProgrammgre.top = new FormAttachment(0, 10);
		fd_lblProgrammgre.left = new FormAttachment(0, 10);
		lblProgrammgre.setLayoutData(fd_lblProgrammgre);
		lblProgrammgre.setText("Programmgr\u00F6\u00DFe");

		Label lblAnzahlAnweisungen = new Label(composite_2, SWT.NONE);
		FormData fd_lblAnzahlAnweisungen = new FormData();
		fd_lblAnzahlAnweisungen.top = new FormAttachment(lblProgrammgre, 6);
		fd_lblAnzahlAnweisungen.left = new FormAttachment(lblProgrammgre, 0,
				SWT.LEFT);
		lblAnzahlAnweisungen.setLayoutData(fd_lblAnzahlAnweisungen);
		lblAnzahlAnweisungen.setText("Anzahl Anweisungen");

		Label lblAuslastungSpeicherbereich = new Label(composite_2, SWT.NONE);
		FormData fd_lblAuslastungSpeicherbereich = new FormData();
		fd_lblAuslastungSpeicherbereich.top = new FormAttachment(
				lblAnzahlAnweisungen, 6);
		fd_lblAuslastungSpeicherbereich.left = new FormAttachment(0, 10);
		lblAuslastungSpeicherbereich
				.setLayoutData(fd_lblAuslastungSpeicherbereich);
		lblAuslastungSpeicherbereich.setText("Auslastung Speicherbereich");

		lblPercent = new Label(composite_2, SWT.NONE);
		FormData fd_lblPercent = new FormData();
		fd_lblPercent.bottom = new FormAttachment(lblAuslastungSpeicherbereich,
				0, SWT.BOTTOM);
		fd_lblPercent.left = new FormAttachment(lblAuslastungSpeicherbereich, 6);
		lblPercent.setLayoutData(fd_lblPercent);

		lblCommandCount = new Text(composite_2, SWT.NONE);
		FormData fd_lblCommandCount = new FormData();
		fd_lblCommandCount.bottom = new FormAttachment(lblAnzahlAnweisungen, 0,
				SWT.BOTTOM);
		fd_lblCommandCount.left = new FormAttachment(lblPercent, 0, SWT.LEFT);
		lblCommandCount.setLayoutData(fd_lblCommandCount);

		lblProgramSize = new Text(composite_2, SWT.NONE);
		FormData fd_lblProgramSize = new FormData();
		fd_lblProgramSize.right = new FormAttachment(lblCommandCount, 0,
				SWT.RIGHT);
		fd_lblProgramSize.left = new FormAttachment(lblProgrammgre, 59);
		fd_lblProgramSize.bottom = new FormAttachment(lblProgrammgre, 0,
				SWT.BOTTOM);
		lblProgramSize.setLayoutData(fd_lblProgramSize);

		pbLoad = new ProgressBar(composite_2, SWT.NONE);
		fd_lblCommandCount.right = new FormAttachment(pbLoad, 0, SWT.RIGHT);
		pbLoad.setSelection(40);
		FormData fd_pbLoad = new FormData();
		fd_pbLoad.top = new FormAttachment(lblCommandCount, 4);
		fd_pbLoad.right = new FormAttachment(lblAuslastungSpeicherbereich, 117,
				SWT.RIGHT);
		fd_pbLoad.left = new FormAttachment(lblAuslastungSpeicherbereich, 6);
		pbLoad.setLayoutData(fd_pbLoad);
	}

	@Override
	public void memoryChanged() {
		table.removeAll();
		for (int i = Memory.getInstance().getMemoryStart(); i <= Memory
				.getInstance().getMemoryEnd(); i += 16) {
			TableItem tableItem = new TableItem(table, SWT.NONE);
			String[] mem = new String[17];
			mem[0] = String.format("0x%04X", i);
			for (int j = 0; j < 16; j++) {

				mem[1 + j] = String.format("%02x",
						Memory.getInstance().get((short) (i + j)));
			}
			tableItem.setText(mem);
			// tableItem.setText(Integer.toHexString(i));
		}
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
			txtRegisterF.setText(String.format("0x%02X", Processor
					.getInstance().getRegisterF()));
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
		if (Simulator.getInstance().getCodeMap()
				.containsKey(Processor.getInstance().getProgramcounter())) {
			int linenumber = Simulator.getInstance().getCodeMap()
					.get(Processor.getInstance().getProgramcounter());
			codeText.setLineBackground(0, codeText.getLineCount(), null);
			codeText.setLineBackground(linenumber, 1,
					new Color(Display.getDefault(), 0xFF, 0xFF, 0x99));
		}
	}
}
