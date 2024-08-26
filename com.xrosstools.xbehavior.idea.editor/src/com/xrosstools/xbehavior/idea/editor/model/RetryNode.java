package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class RetryNode extends DecoratorNode {
    private PropertyEntry<ProcessMode> mode = enumProperty(PROP_MODE, DEFAULT_PROCESS_MODE, ProcessMode.values());
    private PropertyEntry<Long> count = longProperty(PROP_COUNT, DEFAULT_COUNT);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());

    public RetryNode() {
        super(BehaviorNodeType.RETRY);
        register(mode);
        register(count);
        register(timeUnit, () -> getMode() == ProcessMode.TIMEOUT);
    }

    public ProcessMode getMode() {
        return mode.get();
    }
}
