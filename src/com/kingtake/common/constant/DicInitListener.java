package com.kingtake.common.constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.jeecgframework.web.system.service.SystemService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 从数据库初始化字典表数据到内存中
 * @author think
 *
 */
public class DicInitListener  implements javax.servlet.ServletContextListener {
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
		SystemService systemService = (SystemService) webApplicationContext.getBean("systemService");

		String sql = "SELECT T1.CODE AS KEY, T1.CODE_TYPE AS TYPE, T2.CODE, T2.NAME "
				+ " FROM T_B_CODE_TYPE T1, T_B_CODE_DETAIL T2 "
				+ " WHERE T2.CODE_TYPE_ID = T1.ID ORDER BY KEY, CODE";
		List<Map<String, Object>> list = systemService.findForJdbc(sql);
		for(int i = 0; i < list.size(); i++){
			Map<String, Object> map = list.get(i);
			String key = map.get("KEY").toString();
			if(SrmipConstants.YES.equals(map.get("TYPE"))){
				Map<String, Object> detail = SrmipConstants.dicResearchMap.get(key);
				if(detail == null){
					detail = new HashMap<String, Object>();
					SrmipConstants.dicResearchMap.put(key, detail);
				}
				detail.put(map.get("CODE").toString(), map.get("NAME").toString());
			}else{
				Map<String, Object> detail = SrmipConstants.dicStandardMap.get(key);
				if(detail == null){
					detail = new HashMap<String, Object>();
					SrmipConstants.dicStandardMap.put(key, detail);
				}
				detail.put(map.get("CODE").toString(), map.get("NAME").toString());
			}
		}
	}
}
