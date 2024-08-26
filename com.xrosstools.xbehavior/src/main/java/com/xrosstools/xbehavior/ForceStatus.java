package com.xrosstools.xbehavior;

public class ForceStatus extends Decorator {
	private StatusEnum preStatus;
	public ForceStatus(StatusEnum status) {
		this.preStatus = status;
	}

	public StatusEnum tick(Blackboard context) {
		StatusEnum status = getDecorated().tick(context);
		return status == StatusEnum.RUNNING? StatusEnum.RUNNING : preStatus;
	}
}
