package com.xrosstools.xbehavior;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InverterTest {
	private Inverter test;
	private final int attempt = 5;
	private Blackboard bb;
	private TestBehavior internal;

	@Before
	public void setUp() throws Exception {
		test = new Inverter();
		internal = new TestBehavior();
		internal.setMaxAttempt(attempt);
		test.setDecorated(internal);
		bb = new Blackboard();
	}

	@Test
	public void testSuccess() {
		internal.setEndStatus(StatusEnum.FAILURE);		
		for(int i = 0; i < attempt; i++)
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
	}

	@Test
	public void testFailure() {
		internal.setEndStatus(StatusEnum.SUCCESS);		
		for(int i = 0; i < attempt; i++)
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
	}
}