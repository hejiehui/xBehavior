package com.xrosstools.xbehavior;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForceStatusTest {
	private final int attempt = 5;
	private Blackboard bb;
	private TestBehavior internal;
	
	@Before
	public void setup() {
		internal = new TestBehavior();
		internal.setMaxAttempt(attempt);
		bb = new Blackboard();
	}

	@Test
	public void testSuccess() {
		ForceStatus test = new ForceStatus(StatusEnum.SUCCESS);
		test.setDecorated(internal);
		
		internal.setEndStatus(StatusEnum.FAILURE);		
		for(int i = 0; i < attempt; i++)
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
	}

	@Test
	public void testFailure() {
		ForceStatus test = new ForceStatus(StatusEnum.FAILURE);
		test.setDecorated(internal);
		
		internal.setEndStatus(StatusEnum.SUCCESS);		
		for(int i = 0; i < attempt; i++)
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
	}
}
