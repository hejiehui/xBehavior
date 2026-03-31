package com.xrosstools.xbehavior.idea.editor.model;

public class DecoratorNode extends BehaviorNode {
    public DecoratorNode(BehaviorNodeType type) {
        super(type);
        setOutputLimit(1);
    }
}
