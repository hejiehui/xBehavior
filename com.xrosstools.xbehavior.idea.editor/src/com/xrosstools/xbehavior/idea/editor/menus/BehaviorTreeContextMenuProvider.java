package com.xrosstools.xbehavior.idea.editor.menus;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.actions.CodeGenHelper;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.idea.gef.actions.codegen.SimpleCodeGenAction;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.xbehavior.idea.editor.actions.BehaviorTreeMessage;
import com.xrosstools.xbehavior.idea.editor.model.*;
import com.xrosstools.xbehavior.idea.editor.parts.BehaviorNodePart;

import javax.swing.*;

public class BehaviorTreeContextMenuProvider extends ContextMenuProvider implements BehaviorTreeMessage, PropertyConstants {
    private static final String ACTION_CODE_TEMPLATE = "/templates/ActionTemplate.txt";
    private static final String CONDITION_CODE_TEMPLATE = "/templates/ConditionTemplate.txt";

    private static final String ACTION_NAME_TEMPLATE = "%s Action";
    private static final String CONDITION_NAME_TEMPLATE = "%s Condition";

    private Project project;
    private BehaviorTreeDiagram diagram;
    private String actionCodeTemplate;
    private String conditionCodeTemplate;
    public BehaviorTreeContextMenuProvider(Project project) {
        this.project = project;
        actionCodeTemplate = CodeGenHelper.getTemplate(ACTION_CODE_TEMPLATE, getClass()).toString();
        conditionCodeTemplate = CodeGenHelper.getTemplate(CONDITION_CODE_TEMPLATE, getClass()).toString();
    }

    public void setDiagram(BehaviorTreeDiagram diagram) {
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
            menu.add(getCodGenMenu(actionCodeTemplate, ACTION_NAME_TEMPLATE, node));
            ImplementationUtil.buildImplementationMenu(project, menu, node, PROP_IMPLEMENTATION, false);
            return;
        }

        if(node.getType() == BehaviorNodeType.CONDITION && ((ConditionNode)node).getMode() == ConditionNode.Mode.CALLBACK) {
            menu.add(getCodGenMenu(conditionCodeTemplate, CONDITION_NAME_TEMPLATE, node));
            ImplementationUtil.buildImplementationMenu(project, menu, node, PROP_IMPLEMENTATION, false);
        }
    }

    private JMenuItem getCodGenMenu(String codeTemplate, String nameTemplate, BehaviorNode node) {
        String defaultClassName = CodeGenHelper.toClassName(String.format(nameTemplate, node.getName()));
        return createItem(new SimpleCodeGenAction(project, codeTemplate, node, PROP_IMPLEMENTATION, defaultClassName));
    }
}
