package com.xrosstools.xbehavior;

public interface Condition {
    public enum Mode {
        EXPRESSION, CALLBACK
    }

    boolean check(Blackboard context);
}
