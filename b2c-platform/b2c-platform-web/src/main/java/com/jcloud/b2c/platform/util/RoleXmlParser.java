package com.jcloud.b2c.platform.util;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.management.relation.Role;

import org.apache.commons.lang3.StringUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 权限 控制 xml 解析类 
 * 在  spring-servlet.xml 中 配置 bean 并且为单例模式
 * @author cyy
 * @date 2017-05-15
 */
public class RoleXmlParser {
	private static Logger logger = LoggerFactory.getLogger(RoleXmlParser.class);
	
	private static Map<String,String> ALL_ROLES = new HashMap<String,String>();
	
	private static final String ROLE_KEY = "key";
	private static final String ROLE_VALUE = "value";
	public static final String DELIMITER = ","; // 权限 code 之间的分隔符，需要和xml中一致

	public void init(){
		SAXReader rader = new SAXReader();
		// 获取 classpath 根目录
		String filepath = RoleXmlParser.class.getResource("/").getPath();
		
		String xmlFilePath = filepath + "role.xml";
		
		if(logger.isDebugEnabled()){
			logger.debug("role.xml 的绝对路径为：{0}",xmlFilePath);
		}
		
		Document document = null;
		try{
			document = rader.read(new File(xmlFilePath));
		}catch (Exception e) {
			logger.error("role.xml 解析出错",e);
			throw new RuntimeException(e);
		}
		
		Element root = document.getRootElement();
		
		@SuppressWarnings("unchecked")
		Iterator<Element> els  = root.elementIterator();
		while(els.hasNext()){
			Element el = els.next();
			ALL_ROLES.put(el.attributeValue(ROLE_KEY),el.attributeValue(ROLE_VALUE));
		}
		if(logger.isDebugEnabled()){
			logger.debug("role.xml 解析完成");
		}
	}
	/**
	 * 更具key获取 权限
	 * @param key
	 * @return
	 */
	public String [] getRolesByKey(String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		String value = ALL_ROLES.get(key); 
		if(StringUtils.isBlank(value)){
			return null;
		}
		String [] result = value.split(DELIMITER);
		return result;
	}
	
}
