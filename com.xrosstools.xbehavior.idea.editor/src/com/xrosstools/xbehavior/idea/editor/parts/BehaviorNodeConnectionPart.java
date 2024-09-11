package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.figures.ArrowDecoration;
import com.xrosstools.idea.gef.figures.ColorConstants;
import com.xrosstools.idea.gef.figures.Connection;
import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.parts.AbstractNodeConnectionEditPart;
import com.xrosstools.idea.gef.routers.BendpointConnectionRouter;
import com.xrosstools.idea.gef.routers.LightningRoute;

public class BehaviorNodeConnectionPart extends AbstractNodeConnectionEditPart {
    protected Figure createFigure() {
        Connection conn = new Connection();
        conn.setTargetDecoration(new ArrowDecoration());
        conn.setRouter(new LightningRoute(true));
        conn.setForegroundColor(ColorConstants.black);

        return conn;
    }
}
