package de.thetodd.simulator8085.api.listener;


/**
 * @deprecated as of 2.0, you should use ISumulatorListener
 *
 */
public interface ProcessorChangedListener {

	public void memoryChanged();
	public void registerChanged(RegisterChangeEvent evt);
	public void outChanged(byte adr, byte value);
	
}
