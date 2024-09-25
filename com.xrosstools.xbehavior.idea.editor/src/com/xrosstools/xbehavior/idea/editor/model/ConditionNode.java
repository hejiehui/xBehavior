package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

public class ConditionNode extends BehaviorNode{
    public enum Mode {
        EXPRESSION, CALLBACK
    }

    private PropertyEntry<Mode> mode = enumProperty(PROP_MODE, DEFAULT_CONDITION_MODE, Mode.values());
    private PropertyEntry<String> expression = stringProperty(PROP_EXPRESSION);
    private PropertyEntry<String> implementation = stringProperty(PROP_IMPLEMENTATION);

    public ConditionNode() {
        super(BehaviorNodeType.CONDITION);
        setOutputLimit(0);
        register(mode);
        register(expression, ()-> getMode() == Mode.EXPRESSION);
        register(implementation, ()-> getMode() == Mode.CALLBACK);
    }

    public String getExpression() {
        return expression.get();
    }

    public void setExpression(String _expression) {
        expression.set(_expression);
    }

    public Mode getMode() {
        return mode.get();
    }

    public void setMode(Mode _mode) {
        mode.set(_mode);
    }

    public String getImplementation() {
        return implementation.get();
    }

    public void setImplementation(String _implementation) {
        implementation.set(_implementation);
    }


}
