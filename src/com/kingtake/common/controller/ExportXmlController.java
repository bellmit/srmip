package com.kingtake.common.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingtake.common.service.ExportXmlServiceI;

/**
 * 导出xml
 * 
 * @author hzw
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/exportXml")
public class ExportXmlController extends BaseController {
	
	/**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ExportXmlController.class);
	
	@Autowired
    private ExportXmlServiceI exportXmlServiceI;

//	public int toXml(List list, String tableName) {
//		return this.exportXmlServiceI.toXmlService(list, tableName);
//	}

	public static void main(String[] args) {
		try {
			ExportXmlController exportXml = new ExportXmlController();

			Map map = new HashMap();
			map.put("id", "1");
			map.put("xm", "张飞");
			map.put("xb", "男");

			Map map2 = new HashMap();
			map2.put("id", "2");
			map2.put("xm", "张仪");
			map2.put("xb", "男");

			List keyList = new ArrayList();
			keyList.add(map);
			keyList.add(map2);
//			exportXml.toXml(keyList, "project");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("D:/books.xml 文件已生成");
	}
}