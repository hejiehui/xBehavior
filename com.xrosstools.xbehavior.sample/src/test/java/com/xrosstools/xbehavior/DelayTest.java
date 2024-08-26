package com.xrosstools.xbehavior;

import static com.xrosstools.xbehavior.StatusEnum.FAILURE;
import static com.xrosstools.xbehavior.StatusEnum.RUNNING;
import static com.xrosstools.xbehavior.StatusEnum.SUCCESS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class DelayTest {
	private Delay test;
	private Blackboard bb;
	private TestBehavior internal;
	private final long delay = 100;

	@Before
	public void setup() {
		test = new Delay(delay, TimeUnit.MILLISECONDS);
		internal = new TestBehavior();
		test.setDecorated(internal);
		bb = new Blackboard();
	}

	@Test
	public void testSuccess() {
		test(SUCCESS);
	}
	
	@Test
	public void testFailure() {
		test(FAILURE);
	}

	@Test
	public void testRunning() {
		test(RUNNING);
	}

	public void test(StatusEnum result) {
		System.out.println("Test " + result.name());
		internal.setEndStatus(result);
		for(int i = 0; i < 3; i++) {
			assertFalse(internal.isProcessing());
			internal.setSleep(delay * i);
			StatusEnum status = null;
			
			int j=0;
			int k = 0;
			long dur = System.currentTimeMillis();
			boolean done = false;
			while((status = test.tick(bb)) == RUNNING) {
				if(internal.isProcessing()) {
					k++;
					if(!done) {
						System.out.println(System.currentTimeMillis() - dur);
						done = true;
					}
				} else
					j++;

				if(System.currentTimeMillis() - dur > delay * (i + 2))
					break;
			}
			dur = System.currentTimeMillis() - dur;
	
			assertEquals(result, status);
			assertFalse(internal.isProcessing());
			
			System.out.println(String.format("dur: %d  k %d  j %d", dur, k, j));
			assertTrue(dur >= delay * (i+1));
		}
	}

	@Test
	public void testInterrupted() {
		internal.setEndStatus(FAILURE);
		internal.setSleep(delay);
		StatusEnum status = null;
		
		long dur = System.currentTimeMillis();
		while((status = test.tick(bb)) == RUNNING) {
			if(System.currentTimeMillis() - dur > delay * 1.5) {
				assertTrue(internal.isProcessing());
				test.reset();
				break;
			}
		}
				
		dur = System.currentTimeMillis() - dur;

		assertEquals(RUNNING, status);
		assertFalse(internal.isProcessing());
		assertTrue(dur >= delay);
		System.out.println(dur - delay);
	}
}
