package com.xrosstools.xbehavior;

import static com.xrosstools.xbehavior.StatusEnum.FAILURE;
import static com.xrosstools.xbehavior.StatusEnum.RUNNING;
import static com.xrosstools.xbehavior.StatusEnum.SUCCESS;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class RetryTest {
	private Retry test;
	private int maxAttempt = 3;
	private Timeout timeout = new Timeout(1, TimeUnit.SECONDS);
	private StatusAction internal;
	private Blackboard bb;

	@Before
	public void setUp() throws Exception {
		test = new Retry();
		internal = new StatusAction();
		test.setDecorated(internal);
		bb = new Blackboard();
	}

	@Test
	public void testMaxAttemptSuccess() {
		test.setMaxAttempt(maxAttempt);
		
		internal.setSequence(FAILURE, FAILURE, SUCCESS);
			
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(maxAttempt, internal.getTickCount());
	}

	@Test
	public void testMaxAttemptFailure() {
		test.setMaxAttempt(maxAttempt);

		internal.setSequence(FAILURE, FAILURE, FAILURE);
			
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(maxAttempt, internal.getTickCount());
	}

	@Test
	public void testMaxAttempt_Running_Success() {
		test.setMaxAttempt(maxAttempt);

		internal.setSequence(RUNNING, FAILURE, FAILURE, SUCCESS);

		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(FAILURE, RUNNING, FAILURE, RUNNING, SUCCESS);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(5, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Running_Failure() {
		test.setMaxAttempt(maxAttempt);

		internal.setSequence(FAILURE, RUNNING, FAILURE, FAILURE);

		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(FAILURE, RUNNING, FAILURE, RUNNING, FAILURE);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(5, internal.getTickCount());
	}

	//timeout mode

	@Test
	public void testTimeout_Success() {
		test.setTimeout(timeout);
		test.setMaxAttempt(-1);
		
		internal.setSequence(FAILURE, FAILURE, FAILURE, SUCCESS, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE, FAILURE, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Timeout
		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE, SUCCESS);
		//0, 400, 800
		internal.setSleep(400);	
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		//Timeout
		internal.resetTickCount();
		internal.setSequence(FAILURE, SUCCESS);
		//0, 600
		internal.setSleep(600);
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}

	@Test
	public void testTimeout_Running_Success() {
		test.setTimeout(timeout);
		test.setMaxAttempt(-1);

		//Running case 1
		internal.resetTickCount();
		internal.setSequence(RUNNING, FAILURE, RUNNING, SUCCESS, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Running case 2
		internal.resetTickCount();
		internal.setSequence(RUNNING, RUNNING, RUNNING, SUCCESS, RUNNING);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Running case 3
		internal.resetTickCount();
		internal.setSequence(RUNNING, FAILURE, RUNNING, SUCCESS, RUNNING);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
	}

	@Test
	public void testTimeout_Failure() {
		//No timeout
		test.setTimeout(timeout);
		test.setMaxAttempt(-1);
		
		internal.setSequence(FAILURE, FAILURE, FAILURE, FAILURE, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE, FAILURE, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Timeout
		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE, FAILURE);
		//0, 400, 800
		internal.setSleep(400);	
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		//Timeout
		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE);
		//0, 600
		internal.setSleep(600);
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}

	@Test
	public void testTimeout_Running_Failure() {
		//No timeout
		test.setTimeout(timeout);
		test.setMaxAttempt(-1);
		
		internal.setSequence(FAILURE, RUNNING, FAILURE, FAILURE, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(RUNNING, RUNNING, FAILURE, FAILURE, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Timeout
		internal.resetTickCount();
		internal.setSequence(FAILURE, RUNNING, FAILURE);
		//0, 400, 800
		internal.setSleep(400);	

		assertEquals(RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		//Timeout
		internal.resetTickCount();
		internal.setSequence(RUNNING, FAILURE);
		//0, 600
		internal.setSleep(600);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}
}