package com.xrosstools.xbehavior.idea.editor.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.actions.AbstractCodeGenerator;
import com.xrosstools.idea.gef.actions.Action;
import com.xrosstools.idea.gef.actions.CodeDisplayer;
import com.xrosstools.idea.gef.commands.Command;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNode;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorTreeDiagram;

import java.awt.event.ActionEvent;
import java.time.ZonedDateTime;

import static com.xrosstools.idea.gef.actions.CodeGenHelper.*;

public class GenerateHelperAction extends AbstractCodeGenerator implements BehaviorTreeMessage {
    private static final String CREATE_MACHINE =
            "    public static class %s {\n" +//Behavior tree name, constants
            "%s" +
            "        public static Behavior create() {\n" +   //Behavior tree
            "            return load().create(\"%s\");\n" +
            "        }\n" +
            "    }\n\n";

    private static final String MACHINE_COMMENTS =
            "    //%s\n";

    private static final String INVALID_BEHAVIOR_NAME =
            "    /*  Error!!! No. %d behavior tree's name is empty. */\n";

    private Project project;
    private VirtualFile file;
    private BehaviorTreeDiagram diagram;

    public GenerateHelperAction(Project project, VirtualFile file){
        super(project, "Generate model factory");
        this.project = project;
        this.file = file;
    }

    public void setDiagram(BehaviorTreeDiagram diagram) {
        this.diagram = diagram;
    }

    public String getDefaultFileName() {
        return fileToClassName(file.getNameWithoutExtension());
    }

    @Override
    public String getContent(String packageName, String fileName) {
        StringBuffer codeBuf = getTemplate("/templates/HelperTemplate.txt", this.getClass());
        replace(codeBuf, "!PACKAGE!", packageName);
        replace(codeBuf, "!DESCRIPTION!", getValue(diagram.getDescription()));
        replace(codeBuf, "!LAST_GENERATE_TIME!", ZonedDateTime.now().toString());
        replace(codeBuf, "!TEST_CLASS!", fileName);
        replace(codeBuf, "!MODEL_PATH!", findResourcesPath(project, file));

        replace(codeBuf, "!TREE_DEFINITIONS!", "\n" + generateBody().toString());

        return codeBuf.toString();
    }

    private StringBuffer generateBody() {
        StringBuffer constants = new StringBuffer();

        int i = 0;
        for(BehaviorNode tree: diagram.getRoots()) {
            i++;
            StringBuffer buf = new StringBuffer();

            appendDesc(buf, MACHINE_COMMENTS, tree.getDescription());
            if(isEmpty(tree.getName())) {
                constants.append(String.format(INVALID_BEHAVIOR_NAME, i));
            } else {
                String createMachine = String.format(CREATE_MACHINE, toClassName(tree.getName()), buf.toString(), tree.getName());
                constants.append(createMachine);
            }
        }

        return constants;
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public Command createCommand() {
        return null;
    }
}
