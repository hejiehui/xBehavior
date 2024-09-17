package com.xrosstools.xbehavior.idea.editor.policies;

import com.xrosstools.idea.gef.commands.MoveNodeCommand;
import com.xrosstools.idea.gef.policies.NodeContainerEditPolicy;
import com.xrosstools.xbehavior.idea.editor.commands.BTMoveNodeCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTNodeContainerEditPolicy extends NodeContainerEditPolicy {
    private BehaviorTreeDiagram diagram;

    public BTNodeContainerEditPolicy(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    public MoveNodeCommand createMoveNodeCommand() {
        return new BTMoveNodeCommand(diagram);
    }
}
