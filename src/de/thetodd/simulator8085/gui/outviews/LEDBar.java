package de.thetodd.simulator8085.gui.outviews;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import de.thetodd.simulator8085.api.ProcessorChangedListener;
import de.thetodd.simulator8085.api.RegisterChangeEvent;
import de.thetodd.simulator8085.api.Simulator;
import de.thetodd.simulator8085.gui.outviews.widgets.LEDWidget;

public class LEDBar extends Shell implements ProcessorChangedListener {
	private Spinner adrChooser;
	private LEDWidget led8;
	private LEDWidget led7;
	private LEDWidget led3;
	private LEDWidget led2;
	private LEDWidget led1;
	private LEDWidget led4;
	private LEDWidget led6;
	private LEDWidget led5;
	

	private Color onColor = new Color(null, 0,255,0);
	private Color offColor = new Color(null, 0,100,0);

	/**
	 * Create the shell.
	 * @param display
	 */
	public LEDBar(Display display) {
		super(display, SWT.DIALOG_TRIM);
		
		led8 = new LEDWidget(this, SWT.NONE);
		led8.setForeground(offColor);
		led8.setBounds(10, 10, 16, 16);
		
		led7 = new LEDWidget(this, SWT.NONE);
		led7.setForeground(offColor);
		led7.setBounds(32, 10, 16, 16);
		
		led6 = new LEDWidget(this, SWT.NONE);
		led6.setForeground(offColor);
		led6.setBounds(54, 10, 16, 16);
		
		led5 = new LEDWidget(this, SWT.NONE);
		led5.setForeground(offColor);
		led5.setBounds(76, 10, 16, 16);
		
		led4 = new LEDWidget(this, SWT.NONE);
		led4.setForeground(offColor);
		led4.setBounds(98, 10, 16, 16);
		
		led3 = new LEDWidget(this, SWT.NONE);
		led3.setForeground(offColor);
		led3.setBounds(120, 10, 16, 16);
		
		led2 = new LEDWidget(this, SWT.NONE);
		led2.setForeground(offColor);
		led2.setBounds(142, 10, 16, 16);
		
		led1 = new LEDWidget(this, SWT.NONE);
		led1.setForeground(offColor);
		led1.setBounds(164, 10, 16, 16);
		
		adrChooser = new Spinner(this, SWT.BORDER);
		adrChooser.setPageIncrement(1);
		adrChooser.setMaximum(255);
		adrChooser.setSelection(128);
		adrChooser.setBounds(379, 4, 55, 22);
		
		Label lblAddress = new Label(this, SWT.NONE);
		lblAddress.setBounds(328, 7, 45, 15);
		lblAddress.setText("Address:");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("LED Bar");
		setSize(450, 62);

		Simulator.getInstance().registerChangeListener(this);
		
		addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				Simulator.getInstance().unregisterChangeListener(LEDBar.this);				
			}
		});
		
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void memoryChanged() {
	}

	@Override
	public void registerChanged(RegisterChangeEvent evt) {
	}

	@Override
	public void outChanged(byte adr, byte value) {		
		byte selectedAdr = (byte) adrChooser.getSelection();
		if(adr == selectedAdr) {
			led8.setForeground(offColor);
			led7.setForeground(offColor);
			led6.setForeground(offColor);
			led5.setForeground(offColor);
			led4.setForeground(offColor);
			led3.setForeground(offColor);
			led2.setForeground(offColor);
			led1.setForeground(offColor);
			
			if((value&0x80) != 0){
				led8.setForeground(onColor);
			}
			if((value&0x40) != 0){
				led7.setForeground(onColor);
			}
			if((value&0x20) != 0){
				led6.setForeground(onColor);
			}
			if((value&0x10) != 0){
				led5.setForeground(onColor);
			}
			if((value&0x08) != 0){
				led4.setForeground(onColor);
			}
			if((value&0x04) != 0){
				led3.setForeground(onColor);
			}
			if((value&0x02) != 0){
				led2.setForeground(onColor);
			}
			if((value&0x01) != 0){
				led1.setForeground(onColor);
			}
		}
	}
}
