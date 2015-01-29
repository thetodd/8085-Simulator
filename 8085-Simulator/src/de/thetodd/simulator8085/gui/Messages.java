package de.thetodd.simulator8085.gui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "de.thetodd.simulator8085.gui.messages"; //$NON-NLS-1$
	public static String SimulatorMainWindow_grpRegister_text;
	public static String SimulatorMainWindow_lblSp_text;
	public static String SimulatorMainWindow_lblPc_text;
	public static String SimulatorMainWindow_lblFlags_text;
	public static String SimulatorMainWindow_lblSpeicherbereich_text;
	public static String SimulatorMainWindow_btnSetMemory_text;
	public static String SimulatorMainWindow_btnSetMemory_toolTipText;
	public static String SimulatorMainWindow_lblSpeicherbereichEnde_text;
	public static String SimulatorMainWindow_tbtmProgramm_text;
	public static String SimulatorMainWindow_tbtmSpeicher_text;
	public static String SimulatorMainWindow_tblclmnAdresse_text;
	public static String SimulatorMainWindow_tbtmProgramminformationen_text;
	public static String SimulatorMainWindow_lblProgrammgre_text;
	public static String SimulatorMainWindow_lblAnzahlAnweisungen_text;
	public static String SimulatorMainWindow_lblAuslastungSpeicherbereich_text;
	public static String SimulatorMainWindow_mntmFile_text;
	public static String SimulatorMainWindow_mntmNewFile_text;
	public static String SimulatorMainWindow_mntmOpen_text;
	public static String SimulatorMainWindow_mntmSave_text;
	public static String SimulatorMainWindow_mntmPrint_text;
	public static String SimulatorMainWindow_mntmClose_text;
	public static String SimulatorMainWindow_mntmSimulate_text;
	public static String SimulatorMainWindow_mntmAsseble_text;
	public static String SimulatorMainWindow_mntmResetProcessor_text;
	public static String SimulatorMainWindow_mntmSimulate_1_text;
	public static String SimulatorMainWindow_mntmStopSim_text;
	public static String SimulatorMainWindow_mntmNexBreakpoint_text;
	public static String SimulatorMainWindow_mntmOneStep_text;
	public static String SimulatorMainWindow_mntmViewers_text;
	public static String SimulatorMainWindow_mntmList_text;
	public static String SimulatorMainWindow_mntmIntersection_text;
	public static String SimulatorMainWindow_mntmNewSubmenu_text;
	public static String SimulatorMainWindow_mntmHelpContents_text;
	public static String SimulatorMainWindow_mntmAbout_text;
	public static String SimulatorMainWindow_lblNewLabel_5_text;
	public static String SimulatorMainWindow_lblClockrte_text;
	public static String AboutDialog_newShell_text;
	public static String AboutDialog_lblSimulator_text;
	public static String AboutDialog_lblVersion_text;
	public static String AboutDialog_lblNewLabel_text;
	public static String AboutDialog_lblEntstandenImRahmen_text;
	public static String ProgramVersion;
	public static String AboutDialog_styledText_text;
	public static String SimulatorMainWindow_tbtmNewItem_text;
	public static String SimulatorMainWindow_lblPercent_text;
	public static String SimulatorMainWindow_lblProgramSize_text;
	public static String SimulatorMainWindow_lblCommandCount_text;
	public static String SimulatorMainWindow_grpFlags_text;
	public static String StatusBar_lblStatus_text;
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	////////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
