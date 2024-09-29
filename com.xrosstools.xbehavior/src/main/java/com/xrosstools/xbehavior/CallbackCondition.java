package com.xrosstools.xbehavior;

public class CallbackCondition implements Condition, Behavior {
	private Condition condition;
	
	public CallbackCondition(Condition condition) {
		this.condition = condition;
	}

    public final StatusEnum tick(Blackboard context) {
		return check(context) ? StatusEnum.SUCCESS : StatusEnum .FAILURE;
	}

	@Override
	public void reset() {
	}

	@Override
	public boolean check(Blackboard context) {
		return condition.check(context);
	}
}
