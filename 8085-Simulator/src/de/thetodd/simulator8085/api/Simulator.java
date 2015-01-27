package de.thetodd.simulator8085.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.listener.ISimulatorListener;
import de.thetodd.simulator8085.api.listener.ProcessorChangedListener;
import de.thetodd.simulator8085.api.listener.RegisterChangeEvent;
import de.thetodd.simulator8085.api.listener.SimulatorEvent;
import de.thetodd.simulator8085.api.mnemonics.ACIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ADIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ANAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ANIMnemonic;
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
import de.thetodd.simulator8085.api.mnemonics.DADMnemonic;
import de.thetodd.simulator8085.api.mnemonics.DCRMnemonic;
import de.thetodd.simulator8085.api.mnemonics.DCXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.HLTMnemonic;
import de.thetodd.simulator8085.api.mnemonics.INRMnemonic;
import de.thetodd.simulator8085.api.mnemonics.INXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JMMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JMPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JNCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JNZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JPEMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JPOMnemonic;
import de.thetodd.simulator8085.api.mnemonics.JZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LDAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LDAXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LHLDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LXIBMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LXIDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LXIHMnemonic;
import de.thetodd.simulator8085.api.mnemonics.LXISPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.MOVMnemonic;
import de.thetodd.simulator8085.api.mnemonics.MVIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.NOPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ORAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.ORIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.OUTMnemonic;
import de.thetodd.simulator8085.api.mnemonics.PCHLMnemonic;
import de.thetodd.simulator8085.api.mnemonics.POPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.PUSHMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RALMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RARMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RETMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RMMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RNCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RNZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RPEMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RPMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RPOMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RRCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.RZMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SBBMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SBIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SHLDMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SPHLMnemonic;
import de.thetodd.simulator8085.api.mnemonics.STAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.STAXMnemonic;
import de.thetodd.simulator8085.api.mnemonics.STCMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SUBMnemonic;
import de.thetodd.simulator8085.api.mnemonics.SUIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.XCHGMnemonic;
import de.thetodd.simulator8085.api.mnemonics.XRAMnemonic;
import de.thetodd.simulator8085.api.mnemonics.XRIMnemonic;
import de.thetodd.simulator8085.api.mnemonics.XTHLMnemonic;

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
	private HashMap<Byte, Byte> outMap;
	private ArrayList<ProcessorChangedListener> changeListeners;
	private ArrayList<ISimulatorListener> simulatorListeners;
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
		simulatorListeners = new ArrayList<>();
		codeMap = new HashMap<>();
		outMap = new HashMap<>();
	}

	private void getMnemonicsList() {
		mnemonicMap = new HashMap<String, Mnemonic>();
		mnemonicMap.put("mvi", new MVIMnemonic());
		mnemonicMap.put("hlt", new HLTMnemonic());
		mnemonicMap.put("nop", new NOPMnemonic());

		mnemonicMap.put("jmp", new JMPMnemonic());
		mnemonicMap.put("jc", new JCMnemonic());
		mnemonicMap.put("jm", new JMMnemonic());
		mnemonicMap.put("jnc", new JNCMnemonic());
		mnemonicMap.put("jnz", new JNZMnemonic());
		mnemonicMap.put("jpe", new JPEMnemonic());
		mnemonicMap.put("jp", new JPMnemonic());
		mnemonicMap.put("jpo", new JPOMnemonic());
		mnemonicMap.put("jz", new JZMnemonic());

		mnemonicMap.put("add", new ADDMnemonic());
		mnemonicMap.put("adi", new ADIMnemonic());
		mnemonicMap.put("aci", new ACIMnemonic());
		mnemonicMap.put("adc", new ADCMnemonic());
		mnemonicMap.put("sui", new SUIMnemonic());
		mnemonicMap.put("sub", new SUBMnemonic());
		mnemonicMap.put("sbi", new SBIMnemonic());
		mnemonicMap.put("sbb", new SBBMnemonic());

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
		mnemonicMap.put("rc", new RCMnemonic());
		mnemonicMap.put("rm", new RMMnemonic());
		mnemonicMap.put("rnc", new RNCMnemonic());
		mnemonicMap.put("rnz", new RNZMnemonic());
		mnemonicMap.put("rpe", new RPEMnemonic());
		mnemonicMap.put("rp", new RPMnemonic());
		mnemonicMap.put("rpo", new RPOMnemonic());
		mnemonicMap.put("rz", new RZMnemonic());

		mnemonicMap.put("out", new OUTMnemonic());

		mnemonicMap.put("ori", new ORIMnemonic());
		mnemonicMap.put("ora", new ORAMnemonic());
		mnemonicMap.put("ani", new ANIMnemonic());
		mnemonicMap.put("ana", new ANAMnemonic());
		mnemonicMap.put("xri", new XRIMnemonic());
		mnemonicMap.put("xra", new XRAMnemonic());

		mnemonicMap.put("dad", new DADMnemonic());
		mnemonicMap.put("dcr", new DCRMnemonic());
		mnemonicMap.put("inr", new INRMnemonic());

		mnemonicMap.put("sta", new STAMnemonic());
		mnemonicMap.put("stax", new STAXMnemonic());
		mnemonicMap.put("lda", new LDAMnemonic());
		mnemonicMap.put("ldax", new LDAXMnemonic());
		mnemonicMap.put("lhld", new LHLDMnemonic());
		mnemonicMap.put("lxib", new LXIBMnemonic());
		mnemonicMap.put("lxid", new LXIDMnemonic());
		mnemonicMap.put("lxih", new LXIHMnemonic());
		mnemonicMap.put("lxisp", new LXISPMnemonic());

		mnemonicMap.put("pchl", new PCHLMnemonic());
		mnemonicMap.put("xchg", new XCHGMnemonic());
		mnemonicMap.put("xthl", new XTHLMnemonic());
		mnemonicMap.put("stc", new STCMnemonic());
		mnemonicMap.put("sphl", new SPHLMnemonic());
		mnemonicMap.put("shld", new SHLDMnemonic());

		mnemonicMap.put("rrc", new RRCMnemonic());
		mnemonicMap.put("rlc", new RRCMnemonic());
		mnemonicMap.put("ral", new RALMnemonic());
		mnemonicMap.put("rar", new RARMnemonic());
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
			l.outChanged(adr, value);
		}
	}

	/**
	 * Connects a {@link ISimulatorListener} to this Simulator. It will be
	 * notified by special fire methods.
	 * 
	 * @param listener the ISimulatorListener to be connected
	 * @since 2.0.0
	 * @see {@link Simulator#fireSimulatorEvent(SimulatorEvent)}
	 */
	public void registerSimulatorListener(ISimulatorListener listener) {
		simulatorListeners.add(listener);
	}
	
	/**
	 * Disconnects a {@link ISimulatorListener} from this Simulator. It will not be
	 * notified any more.
	 * 
	 * @param listener the ISimulatorListener to be disconnected
	 * @since 2.0.0
	 */
	public void unregisterSimulatorListener(ISimulatorListener listener) {
		simulatorListeners.remove(listener);
	}
	
	/**
	 * Fire a {@link SimulatorEvent}. Notifies all connected listeners.
	 * @param evt the SimulatorEvent to be fired
	 * @see {@link ISimulatorListener}, {@link SimulatorEvent}
	 */
	private void fireSimulatorEvent(SimulatorEvent evt) {
		for (ISimulatorListener listener : simulatorListeners) {
			listener.globalSimulatorEvent(evt);
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

	public void setOutEntry(final byte adr, final byte c) {
		outMap.put(adr, c);
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				fireOutChangedEvent(adr, c);
			}
		});

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
		fireSimulatorEvent(new SimulatorEvent("Code assembled.", SimulatorEvent.TYPE.SUCCESS));
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * Checks if the register in the parameter r is on of A,F,B,C,D,E,H,L
	 * 
	 * @param r
	 *            a registername
	 * @return true if r is a registername
	 */
	public static boolean isRegistername(String r) {
		String[] registers = { "A", "F", "B", "C", "D", "E", "H", "L" };
		return Arrays.asList(registers).contains(r.toUpperCase());
	}

	public static boolean isNumber(String n) {
		try {
			Integer.decode(n).byteValue();
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isLabel(String l) {
		return Simulator.getInstance().labelMap.containsKey(l);
	}
}
