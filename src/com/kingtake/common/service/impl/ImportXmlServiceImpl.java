package com.kingtake.common.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.service.ImportXmlServiceI;

@Service("importXmlServiceImpl")
@Transactional
public class ImportXmlServiceImpl extends CommonServiceImpl implements ImportXmlServiceI {

	@Override
	public List importXml(File file) {
		SAXReader reader = new SAXReader();
		Document document;
		List list = new ArrayList();
		try {
			document = reader.read(file);
			Element root = document.getRootElement();
			//获取表名
			String tableName = root.getName();
			
			//获取所有节点
			List<Element> childElements = root.elements();
			for(int a=0;a<childElements.size();a++){
				Element child = childElements.get(a);
				//获取节点下的子元素
				Map map = new HashMap();
				List<Element> elementList = child.elements();
				for(int b=0;b<elementList.size();b++){
					Element ele = elementList.get(b);
					map.put(ele.getName(), ele.getText());
				}
				map.put("tableName", tableName);
				map.put("count", childElements.size());
				list.add(map);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}