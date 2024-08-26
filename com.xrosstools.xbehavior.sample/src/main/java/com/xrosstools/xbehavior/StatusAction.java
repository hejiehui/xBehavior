package com.xrosstools.xbehavior;

public class StatusAction implements Action {
	private StatusEnum[] sequence;
	private long sleep;

	private volatile int index;
	private volatile int tickCount;
	
	public StatusAction setSequence(StatusEnum...sequence) {
		this.sequence = sequence;
		return this;
	}

	public long getSleep() {
		return sleep;
	}

	public StatusAction setSleep(long sleep) {
		this.sleep = sleep;
		return this;
	}

	@Override
	public StatusEnum tick(Blackboard context) {
		tickCount++;
		if(sleep > 0)
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				return StatusEnum.FAILURE;
			}

		StatusEnum cur = sequence[index];
		index++;
		return cur;
	}
	
	public void reset() {
	}
	
	public int getTickCount() {
		return tickCount;
	}
	
	public void resetTickCount() {
		tickCount = 0;
		index = 0;
	}

}