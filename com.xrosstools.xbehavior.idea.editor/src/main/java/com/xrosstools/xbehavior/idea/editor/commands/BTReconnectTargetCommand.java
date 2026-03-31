package com.xrosstools.xbehavior.idea.editor.commands;

import com.xrosstools.idea.gef.commands.ReconnectTargetCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTReconnectTargetCommand extends ReconnectTargetCommand {
    private BehaviorTreeDiagram diagram;

    public BTReconnectTargetCommand(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public void postExecute() {
        BehaviorNode node = (BehaviorNode)getConnection().getSource();
        BTMoveNodeCommand.reorderChildren(diagram, node);
    }
}
