package de.thetodd.simulator8085.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import de.thetodd.simulator8085.api.mnemonics.ADDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.HLTMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JMPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.MVIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.NOPMnemonic;

public class Simulator {

	private static Simulator simulatorInstance;
	private ArrayList<Short> breakpoints;
	private HashMap<String, Short> labelMap;
	private int commandCount;
	private int programSize;

	private HashMap<String,Mnemonic> mnemonicMap;
	private ArrayList<ProcessorChangedListener> changeListeners;
	private HashMap<Short,Integer> codeMap; //Address <-> Codeline Map
	
	public static Simulator getInstance() {
		if (simulatorInstance == null) {
			simulatorInstance = new Simulator();
		}
		return simulatorInstance;
	}
	
	public Simulator() {
		mnemonicMap = new HashMap<String, Mnemonic>();
		mnemonicMap.put("mvi", new MVIMnemonic());
		mnemonicMap.put("hlt", new HLTMnemonic());
		mnemonicMap.put("nop", new NOPMnemonic());
		mnemonicMap.put("jmp", new JMPMnemonic());
		mnemonicMap.put("add", new ADDMnemonic());
		
		breakpoints = new ArrayList<>();
		labelMap = new HashMap<>();
		changeListeners = new ArrayList<>();
		codeMap = new HashMap<>();
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
	
	public Collection<Mnemonic> getUsableMnemonics() {
		return mnemonicMap.values();
	}
	
	public HashMap<String,Mnemonic> getMnemonics() {
		return mnemonicMap;
	}
	
	public byte[] getOpcodes(String mnemonic, String[] args) {
		Mnemonic m = mnemonicMap.get(mnemonic);
		return m.getOpcode(args);
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
	 * @param commandCount the commandCount to set
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
	 * @param programSize the programSize to set
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
}
