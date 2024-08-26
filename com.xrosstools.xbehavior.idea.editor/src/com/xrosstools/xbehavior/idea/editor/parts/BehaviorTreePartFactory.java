package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.EditPart;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNodeConnection;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

public class BehaviorTreePartFactory implements EditPartFactory {
    public EditPart createEditPart(EditPart parent, Object model) {
        AbstractGraphicalEditPart part = null;

        if(model == null)
            part = null;

        if(model instanceof BehaviorTreeDiagram)
            part = new BehaviorTreeDiagramPart();
        else if(model instanceof BehaviorNode)
            part = new BehaviorNodePart();
        else if(model instanceof BehaviorNodeConnection)
            part = new BehaviorNodeConnectionPart();

        part.setModel(model);
        return part;
    }
}
