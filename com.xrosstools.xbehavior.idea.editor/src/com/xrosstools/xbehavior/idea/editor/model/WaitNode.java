package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class WaitNode extends DecoratorNode {
    private PropertyEntry<Long> timeout = longProperty(PROP_TIMEOUT, DEFAULT_COUNT);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());

    public WaitNode() {
        super(BehaviorNodeType.WAIT);
        register(timeout);
        register(timeUnit);
    }

    public Long getTimeout() {
        return timeout.get();
    }

    public void setTimeout(Long _timeout) {
        timeout.set(_timeout);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit.get();
    }

    public void setTimeUnit(TimeUnit _timeUnit) {
        timeUnit.set(_timeUnit);
    }
}
