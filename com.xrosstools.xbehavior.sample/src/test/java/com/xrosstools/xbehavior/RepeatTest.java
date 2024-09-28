package com.xrosstools.xbehavior;

import static com.xrosstools.xbehavior.StatusEnum.FAILURE;
import static com.xrosstools.xbehavior.StatusEnum.RUNNING;
import static com.xrosstools.xbehavior.StatusEnum.SUCCESS;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.xrosstools.xbehavior.def.ValueProperty;

public class RepeatTest {
	private Repeat test;
	private int maxAttempt = 3;
	private Property<Long> delay = ValueProperty.of(1L);
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	private StatusAction internal;
	private Blackboard bb;

	public void setUp(Repeat test) {
		this.test = test;
		internal = new StatusAction();
		test.setDecorated(internal);
		bb = new Blackboard();
	}
	
	//MAX_ATTEMPT mode
	
	@Test
	public void testRepeatUntilFailure_Success() {
		setUp(new Repeat(ValueProperty.of(maxAttempt), true));
		
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS);
			
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(maxAttempt, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Failure() {
		setUp(new Repeat(ValueProperty.of(maxAttempt), true));

		internal.setSequence(SUCCESS, FAILURE, SUCCESS);
			
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Running_Success() {
		setUp(new Repeat(ValueProperty.of(maxAttempt), true));
		
		internal.setSequence(SUCCESS, RUNNING, SUCCESS, SUCCESS);

		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(SUCCESS, RUNNING, SUCCESS, RUNNING, SUCCESS);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(5, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Running_Failure() {
		setUp(new Repeat(ValueProperty.of(maxAttempt), true));

		internal.setSequence(SUCCESS, RUNNING, SUCCESS, FAILURE);

		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(SUCCESS, RUNNING, SUCCESS, RUNNING, FAILURE);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(5, internal.getTickCount());
	}

	@Test
	public void testRepeat_Success() {
		setUp(new Repeat(ValueProperty.of(maxAttempt), false));

		internal.setSequence(SUCCESS, SUCCESS, SUCCESS);
			
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(maxAttempt, internal.getTickCount());

		internal.resetTickCount();
		internal.setSequence(FAILURE, FAILURE, FAILURE);
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(maxAttempt, internal.getTickCount());

		internal.resetTickCount();
		internal.setSequence(SUCCESS, RUNNING, FAILURE, RUNNING, SUCCESS);
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.RUNNING, test.tick(bb));
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(5, internal.getTickCount());
	}


	//timeout mode

	@Test
	public void testRepeatUntilFailure_Success_Timeout() {
		setUp(new Repeat(delay, timeUnit, true));
		
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS, SUCCESS, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
		
		internal.resetTickCount();
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		internal.resetTickCount();
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS, SUCCESS);
		//0, 400, 800
		internal.setSleep(400);	
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		//Timeout
		internal.resetTickCount();
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS, SUCCESS);
		//0, 600
		internal.setSleep(600);
		assertEquals(StatusEnum.SUCCESS, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Running_Success_Timeout() {
		setUp(new Repeat(delay, timeUnit, true));

		//Running case 1
		internal.resetTickCount();
		internal.setSequence(RUNNING, SUCCESS, RUNNING, SUCCESS, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Running case 2
		internal.resetTickCount();
		internal.setSequence(RUNNING, SUCCESS, RUNNING, RUNNING, RUNNING);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		//Running case 3
		internal.resetTickCount();
		internal.setSequence(RUNNING, SUCCESS, RUNNING, RUNNING, FAILURE, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(SUCCESS, test.tick(bb));
		assertEquals(4, internal.getTickCount());
	}

	@Test
	public void testRepeatUntilFailure_Failure_Timeout() {
		setUp(new Repeat(delay, timeUnit, true));
		
		internal.setSequence(SUCCESS, SUCCESS, SUCCESS, FAILURE);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		internal.resetTickCount();
		//0, 400, 800
		internal.setSleep(400);	
		internal.setSequence(SUCCESS, SUCCESS, FAILURE);
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		internal.resetTickCount();
		//0, 600
		internal.setSleep(600);
		internal.setSequence(SUCCESS, FAILURE);
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}
	
	@Test
	public void testRepeatUntilFailure_Running_Failure_Timeout() {
		setUp(new Repeat(delay, timeUnit, true));
		
		//Running case 1
		internal.resetTickCount();
		internal.setSequence(RUNNING, SUCCESS, RUNNING, FAILURE, SUCCESS);
		//0, 300, 600, 900
		internal.setSleep(300);
		
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(FAILURE, test.tick(bb));
		assertEquals(4, internal.getTickCount());

		internal.resetTickCount();
		//0, 400, 800
		internal.setSleep(400);	
		internal.setSequence(SUCCESS, RUNNING, FAILURE);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(3, internal.getTickCount());
		
		internal.resetTickCount();
		//0, 400, 800
		internal.setSleep(400);	
		internal.setSequence(RUNNING, SUCCESS, FAILURE);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(3, internal.getTickCount());

		internal.resetTickCount();
		//0, 600
		internal.setSleep(600);
		internal.setSequence(RUNNING, FAILURE);
		assertEquals(RUNNING, test.tick(bb));
		assertEquals(StatusEnum.FAILURE, test.tick(bb));
		assertEquals(2, internal.getTickCount());
	}
}
