package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class ActionNode extends BehaviorNode {
    private PropertyEntry<String> implementation = stringProperty(PROP_IMPLEMENTATION);
    private PropertyEntry<Boolean> asynchronized = booleanProperty(PROP_ASYNCHRONOUS, false);
    private PropertyEntry<Long> timeout = longProperty(PROP_TIMEOUT, DEFAULT_COUNT);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());

    public ActionNode() {
        super(BehaviorNodeType.ACTION);
        setOutputLimit(0);
        register(asynchronized);
        register(implementation);
        register(timeout);
        register(timeUnit);
    }
}
