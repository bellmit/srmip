package com.kingtake.common.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 
 *<b>properties 文件工具类</b>
 *<p>
 * 提供对 properties 操作函数
 *</p>
 */
public class PropertiesUtil {

	/**
	 * 
	 *<b> 读取配置文件 </b>
	 * 
	 * @param url
	 * @return Map
	 */
	public static Map<String, String> read(String path) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Reader reader = new InputStreamReader(Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(path),
					"UTF-8");
			Properties propertie = new Properties();
			propertie.load(reader);
			for (Entry<Object, Object> entry : propertie.entrySet()) {
				map.put(entry.getKey().toString(), entry.getValue()
						.toString());
			}
			propertie.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
