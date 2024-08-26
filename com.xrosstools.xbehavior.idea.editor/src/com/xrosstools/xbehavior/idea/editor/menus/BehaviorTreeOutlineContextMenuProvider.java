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
//    private NamedElementContainerContextMenuProvider namedElementContainerProvider;
//    private NamedElementContextMenuProvider namedElementContextMenuProvider;

    public BehaviorTreeOutlineContextMenuProvider(Project project, BehaviorTreeDiagram diagram) {
        this.project = project;
        this.diagram = diagram;
//        namedElementContextMenuProvider = new NamedElementContextMenuProvider(project, diagram);
//        namedElementContainerProvider = new NamedElementContainerContextMenuProvider(project, diagram);
    }

    public JPopupMenu buildContextMenu(Object selected) {
        AbstractTreeEditPart editPart = (AbstractTreeEditPart )selected;
        JPopupMenu menu = new JPopupMenu();
//        if(editPart instanceof NamedElementContainerTreePart) {
//            namedElementContainerProvider.buildContextMenu(menu, (NamedElementContainerTreePart)editPart);
//        }else if(editPart instanceof NamedElementTreePart) {
//            namedElementContextMenuProvider.buildContextMenu(menu, (NamedElementTreePart)editPart);
//        }
        return menu;
    }
}
