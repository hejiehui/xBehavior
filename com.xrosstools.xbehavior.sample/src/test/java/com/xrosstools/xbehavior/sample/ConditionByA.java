package com.xrosstools.xbehavior.sample;

import com.xrosstools.xbehavior.Blackboard;
import com.xrosstools.xbehavior.Condition;

public class ConditionByA implements Condition {
    public static final String A = "A";
    public static final String B = "B";
    @Override
    public boolean check(Blackboard blackboard) {
        return (boolean)blackboard.get(A);
    }
}
