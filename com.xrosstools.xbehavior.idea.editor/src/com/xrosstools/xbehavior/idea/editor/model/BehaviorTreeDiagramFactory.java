package com.xrosstools.xbehavior.idea.editor.model;

import com.xrosstools.idea.gef.util.PropertySourceXmlAccessor;
import com.xrosstools.idea.gef.util.PropertySourceXmlRegister;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.xrosstools.idea.gef.util.XmlHelper.*;

public class BehaviorTreeDiagramFactory implements PropertyConstants {
    private static final String BEHAVIOR_TREE = "behavior_tree";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private static final String NODES = "nodes";

    private static final String CONNECTIONS = "connections";
    private static final String CONNECTION = "connection";
    private static final String SOURCE_INDEX = "source_index";
    private static final String TARGET_INDEX = "target_index";

    private PropertySourceXmlRegister<BehaviorNodeType> register = new PropertySourceXmlRegister<>();

    public BehaviorTreeDiagramFactory() {
        register.register(BehaviorNodeType.ACTION).attributes(PROP_NAME, PROP_IMPLEMENTATION).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.CONDITION).attributes(PROP_NAME, PROP_IMPLEMENTATION, PROP_MODE).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.PARALLEL).attributes(PROP_NAME, PROP_COUNT, PROP_MODE).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.REPEAT).attributes(PROP_NAME, PROP_COUNT, PROP_MODE, PROP_TIME_UNIT).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.RETRY).attributes(PROP_NAME, PROP_COUNT, PROP_MODE, PROP_TIME_UNIT).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.WAIT).attributes(PROP_NAME, PROP_TIMEOUT, PROP_TIME_UNIT).nodes(PROP_DESCRIPTION);

        PropertySourceXmlAccessor accessor = new PropertySourceXmlAccessor().attributes(PROP_NAME).nodes(PROP_DESCRIPTION);

        register.register(BehaviorNodeType.SEQUENCE, accessor);

        register.register(BehaviorNodeType.SELECTOR, accessor);

        register.register(BehaviorNodeType.INVERTER, accessor);

        register.register(BehaviorNodeType.FORCE_SUCCESS, accessor);

        register.register(BehaviorNodeType.FORCE_FAILURE, accessor);
    }

    public BehaviorTreeDiagram getFromXML(Document doc) {
        BehaviorTreeDiagram diagram = new BehaviorTreeDiagram();
        Node root = doc.getElementsByTagName(BEHAVIOR_TREE).item(0);

        diagram.setDescription(getChildNodeText(root, DESCRIPTION));

        List<BehaviorNode> nodes = createNodes(doc, diagram);
        linkNode(doc, nodes);
        diagram.getChildren().addAll(nodes);


        return diagram;
    }

    private List<BehaviorNode> createNodes(Document doc, BehaviorTreeDiagram diagram) {
        if (doc.getElementsByTagName(NODES).getLength() == 0)
            return Collections.emptyList();

        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));

        List<BehaviorNode> nodes = new ArrayList<>();
        for (int i = 0; i < nodeNodes.size(); i++) {
            BehaviorNode node = null;
            Node nodeNode = nodeNodes.get(i);
            BehaviorNodeType type = BehaviorNodeType.findByNodeName(nodeNode.getNodeName());

            try {
                node = (BehaviorNode)type.getTypeClass().newInstance();
            } catch (Throwable e) {
                throw new IllegalArgumentException(type.getTypeClass().getCanonicalName());
            }

            if(register.contains(type))
                register.readProperties(type, nodeNode, node);
            else
                throw new IllegalArgumentException(type.name());

            nodes.add(node);
        }

        return nodes;
    }

    private void linkNode(Document doc, List<BehaviorNode> nodes) {
        if (doc.getElementsByTagName(CONNECTIONS).getLength() == 0)
            return;

        List<Node> connectionNodes = getValidChildNodes(doc.getElementsByTagName(CONNECTIONS).item(0));
        for (int i = 0; i < connectionNodes.size(); i++) {
            Node connectionNode = connectionNodes.get(i);

            BehaviorNode parent = nodes.get(getIntAttribute(connectionNode, SOURCE_INDEX));
            BehaviorNode child = nodes.get(getIntAttribute(connectionNode, TARGET_INDEX));
            BehaviorNodeConnection conn = new BehaviorNodeConnection(parent, child);
        }
    }

    public Document convertToXML(BehaviorTreeDiagram diagram) {
        Document doc = null;
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element root = doc.createElement(BEHAVIOR_TREE);
            doc.appendChild(root);

            root.appendChild(createNode(doc, DESCRIPTION, diagram.getDescription()));

            Element nodes = createNode(doc, root, NODES);
            writeNodes(doc, nodes, diagram.getChildren());

            Element connections = createNode(doc, root, CONNECTIONS);
            writeConnections(doc, connections, diagram.getChildren());

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeNodes(Document doc, Element nodes, List<BehaviorNode> behaviorNodes) {
        for (BehaviorNode behaviorNode : behaviorNodes) {
            String nodeName = behaviorNode.getType().name().toLowerCase();
            Element node = createNode(doc, nodes, nodeName);
            BehaviorNodeType type = behaviorNode.getType();

            if(register.contains(type))
                register.writeProperties(doc, type, node, behaviorNode);
            else
                throw new IllegalArgumentException(type.name());
        }
    }

    private void writeConnections(Document doc, Element connectionsNode, List<BehaviorNode> nodes) {
        for (BehaviorNode behaviorNode : nodes) {
            for(BehaviorNodeConnection conn: behaviorNode.getOutputs()) {
                Element connNode = createNode(doc, connectionsNode, CONNECTION);
                connNode.setAttribute(SOURCE_INDEX, String.valueOf(nodes.indexOf(behaviorNode)));
                connNode.setAttribute(TARGET_INDEX, String.valueOf(nodes.indexOf(conn.getTarget())));
            }
        }
    }

    public static String format(Document doc) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        TransformerFactory tFactory =TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(out);
        transformer.transform(source, result);

        // To make well formated document
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(new ByteArrayInputStream(out.toByteArray()));

        XMLWriter writer = null;
        StringWriter stringWriter = new StringWriter();
        OutputFormat format = new OutputFormat(" ", true);
        writer = new XMLWriter(stringWriter, format);
        writer.write(document);
        writer.flush();
        return stringWriter.toString();
    }
}