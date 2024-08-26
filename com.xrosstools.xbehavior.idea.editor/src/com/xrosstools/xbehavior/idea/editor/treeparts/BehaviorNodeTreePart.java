package com.xrosstools.xbehavior.idea.editor.treeparts;

import com.xrosstools.idea.gef.treeparts.NodeTreePart;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;

import javax.swing.*;

public class BehaviorNodeTreePart extends NodeTreePart {
    public BehaviorNodeTreePart(Object model) {
        super(model, true);
    }

    public BehaviorNode getBehaviorNode() {
        return (BehaviorNode)getModel();
    }

    public String getText() {
        return getBehaviorNode().getTreeDisplayText();
    }

    @Override
    public Icon getImage() {
        return getBehaviorNode().getType().getTypeIcon();
    }
}