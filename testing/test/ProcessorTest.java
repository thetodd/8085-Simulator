package test;

import static org.junit.Assert.*;

import org.junit.Test;

import de.thetodd.simulator8085.api.Processor;

public class ProcessorTest {

	@Test
	public void testRegisterA() {
		Processor.getInstance().setRegisterA((byte) 0xCC);
		assertEquals(Processor.getInstance().getRegisterA(),(byte) 0xCC);
	}

}
