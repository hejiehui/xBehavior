package com.xrosstools.xbehavior;

import java.util.concurrent.TimeUnit;

public class Timeout {
	private long delay;
	private TimeUnit timeUnit;
	private long startTime;
	
	private long normalizedDelay;
	
	public Timeout(long delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
		normalize();
	}

	private void normalize() {
		if(delay < 0)
			return;

		if(timeUnit == TimeUnit.NANOSECONDS || timeUnit == TimeUnit.MICROSECONDS)
			normalizedDelay = TimeUnit.NANOSECONDS.convert(delay, timeUnit);
		else
			normalizedDelay = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
	}
	
	public long getDelay() {
		return delay;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public boolean isStarted() {
		return startTime > 0;
	}
	
	public void start() {
		if(timeUnit == TimeUnit.NANOSECONDS || timeUnit == TimeUnit.MICROSECONDS)
			startTime = System.nanoTime();
		else
			startTime = System.currentTimeMillis();
	}

	public boolean isTimeout() {
		if(delay < 0)
			return false;

		if(timeUnit == TimeUnit.NANOSECONDS || timeUnit == TimeUnit.MICROSECONDS)
			return System.nanoTime() - startTime >= normalizedDelay;
		else
			return System.currentTimeMillis() - startTime >= normalizedDelay;
	}

	public void reset() {
		startTime = 0;
	}
}