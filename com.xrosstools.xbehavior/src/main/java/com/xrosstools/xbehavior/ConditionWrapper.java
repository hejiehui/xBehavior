package com.xrosstools.xbehavior;

public class ConditionWrapper implements Behavior {
	private Condition condition;

	public ConditionWrapper(Condition condition) {
		this.condition = condition;
	}

	public StatusEnum tick(Blackboard context) {
		return condition.check(context) ? StatusEnum.SUCCESS : StatusEnum .FAILURE;
	}

	@Override
	public void reset() {
		condition.reset();
	}
}
