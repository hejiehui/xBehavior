package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.sample.StatusAction;

public class AsyncActionTest {
	private long delay = 1000;
	
	private AsyncAction test;
	private StatusAction internal;
	private Blackboard bb;
	
	@Before
	public void setUp() throws Exception {
		internal = new StatusAction();
		test = new AsyncAction(delay, TimeUnit.MILLISECONDS, internal); 
		bb = new Blackboard();
	}

	@Test
	public void testSuccess() throws InterruptedException {
		internal.setSequence(StatusEnum.SUCCESS);
		internal.setSleep(delay - 2);
		StatusEnum status;
		long dur = System.currentTimeMillis();
		int i = 0;
		while((status = test.tick(bb)) == StatusEnum.RUNNING) {
			TimeUnit.MICROSECONDS.sleep(1);
			i++;
		}
		assertEquals(StatusEnum.SUCCESS, status);
		System.out.println(String.format("Dur %d count %d", (System.currentTimeMillis() - dur), i));
	}

	@Test
	public void testFailure() throws InterruptedException {
		internal.setSequence(StatusEnum.FAILURE);
		internal.setSleep(delay - 2);
		StatusEnum status;
		long dur = System.currentTimeMillis();
		int i = 0;
		while((status = test.tick(bb)) == StatusEnum.RUNNING) {
			TimeUnit.MICROSECONDS.sleep(1);
			i++;
		}
		assertEquals(StatusEnum.FAILURE, status);
		System.out.println(String.format("Dur %d count %d", (System.currentTimeMillis() - dur), i));
	}
	
	@Test
	public void testTimeout() throws InterruptedException {
		internal.setSequence(StatusEnum.SUCCESS);
		internal.setSleep(delay + 2);
		StatusEnum status;
		long dur = System.currentTimeMillis();
		int i = 0;
		while((status = test.tick(bb)) == StatusEnum.RUNNING) {
			TimeUnit.MICROSECONDS.sleep(1);
			i++;
		}
		assertEquals(StatusEnum.FAILURE, status);
		System.out.println(String.format("Dur %d count %d", (System.currentTimeMillis() - dur), i));
	}
}
