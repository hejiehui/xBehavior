package com.xrosstools.xbehavior.idea.editor.parts;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.xbehavior.idea.editor.model.ConditionNode;

public class ConditionNodePart extends BehaviorNodePart {
    private Project project;
    public ConditionNodePart(Project project) {
        this.project = project;
    }

    @Override
    public void performAction() {
        ConditionNode node = (ConditionNode)getModel();

        if(node.getMode() == ConditionNode.Mode.CALLBACK && node.getImplementation() != null && node.getImplementation().trim().length() > 0)
            ImplementationUtil.openImpl(project, node.getImplementation());
    }
}
