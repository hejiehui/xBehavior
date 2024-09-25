package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class WaitNode extends DecoratorNode {
    private PropertyEntry<String> timeout = stringProperty(PROP_TIMEOUT, DEFAULT_COUNT_STR);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());

    public WaitNode() {
        super(BehaviorNodeType.WAIT);
        register(timeout);
        register(timeUnit);
    }
}
