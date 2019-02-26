package com.kingtake.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	public static Document getDoc(String path){
		try {
			SAXReader reader = new SAXReader();
			Document doc = reader.read(new FileInputStream(path));
			return doc;
		} catch (FileNotFoundException e) {
			System.out.println("xml文件不存在，路径错误");
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
