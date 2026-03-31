package com.xrosstools.xbehavior.idea.editor;

import com.intellij.ide.highlighter.XmlLikeFileType;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class XbehaviorFileType extends XmlLikeFileType {
    public static final String NAME = "Xross Behavior Model File";
    public static final String DESCRIPTION = "Xross Behavior Model File";
    public static final String EXTENSION = "xbehavior";

    public static final XbehaviorFileType INSTANCE = new XbehaviorFileType();

    public XbehaviorFileType() {
        super(XMLLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return XbehaviorIcons.TREE;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    @Nullable
    @Override
    public String getCharset(@NotNull VirtualFile virtualFile, @NotNull byte[] bytes) {
        return null;
    }
}
