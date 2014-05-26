package de.thetodd.simulator8085.api;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import de.thetodd.simulator8085.api.mnemonics.ACIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CALLMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CMAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CMCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CMMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CNCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CNZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CPEMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CPOMnemonic;
import de.thetodd.simulator8085.api.mnemonics.CZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.DCXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.HLTMnemonic;
import de.thetodd.simulator8085.api.mnemonics.INXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JMPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.MOVMnemonic;
import de.thetodd.simulator8085.api.mnemonics.MVIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.NOPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ORAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ORIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.OUTMnemonic;
import de.thetodd.simulator8085.api.mnemonics.POPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.PUSHMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RETMnemonic;

public class Simulator {

	private static Simulator simulatorInstance;
	private ArrayList<Short> breakpoints;
	private HashMap<String, Short> labelMap;
	private int commandCount;
	private int programSize;

	/**
	 * true if program is in debug mode
	 */
	private boolean debugMode;

	private HashMap<String, Mnemonic> mnemonicMap;
	private HashMap<Byte,Byte> outMap;
	private ArrayList<ProcessorChangedListener> changeListeners;
	private HashMap<Short, Integer> codeMap; // Address <-> Codeline Map
	private boolean isAssembled;

	public static Simulator getInstance() {
		if (simulatorInstance == null) {
			simulatorInstance = new Simulator();
		}
		return simulatorInstance;
	}

	public Simulator() {

		getMnemonicsList();

		breakpoints = new ArrayList<>();
		labelMap = new HashMap<>();
		changeListeners = new ArrayList<>();
		codeMap = new HashMap<>();
		outMap = new HashMap<>();
	}

	private void getMnemonicsList() {
		mnemonicMap = new HashMap<String, Mnemonic>();
		mnemonicMap.put("mvi", new MVIMnemonic());
		mnemonicMap.put("hlt", new HLTMnemonic());
		mnemonicMap.put("nop", new NOPMnemonic());
		mnemonicMap.put("jmp", new JMPMnemonic());
		mnemonicMap.put("add", new ADDMnemonic());
		mnemonicMap.put("adi", new ADIMnemonic());
		mnemonicMap.put("aci", new ACIMnemonic());
		mnemonicMap.put("adc", new ADCMnemonic());
		mnemonicMap.put("push", new PUSHMnemonic());
		mnemonicMap.put("pop", new POPMnemonic());
		
		mnemonicMap.put("call", new CALLMnemonic());
		mnemonicMap.put("cc", new CCMnemonic());
		mnemonicMap.put("cm", new CMMnemonic());
		mnemonicMap.put("cnc", new CNCMnemonic());
		mnemonicMap.put("cnz", new CNZMnemonic());
		mnemonicMap.put("cpe", new CPEMnemonic());
		mnemonicMap.put("cp", new CPMnemonic());
		mnemonicMap.put("cpo", new CPOMnemonic());
		mnemonicMap.put("cz", new CZMnemonic());
		
		mnemonicMap.put("cma", new CMAMnemonic());
		mnemonicMap.put("cmc", new CMCMnemonic());
		
		mnemonicMap.put("dcx", new DCXMnemonic());
		mnemonicMap.put("inx", new INXMnemonic());
		
		mnemonicMap.put("mov", new MOVMnemonic());
		
		mnemonicMap.put("ret", new RETMnemonic());
		
		mnemonicMap.put("out", new OUTMnemonic());
		
		mnemonicMap.put("ori", new ORIMnemonic());
		mnemonicMap.put("ora", new ORAMnemonic());

	}

	public HashMap<Short, Integer> getCodeMap() {
		return codeMap;
	}

	public void registerChangeListener(ProcessorChangedListener listener) {
		changeListeners.add(listener);
	}

	public void unregisterChangeListener(ProcessorChangedListener listener) {
		changeListeners.remove(listener);
	}

	public void fireRegisterChangeEvent(RegisterChangeEvent evt) {
		for (ProcessorChangedListener l : changeListeners) {
			l.registerChanged(evt);
		}
	}

	public void fireMemoryChangeEvent() {
		for (ProcessorChangedListener l : changeListeners) {
			l.memoryChanged();
		}
	}
	
	public void fireOutChangedEvent(byte adr, byte value) {
		for (ProcessorChangedListener l : changeListeners) {
			l.outChanged(adr,value);
		}
	}

	public Collection<Mnemonic> getUsableMnemonics() {
		return mnemonicMap.values();
	}

	public HashMap<String, Mnemonic> getMnemonics() {
		return mnemonicMap;
	}

	public byte[] getOpcodes(String mnemonic, String[] args) {
		Mnemonic m = mnemonicMap.get(mnemonic);
		return m.getOpcode(args);
	}

	public byte[] getOpcodes(String line) {
		String mnemonic = line.split(" ")[0].toLowerCase();
		if (isMnemonic(mnemonic)) {
			String[] args = new String[0];
			if (line.split(" ").length > 1) {
				args = line.split(" ")[1].split(",");
			}
			return getOpcodes(mnemonic, args);
		} else {
			return new byte[0];
		}
	}

	public void setOutEntry(byte adr, byte c) {
		outMap.put(adr, c);
		fireOutChangedEvent(adr,c);
	}
	
	public byte getOutEntry(byte adr) {
		return outMap.get(adr);
	}
	
	/**
	 * Return true if m is a valid mnemonic
	 * 
	 * @param m
	 *            mnemonic to test
	 * @return true id m is a mnemonic.
	 */
	public boolean isMnemonic(String m) {
		return mnemonicMap.containsKey(m.toLowerCase());
	}

	public void addBreakpoint(short adr) {
		breakpoints.add(adr);
	}

	public boolean isBreakpoint(short adr) {
		return breakpoints.contains(adr);
	}

	public void addLabel(String labelName, short adresse) {
		labelMap.put(labelName, adresse);
	}

	public short getLabelAdress(String label) {
		return labelMap.get(label);
	}

	/**
	 * @return the commandCount
	 */
	public int getCommandCount() {
		return commandCount;
	}

	/**
	 * @param commandCount
	 *            the commandCount to set
	 */
	public void setCommandCount(int commandCount) {
		this.commandCount = commandCount;
	}

	/**
	 * @return the programSize
	 */
	public int getProgramSize() {
		return programSize;
	}

	/**
	 * @param programSize
	 *            the programSize to set
	 */
	public void setProgramSize(int programSize) {
		this.programSize = programSize;
	}

	public ArrayList<Short> getBreakpoints() {
		return breakpoints;
	}

	public HashMap<String, Short> getLabelMap() {
		return labelMap;
	}

	public boolean isAssembled() {
		return isAssembled;
	}

	public void setAssembled(boolean isAssembled) {
		this.isAssembled = isAssembled;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}
}
