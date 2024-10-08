package com.xrosstools.xbehavior;

import com.xrosstools.xbehavior.def.PropertyConstants;

public enum BehaviorType implements PropertyConstants {
    SEQUENCE,
    SELECTOR,
    PARALLEL,

    INVERTER,
    REPEAT,
    RETRY,
    WAIT,
    FORCE_SUCCESS,
    FORCE_FAILURE,

    CONDITION,
    ACTION,
    FIXED_STATUS,
	SLEEP,
	SUBTREE;

    public String getNodeName() {
        return name().toLowerCase();
    }

    public static BehaviorType findByNodeName(String nodeName) {
        for(BehaviorType type: values()) {
            if(type.getNodeName().equals(nodeName))
                return type;
        }
        throw new IllegalArgumentException(nodeName);
    }
}
