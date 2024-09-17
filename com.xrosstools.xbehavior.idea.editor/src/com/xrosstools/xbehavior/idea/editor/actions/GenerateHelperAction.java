package com.xrosstools.xbehavior.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import java.awt.event.ActionEvent;
import java.time.ZonedDateTime;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateHelperAction extends Action implements BehaviorTreeMessage {
    private static final String CREATE_MACHINE =
            "    public static class %s {\n" +//Behavior tree name, constants
            "%s" +
            "        public static Behavior create() throws Exception {\n" +   //Behavior tree
            "            return load().create(\"%s\");\n" +
            "        }\n" +
            "    }\n\n";

    private static final String MACHINE_COMMENTS =
            "    //%s\n";

    private Project project;
    private VirtualFile file;
    private BehaviorTreeDiagram diagram;

    public GenerateHelperAction(Project project, VirtualFile file, BehaviorTreeDiagram diagram){
        this.project = project;
        this.file = file;
        this.diagram = diagram;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuffer codeBuf = getTemplate("/templates/HelperTemplate.txt", this.getClass());
        replace(codeBuf, "!PACKAGE!", getValue("com.xrosstools.xbehavior"));
        replace(codeBuf, "!DESCRIPTION!", getValue(diagram.getDescription()));
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", toClassName(file.getName()));
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));

        replace(codeBuf, "!TREE_DEFINITIONS!", "\n" + generateBody().toString());

        new CodeDisplayer("Generated helper", codeBuf.toString()).show();
    }

    private StringBuffer generateBody() {
        StringBuffer constants = new StringBuffer();

        for(BehaviorNode tree: diagram.getRoots()) {
            StringBuffer buf = new StringBuffer();

            appendDesc(buf, MACHINE_COMMENTS, tree.getDescription());
            String createMachine = String.format(CREATE_MACHINE, toClassName(tree.getName()), buf.toString(), tree.getName());
            constants.append(createMachine);
        }

        return constants;
    }

    public Command createCommand() {
        return null;
    }
}
