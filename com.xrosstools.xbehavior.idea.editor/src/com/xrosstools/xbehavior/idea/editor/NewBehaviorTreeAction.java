package com.xrosstools.xbehavior.idea.editor;

import com.xrosstools.idea.gef.DefaultNewModelFileAction;

public class NewBehaviorTreeAction extends DefaultNewModelFileAction {
    public NewBehaviorTreeAction() {
        super("Xross Behavior Tree", XbehaviorFileType.EXTENSION, XbehaviorIcons.TREE, "new_xbehavior_file", "/templates/template.xbehavior");
    }
}
