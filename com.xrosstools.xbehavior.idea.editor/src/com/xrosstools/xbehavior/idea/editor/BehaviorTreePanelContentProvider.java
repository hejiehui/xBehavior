package com.xrosstools.xbehavior.idea.editor;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractPanelContentProvider;
import com.xrosstools.idea.gef.ContextMenuProvider;
import com.xrosstools.idea.gef.parts.EditPartFactory;
import com.xrosstools.xbehavior.idea.editor.actions.BehaviorTreeMessage;
import com.xrosstools.xbehavior.idea.editor.actions.GenerateHelperAction;
import com.xrosstools.xbehavior.idea.editor.layout.LayoutAlgorithm;
import com.xrosstools.xbehavior.idea.editor.menus.BehaviorTreeContextMenuProvider;
import com.xrosstools.xbehavior.idea.editor.menus.BehaviorTreeOutlineContextMenuProvider;
import com.xrosstools.xbehavior.idea.editor.model.*;
import com.xrosstools.xbehavior.idea.editor.parts.BehaviorTreePartFactory;
import com.xrosstools.xbehavior.idea.editor.treeparts.BehaviorTreeTreePartFactory;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;

public class BehaviorTreePanelContentProvider extends AbstractPanelContentProvider<BehaviorTreeDiagram> implements XbehaviorIcons, BehaviorTreeMessage {
    private Project project;
    private VirtualFile virtualFile;
    private BehaviorTreeDiagram diagram;
    private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();

    private BehaviorTreeDiagramFactory factory = new BehaviorTreeDiagramFactory();

    public BehaviorTreePanelContentProvider(Project project, VirtualFile virtualFile) {
        super(virtualFile);
        this.project = project;
        this.virtualFile = virtualFile;
    }

    @Override
    public BehaviorTreeDiagram getContent() throws Exception {
        diagram = factory.getFromXML(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(virtualFile.getInputStream()));
        return diagram;
    }

    @Override
    public void saveContent() throws Exception {
        String contentStr = factory.format(factory.convertToXML(diagram));
        virtualFile.setBinaryContent(contentStr.getBytes(virtualFile.getCharset()));
    }

    @Override
    public ContextMenuProvider getContextMenuProvider() {
        return new BehaviorTreeContextMenuProvider(project, diagram);
    }

    @Override
    public ContextMenuProvider getOutlineContextMenuProvider() {
        return new BehaviorTreeOutlineContextMenuProvider(project, diagram);
    }

    @Override
    public void buildPalette(JPanel palette) {
        palette.add(createConnectionButton());

        for(BehaviorNodeType type: BehaviorNodeType.values()) {
            palette.add(createNodeButton(type.getDisplayName(), type.getTypeIcon(), type.getTypeClass()));
        }

        palette.add(createPaletteButton(new GenerateHelperAction(project, virtualFile, diagram), GENERATE_HELPER_ICON, GENERATE_HELPER));
    }

    private JButton createConnectionButton() {
        JButton btn = new JButton("Connection", CONNECTION);
        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> createConnection(new BehaviorNodeConnection()));
        return btn;
    }

    private JButton createNodeButton(String name, Icon icon, final Class nodeClass) {
        JButton btn = new JButton(name, icon);
//        btn.setPreferredSize(new Dimension(100, 50));
        btn.setContentAreaFilled(false);
        btn.addActionListener(e -> {
            try {
                createModel(nodeClass.newInstance());
            }catch (Throwable ex) {
                throw new IllegalArgumentException(nodeClass.getCanonicalName());
            }
        });
        return btn;
    }

    @Override
    public ActionGroup createToolbar() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 1), ALIGN_BOTTOM, ALIGN_BOTTOM_MSG));
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 0.5f), ALIGN_MIDDLE, ALIGN_MIDDLE_MSG));
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, true, 0), ALIGN_TOP, ALIGN_TOP_MSG));
//        actionGroup.addSeparator();
//
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 0), ALIGN_LEFT, ALIGN_LEFT_MSG));
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 0.5f), ALIGN_CENTER, ALIGN_CENTER_MSG));
//        actionGroup.add(createToolbarAction(new DecisionTreeLayoutAction(diagram, false, 1), ALIGN_RIGHT, ALIGN_RIGHT_MSG));

        return actionGroup;
    }

    @Override
    public EditPartFactory createEditPartFactory() {
        return new BehaviorTreePartFactory(project);
    }

    @Override
    public EditPartFactory createTreePartFactory() {
        return new BehaviorTreeTreePartFactory();
    }

    public void preBuildRoot(){
        layoutAlgorithm.layout(diagram);
    }

    public void postBuildRoot(){
        layoutAlgorithm.layout(diagram);
        getEditorPanel().refreshVisual();

        layoutAlgorithm.layout(diagram);
        getEditorPanel().refreshVisual();
    }

}
