package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class SleepNode extends BehaviorNode {
    private PropertyEntry<String> count = stringProperty(PROP_COUNT, DEFAULT_COUNT_STR);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());
    public SleepNode() {
        super(BehaviorNodeType.SLEEP);
        register(count);
        register(timeUnit);
    }

}
