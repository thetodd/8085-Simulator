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
import org.eclipse.swt.graphics.Image;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import de.thetodd.simulator8085.api.Memory;
import de.thetodd.simulator8085.api.Processor;
import de.thetodd.simulator8085.api.ProcessorChangedListener;
import de.thetodd.simulator8085.api.RegisterChangeEvent;
import de.thetodd.simulator8085.api.RegisterChangeEvent.Register;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.api.SyntaxHighlighter;
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
	private Label lblSignFlag;
	private Label lblZeroFlag;
	private Label lblACarryFlag;
	private Label lblParityFlag;
	private Label lblCarryFlag;

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
		shlSimulator.setSize(737, 462);
		shlSimulator.setText("8085 Simulator");
		shlSimulator.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shlSimulator, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Group grpRegister = new Group(composite, SWT.NONE);
		grpRegister.setLayout(new GridLayout(7, false));
		grpRegister.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 2));
		grpRegister.setText("Registers");

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
		lblSp.setText("Stackpointer");
		lblSp.setAlignment(SWT.CENTER);

		Label lblPc = new Label(grpRegister, SWT.NONE);
		lblPc.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				2, 1));
		lblPc.setText("Programcounter");
		lblPc.setAlignment(SWT.CENTER);

		Label lblFlags = new Label(grpRegister, SWT.NONE);
		lblFlags.setText("Flags");
		lblFlags.setAlignment(SWT.CENTER);
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

		Composite composite_3 = new Composite(grpRegister, SWT.NONE);
		composite_3.setLayout(new GridLayout(5, false));
		GridData gd_composite_3 = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1);
		gd_composite_3.heightHint = 55;
		composite_3.setLayoutData(gd_composite_3);

		Label lblNewLabel = new Label(composite_3, SWT.NONE);
		lblNewLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel.setText("S");

		Label lblNewLabel_1 = new Label(composite_3, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_1.setText("Z");

		Label lblNewLabel_2 = new Label(composite_3, SWT.NONE);
		lblNewLabel_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_2.setText("AC");

		Label lblNewLabel_3 = new Label(composite_3, SWT.NONE);
		lblNewLabel_3.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_3.setText("P");

		Label lblNewLabel_4 = new Label(composite_3, SWT.NONE);
		lblNewLabel_4.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblNewLabel_4.setText("C");

		lblSignFlag = new Label(composite_3, SWT.NONE);
		lblSignFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblZeroFlag = new Label(composite_3, SWT.NONE);
		lblZeroFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		lblACarryFlag = new Label(composite_3, SWT.NONE);
		lblACarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblParityFlag = new Label(composite_3, SWT.NONE);
		lblParityFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/delete.png"));

		lblCarryFlag = new Label(composite_3, SWT.NONE);
		lblCarryFlag.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/accept.png"));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				1, 2));
		composite_1.setLayout(new GridLayout(3, false));

		Label lblSpeicherbereich = new Label(composite_1, SWT.NONE);
		lblSpeicherbereich.setText("Memory start");

		txtMemoryStart = new Text(composite_1, SWT.BORDER);
		txtMemoryStart.setText("0x1800");

		Button btnSetMemory = new Button(composite_1, SWT.NONE);
		btnSetMemory.setText("set memory");
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
		btnSetMemory.setToolTipText("Speicher anpassen");
		btnSetMemory.setImage(SWTResourceManager.getImage(SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/drive_edit.png"));
		btnSetMemory.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
				1, 2));

		Label lblSpeicherbereichEnde = new Label(composite_1, SWT.NONE);
		lblSpeicherbereichEnde.setText("Memory end");

		txtMemoryEnd = new Text(composite_1, SWT.BORDER);
		txtMemoryEnd.setText("0x1BFF");

		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2,
				1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(
				SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem tbtmProgramm = new CTabItem(tabFolder, SWT.NONE);
		tbtmProgramm
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/application_xp_terminal.png"));
		tbtmProgramm.setText("Program");
		tabFolder.setSelection(tbtmProgramm);

		codeText = new StyledText(tabFolder, SWT.BORDER|SWT.V_SCROLL);
		codeText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				SyntaxHighlighter highlighter = new SyntaxHighlighter();
				highlighter.highlight(codeText);
			}
		});
		codeText.setText(";Kommentar\r\nORG 0x1800\r\nJMP start\r\nstart:\r\nMVI A,0x33\r\nADD A\r\nJMP ende\r\nORG 0x1900\r\n@:\r\nende:\r\nMVI A,0x44\r\nMVI A,0x55\r\nJMP start\r\nHLT");
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
		tbtmSpeicher.setText("Memory");

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
		tbtmProgramminformationen.setText("Program survey");

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

		Menu menu = new Menu(shlSimulator, SWT.BAR);
		shlSimulator.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNewFile = new MenuItem(menu_1, SWT.NONE);
		mntmNewFile.setText("New File\tCtrl+N");
		mntmNewFile.setAccelerator(SWT.MOD1+'N');

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/folder_explore.png"));
		mntmOpen.setText("Open...\tCtrl+O");
		mntmOpen.setAccelerator(SWT.MOD1+'O');

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/disk.png"));
		mntmSave.setText("Save...\tCtrl+S");
		mntmSave.setAccelerator(SWT.MOD1 + 'S');

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmClose = new MenuItem(menu_1, SWT.NONE);
		mntmClose.setText("Close");
		mntmClose.setAccelerator(SWT.MOD2+SWT.F4);

		MenuItem mntmSimulate = new MenuItem(menu, SWT.CASCADE);
		mntmSimulate.setText("Simulate");

		Menu menu_2 = new Menu(mntmSimulate);
		mntmSimulate.setMenu(menu_2);

		MenuItem mntmAsseble = new MenuItem(menu_2, SWT.NONE);
		mntmAsseble.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
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
		mntmAsseble.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/wrench.png"));
		mntmAsseble.setText("Assemble\tF6");
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
			}
		});
		mntmResetProcessor
				.setImage(SWTResourceManager
						.getImage(SimulatorMainWindow.class,
								"/de/thetodd/simulator8085/gui/icons/arrow_rotate_anticlockwise.png"));
		mntmResetProcessor.setText("Reset Processor\tCtrl+R");
		mntmResetProcessor.setAccelerator(SWT.MOD1 + 'R');

		MenuItem mntmNexBreakpoint = new MenuItem(menu_2, SWT.NONE);
		mntmNexBreakpoint.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SimulateAction simulate = new SimulateAction();
				simulate.run();
				updateLineHighlighting();
			}
		});
		mntmNexBreakpoint.setImage(SWTResourceManager.getImage(
				SimulatorMainWindow.class,
				"/de/thetodd/simulator8085/gui/icons/control_end_blue.png"));
		mntmNexBreakpoint.setText("Next Breakpoint\tF8");
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
		mntmOneStep.setText("One Step\tF7");
		mntmOneStep.setAccelerator(SWT.F7);

		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setText("Help");

		Menu menu_3 = new Menu(mntmNewSubmenu);
		mntmNewSubmenu.setMenu(menu_3);

		MenuItem mntmHelpContents = new MenuItem(menu_3, SWT.NONE);
		mntmHelpContents.setText("Help Contents\tF1");
		mntmHelpContents.setAccelerator(SWT.F1);

		MenuItem mntmAbout = new MenuItem(menu_3, SWT.NONE);
		mntmAbout.setText("About...");
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
