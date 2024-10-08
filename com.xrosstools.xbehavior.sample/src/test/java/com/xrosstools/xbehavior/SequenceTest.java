package com.xrosstools.xbehavior;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.sample.TestBehavior;

public class SequenceTest {
	private Sequence test;
	private TestBehavior a, b, c;
	private Blackboard bb;
	
	@Before
	public void setup() {
		test = new Sequence();
		a = b =c =null;
		bb = new Blackboard();
	}
	
	private TestBehavior a(StatusEnum status) {
		return a = new TestBehavior(status);
	}

	private TestBehavior b(StatusEnum status) {
		return b = new TestBehavior(status);
	}

	private TestBehavior c(StatusEnum status) {
		return c = new TestBehavior(status);
	}

	@Test
	public void testResetParent() {
		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.SUCCESS));
		test.add(c(StatusEnum.RUNNING));
		
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(2l, test.getCurrentIndex());

		test.resetParent();
		assertEquals(0l, test.getCurrentIndex());
	}

	@Test
	public void testSetGetReactive() {
		assertFalse(test.isReactive());
		test = new Sequence(true);
		assertTrue(test.isReactive());
	}

	@Test
	public void testReactiveSuccess() {
		test = new Sequence(true);

		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.SUCCESS));		
		test.add(c(StatusEnum.SUCCESS));
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(1l, c.getTtickedCount());
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(2l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(2l, c.getTtickedCount());
	}

	@Test
	public void testReactiveFailure() {
		test = new Sequence(true);

		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.FAILURE));
		test.add(c(StatusEnum.RUNNING));
		
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());

		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(2l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
	}

	@Test
	public void testReactiveRunning() {
		test = new Sequence(true);

		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.RUNNING));
		test.add(c(StatusEnum.SUCCESS));
		
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
		
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(2l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
	}

	@Test
	public void testTickSuccess() {
		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.SUCCESS));
		test.add(c(StatusEnum.SUCCESS));
		
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(1l, c.getTtickedCount());

		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(2l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(2l, c.getTtickedCount());
	}

	@Test
	public void testTickFailure() {
		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.FAILURE));
		test.add(c(StatusEnum.SUCCESS));
		
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());

		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(0l, test.getCurrentIndex());
		assertEquals(2l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
	}

	@Test
	public void testTickRunning() {
		test.add(a(StatusEnum.SUCCESS));
		test.add(b(StatusEnum.RUNNING));
		test.add(c(StatusEnum.SUCCESS));
		
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(1l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(1l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
		
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(1l, test.getCurrentIndex());
		assertEquals(1l, a.getTtickedCount());
		assertEquals(2l, b.getTtickedCount());
		assertEquals(0l, c.getTtickedCount());
	}
}
