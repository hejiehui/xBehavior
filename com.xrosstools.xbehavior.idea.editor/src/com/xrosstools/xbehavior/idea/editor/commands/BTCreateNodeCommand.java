package com.xrosstools.xbehavior.idea.editor.commands;

import com.xrosstools.idea.gef.commands.CreateNodeCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BTCreateNodeCommand extends CreateNodeCommand {
    private BehaviorTreeDiagram diagram;

    public BTCreateNodeCommand(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public void postExecute() {
        BTMoveNodeCommand.reorderRoot(diagram);
    }
}
