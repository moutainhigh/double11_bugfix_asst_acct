package com.yuwang.pinju.core.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * Operator to manipulate XML <code>Element</code>.
 * 
 * @author James
 */
public final class XMLOperator {

    /**
     * Private constructor
     */
    public XMLOperator() {
    }

    /**
     * Appends a new element called <em>elementName</em> to an element.
     * Creates and returns a root element if specified element <em>e</em> is
     * <code>null</code>.
     * 
     * @param e
     *            The element to be appended with a new element
     * @param elementName
     *            The name of new element
     * @return Element appended
     */
    public static Element appendNewElement(Element e, String elementName) {
        if (elementName == null || "".equals(elementName.trim())) {
            throw new IllegalArgumentException("element name cannot be null");
        }
        Element element = e;
        Element newElement = null;
        Document doc = null;

        if (element == null) { // create root if e is null
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
            } catch (ParserConfigurationException ex) {
                throw new IllegalStateException(
                        "Failed to get a document builder");
            }
            doc = builder.newDocument();
            newElement = doc.createElement(elementName);
            element = (Element) doc.appendChild(newElement);
            return element;
        }

        doc = element.getOwnerDocument();
        newElement = doc.createElement(elementName);
        element = (Element) element.appendChild(newElement);
        return element;
    }

    /**
     * Sets a string <em>value</em> to the element.
     * 
     * @param element
     *            The element to be set
     * @param value
     *            Element text value
     */
    public static void setElementText(Element element, String value) {
        if (element == null) {
            throw new IllegalArgumentException(
                    "cannot set text to null element");
        }
        String elementValue = value;
        Document doc = null;
        Text text = null;

        if (elementValue == null) {
            elementValue = "";
        }

        // Get the document and create text node
        doc = element.getOwnerDocument();
        text = doc.createTextNode(elementValue);
        element.appendChild(text);
    }

    /**
     * Sets a long <em>value</em> to the element.
     * 
     * @param element
     *            The element to be set
     * @param value
     *            Element long value
     */
    public static void setElementText(Element element, long value) {
        setElementText(element, Long.toString(value));
    }

    /**
     * 
     * Sets a boolean <em>value</em> to the element.
     * 
     * @param element
     *            The element to be set
     * @param value
     *            Element boolean value
     */
    public static void setElementText(Element element, boolean value) {
        setElementText(element, Boolean.toString(value));
    }

    /**
     * Returns the text content of a child node.
     * 
     * @param parent
     *            Parent Element
     * @param name
     *            Name of the child
     * @return String text of the child node
     */
    public static String getChildText(Element parent, String name) {
        Element e = getChildByName(parent, name);
        if (e == null) {
            return "";
        }
        return getText(e);
    }

    /**
     * Returns the text content of the XML element.
     * 
     * @param e
     *            Element
     * @return String text of the element
     */
    public static String getText(Element e) {
        NodeList nl = e.getChildNodes();
        int max = nl.getLength();
        for (int i = 0; i < max; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == Node.TEXT_NODE) {
                return n.getNodeValue();
            }
        }
        return "";
    }

    /**
     * Returns the child element based on the element and name
     * 
     * @param e
     *            The parent element under which to look
     * @param name
     *            Name of the child
     * @return Element the child element if found otherwise null
     */
    public static Element getChildByName(Element e, String name) {
        Element[] list = getChildrenByName(e, name);
        if (list.length == 0) {
            return null;
        }
        if (list.length > 1) {
            throw new IllegalStateException("Too many elements found!");
        }
        return list[0];
    }

    /**
     * Returns an array of the children based on parent and name
     * 
     * @param e
     *            The parent element under which to look
     * @param name
     *            Name of the children to retrieve
     * @return Element[] Array of elements
     */
    public static Element[] getChildrenByName(Element e, String name) {
        NodeList nl = e.getChildNodes();
        int max = nl.getLength();
        LinkedList list = new LinkedList();
        for (int i = 0; i < max; i++) {
            Node n = nl.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE
                    && n.getNodeName().equals(name)) {
                list.add(n);
            }
        }
        return (Element[]) list.toArray(new Element[list.size()]);
    }
    /**
     * @ 将给定的xml文件的InputSource,获取所有叶子节点值
     * @param file File
     * @return Object 如果是一条记录是Map类型,如果是多条这是List类型,list放入的是Map;
     * Map 中key是节点名称
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws SAXException SAXException
     * @throws IOException IOException
     * @author James
     * @since  1.0
     */
    public Object getAllDataFromXml(final File file)
        throws ParserConfigurationException, SAXException, IOException {
        Assert.notNull(file , "File must be not null");
        Document doc =  XMLDOMParser.parse(file);
        Node root = doc.getDocumentElement();
        GetNode getnode = new GetNode(root);
        return getnode.getAllNodeValue();
    }
    /**
     * @ 将给定的xml文件的InputSource,获取所有叶子节点值
     * @param inputSource InputSource
     * @return Object 如果是一条记录是Map类型,如果是多条这是List类型,list放入的是Map;
     * Map 中key是节点名称
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws SAXException SAXException
     * @throws IOException IOException
     * @author James
     * @since  1.0
     */
    public Object getAllDataFromXml(final InputSource inputSource)
        throws ParserConfigurationException, SAXException, IOException {
        Assert.notNull(inputSource , "InputSource must be not null");
        Document doc =  XMLDOMParser.parse(inputSource);
        Node root = doc.getDocumentElement();
        GetNode getnode = new GetNode(root);
        return getnode.getAllNodeValue();
    }
    /**
     * @ 将给定的xml文件流,获取所有叶子节点值
     * @param input InputStream
     * @return Object 如果是一条记录是Map类型,如果是多条这是List类型,list放入的是Map;
     * Map 中key是节点名称
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws SAXException SAXException
     * @throws IOException IOException
     * @author James
     * @since  1.0
     */
    public Object getAllDataFromXml(final InputStream input)
        throws ParserConfigurationException, SAXException, IOException {
        Assert.notNull(input , "InputStream must be not null");
        Document doc =  XMLDOMParser.parse(input);
        Node root = doc.getDocumentElement();
        GetNode getnode = new GetNode(root);
        return getnode.getAllNodeValue();
    }
    /**
     * @根据某个结点解析所有的值
     * @param nodeT
     * @return
     * @author James
     * @since 1.0
     */
    public Object GetAllDataFromXml(final  Node nodeT , boolean isMulchild) {
        Assert.notNull(nodeT , "nodeT must be not null");
        GetNode getnode = new GetNode(nodeT , isMulchild);
        return getnode.getAllNodeValue();
    }
    /**
     * @内部类,获得给定node的所有叶子节点的map(名称和值)
     * @author James
     * @since  1.0
     */
    private final class GetNode {
        /**
         * @需要解析的节点
         */
        private final Node root;
        private final boolean isMulchild;
        /**
         * @构造一个GetNode
         * @param nodeT Node
         */
        public GetNode(final Node nodeT) {
            this(nodeT , true);
        }
        public GetNode(final Node nodeT , final boolean isMulchild) {
            this.root = nodeT;
            this.isMulchild = isMulchild;
        }
        /**
         * @得到root节点的所有节点值
         * @return Object
         */
        private Object getAllNodeValue() {
            Assert.notNull(root , "root must be not null");
            NodeList nodeList = root.getChildNodes();
            if(!this.isMulchild) {
                Map result = new TreeMap();
                for (int i = 0, len = nodeList.getLength(); i < len; i++) {
                    Node n = nodeList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        getNodeValue(n, result);
                    }
                }
                return result;
            }
            if (nodeList.getLength() == 1) {
                Map result = new HashMap();
                getNodeValue(nodeList.item(1), result);
                return result;
            } else {
                List < Map > resultList = new LinkedList < Map >();
                for (int i = 0, len = nodeList.getLength(); i < len; i++) {
                    Node n = nodeList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Map map = new HashMap();
                        getNodeValue(n, map);
                        resultList.add(map);
                    }
                }
                if (resultList.size() == 1) {
                    return resultList.get(0);
                }
                return resultList;
            }
        }
        /**
         * @得到叶子节点值
         * @param node Node
         * @param map Map
         */
        private void getNodeValue(final Node node , final Map map) {
            Assert.notNull(node , "node must be not null");
            Assert.notNull(map , "map must be not null");
            NodeList nl = node.getChildNodes();
            if (nl.getLength() == 1) {
                Node n = nl.item(0);
                if (n.getNodeType() == Node.TEXT_NODE) {
                    Text t = (Text) n;
                    String name = n.getParentNode().getNodeName();
                    String value = t.getNodeValue();
                    map.put(name, value);
                }

            } else {
                for (int i = 0, len = nl.getLength(); i < len; i++) {
                    Node n = nl.item(i);
                    getNodeValue(n, map);
                }
            }

        }
    }
   /* public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            InputStream in = new FileInputStream("c:/icbc.xml");
            XMLOperator testIcbc = new XMLOperator();
            Object object = testIcbc.getAllDateFromXml(in);
            if(object instanceof Map){
                Map map = (Map)object;
                System.out.println("="+map.get("merID"));
            } else{
                List list = (List)object;
                for(int i=0 ;i<list.size();i++) {
                    Map result = (Map)list.get(i);
                    System.out.println(result.get("merID"));
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/
}
