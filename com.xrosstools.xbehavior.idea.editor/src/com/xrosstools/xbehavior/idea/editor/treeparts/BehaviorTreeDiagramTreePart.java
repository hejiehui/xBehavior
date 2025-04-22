package com.xrosstools.xbehavior.idea.editor.treeparts;

import com.xrosstools.idea.gef.parts.AbstractTreeEditPart;
import com.xrosstools.xbehavior.idea.editor.XbehaviorIcons;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class BehaviorTreeDiagramTreePart extends AbstractTreeEditPart implements PropertyChangeListener {
    @Override
    public Icon getImage() {
        return XbehaviorIcons.TREE;
    }

    public List getModelChildren() {
        List children = new ArrayList();
        children.addAll(((BehaviorTreeDiagram )getModel()).getRoots());
        return children;
    }
}
