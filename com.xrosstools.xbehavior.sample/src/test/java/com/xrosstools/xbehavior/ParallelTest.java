package com.xrosstools.xbehavior;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParallelTest {
	private Parallel test;
	private TestBehavior a, b, c;
	private Blackboard bb;
	
	@Before
	public void setup() {
		test = new Parallel();
		a = new TestBehavior();
		b = new TestBehavior();
		c = new TestBehavior();
		test.add(a);
		test.add(b);
		test.add(c);
		bb = new Blackboard();
	}

	@Test
	public void testSuccess() {
		test.setMinSuccess(3);
		a.setMaxAttempt(3);
		b.setMaxAttempt(2);
		c.setMaxAttempt(1);
		
		for(int i = 0; i < 3; i++) {
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		}
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
	}

	@Test
	public void testSuccessFast() {
		test.setMinSuccess(1);
		a.setMaxAttempt(3);
		b.setMaxAttempt(2);
		c.setMaxAttempt(0);
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		
		test.setMinSuccess(2);
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
	}

	@Test
	public void testFailure() {
		test.setMinSuccess(3);
		a.setMaxAttempt(3);
		b.setMaxAttempt(2);
		c.setMaxAttempt(1);
		a.setEndStatus(StatusEnum.FAILURE);
		
		for(int i = 0; i < 3; i++) {
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		}
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
	}

	@Test
	public void testFailureFast() {
		test.setMinSuccess(3);
		a.setMaxAttempt(3);
		b.setMaxAttempt(2);
		c.setMaxAttempt(0);
		c.setEndStatus(StatusEnum.FAILURE);
		
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		
		test.setMinSuccess(2);
		b.setEndStatus(StatusEnum.FAILURE);
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
	}

	@Test
	public void testRunning() {
		test.setMinSuccess(3);
		a.setMaxAttempt(3);
		b.setMaxAttempt(2);
		c.setMaxAttempt(1);
		c.setEndStatus(StatusEnum.RUNNING);
		
		for(int i = 0; i < 3; i++) {
			assertEquals(StatusEnum.RUNNING, test.tick(bb));
		}
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
	}

}
