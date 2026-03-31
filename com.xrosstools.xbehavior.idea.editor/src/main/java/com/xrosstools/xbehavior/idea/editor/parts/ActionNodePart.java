package com.xrosstools.xbehavior.idea.editor.parts;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.actions.ImplementationUtil;
import com.xrosstools.xbehavior.idea.editor.model.ActionNode;

public class ActionNodePart extends BehaviorNodePart {
    private Project project;
    public ActionNodePart(Project project) {
        this.project = project;
    }

    @Override
    public void performAction() {
        ActionNode node = (ActionNode)getModel();

        if(node.getImplementation() != null && node.getImplementation().trim().length() > 0)
        ImplementationUtil.openImpl(project, node.getImplementation());
    }
}
