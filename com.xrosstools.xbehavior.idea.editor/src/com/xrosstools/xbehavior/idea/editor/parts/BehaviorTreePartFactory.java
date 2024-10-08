package com.xrosstools.xbehavior.idea.editor.parts;

import com.intellij.openapi.project.Project;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xbehavior.idea.editor.model.*;

public class BehaviorTreePartFactory implements EditPartFactory {
    private Project project;
    private BehaviorTreeDiagram diagram;

    public BehaviorTreePartFactory(Project project) {
        this.project = project;
    }

    public EditPart createEditPart(EditPart parent, Object model) {
        AbstractGraphicalEditPart part = null;

        if(model == null)
            part = null;

        if(model instanceof BehaviorTreeDiagram) {
            part = new BehaviorTreeDiagramPart();
            diagram = (BehaviorTreeDiagram) model;
        } else if(model instanceof ActionNode) {
            part = new ActionNodePart(project);
        } else if(model instanceof ConditionNode) {
            part = new ConditionNodePart(project);
        } else if(model instanceof SubtreeNode) {
            part = new SubtreeNodePart();
            ((SubtreeNode)model).setDiagram(diagram);
        } else if(model instanceof BehaviorNode) {
            part = new BehaviorNodePart();
        } else if(model instanceof BehaviorNodeConnection)
            part = new BehaviorNodeConnectionPart();

        part.setModel(model);
        return part;
    }
}
