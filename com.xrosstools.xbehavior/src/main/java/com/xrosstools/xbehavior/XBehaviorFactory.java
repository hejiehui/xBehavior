package com.xrosstools.xbehavior;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xrosstools.xbehavior.def.BehaviorDef;
import com.xrosstools.xbehavior.def.CompositeDef;
import com.xrosstools.xbehavior.def.DecoratorDef;
import com.xrosstools.xbehavior.def.PropertyConstants;

public class XBehaviorFactory implements PropertyConstants {
    private static final String NODES = "nodes";

    private static final String CONNECTIONS = "connections";
    private static final String SOURCE_INDEX = "source_index";
    private static final String TARGET_INDEX = "target_index";

    private static final ConcurrentHashMap<String, XBehaviorFactory> factories = new ConcurrentHashMap<>();
	private Evaluator evaluator;
	private Map<String, BehaviorDef> treeDefs = new HashMap<>();
	
	public Behavior create(String name) {
		if(!treeDefs.containsKey(name))
			throw new IllegalArgumentException("Can not find behavior: " + name);
		return treeDefs.get(name).create();
	}
    
	public static XBehaviorFactory load(URL url) {
	    String path = url.toString();
	    
	    if(isLoaded(url.toString()))
	        return factories.get(path);
	    
        try {
			return getFactory(path, load(url.openStream()));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * It will first check model file from file path, if it does not exist, it will try classpath then. 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static XBehaviorFactory load(String path) {
        if(isLoaded(path))
            return factories.get(path);
        
		InputStream in;
		File f = new File(path);
		try {
			if(f.exists())
				in = new FileInputStream(f);
			else {
				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				if (classLoader == null) {
					classLoader = XBehaviorFactory.class.getClassLoader();
				}
				in = classLoader.getResource(path).openStream();
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
        
        return getFactory(path, load(in));
	}
	
	private static XBehaviorFactory getFactory(String path, XBehaviorFactory factory) {
		XBehaviorFactory oldFactory = factories.putIfAbsent(path, factory);        
        return oldFactory == null ? factory : oldFactory;
	}
	
	public static XBehaviorFactory load(InputStream in) {
		XBehaviorFactory factory = null;
		try{
			Document doc= DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in);
			factory = getFromDocument(doc);
		} catch(Throwable e) {
			throw new IllegalStateException(e);
		}finally {
			try{
				if(in != null)
					in.close();
			}catch(Throwable e1){
			}
		}
		return factory;
	}
	
	private static boolean isLoaded(String path) {
	    return factories.containsKey(path);
	}
    
	private static XBehaviorFactory getFromDocument(Document doc) throws Exception {
		XBehaviorFactory factory = new XBehaviorFactory();
		factory.readDiagram(doc);
		return factory;
	}

	private Node nodeNode;
	private String get(String attributeName){
		return getAttribute(nodeNode, attributeName);
	}
	
	private void readDiagram(Document doc) throws Exception {
		Element root = doc.getDocumentElement();
		//Node root = doc.getElementsByTagName(BEHAVIOR_TREE).item(0);
		
		String description = getChildNodeText(root, PROP_DESCRIPTION);
		String className = root.getAttribute(PROP_EVALUATOR);
		evaluator = (Evaluator)Class.forName(className).getDeclaredConstructor().newInstance();
		List<BehaviorDef> nodes = readNodes(doc);
		linkNodes(doc, nodes);
	}

	private List<BehaviorDef> readNodes(Document doc) {
        if (doc.getElementsByTagName(NODES).getLength() == 0)
            return Collections.emptyList();

        List<Node> nodeNodes = getValidChildNodes(doc.getElementsByTagName(NODES).item(0));

        ProcessMode mode;
		List<BehaviorDef> nodes = new ArrayList<BehaviorDef>();
        for (int i = 0; i < nodeNodes.size(); i++) {
        	BehaviorDef node = null;
            nodeNode = nodeNodes.get(i);
            BehaviorType type = BehaviorType.findByNodeName(nodeNode.getNodeName());
            switch (type) {
            case SEQUENCE:
            	node = CompositeDef.sequenceDef(get(PROP_REACTIVE));
				break;
            case SELECTOR:
            	node = CompositeDef.selectorDef(get(PROP_REACTIVE));
				break;
            case PARALLEL:
            	node = CompositeDef.parallelDef(get(PROP_MODE), get(PROP_COUNT));
				break;
            case INVERTER:
				node = DecoratorDef.inverterDef();
				break;
            case REPEAT:
            	mode = ProcessMode.valueOf(get(PROP_MODE));
            	if(mode == ProcessMode.MAX_ATTEMPT)
            		node = DecoratorDef.repeatDef(get(PROP_COUNT), 
            				Boolean.valueOf(get(PROP_REPEAT_UNTIL_FAILURE)));
        		else
        			node = DecoratorDef.repeatDef(get(PROP_COUNT), 
        					TimeUnit.valueOf(get(PROP_TIME_UNIT)), 
        					Boolean.valueOf(get(PROP_REPEAT_UNTIL_FAILURE)));
				break;
            case RETRY:
            	mode = ProcessMode.valueOf(get(PROP_MODE));
            	if(mode == ProcessMode.MAX_ATTEMPT)
            		node = DecoratorDef.retryDef(get(PROP_COUNT));
        		else
        			node = DecoratorDef.retryDef(get(PROP_COUNT), 
        					TimeUnit.valueOf(get(PROP_TIME_UNIT)));
    			break;
            case WAIT:
            	node = DecoratorDef.waitDef(get(PROP_TIMEOUT), TimeUnit.valueOf(get(PROP_TIME_UNIT)));
				break;
            case FORCE_SUCCESS:
            	node = DecoratorDef.forceStatusDef(StatusEnum.SUCCESS);
				break;
            case FORCE_FAILURE:
            	node = DecoratorDef.forceStatusDef(StatusEnum.FAILURE);
				break;
            case CONDITION:
            	if(Condition.Mode.valueOf(get(PROP_MODE)) == Condition.Mode.CALLBACK)
            		node = BehaviorDef.callbackConditionDef(get(PROP_IMPLEMENTATION));
            	else
            		node = BehaviorDef.exprConditionDef(evaluator, get(PROP_LEFT_EXPRESSION), get(PROP_OPERATOR), get(PROP_RIGHT_EXPRESSION));
				break;
            case ACTION:
            	boolean async = Boolean.parseBoolean(get(PROP_ASYNCHRONOUS));
            	if(async)
            		node = BehaviorDef.asynchActionDef(get(PROP_IMPLEMENTATION), 
            				get(PROP_TIMEOUT), 
            				TimeUnit.valueOf(get(PROP_TIME_UNIT)));
            	else
            		node = BehaviorDef.actionDef(get(PROP_IMPLEMENTATION));
				break;
            case FIXED_STATUS:
            	node = BehaviorDef.fixedStatusDef(get(PROP_STATUS));
				break;
            case SLEEP:
            	node = BehaviorDef.sleepDef(get(PROP_COUNT), 
    					TimeUnit.valueOf(get(PROP_TIME_UNIT)));
				break;
            case SUBTREE:
            	node = BehaviorDef.subtreeDef(treeDefs, get(PROP_SUBTREE));
				break;
			default:
				break;
			}

            node.setName(get(PROP_NAME));
            node.setDescription(get(PROP_DESCRIPTION));
            nodes.add(node);
        }

		return nodes;
	}
	
	private void linkNodes(Document doc, List<BehaviorDef> nodes) {
        if (doc.getElementsByTagName(CONNECTIONS).getLength() == 0)
        	return;

        List<BehaviorDef> topNodes = new ArrayList<>(nodes);
        List<Node> connectionNodes = getValidChildNodes(doc.getElementsByTagName(CONNECTIONS).item(0));
        for (int i = 0; i < connectionNodes.size(); i++) {
            Node connectionNode = connectionNodes.get(i);

            BehaviorDef parent = nodes.get(getIntAttribute(connectionNode, SOURCE_INDEX));
            BehaviorDef child = nodes.get(getIntAttribute(connectionNode, TARGET_INDEX));
            if(parent instanceof CompositeDef)
            	((CompositeDef) parent).getChildDefs().add(child);
            else
            	((DecoratorDef)parent).setChildDef(child);
            topNodes.remove(child);
        }
        
        for(BehaviorDef def: topNodes)
        	if(def.getName() != null)
        		treeDefs.put(def.getName(), def);
	}
	
    private int getIntAttribute(Node node, String attributeName){
        return Integer.parseInt(getAttribute(node, attributeName));
    }
    
    private String getAttribute(Node node, String attributeName){
        NamedNodeMap map = node.getAttributes();
        for(int i = 0; i < map.getLength(); i++){
            if(attributeName.equals(map.item(i).getNodeName()))
                return map.item(i).getNodeValue();
        }
        
        return null;
    }
    
    private boolean isValidNode(Node node) {
        return !node.getNodeName().equals("#text");
    }
    
    private List<Node> getValidChildNodes(Node node) {
        List<Node> nl = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            if(isValidNode(nodeList.item(i)))
                nl.add(nodeList.item(i));
        }
        return nl;
    }
    
    public String getChildNodeText(Node node, String childName) {
        Node child = getChildNode(node, childName);
        return child == null ? null : child.getTextContent();
    }
    
    public Node getChildNode(Node node, String name) {
        List<Node> children = getValidChildNodes(node);
        Node found = null;

        for(int i = 0; i < children.size(); ++i) {
            if (((Node)children.get(i)).getNodeName().equalsIgnoreCase(name)) {
                found = (Node)children.get(i);
                break;
            }
        }

        return found;
    }
}
