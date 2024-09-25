package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.concurrent.TimeUnit;

public class RepeatNode extends DecoratorNode {
    private PropertyEntry<ProcessMode> mode = enumProperty(PROP_MODE, DEFAULT_PROCESS_MODE, ProcessMode.values());
    private PropertyEntry<Boolean> repeatUntilFailure = booleanProperty(PROP_REPEAT_UNTIL_FAILURE, DEFAULT_REPEAT_UNTIL_FAILURE);
    private PropertyEntry<String> count = stringProperty(PROP_COUNT, DEFAULT_COUNT_STR);
    private PropertyEntry<TimeUnit> timeUnit = enumProperty(PROP_TIME_UNIT, DEFAULT_TIME_UNIT, TimeUnit.values());

    public RepeatNode() {
        super(BehaviorNodeType.REPEAT);
        register(mode);
        register(repeatUntilFailure);
        register(count);
        register(timeUnit, () -> getMode() == ProcessMode.TIMEOUT);
    }

    public ProcessMode getMode() {
        return mode.get();
    }
}
