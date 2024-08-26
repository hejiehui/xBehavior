package com.xrosstools.xbehavior.idea.editor.actions;

public interface BehaviorTreeMessage {
    String SEQUENCE_NODE = "SEQUENCE";
    String SELECTOR_NODE = "SELECTOR";
    String PARALLEL_NODE = "PARALLEL";

    String INVERTER_NODE = "INVERTER";
    String REPEATER_NODE = "REPEAT";

    String ACTION_NODE = "ACTION";
    String CONDITION_NODE = "CONDITION";

    String BEHAVIOR = "behavior";
    String NODE = "node";
}
