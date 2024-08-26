package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.model.Node;
import com.xrosstools.idea.gef.util.PropertyEntry;
import com.xrosstools.idea.gef.util.TextPropertyDescriptor;

public abstract class BehaviorNode extends Node<BehaviorNodeConnection> implements PropertyConstants {
    private PropertyEntry<String> name = stringProperty(PROP_NAME);
    private PropertyEntry<String> description = stringProperty(PROP_DESCRIPTION);

    private BehaviorNodeType type;

    public BehaviorNode(BehaviorNodeType type) {
        this.type = type;
        setInputLimit(1);
        register(name, new TextPropertyDescriptor());
        register(description, new TextPropertyDescriptor());
    }

    public BehaviorNodeType getType() {
        return type;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String _name) {
        name.set(_name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String _description) {
        description.set(_description);
    }

    public String getFigureDisplayText() {
        return getName();
    }

    public String getTreeDisplayText() {
        return getFigureDisplayText();
    }
}