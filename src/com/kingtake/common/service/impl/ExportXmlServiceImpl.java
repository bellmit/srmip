package com.kingtake.common.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.service.ExportXmlServiceI;

@Service("exportXmlServiceImpl")
@Transactional
public class ExportXmlServiceImpl extends CommonServiceImpl implements ExportXmlServiceI {

	@Override
	public int toXmlService(List list, String tableName, String classPath) {
		// 创建根节点 并设置它的属性 ;
		Element root = new Element(tableName).setAttribute("count", Integer.toString(list.size()));
		// 将根节点添加到文档中
		Document Doc = new Document(root);
		// 创建节点
		for (int i = 0; i < list.size(); i++) {
			Element elements = new Element(tableName + "_row");
			Map map = (Map) list.get(i);
			Set keySet = map.keySet();
			Iterator iterator = keySet.iterator();
			while (iterator.hasNext()) {
				Object keys = iterator.next();
				//转小写
				String lowerKey = keys.toString().toLowerCase();
				// 给 节点添加子节点并赋值；
				if (map.get(keys) == null) {
					elements.addContent(new Element(lowerKey).setText(""));
				} else {
					elements.addContent(new Element(lowerKey).setText(map.get(keys).toString()));
				}
			}
			root.addContent(elements);
		}
		// 输出 books.xml 文件；
		// 使xml文件 缩进效果
		Format format = Format.getPrettyFormat();
		XMLOutputter XMLOut = new XMLOutputter(format);
		int x = 0;
		try {
			String realPath = classPath + "/exportCw/";// 文件的硬盘真实路径
            File dir = new File(realPath);
            if (!dir.exists()) {
                dir.mkdirs();// 创建根目录
            }
			XMLOut.output(Doc, new FileOutputStream(classPath + "/exportCw/"+tableName+".xml"));
			x++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}

}