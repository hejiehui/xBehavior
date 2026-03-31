package com.xrosstools.xbehavior.idea.editor.references;

import com.intellij.patterns.XmlAttributeValuePattern;
import com.intellij.patterns.XmlPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.xrosstools.xbehavior.idea.editor.XbehaviorFileType;
import com.xrosstools.xbehavior.idea.editor.model.BehaviorNodeType;
import com.xrosstools.xbehavior.idea.editor.model.PropertyConstants;

import static com.intellij.patterns.StandardPatterns.string;
import static com.intellij.patterns.XmlPatterns.xmlFile;

public class XbehaviorReferenceContributor extends PsiReferenceContributor implements PropertyConstants {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registerAttr(registrar, BehaviorNodeType.ACTION.getNodeName(), PROP_IMPLEMENTATION);
        registerAttr(registrar, BehaviorNodeType.CONDITION.getNodeName(), PROP_IMPLEMENTATION);
    }

    private void registerAttr(PsiReferenceRegistrar registrar, String parentTagName, String attribute) {
        XmlAttributeValuePattern pattern = XmlPatterns.xmlAttributeValue()
                .withParent(
                        XmlPatterns.xmlAttribute().withName(attribute)
                                .withParent(XmlPatterns.xmlTag().withName(parentTagName)))
                .inFile(xmlFile().withName(string().endsWith("." + XbehaviorFileType.EXTENSION)));

        registrar.registerReferenceProvider(pattern, new AttributeClassReferenceProvider());
    }
}
