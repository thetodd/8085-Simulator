package de.thetodd.simulator8085.api;

public interface ProcessorChangedListener {

	public void memoryChanged();
	public void registerChanged(RegisterChangeEvent evt);
	
}
