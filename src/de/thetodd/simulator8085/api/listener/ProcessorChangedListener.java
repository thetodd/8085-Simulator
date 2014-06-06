package de.thetodd.simulator8085.api.listener;



public interface ProcessorChangedListener {

	public void memoryChanged();
	public void registerChanged(RegisterChangeEvent evt);
	public void outChanged(byte adr, byte value);
	
}
