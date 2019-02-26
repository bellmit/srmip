package com.kingtake.common.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import com.kingtake.common.service.ImportXmlServiceI;
import com.kingtake.common.service.impl.ImportXmlServiceImpl;

public class ImportXmlController {
	
	@Autowired
    private ImportXmlServiceI importXmlServiceI;
	
	public static void main(String[] args) throws Exception {
		File file = new File("D:/td_mxys_mx.xml");
		ImportXmlServiceImpl test = new ImportXmlServiceImpl();
		test.importXml(file);
	}
}