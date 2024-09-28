package com.xrosstools.xbehavior;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.xrosstools.xbehavior.def.ValueProperty;

public class AsyncAction implements Action {
	private static final ExecutorService executor = Executors.newCachedThreadPool();

	private Action actualAction;
	
	private Property<Long> delay = ValueProperty.of(-1L);
	private TimeUnit timeUnit = TimeUnit.SECONDS;

	private boolean started = false;
	private Timeout timeout;
	private Future<StatusEnum> future;

	public AsyncAction(Long delay, TimeUnit timeUnit, Action actualAction) {
		this(ValueProperty.of(delay), timeUnit, actualAction);
	}

	public AsyncAction(Property<Long> delay, TimeUnit timeUnit, Action actualAction) {
		this.actualAction = actualAction;
		this.delay = delay;
		this.timeUnit = timeUnit;
	}
	
	@Override
	public StatusEnum tick(Blackboard context) {
		if (future == null) {
			start(context);
		}
	
		if(timeout.isTimeout()) {
			StatusEnum status = getResult(future);
			reset();
			return status == StatusEnum.RUNNING ? StatusEnum.FAILURE : status;
		} else {
			StatusEnum status = getResult(future);
			if(status == StatusEnum.RUNNING)
				return StatusEnum.RUNNING;

			reset();
			return status;
		}
	}
	
	private void start(final Blackboard context) {
		timeout = new Timeout(delay.get(context), timeUnit);
		
		future = executor.submit(new Callable<StatusEnum>() {
			public StatusEnum call() throws Exception {
				return actualAction.tick(context);
			}
		});
		
		timeout.start();
	}

	@Override
	public void reset() {
		timeout = null;
		resetFuture(future);
		future = null;
		actualAction.reset();
	}

	public static StatusEnum getResult(Future<StatusEnum> future) {
		if(future.isDone()) {
			try {
				return future.get();
			} catch (Exception e) {
				return StatusEnum.FAILURE;
			}
		}

		return StatusEnum.RUNNING;
	}

	public static void resetFuture(Future<StatusEnum> future) {
		if(future == null) return;

		if(!(future.isDone() || future.isCancelled()))
			future.cancel(true);
	}
}
