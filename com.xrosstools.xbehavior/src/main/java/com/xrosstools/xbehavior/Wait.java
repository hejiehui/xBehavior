package com.xrosstools.xbehavior;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.def.ValueProperty;

import static com.xrosstools.xbehavior.AsyncAction.*; 

public class Wait extends Decorator {
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

	private Property<Long> delay;
	private TimeUnit timeUnit;

	private Future<StatusEnum> future;

	public Wait(Long delay, TimeUnit timeUnit) {
		this(ValueProperty.of(delay), timeUnit);
	}

	public Wait(Property<Long> delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}

	@Override
	public StatusEnum tick(final Blackboard context) {
		if(future == null) {
			final Behavior actualAction = getDecorated();

			future = executor.schedule(new Callable<StatusEnum>() {
				public StatusEnum call() throws Exception {
					return actualAction.tick(context);
				}
			}, delay.get(context), timeUnit);
		}

		StatusEnum status = getResult(future);
		if(status == StatusEnum.RUNNING)
			return StatusEnum.RUNNING;

		reset();
		return status;
	}
	
	public void resetParent() {
		resetFuture(future);
		future = null;
	}
}
