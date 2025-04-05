package com.xrosstools.xbehavior.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xbehavior.idea.editor.actions.BehaviorTreeMessage;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import javax.swing.*;

public class BehaviorTreeOutlineContextMenuProvider extends ContextMenuProvider implements BehaviorTreeMessage {
    private Project project;
    private BehaviorTreeDiagram diagram;

    public BehaviorTreeOutlineContextMenuProvider(Project project) {
        this.project = project;
    }

    public void setDiagram(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }


    public JPopupMenu buildContextMenu(Object selected) {
        AbstractTreeEditPart editPart = (AbstractTreeEditPart )selected;
        JPopupMenu menu = new JPopupMenu();
        return menu;
    }
}
