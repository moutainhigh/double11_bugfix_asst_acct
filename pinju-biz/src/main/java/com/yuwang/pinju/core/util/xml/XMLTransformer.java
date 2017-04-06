package com.yuwang.pinju.core.util.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

/**
 * 使用XSL将XML文档转换成HTML文档.
 * 
 * @see javax.xml.transform.Transformer
 */
public class XMLTransformer {

    /**
     * 使用XSL将XML文档转换成HTML文档.
     * <p>
     * XML声明(形如<i>&lt;?xml version="1.0" encoding="UTF-8" ?></i>) 会被忽略,
     * 如果你希望生成XML声明,可以使用方法{@link #transform(Source, Source, Writer, Properties)}，
     * 并在Properties里加上相应的配置.
     * 
     * @param xml 需要转换的XML文档
     * @param xsl stylesheet文档
     * @param out 转换后的HTML文档输出到此Writer中
     * @exception TransformerException -
     *                If an unrecoverable error occurs during the course of the
     *                transformation.
     * @exception IOException
     * @see #transform(Source, Source, Writer, Properties)
     */
    public static void transform(Source xml, Source xsl, Writer out)
            throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(xsl);
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(xml, new StreamResult(out));
        out.flush();
    }

    /**
     * 使用XSL将XML文档转换成HTML文档.
     * 
     * @param xml 需要转换的XML文档
     * @param xsl stylesheet文档
     * @param out 转换后的HTML文档输出到此Writer中
     * @param props 用于定制输出格式,参考{@link javax.xml.transform.Transformer#setOutputProperties(java.util.Properties)}
     * @exception TransformerException -
     *                If an unrecoverable error occurs during the course of the
     *                transformation.
     * @exception IOException
     * @see javax.xml.transform.OutputKeys
     */
    public static void transform(Source xml, Source xsl, Writer out,
            Properties props) throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(xsl);
        if (props != null) {
            t.setOutputProperties(props);
        }
        t.transform(xml, new StreamResult(out));
        out.flush();
    }

    /**
     * 使用XSL将XML文档转换成HTML文档.
     * <p>
     * XML声明(形如<i>&lt;?xml version="1.0" encoding="UTF-8" ?></i>) 会被忽略,
     * 如果你希望生成XML声明,可以使用方法{@link #transform(Source, Source, OutputStream, Properties)}，
     * 并在Properties里加上相应的配置.
     * @param xml 需要转换的XML文档
     * @param xsl stylesheet文档
     * @param out 转换后的HTML文档输出到此OutputStream中
     * @exception TransformerException -
     *                If an unrecoverable error occurs during the course of the
     *                transformation.
     * @exception IOException
     * @see #transform(Source, Source, OutputStream, Properties)
     */
    public static void transform(Source xml, Source xsl, OutputStream out)
            throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(xsl);
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.transform(xml, new StreamResult(out));
        out.flush();
    }

    /**
     * 使用XSL将XML文档转换成HTML文档.
     * @param xml 需要转换的XML文档
     * @param xsl stylesheet文档
     * @param out 转换后的HTML文档输出到此OutputStream中
     * @param props 用于定制输出格式,参考{@link javax.xml.transform.Transformer#setOutputProperties(java.util.Properties)}
     * @exception TransformerException -
     *                If an unrecoverable error occurs during the course of the
     *                transformation.
     * @exception IOException
     * @see javax.xml.transform.OutputKeys
     */
    public static void transform(Source xml, Source xsl, OutputStream out,
            Properties props) throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer t = factory.newTransformer(xsl);
        if (props != null) {
            t.setOutputProperties(props);
        }
        t.transform(xml, new StreamResult(out));
        out.flush();
    }

}