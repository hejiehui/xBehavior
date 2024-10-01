package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

public class ConditionNode extends BehaviorNode{
    public enum Mode {
        EXPRESSION, CALLBACK
    }

    private PropertyEntry<Mode> mode = enumProperty(PROP_MODE, DEFAULT_CONDITION_MODE, Mode.values());
    private PropertyEntry<String> leftExpression = stringProperty(PROP_LEFT_EXPRESSION);
    private PropertyEntry<String> operator = enumProperty(PROP_OPERATOR, ConditionOperator.EQUAL.getText(), ConditionOperator.getAllOperatorText());
    private PropertyEntry<String> rightExpression = stringProperty(PROP_RIGHT_EXPRESSION);
    private PropertyEntry<String> implementation = stringProperty(PROP_IMPLEMENTATION);

    public ConditionNode() {
        super(BehaviorNodeType.CONDITION);
        setOutputLimit(0);
        register(mode);
        register(leftExpression, ()-> getMode() == Mode.EXPRESSION);
        register(operator, ()-> getMode() == Mode.EXPRESSION);
        register(rightExpression, ()-> getMode() == Mode.EXPRESSION && ConditionOperator.locate(operator.get()).requireParameter());
        register(implementation, ()-> getMode() == Mode.CALLBACK);
    }

    public Mode getMode() {
        return mode.get();
    }
}
