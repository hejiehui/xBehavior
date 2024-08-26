package com.xrosstools.xbehavior.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xbehavior.idea.editor.actions.BehaviorTreeMessage;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import javax.swing.*;

public class BehaviorTreeContextMenuProvider extends ContextMenuProvider implements BehaviorTreeMessage {
//    private NodeContextMenuProvider nodeMenuProvider;
//    private ConnectionContextMenuProvider connMenuProvider;
//    private ExpressionContextMenuProvider expMenuProvider;
//    private DiagramContextMenuProvider diagramMenuProvider;


    public BehaviorTreeContextMenuProvider(Project project, BehaviorTreeDiagram diagram) {
//
//        nodeMenuProvider = new NodeContextMenuProvider(project, diagram);
//        expMenuProvider = new ExpressionContextMenuProvider(project, diagram);
//        connMenuProvider = new ConnectionContextMenuProvider(project, diagram);
//        diagramMenuProvider = new DiagramContextMenuProvider(project, diagram);
    }

    public JPopupMenu buildContextMenu(Object selected) {
        EditPart part = (EditPart)selected;
        // Add standard action groups to the menu
        JPopupMenu menu = new JPopupMenu();
//        if(part instanceof DecisionTreeNodeConnectionPart) {
//            connMenuProvider.buildContextMenu(menu, (DecisionTreeNodeConnectionPart)part);
//        }else if(part instanceof DecisionTreeNodePart) {
//            nodeMenuProvider.buildContextMenu(menu, (DecisionTreeNodePart)part);
//        }else if(part instanceof BaseExpressionPart) {
//            expMenuProvider.buildContextMenu(menu, (BaseExpressionPart)part);
//        }else if(part instanceof DecisionTreeDiagramPart) {
//            diagramMenuProvider.buildContextMenu(menu, (DecisionTreeDiagramPart)part);
//        }

        return menu;
    }

}
