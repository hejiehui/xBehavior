package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

public class SequenceNode extends CompositeNode {
    private PropertyEntry<Boolean> reactive = booleanProperty(PROP_REACTIVE, DEFAULT_REACTIVE);
    public SequenceNode() {
        super(BehaviorNodeType.SEQUENCE);
        register(reactive);
    }
}
