package de.thetodd.simulator8085.api;

import java.util.HashMap;
import java.util.Set;

public class Memory {

	private static Memory memoryInstance;

	private HashMap<Short, Byte> memory;
	private short start, end; // Memory boundaries

	public static Memory getInstance() {
		if (memoryInstance == null) {
			memoryInstance = new Memory((short) 0x1800, (short) 0x1bff);
		}
		return memoryInstance;
	}

	public Memory(short start, short end) {
		memory = new HashMap<Short, Byte>();
		setRange(start, end);
	}

	/**
	 * Sets the range of the simulated memory. Clears the old memory and creates
	 * a new memory cells.
	 * 
	 * @param start
	 * @param end
	 * @throws Exception
	 */
	public void setRange(short start, short end) {
		start = (short) (start & (short) 0xFFF0);
		end = (short) (end | (short) 0x000F);
		memory.clear();
		for (short i = start; i <= end; i++) {
			byte n = (byte) (Math.random() * 0xff); // Random Memorycontent
			memory.put(i, n);
		}
		this.start = start;
		this.end = end;
		// Simulator.getInstance().fireMemoryChangeEvent();
	}

	// TODO: Überprüfung, ob Speicherzelle zulässig
	public byte get(short adr) {
		return memory.get(adr);
	}

	// TODO: Überprüfung, ob Speicherzelle zulässig
	public void put(short adr, byte content) {
		memory.put(adr, content);
		Simulator.getInstance().fireMemoryChangeEvent();
	}

	public void resetMemory() {
		Set<Short> keys = memory.keySet();
		for (Short key : keys) {
			memory.put(key, (byte) 0x00);
		}
		Simulator.getInstance().fireMemoryChangeEvent();
	}

	public short getMemoryStart() {
		return this.start;
	}

	public short getMemoryEnd() {
		return this.end;
	}

	public short getMemorySize() {
		return (short) (this.end - this.start);
	}
}
