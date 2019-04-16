package com.jia.mySpring.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 模仿 spring 的 ioc 容器，通过读取 spring.xml
 */
public class SimpleIOC {
    /**
     * bean 与id 的映射关系
     */
    private Map<String, Object> beanMap = new HashMap<>();

    /**
     * 通过构造器传入配置文件的路径
     */
    public SimpleIOC(String xmlPath) throws Exception {
        location(xmlPath);
    }

    /**
     * 根据 bean 的 id 返回对应的bean，如果没有对应的 Bean，就抛出异常
     */
    public Object getBean(String beanId){
        Object bean = beanMap.get(beanId);
        if(bean == null){
            throw new IllegalArgumentException("No Such Bean");
        }
        return bean;
    }

    private void location(String xmlPath) throws Exception{
        // 加载 xml 文件，需要用到 javax.xml 包
        InputStream xmlInputStream = new FileInputStream(xmlPath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlInputStream);
        Element root = doc.getDocumentElement();
        NodeList element = root.getChildNodes();

        // 遍历 bean 标签
        int len = element.getLength();
        for(int i = 0; i < len; i++){
            Node node = element.item(i);
            // node 为 bean 标签节点
            if(node instanceof  Element){
                Element ele = (Element)node;
                String beanId = ele.getAttribute("id");
                String className = ele.getAttribute("class");

                // 加载类 className
                Class clazz = null;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return ;
                }

                Object bean = clazz.newInstance();

                // 为 bean 设值
                NodeList properties = ele.getElementsByTagName("property");
                // 字段的名称和值，值可能是一个字符串，整数，浮点数，或者其他类的id
                String field = null;
                String value = null;
                String ref = null;

                int proLen = properties.getLength();
                for(int j = 0; j < proLen; j++){
                    Node propertyNode = properties.item(j);
                    if(propertyNode instanceof  Element){
                        Element proElem = (Element)propertyNode;
                        field = proElem.getAttribute("name");
                        value = proElem.getAttribute("value");

                        // 通过反射将相关的 bean 属性设置为可访问
                        Field declareField = clazz.getDeclaredField(field);
                        declareField.setAccessible(true);

                        if(value != null && value.length() > 0){
                            // 说明不是引用
                            declareField.set(bean, value);
                        }else{
                            ref = proElem.getAttribute("ref");
                            if(ref == null || ref.length() == 0){
                                throw new IllegalArgumentException("there is no reference here");
                            }
                            declareField.set(bean, getBean(ref));
                        }
                        registerBean(beanId, bean);
                    }
                }
            }
        }
    }

    public void registerBean(String beanId, Object bean){
        beanMap.put(beanId, bean);
    }
}
