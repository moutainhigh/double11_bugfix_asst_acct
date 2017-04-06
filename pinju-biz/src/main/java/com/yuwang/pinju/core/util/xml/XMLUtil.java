/**
 * 
 */
package com.yuwang.pinju.core.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author James
 * 
 */
public class XMLUtil {
    private Node rootNode;
    private Document doc;
    

    private String xmlStr;

    public XMLUtil(String xmlStr) {
        this.xmlStr = xmlStr;
        initWithXmlStr();
    }

    public void initWithXmlStr() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder build = factory.newDocumentBuilder();
            // String temp = new String(this.xmlStr.getBytes(), "UTF-8");
            doc= build.parse(new ByteArrayInputStream(this.xmlStr.getBytes()));
            rootNode=doc;
            // doc = doc.getFirstChild();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String xml2Str(Document doc) throws TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty("encoding", "UTF-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        t.transform(new DOMSource(doc), new StreamResult(bos));
        String xmlStr = bos.toString();
        return xmlStr;

    }

    public static Document str2Xml(String xmlStr)
            throws ParserConfigurationException, SAXException, IOException {

        StringReader sr = new StringReader(xmlStr);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(is);
        return doc;

    }

    public String getProperty(String key) {
        int index = key.lastIndexOf('.');
        String propertyName = key.substring(index + 1);
        Node node = getNode(key, false);
        if (node == null) {
            return "";
        }
        NamedNodeMap attrs = node.getAttributes();
        Node attr = attrs.getNamedItem(propertyName);
        if (attr == null) {
            return "";
        }
        return attr.getNodeValue();
    }

    public String getValue(String key) {
        Node node = getNode(key, true);
        if (node == null) {
            return "";
        }
        Node temp = node.getFirstChild();
        if (null == temp) {
            return "";
        } else {
            return temp.getNodeValue();
        }
    }

    public Node getNode(String key, boolean isGetValue) {
        StringTokenizer st = new StringTokenizer(key, ".");
        Node currentNode = rootNode;
        Node preNode = null;
        int count = st.countTokens();
        for (int i = 0; i < count; i++) {
            if (!isGetValue && i == count - 1) {
                return currentNode;
            }
            preNode = currentNode;
            String nodeName = (String) st.nextElement();
            NodeList nodes = currentNode.getChildNodes();
            int len = nodes.getLength();
            for (int j = 0; j < len; j++) {
                Node node = nodes.item(j);
                if (nodeName.equalsIgnoreCase(node.getNodeName())) {
                    currentNode = node;
                    break;
                }
            }
            if (currentNode == preNode) {
                return null;
            }
        }

        return currentNode;
    }
   public void setNodeValue(String key,String value)
   {
       Node node = getNode(key, true);
       Assert.notNull(node , "node must be not null");
       Assert.notNull(value , "node's value must be not null");
        if (node.hasChildNodes())
          node.getFirstChild().setNodeValue(value);
        else
        {     
            node.appendChild(doc.createTextNode(value));
            
        }     
       
   }
    public static String replace(String xml, Map value) {
        int len = xml.length();
        StringBuffer buf = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            char c = xml.charAt(i);
            if (c == '$') {
                i++;
                StringBuffer key = new StringBuffer();
                char temp = xml.charAt(i);
                while (temp != '}') {
                    if (temp != '{') {
                        key.append(temp);
                    }
                    i++;
                    temp = xml.charAt(i);
                }
                String variable = (String) value.get(key.toString());
                if (null == variable) {
                    buf.append("");
                } else {
                    buf.append(variable);
                }
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public static void main(String[] args) throws Exception {
        String test = "<?xml version=\"1.0\" encoding=\"GBK\"?><ICBC><B2C><SubmitOrder><merID>${id}</merID></SubmitOrder></B2C></ICBC>";
        Map map = new HashMap();
        map.put("id", "1");
        String result = replace(test, map);
        test = "<?xml version=\"1.0\" encoding=\"GBK\"?><ICBC><B2C><aa/><SubmitOrder><merID>商户代码</merID></SubmitOrder></B2C></ICBC>";
        test="<?xml version = '1.0' encoding = 'UTF-8'?><Preference><Accounting><ClosedAcctPeriodDate/></Accounting>    <Login>3<MaxAttempts/>        <FailedWindowTime/>    </Login>    <Register>4<DefaultServiceLevel/>    </Register></Preference>";
        XMLUtil util = new XMLUtil(test);
        //String value = util.getValue("ICBC.B2C.SubmitOrder.merID");
         Node node=util.getNode("Preference.Accounting.ClosedAcctPeriodDate", true);
          Document doc=util.getDoc();
          node.appendChild(doc.createTextNode("aa"));
         System.out.println(node.getNodeName());         
         System.out.println(node.getFirstChild().getNodeValue());
            
        
    }

    /**
     * @return Returns the doc.
     */
    public Document getDoc() {
        return doc;
    }
}
