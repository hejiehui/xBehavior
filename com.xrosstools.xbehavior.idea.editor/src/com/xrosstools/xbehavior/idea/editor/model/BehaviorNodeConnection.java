package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.model.NodeConnection;

public class BehaviorNodeConnection extends NodeConnection<BehaviorNode, BehaviorNode> {
    public BehaviorNodeConnection(){}

    public BehaviorNodeConnection(BehaviorNode parent, BehaviorNode child) {
        super(parent, child);
    }
}
