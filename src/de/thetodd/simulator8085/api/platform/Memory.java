package de.thetodd.simulator8085.api.platform;

import java.util.HashMap;

import org.eclipse.swt.widgets.Display;

import de.thetodd.simulator8085.api.Simulator;

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
		boolean debug = Simulator.getInstance().isDebugMode();
		for (short i = start; i <= end; i++) {
			byte n = 0x00;
			if (!debug) {
				n = (byte) (Math.random() * 0xff); // Random Memorycontent
			}
			memory.put(i, n);
		}
		this.start = start;
		this.end = end;
		// Simulator.getInstance().fireMemoryChangeEvent();
	}

	// TODO: Ueberpruefung, ob Speicherzelle zulaessig
	public byte get(short adr) {
		return memory.get(adr);
	}

	// TODO: Ueberpruefung, ob Speicherzelle zulaessig
	public void put(short adr, byte content) {
		memory.put(adr, content);
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				Simulator.getInstance().fireMemoryChangeEvent();
			}
		});
		//Simulator.getInstance().fireMemoryChangeEvent();
	}

	public void resetMemory() {
		setRange(this.start,this.end);
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

	/**
	 * Decreases SP register and writes a byte to the memory at the position SP
	 * points to.
	 * 
	 * @param b
	 *            the byte to be written to stack.
	 */
	public void pushStack(byte b) {
		Processor.getInstance().setStackpointer(
				(short) (Processor.getInstance().getStackpointer() - 1));
		Memory.getInstance().put(Processor.getInstance().getStackpointer(), b);
	}

	/**
	 * Gets the byte from the memory at the position SP points to and increases
	 * SP register.
	 * 
	 * @return Byte from memory SP points to.
	 */
	public byte popStack() {
		byte b = Memory.getInstance().get(
				Processor.getInstance().getStackpointer());
		Processor.getInstance().setStackpointer(
				(short) (Processor.getInstance().getStackpointer() + 1));
		return b;
	}
}
