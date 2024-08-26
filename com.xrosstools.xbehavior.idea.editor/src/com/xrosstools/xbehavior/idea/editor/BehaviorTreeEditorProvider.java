package com.xrosstools.xbehavior.idea.editor;

import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.xrosstools.idea.gef.AbstractDiagramEditorProvider;
import com.xrosstools.idea.gef.PanelContentProvider;
import org.jetbrains.annotations.NotNull;

public class BehaviorTreeEditorProvider extends AbstractDiagramEditorProvider {
    public static final String TREE = "tree";
    public static final String NODE = "node";
    public static final String CONNECTION = "connection";

    @Override
    public FileType getFileType() {
        return XbehaviorFileType.INSTANCE;
    }

    @Override
    public String getExtention() {
        return XbehaviorFileType.EXTENSION;
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return "Xross Behavior Tree Edtitor";
    }

    @Override
    public PanelContentProvider createPanelContentProvider(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new BehaviorTreePanelContentProvider(project, virtualFile);
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }
}
