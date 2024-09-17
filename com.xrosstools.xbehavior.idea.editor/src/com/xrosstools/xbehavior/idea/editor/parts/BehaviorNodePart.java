package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.figures.AbstractAnchor;
import com.xrosstools.idea.gef.figures.ChopboxAnchor;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.MidpointAnchor;
import com.xrosstools.idea.gef.parts.AbstractConnectionEditPart;
import com.xrosstools.idea.gef.parts.AbstractGraphicalEditPart;
import com.xrosstools.idea.gef.parts.AbstractNodeEditPart;
import com.xrosstools.idea.gef.parts.EditPolicy;
import com.xrosstools.idea.gef.policies.NodeEditPolicy;
import com.xrosstools.xbehavior.idea.editor.figures.BehaviorNodeFigure;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;
import com.xrosstools.xbehavior.idea.editor.model.PropertyConstants;
import com.xrosstools.xbehavior.idea.editor.policies.BTNodeEditPolicy;

import java.awt.*;

public class BehaviorNodePart extends AbstractNodeEditPart {
    public BehaviorNode getBehaviorNode() {
        return (BehaviorNode)getModel();
    }

    protected Figure createFigure() {
        return new BehaviorNodeFigure(getBehaviorNode().getType());
    }

    public AbstractAnchor getSourceConnectionAnchor(AbstractConnectionEditPart connectionEditPart) {
        return new MidpointAnchor(this.getFigure(), true);
    }

    public AbstractAnchor getTargetConnectionAnchor(AbstractConnectionEditPart connectionEditPart) {
        return new MidpointAnchor(this.getFigure(), true);
    }

    @Override
    protected EditPolicy createEditPolicy() {
        return new BTNodeEditPolicy((BehaviorTreeDiagram) getParent().getModel());
    }

    protected void refreshVisuals() {
        BehaviorNode node = getBehaviorNode();
        BehaviorNodeFigure figure = (BehaviorNodeFigure)getFigure();

        figure.setText(node.getName());

        Point loc = node.getLocation();
        Dimension size = new Dimension(-1, PropertyConstants.DEFAULT_HEIGHT);
        Rectangle rectangle = new Rectangle(loc, size);
        ((AbstractGraphicalEditPart)getParent()).setLayoutConstraint(this, getFigure(), rectangle);

        node.getSize().setSize(new Dimension(figure.getPreferredSize()));
    }
}
