package com.xrosstools.xbehavior.idea.editor.commands;

import com.xrosstools.idea.gef.commands.ReconnectSourceCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTReconnectSourceCommand extends ReconnectSourceCommand {
    private BehaviorTreeDiagram diagram;

    public BTReconnectSourceCommand(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public void postExecute() {
        BehaviorNode node = (BehaviorNode)getConnection().getSource();
        BTMoveNodeCommand.reorderChildren(diagram, node);
    }
}
