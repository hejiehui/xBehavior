package com.xrosstools.xbehavior.idea.editor.policies;

import com.xrosstools.idea.gef.commands.CreateConnectionCommand;
import com.xrosstools.idea.gef.commands.ReconnectSourceCommand;
import com.xrosstools.idea.gef.commands.ReconnectTargetCommand;
import com.xrosstools.idea.gef.policies.NodeEditPolicy;
import com.xrosstools.xbehavior.idea.editor.commands.BTCreateConnectionCommand;
import com.xrosstools.xbehavior.idea.editor.commands.BTReconnectSourceCommand;
import com.xrosstools.xbehavior.idea.editor.commands.BTReconnectTargetCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTNodeEditPolicy extends NodeEditPolicy {
    private BehaviorTreeDiagram diagram;

    public BTNodeEditPolicy(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    public CreateConnectionCommand createCreateConnectionCommand() {
        return new BTCreateConnectionCommand(diagram);
    }

    public ReconnectSourceCommand createReconnectSourceCommand() {
        return new BTReconnectSourceCommand(diagram);
    }

    public ReconnectTargetCommand createReconnectTargetCommand() {
        return new BTReconnectTargetCommand(diagram);
    }

}
