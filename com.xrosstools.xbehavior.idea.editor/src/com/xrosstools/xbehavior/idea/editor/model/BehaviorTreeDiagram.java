package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.model.Diagram;
import com.xrosstools.idea.gef.util.PropertyEntry;

import java.util.ArrayList;
import java.util.List;

public class BehaviorTreeDiagram extends Diagram<BehaviorNode> implements PropertyConstants {
    private PropertyEntry<String> description = stringProperty(PROP_DESCRIPTION);
    private PropertyEntry<String> evaluator = stringProperty(PROP_EVALUATOR, "default");


    private boolean isHorizantal;
    private int verticalSpace = 50;
    private int horizantalSpace = 50;
    private float alignment = 0.5f;
    private int nodeWidth = 100;
    private int nodeHeight = 50;

    public BehaviorTreeDiagram(){
        register(description);
        register(evaluator);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String _description) {
        description.set(_description);
    }

    public String getEvaluator() {
        return evaluator.get();
    }

    public void setEvaluator(String _evaluator) {
        evaluator.set(_evaluator);
    }

    public List<BehaviorNode> getRoots() {
        List<BehaviorNode> roots = new ArrayList<>();

        for(BehaviorNode node: getChildren())
            if(node.getInputs().size() == 0)
                roots.add(node);

        return roots;
    }

    public int getVerticalSpace() {
        return verticalSpace;
    }

    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
        fireLayoutChange();
    }

    public int getHorizantalSpace() {
        return horizantalSpace;
    }

    public void setHorizantalSpace(int horizantalSpace) {
        this.horizantalSpace = horizantalSpace;
        fireLayoutChange();
    }

    public float getAlignment() {
        return alignment;
    }

    public void setAlignment(float alignment) {
        this.alignment = alignment;
        fireLayoutChange();
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
        fireLayoutChange();
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(int nodeHeight) {
        this.nodeHeight = nodeHeight;
        fireLayoutChange();
    }

    public boolean isHorizantal() {
        return isHorizantal;
    }

    public void setHorizantal(boolean isHorizantal) {
        this.isHorizantal = isHorizantal;
        fireLayoutChange();
    }

    public void fireLayoutChange(){
      firePropertyChange(PROP_LAYOUT, -1, 0);
    }
}
