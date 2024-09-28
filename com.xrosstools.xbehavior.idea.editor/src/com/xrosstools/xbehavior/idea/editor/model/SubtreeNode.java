package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.ArrayList;
import java.util.List;

public class SubtreeNode extends BehaviorNode {
    private BehaviorTreeDiagram diagram;
    private PropertyEntry<String> subtree = enumProperty(PROP_SUBTREE, "", ()-> getCandidates());

    public SubtreeNode() {
        super(BehaviorNodeType.SUBTREE);
        setOutputLimit(0);
        register(subtree);
    }

    public void setDiagram(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    private String[] getCandidates() {

        List<String> names = new ArrayList<>();
        for(BehaviorNode node: diagram.getRoots()){
            if(node.getName() == null || node.getName().trim().isEmpty())
                continue;
            names.add(node.getName());
        }
        return names.toArray(new String[names.size()]);
    }
}
