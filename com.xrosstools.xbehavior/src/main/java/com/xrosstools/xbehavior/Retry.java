package com.xrosstools.xbehavior;

import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.def.ValueProperty;

public class Retry extends Decorator {
	private Property<Integer> maxAttempt = ValueProperty.of(-1);
	private Property<Long> delay = ValueProperty.of(-1L);
	private TimeUnit timeUnit = TimeUnit.SECONDS;

	private volatile boolean started = false;
	private volatile Timeout timeout;
	private volatile int max = -1;
	private volatile int count = 0;
	
	public Retry(Property<Long> delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}
	
	public Retry(Property<Integer> maxAttempt) {
		this.maxAttempt = maxAttempt;
	}

	public StatusEnum tick(Blackboard context) {
		start(context);
		Behavior decorated = getDecorated();

		while(max < 0 || count < max) {
			if(timeout.isTimeout())
				break;

			StatusEnum status = decorated.tick(context);
			if(status == StatusEnum.SUCCESS) {
				reset();
				return StatusEnum.SUCCESS;
			}

			if(status == StatusEnum.RUNNING) {
				return StatusEnum.RUNNING;
			}
			
			resetChild();
			count++;
		}
		
		reset();
		return StatusEnum.FAILURE;
	}
	
	private void start(Blackboard context) {
		if(started)
			return;

		max = maxAttempt.get(context);
		count = 0;
		timeout = new Timeout(delay.get(context), timeUnit);
		timeout.start();
		started = true;
	}

	public void resetParent() {
		started = false;
		count = 0;
		timeout = null;
	}
}
