package com.yuwang.pinju.core.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * 将一个DOM节点序列化成一个字符串.
 * 与{@link XMLTransformer}使用的相通的底层类库，都是{@link javax.xml.transform.Transformer}
 * 
 * @see javax.xml.transform.Transformer
 */
public class XMLSerializer {

    /**
     * 将一个DOM节点序列化成一个字符串.DOM节点可能是DOM,Element,等.
     * <p>
     * XML声明(形如<i>&lt;?xml version="1.0" encoding="UTF-8" ?></i>) 会被忽略,
     * 如果你希望生成XML声明,可以使用方法{@link #serialize(Node,Writer,Properties)}，
     * 并在Properties里加上相应的配置.
     * 
     * @param doc 需要序列化的XML节点，可能是Document,Element等
     * @param out 序列化到此Writer中
     * @exception IOException
     *                If any IO errors occur.
     * @exception TransformerConfigurationException
     * @exception TransformerException
     */
    public static void serialize(Node doc, Writer out)
            throws TransformerConfigurationException, TransformerException,
            IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.transform(new DOMSource(doc), new StreamResult(out));
        out.flush();
    }

    /**
     * 将一个DOM节点序列化成一个字符串.DOM节点可能是DOM,Element,等.
     * 
     * @param doc 需要序列化的XML节点，可能是Document,Element等
     * @param out 序列化到此Writer中
     * @param props 用于定制序列化输出格式,参考{@link javax.xml.transform.Transformer#setOutputProperties(java.util.Properties)}
     * @exception IOException
     *                If any IO errors occur.
     * @exception TransformerConfigurationException
     * @exception TransformerException
     * @see javax.xml.transform.OutputKeys
     */
    public static void serialize(Node doc, Writer out, Properties props)
            throws TransformerConfigurationException, TransformerException,
            IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        if (props != null){
            trans.setOutputProperties(props);
        }
        trans.transform(new DOMSource(doc), new StreamResult(out));
        out.flush();
    }

    /**
     * 将一个DOM节点序列化成一个字符串.DOM节点可能是DOM,Element,等.
     * <p>
     * XML声明(形如<i>&lt;?xml version="1.0" encoding="UTF-8" ?></i>) 会被忽略,
     * 如果你希望生成XML声明,可以使用方法{@link #serialize(Node, OutputStream, Properties)}，
     * 并在Properties里加上相应的配置.
     * 
     * @param doc 需要序列化的XML节点，可能是Document,Element等
     * @param out 序列化到此OutputStream中
     * @exception IOException
     *                If any IO errors occur.
     * @exception TransformerConfigurationException
     * @exception TransformerException
     */
    public static void serialize(Node doc, OutputStream out)
            throws TransformerConfigurationException, TransformerException,
            IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.transform(new DOMSource(doc), new StreamResult(out));
        out.flush();
    }

    /**
     * 将一个DOM节点序列化成一个字符串.DOM节点可能是DOM,Element,等.
     * 
     * @param doc 需要序列化的XML节点，可能是Document,Element等
     * @param out 序列化到此OutputStream中
     * @param props 用于定制序列化输出格式,参考{@link javax.xml.transform.Transformer#setOutputProperties(java.util.Properties)}
     * @exception IOException
     *                If any IO errors occur.
     * @exception TransformerConfigurationException
     * @exception TransformerException
     * @see javax.xml.transform.OutputKeys
     */
    public static void serialize(Node doc, OutputStream out, Properties props)
            throws TransformerConfigurationException, TransformerException,
            IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        if (props != null){
            trans.setOutputProperties(props);
        }
        trans.transform(new DOMSource(doc), new StreamResult(out));
        out.flush();
    }
    
}