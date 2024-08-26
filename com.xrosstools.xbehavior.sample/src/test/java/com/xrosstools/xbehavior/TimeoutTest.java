package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class TimeoutTest {
	private Timeout test;

	@Test
	public void testGets() {
		test = new Timeout(10, TimeUnit.SECONDS);

		assertEquals(10, test.getDelay());
		assertEquals(TimeUnit.SECONDS, test.getTimeUnit());
	}

	@Test
	public void testStart() {
		test = new Timeout(10, TimeUnit.SECONDS);

		assertFalse(test.isStarted());
		test.start();
		assertTrue(test.isStarted());
	}

	@Test
	public void testNanoSecond() {
		testByNano(10, TimeUnit.NANOSECONDS);
	}
	
	@Test
	public void testMicroSecond() {
		testByNano(10, TimeUnit.MICROSECONDS);
	}
	
	@Test
	public void testMilliSecond() {
		testByMilli(10, TimeUnit.MILLISECONDS);
	}
	
	@Test
	public void testSecond() {
		testByMilli(2, TimeUnit.SECONDS);
	}
	
	@Test
	public void testMinute() {
//		testByMilli(1, TimeUnit.MINUTES);
	}
	
	private void testByNano(long delay, TimeUnit unit) {
		test = new Timeout(delay, unit);
		long nanoDelay = TimeUnit.NANOSECONDS.convert(delay, unit);
		long dur = System.nanoTime();

		int i = 0;
		test.start();
		while(test.isTimeout() == false) i++;
		
		dur = System.nanoTime() - dur;
		
		assertTrue(dur >= nanoDelay);
		System.out.println(String.format("%s : %d  %d", unit.name(), dur - nanoDelay, i));
//		assertTrue(dur - nanoDelay < 100);
	}

	private void testByMilli(long delay, TimeUnit unit) {
		test = new Timeout(delay, unit);

		long dur = System.currentTimeMillis();
		test.start();
		while(test.isTimeout() == false)
			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch (InterruptedException e) {
				fail();
			}
		
		dur = System.currentTimeMillis() - dur;
		long milliDelay = TimeUnit.MILLISECONDS.convert(delay, unit);
		assertTrue(dur >= milliDelay);
		System.out.println(String.format("%s : %d", unit.name(), dur - milliDelay));
		assertTrue(dur - milliDelay < 10);
		
	}
}
