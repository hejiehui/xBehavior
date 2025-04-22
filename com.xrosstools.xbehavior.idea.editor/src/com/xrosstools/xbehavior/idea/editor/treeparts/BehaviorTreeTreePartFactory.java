package com.xrosstools.xbehavior.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BehaviorTreeTreePartFactory implements EditPartFactory {
    public EditPart createEditPart(EditPart parent, Object model) {
        AbstractTreeEditPart part = null;
        if(model == null)
            return part;

        if(model instanceof BehaviorTreeDiagram)
            part = new BehaviorTreeDiagramTreePart();
        else if(model instanceof BehaviorNode)
            part = new BehaviorNodeTreePart();

        part.setModel(model);
        return part;
    }
}
