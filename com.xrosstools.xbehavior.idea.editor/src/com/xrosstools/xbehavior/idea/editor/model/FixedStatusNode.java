package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

public class FixedStatusNode extends BehaviorNode{
    public enum Status {
        SUCCESS, FAILURE, RUNNING
    }

    private PropertyEntry<Status> status = enumProperty(PROP_STATUS, Status.SUCCESS, Status.values());

    public FixedStatusNode() {
        super(BehaviorNodeType.FIXED_STATUS);
        setOutputLimit(0);
        register(status);
    }

    public Status getStatus() {
        return status.get();
    }

    public void setStatus(Status _status) {
        status.set(_status);
    }
}
