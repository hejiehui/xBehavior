package com.xrosstools.xbehavior.idea.editor.commands;

import com.xrosstools.idea.gef.commands.CreateConnectionCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTCreateConnectionCommand extends CreateConnectionCommand {
    private BehaviorTreeDiagram diagram;

    public BTCreateConnectionCommand(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public void postExecute() {
        BehaviorNode node = (BehaviorNode)getConnection().getSource();
        BTMoveNodeCommand.reorderChildren(diagram, node);
    }
}
