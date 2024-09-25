package com.xrosstools.xbehavior.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xbehavior.idea.editor.actions.BehaviorTreeMessage;
import com.xrosstools.xbehavior.idea.editor.model.*;
import com.xrosstools.xbehavior.idea.editor.parts.BehaviorNodePart;

import javax.swing.*;

public class BehaviorTreeContextMenuProvider extends ContextMenuProvider implements BehaviorTreeMessage, PropertyConstants {
    private Project project;
    private BehaviorTreeDiagram diagram;
    public BehaviorTreeContextMenuProvider(Project project, BehaviorTreeDiagram diagram) {
        this.project = project;
        this.diagram = diagram;
    }

    public JPopupMenu buildContextMenu(Object selected) {
        EditPart part = (EditPart)selected;
        JPopupMenu menu = new JPopupMenu();
        if(part instanceof BehaviorNodePart) {
            builNodedContextMenu(menu, (BehaviorNodePart)part);
        }

        return menu;
    }

    private void builNodedContextMenu(JPopupMenu menu, BehaviorNodePart part) {
        BehaviorNode node = part.getBehaviorNode();
        if(node.getType() == BehaviorNodeType.ACTION) {
            ImplementationUtil.buildImplementationMenu(project, menu, node, PROP_IMPLEMENTATION, false);
            return;
        }

        if(node.getType() == BehaviorNodeType.CONDITION && ((ConditionNode)node).getMode() == ConditionNode.Mode.CALLBACK) {
            ImplementationUtil.buildImplementationMenu(project, menu, node, PROP_IMPLEMENTATION, false);
        }
    }
}
