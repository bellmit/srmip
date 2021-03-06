package org.jeecgframework.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;
import org.jeecgframework.web.system.pojo.base.DuplicateCheckPage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**   
 * @Title: Action
 * @Description: 校验工具Action
 * @author 张代浩
 * @date 2013-09-12 22:27:30
 * @version V1.0   
 */
@Controller
@RequestMapping("/duplicateCheckAction")
public class DuplicateCheckAction extends BaseController {

	private static final Logger logger = Logger.getLogger(DuplicateCheckAction.class);

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	
	/**
	 * 校验数据是否在系统中是否存在
	 * @return
	 */
	@RequestMapping(params = "doDuplicateCheck")
	@ResponseBody
	public AjaxJson doDuplicateCheck(DuplicateCheckPage duplicateCheckPage, HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		Long num = null;
		String[] fieldNames = duplicateCheckPage.getFieldName().split("#");
		if(StringUtils.isNotBlank(duplicateCheckPage.getRowObid())){
			//[2].编辑页面校验
			if(fieldNames.length>0){
				Object[] obj = new Object[fieldNames.length+1];
				obj[0] = duplicateCheckPage.getRowObid();
				String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE id != ? ";
				String field = " and "+ fieldNames[0] +" =?";
				for(int i=0;i<fieldNames.length;i++){
					if(i!=0){
						field =" or "+ fieldNames[i] +" =?";
					}
					sql += field;
					obj[i+1]=duplicateCheckPage.getFieldVlaue();
				}
				num=jdbcDao.getCountForJdbcParam(sql,obj);
			}else{
				String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =? and id != ?";
				num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue(),duplicateCheckPage.getRowObid()});
			}
		}else{
			//[1].添加页面校验
			if(fieldNames.length>0){
				Object[] obj = new Object[fieldNames.length];
				String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE ";
				String field = "";
				for(int i=0;i<fieldNames.length;i++){
					field = fieldNames[i] +" =? or ";
					sql += field;
					obj[i]=duplicateCheckPage.getFieldVlaue();
				}
				sql=sql.substring(0, sql.lastIndexOf("or"));
				num=jdbcDao.getCountForJdbcParam(sql,obj);
			}else{
				String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =?";
				num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
				
			}
		}
		
		if(num==null||num==0){
			//该值可用
			j.setSuccess(true);
			j.setMsg("该值可用！");
		}else{
			//该值不可用
			j.setSuccess(false);
			j.setMsg("该值不可用，系统中已存在！");
		}
		return j;
	}
}
