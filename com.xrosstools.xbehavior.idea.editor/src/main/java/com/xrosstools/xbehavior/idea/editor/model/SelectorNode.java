package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

public class SelectorNode extends CompositeNode {
    private PropertyEntry<Boolean> reactive = booleanProperty(PROP_REACTIVE, DEFAULT_REACTIVE);


    public SelectorNode() {
        super(BehaviorNodeType.SELECTOR);
        register(reactive);
    }
}
