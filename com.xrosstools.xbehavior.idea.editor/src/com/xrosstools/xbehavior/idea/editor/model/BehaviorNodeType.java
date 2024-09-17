package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.xbehavior.idea.editor.XbehaviorIcons;

import javax.swing.*;

public enum BehaviorNodeType implements XbehaviorIcons {
    SEQUENCE(SequenceNode.class, SEQUENCE_ICON),
    SELECTOR(SelectorNode.class, SELECTOR_ICON),
    PARALLEL(ParallelNode.class, PARALLEL_ICON),

    INVERTER(InverterNode.class, INVERTER_ICON),
    REPEAT(RepeatNode.class, REPEAT_ICON),
    RETRY(RetryNode.class, RETRY_ICON),
    WAIT(WaitNode.class, WAIT_ICON),
    FORCE_SUCCESS(ForceSuccessNode.class, FORCE_SUCCESS_ICON),
    FORCE_FAILURE(ForceFailureNode.class, FORCE_FAILURE_ICON),

    CONDITION(ConditionNode.class, CONDITION_ICON),
    ACTION(ActionNode.class, ACTION_ICON),
    FIXED_STATUS(FixedStatusNode.class, FIXED_STATUS_ICON),
    SLEEP(SleepNode.class, SLEEP_ICON),
    SUBTREE(SubtreeNode.class, SUBTREE_ICON);

    private String displayName;
    private String nodeName;
    private Class typeClass;
    private Icon typeIcon;

    BehaviorNodeType(Class typeClass, Icon typeIcon) {
        this.typeClass = typeClass;
        this.typeIcon = typeIcon;
        nodeName = name().toLowerCase();

        displayName = Character.toUpperCase(nodeName.charAt(0)) + nodeName.substring(1);
        displayName = displayName.replace("_", " ");
    }

    public Icon getTypeIcon() {
        return typeIcon;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public static BehaviorNodeType findByNodeName(String nodeName) {
        for(BehaviorNodeType type: values()) {
            if(type.getNodeName().equals(nodeName))
                return type;
        }
        throw new IllegalArgumentException(nodeName);
    }
}
