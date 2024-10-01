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

	private volatile Future<StatusEnum> future;
	private volatile boolean started = false;

	public Wait(Long delay, TimeUnit timeUnit) {
		this(ValueProperty.of(delay), timeUnit);
	}

	public Wait(Property<Long> delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}

	@Override
	public StatusEnum tick(final Blackboard context) {
		start(context);

		StatusEnum status = getResult(context);
		
		if(status == StatusEnum.RUNNING)
			return StatusEnum.RUNNING;
		
		reset();
		return status;
	}
	
	private void start(final Blackboard context) {
		if(started)
			return;

		future = executor.schedule(new Callable<StatusEnum>() {
			public StatusEnum call() throws Exception {
				return getDecorated().tick(context);
			}
		}, delay.get(context), timeUnit);
		
		started = true;
	}

	private StatusEnum getResult(Blackboard context) {
		if(future == null)
			return getDecorated().tick(context);
			
		if(future.isDone()) {
			try {
				StatusEnum status = future.get();
				future = null;
				return status;
			} catch (Exception e) {
				future = null;
				return StatusEnum.FAILURE;
			}
		}

		return StatusEnum.RUNNING;
	}
	
	public void resetParent() {
		resetFuture(future);
		future = null;
		started = false;
	}
}
