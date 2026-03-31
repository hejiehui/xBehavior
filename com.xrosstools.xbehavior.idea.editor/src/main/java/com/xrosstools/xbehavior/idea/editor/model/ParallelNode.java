package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.IntegerPropertyDescriptor;
import com.xrosstools.idea.gef.util.ListPropertyDescriptor;
import com.xrosstools.idea.gef.util.PropertyEntry;

public class ParallelNode extends CompositeNode {
    enum Mode {
        ALL, ANY, SOME,
    }

    private PropertyEntry<Mode> mode = enumProperty(PROP_MODE, DEFAULT_PARALLEL_MODE, Mode.values());
    private PropertyEntry<String> count = stringProperty(PROP_COUNT, DEFAULT_COUNT_STR);

    public ParallelNode() {
        super(BehaviorNodeType.PARALLEL);
        register(mode, new ListPropertyDescriptor(Mode.values()));
        register(count, new IntegerPropertyDescriptor(), ()-> getMode() == Mode.SOME);
    }

    public Mode getMode() {
        return mode.get();
    }

    public void setMode(Mode _mode) {
        this.mode.set(_mode);
    }
}
