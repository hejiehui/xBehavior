package com.xrosstools.xbehavior.idea.editor.commands;

import com.xrosstools.idea.gef.commands.MoveNodeCommand;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNodeConnection;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import java.util.Comparator;
import java.util.List;

public class BTMoveNodeCommand extends MoveNodeCommand {
    private BehaviorTreeDiagram diagram;

    public BTMoveNodeCommand(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    @Override
    public void postExecute() {
        BehaviorNode node = (BehaviorNode)getNode();
        if(node.getInputs().size() == 1)
            reorderChildren(diagram, node.getInputs().get(0).getSource());
        else
            reorderRoot(diagram);
    }

    public static void reorderChildren(BehaviorTreeDiagram diagram, BehaviorNode parent) {
        List<BehaviorNodeConnection> outputs = parent.getOutputs();
        outputs.sort(Comparator.comparingInt(o -> o.getTarget().getLocation().x));
        diagram.firePropertyChange(BehaviorTreeDiagram.PROP_LAYOUT);
    }

    public static void reorderRoot(BehaviorTreeDiagram diagram) {
        List<BehaviorNode> nodes = diagram.getChildren();
        nodes.sort(Comparator.comparingInt(o -> o.getInputs().size() == 0 ? o.getLocation().y : 0));
        diagram.firePropertyChange(BehaviorTreeDiagram.PROP_LAYOUT);
    }
}
