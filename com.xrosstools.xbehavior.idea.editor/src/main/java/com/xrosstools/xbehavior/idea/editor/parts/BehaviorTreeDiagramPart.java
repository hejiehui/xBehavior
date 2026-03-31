package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.FreeformLayout;
import com.xrosstools.idea.gef.parts.AbstractNodeContainerEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.xbehavior.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;
import com.xrosstools.xbehavior.idea.editor.policies.BTNodeContainerEditPolicy;

import java.beans.PropertyChangeEvent;

public class BehaviorTreeDiagramPart extends AbstractNodeContainerEditPart {
    private LayoutAlgorithm layout = new LayoutAlgorithm();

    protected Figure createFigure() {
        Figure figure = new Figure();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
    }

    protected EditPolicy createEditPolicy() {
        return new BTNodeContainerEditPolicy((BehaviorTreeDiagram) getModel());
    }


    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if (BehaviorTreeDiagram.PROP_LAYOUT.equals(prop)){
            layout.layout((BehaviorTreeDiagram) getModel());
        }
        super.propertyChange(evt);
    }
}
