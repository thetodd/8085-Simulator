package test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.thetodd.simulator8085.api.platform.Processor;

public class ProcessorTest {

	@Test
	public void testRegisterA() {
		Processor.getInstance().setRegisterA((byte) 0xCC);
		assertEquals(Processor.getInstance().getRegisterA(),(byte) 0xCC);
	}
	
	@Test
	public void testRegisterPairDE() {
		Processor.getInstance().setRegisterD((byte) 0x1b);
		Processor.getInstance().setRegisterE((byte) 0xf0);
		assertEquals(Processor.getInstance().getRegisterDE(), 0x1bf0);
	}

}
