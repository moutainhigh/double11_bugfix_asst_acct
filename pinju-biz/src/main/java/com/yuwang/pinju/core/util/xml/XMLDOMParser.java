package com.yuwang.pinju.core.util.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * 将XML文档（XML文件，XML字符串等）解析成DOM树
 * 
 * @see javax.xml.parsers.DocumentBuilder
 * @see org.xml.sax.InputSource
 */
public class XMLDOMParser {

    /**
     * 将XML文档（XML文件，XML字符串等）解析成DOM树
     * 
     * @param input 需要解析的XML文档，参考InputSource文档了解如何构造InputSource及可以构造哪些InputSource
     * @return Document - 解析出的DOM树
     * @throws ParserConfigurationException
     *                If a DocumentBuilder cannot be created which satisfies the
     *                configuration requested.
     * @throws SAXException
     *                If any parse errors occur.
     * @throws IOException
     *                If any IO errors occur.
     */
    public static Document parse(InputSource input)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(input);
    }
    /**
     * @根据InputStream将xml文档解析成DOM树
     * @param inputStream InputStream
     * @return Document
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws SAXException SAXException
     * @throws IOException IOException
     * @author James
     * @since  1.0
     */
    public static Document parse(final InputStream inputStream)
        throws ParserConfigurationException, SAXException, IOException {
        InputSource inputSource = new InputSource(inputStream);
        return XMLDOMParser.parse(inputSource);
    }
    /**
     * @根据文件将xml文档解析成DOM树
     * @param file File
     * @return Document
     * @throws ParserConfigurationException ParserConfigurationException
     * @throws SAXException SAXException
     * @throws IOException IOException
     * @author James
     * @since  1.0
     */
    public static Document parse(final File file)
        throws ParserConfigurationException, SAXException , IOException {
        InputStream inputStream = new FileInputStream(file);
        return XMLDOMParser.parse(inputStream);
    }
}
