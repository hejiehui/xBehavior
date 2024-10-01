package com.xrosstools.xbehavior;

import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.def.ValueProperty;

public class Sleep implements Action {
	private Property<Long> delay;
	private TimeUnit timeUnit;

	private volatile Timeout timeout;

	public Sleep(long delay, TimeUnit timeUnit) {
		this(ValueProperty.of(delay), timeUnit);
	}
	
	public Sleep(Property<Long> delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}
	
	@Override
	public StatusEnum tick(Blackboard context) {
		if(isStarted(context) == false)
			timeout.start();

		if(timeout.isStarted() && timeout.isTimeout()) {
			reset();
			return StatusEnum.SUCCESS;
		} else
			return StatusEnum.RUNNING;
	}
	
	private boolean isStarted(Blackboard context) {
		if(timeout == null)
			timeout = new Timeout(delay.get(context), timeUnit);

		return timeout.isStarted();
	}

	@Override
	public void reset() {
		timeout.reset();
	}
}