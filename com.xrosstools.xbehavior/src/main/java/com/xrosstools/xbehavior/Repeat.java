package com.xrosstools.xbehavior;

import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.def.ValueProperty;

public class Repeat extends Decorator {
	private boolean repeatUntilFailure = true;

	private Property<Integer> maxAttempt = ValueProperty.of(-1);
	private Property<Long> delay;
	private TimeUnit timeUnit;

	private Timeout timeout = Timeout.NEVER;
	private int count = 0;
	
	public Repeat() {}

	public Repeat(Property<Long> delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}
	
	public Repeat(Property<Integer> maxAttempt) {
		this.maxAttempt = maxAttempt;
	}
	
	public void setTimeout(Timeout timeout) {
		this.timeout = timeout == null ?  Timeout.NEVER : timeout;
	}
			
	public void setMaxAttempt(int maxAttempt) {
		this.maxAttempt = ValueProperty.of(maxAttempt);
	}
	
	public boolean isRepeatUntilFailure() {
		return repeatUntilFailure;
	}

	public void setRepeatUntilFailure(boolean repeatUntilFailure) {
		this.repeatUntilFailure = repeatUntilFailure;
	}

	public StatusEnum tick(Blackboard context) {
		start(context);		
		Behavior decorated = getDecorated();

		//If maxAttempt < 0, then repeat until failure
		int max = maxAttempt.get(context);
		while(max < 0 || count < max) {
			if(timeout.isTimeout())
				break;

			StatusEnum status = decorated.tick(context);

			if(status == StatusEnum.FAILURE && repeatUntilFailure == true) {
				reset();
				return StatusEnum.FAILURE;
			}
		
			if(status == StatusEnum.RUNNING)
				return StatusEnum.RUNNING;

			resetChild();
			count++;
		}

		reset();
		return StatusEnum.SUCCESS;
	}

	private void start(Blackboard context) {
		if(timeout.isStarted())
			return;

		if(delay != null && timeout.getDelay() != delay.get(context))
			timeout = new Timeout(delay.get(context), timeUnit);
		
		timeout.start();
	}
	
	public void resetParent() {
		count = 0;
		timeout.reset();
	}
}
