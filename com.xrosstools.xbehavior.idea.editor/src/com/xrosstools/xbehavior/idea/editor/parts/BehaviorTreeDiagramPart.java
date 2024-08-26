package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.idea.gef.figures.Figure;
import com.xrosstools.idea.gef.figures.FreeformLayout;
import com.xrosstools.idea.gef.parts.AbstractDiagramEditPart;
import com.xrosstools.xbehavior.idea.editor.layout.LayoutAlgorithm;

public class BehaviorTreeDiagramPart extends AbstractDiagramEditPart {
    private LayoutAlgorithm layout = new LayoutAlgorithm();

    protected Figure createFigure() {
        Figure figure = new Figure();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
    }
}
