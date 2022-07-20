package org.apache.ibatis.test;

import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

/**
 * @author xiaoK
 * @date 2022/7/15
 */
@Log4j2
public class XPathParserTest {
    @Test
    public void testXpathParser() {
        Properties properties = new Properties();
        properties.setProperty("jdbc.driverClass", "jdbc.driverClass");
        properties.put("jdbc", "hahaha");
        XPathParser xPathParser = new XPathParser("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<!DOCTYPE configuration PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-config.dtd\">\n" +
                "<configuration>\n" +
                "    <!-- 引入外部资源文件 -->\n" +
                "    <properties resource=\"jdbc.properties\"></properties>\n" +
                "    <!-- 设置驼峰匹配 -->\n" +
                "    <settings>\n" +
                "        <setting name=\"mapUnderscoreToCamelCase\" value=\"true\"/>\n" +
                "    </settings>\n" +
                "    <!-- 设置包扫描(别名) -->\n" +
                "    <typeAliases>\n" +
                "        <package name=\"cn.itcast.pojo\"/>\n" +
                "    </typeAliases>\n" +
                "    <!-- 配置环境：可以配置多个环境，default：配置某一个环境的唯一标识，表示默认使用哪个环境 -->\n" +
                "    <environments default=\"development\">\n" +
                "        <environment id=\"development\">\n" +
                "            <transactionManager type=\"JDBC\"/>\n" +
                "            <dataSource type=\"POOLED\">\n" +
                "                <!-- 配置连接信息 -->\n" +
                "                <property name=\"driver\" value=\"${jdbc.driverClass}\"/>\n" +
                "                <property name=\"url\" value=\"${jdbc.url}\"/>\n" +
                "                <property name=\"username\" value=\"${jdbc.username}\"/>\n" +
                "                <property name=\"password\" value=\"${jdbc.password}\"/>\n" +
                "            </dataSource>\n" +
                "        </environment>\n" +
                "    </environments>\n" +
                "    <!-- 配置映射文件：用来配置sql语句和结果集类型等 -->\n" +
                "    <mappers>\n" +
                "        <mapper resource=\"UserMapper.xml\"/>\n" +
                "    </mappers>\n" +
                "</configuration>", false, properties);
        String name = xPathParser.evalString("//typeAliases/package/@name");

        //environments下environment下dataSource标签的type值
        String type = xPathParser.evalString("//environments/environment/dataSource/@type");

        //dataSource下property下的所有value值
        List<XNode> values = xPathParser.evalNodes("//dataSource/property/@value");

        // 获取整个configuration
        XNode xNode = xPathParser.evalNode("/configuration");

    }
}
