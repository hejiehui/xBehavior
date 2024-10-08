package com.xrosstools.xbehavior.idea.editor.parts;

import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.SubtreeNode;

import java.util.Objects;

public class SubtreeNodePart extends BehaviorNodePart {
    @Override
    public void performAction() {
        SubtreeNode node = (SubtreeNode)getModel();

        if(node.getSubtree() == null)
            return;

        for(BehaviorNode reference: node.getDiagram().getRoots()) {
            if(Objects.equals(reference.getName(), node.getSubtree()))
                getContext().getContentPane().selectModel(reference);
        }
    }
}
