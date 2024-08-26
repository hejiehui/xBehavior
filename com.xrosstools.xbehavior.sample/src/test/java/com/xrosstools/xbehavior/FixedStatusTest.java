package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FixedStatusTest {
	@Test
	public void testSucess() {
		FixedStatus test = new FixedStatus();
		
		Blackboard bb = new Blackboard();
		test.setStatus(StatusEnum.SUCCESS);
		assertEquals(StatusEnum.SUCCESS, test.getStatus());
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
	}

	@Test
	public void testFailure() {
		FixedStatus test = new FixedStatus();
		
		Blackboard bb = new Blackboard();
		test.setStatus(StatusEnum.FAILURE);
		assertEquals(StatusEnum.FAILURE, test.getStatus());
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
	}

	@Test
	public void testRunning() {
		FixedStatus test = new FixedStatus();
		
		Blackboard bb = new Blackboard();
		test.setStatus(StatusEnum.RUNNING);
		assertEquals(StatusEnum.RUNNING, test.getStatus());
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
	}

}
