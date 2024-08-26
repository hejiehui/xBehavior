package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.figures.ArrowDecoration;
import com.xrosstools.idea.gef.figures.ColorConstants;
import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractNodeConnectionEditPart;
import com.xrosstools.idea.gef.routers.BendpointConnectionRouter;

public class BehaviorNodeConnectionPart extends AbstractNodeConnectionEditPart {
    protected Figure createFigure() {
        Connection conn = new Connection();
        conn.setTargetDecoration(new ArrowDecoration());
        conn.setRouter(new BendpointConnectionRouter());
        conn.setForegroundColor(ColorConstants.black);

        return conn;
    }
}
