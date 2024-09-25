package com.xrosstools.xbehavior;

public abstract class Condition implements Behavior {
    public enum Mode {
        EXPRESSION, CALLBACK
    }

    public abstract boolean check(Blackboard context);

    public final StatusEnum tick(Blackboard context) {
		return check(context) ? StatusEnum.SUCCESS : StatusEnum .FAILURE;
	}
}
