package com.xrosstools.xbehavior;

public class FixedStatus implements Action {
	private StatusEnum status;
	
	public FixedStatus() {
		this(StatusEnum.SUCCESS);
	}

	public FixedStatus(StatusEnum status) {
		this.status = status;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public StatusEnum tick(Blackboard context) {
		return status;
	}

	@Override
	public void reset() {
	}
}
