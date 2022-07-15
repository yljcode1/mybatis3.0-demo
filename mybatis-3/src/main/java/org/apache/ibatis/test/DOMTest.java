package org.apache.ibatis.test;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.io.Resources;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaoK
 * @date 2022/7/15
 */
@Log4j2
public class DOMTest {
    @Test
    public void testDocumentBuilderFactory() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // 开启验证
        documentBuilderFactory.setValidating(true);
        documentBuilderFactory.setNamespaceAware(false);
        documentBuilderFactory.setIgnoringComments(true);
        documentBuilderFactory.setIgnoringElementContentWhitespace(false);
        documentBuilderFactory.setCoalescing(false);
        documentBuilderFactory.setExpandEntityReferences(true);

        // 创建DocumentBuilder对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        InputStream resource = Resources.getResourceAsStream("inventory.xml");
        // 将文档加载到一个Document对象中
        Document document = documentBuilder.parse(resource);


        // 创建XPathFactory对象
        XPathFactory xPathFactory = XPathFactory.newInstance();

        XPath xPath = xPathFactory.newXPath();
        // 编译 XPath表达式
        XPathExpression compile = xPath.compile("//book[author='Neal Stephenson']/title/text()");
        Object result = compile.evaluate(document, XPathConstants.NODESET);
        log.info("查询坐着为Neal Stephenson的图书标题");
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            log.info("这个值是: {}", nodes.item(i).getNodeValue());
        }
        log.info("查询1997年之后图书的标题");
        nodes = (NodeList) xPath.evaluate("//book[@year>1997]/title/text()", document, XPathConstants.NODESET);
        for (int i = 0; i < nodes.getLength(); i++) {
            log.info("这个值是: {}", nodes.item(i).getNodeValue());
        }
    }
}
