package com.xrosstools.xbehavior.idea.editor.figures;

import com.xrosstools.idea.gef.figures.*;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNodeType;

import javax.swing.*;

public class BehaviorNodeFigure extends RoundedRectangle {
    private Text label;

    public BehaviorNodeFigure(BehaviorNodeType type) {
        ToolbarLayout layout= new ToolbarLayout();
        layout.setHorizontal(false);
        layout.setSpacing(5);
        layout.setStretchMinorAxis(false);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        setLayoutManager(layout);
        this.getInsets().set(5,5,5,5);

        Label typeAndName = new Label();
        typeAndName.setIcon(type.getTypeIcon());
        typeAndName.setText(type.getDisplayName());
        add(typeAndName);

        label = new Text();
        add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }
}
