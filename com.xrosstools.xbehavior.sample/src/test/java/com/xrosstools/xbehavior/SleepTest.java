package com.xrosstools.xbehavior;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class SleepTest {
	@Test
	public void test() throws InterruptedException {
		int i = 3;
		Sleep test = new Sleep(i, TimeUnit.SECONDS);
		Blackboard bb = new Blackboard();
		long dur = System.currentTimeMillis();
		StatusEnum status;
		while((status = test.tick(bb)) == StatusEnum.RUNNING)
			TimeUnit.MILLISECONDS.sleep(1);
		
		dur = System.currentTimeMillis() - dur;
		assertTrue(dur - i * 1000 < 200);
		System.out.println(dur - i * 1000);
		assertEquals(status, StatusEnum.SUCCESS);
	}
}
