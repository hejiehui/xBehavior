package com.xrosstools.xbehavior;

public class TestBehavior implements Behavior {
	private StatusEnum initStatus = StatusEnum.RUNNING;
	private StatusEnum endStatus;
	private long sleep;
	private int maxAttempt;

	private volatile int count;
	private volatile int tickedCount;
	private volatile boolean processing;
	
	private int resetCount;

	TestBehavior() {
		this(StatusEnum.SUCCESS);
	}

	TestBehavior(StatusEnum status) {
		this.endStatus = status;
	}
	
	public StatusEnum getEndStatus() {
		return endStatus;
	}

	public TestBehavior setEndStatus(StatusEnum status) {
		this.endStatus = status;
		return this;
	}

	public StatusEnum getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(StatusEnum initStatus) {
		this.initStatus = initStatus;
	}

	public long getSleep() {
		return sleep;
	}

	public TestBehavior setSleep(long sleep) {
		this.sleep = sleep;
		return this;
	}

	public int getMaxAttempt() {
		return maxAttempt;
	}

	public TestBehavior setMaxAttempt(int maxAttempt) {
		this.maxAttempt = maxAttempt;
		return this;
	}

	@Override
	public StatusEnum tick(Blackboard context) {
		processing = true;
		tickedCount++;
		if(sleep > 0)
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				return StatusEnum.FAILURE;
			}

		if(maxAttempt <= 0 || count == maxAttempt) {
			reset();
			return endStatus;
		}
		
		count++;
		return initStatus;
	}
	
	public void reset() {
		count = 0;
		processing = false;
		resetCount++;
	}
	
	public boolean isProcessing() {
		return processing;
	}

	public int getTtickedCount() {
		return tickedCount;
	}
	
	public void resetTtickedCount() {
		tickedCount = 0;
	}
	
	public int getResetCount() {
		return resetCount;
	}
}