package com.kingtake.project.service.tpmincome;
import com.kingtake.project.entity.tpmincome.TPmIncomeEntity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TPmIncomeServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmIncomeEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmIncomeEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmIncomeEntity t);
    /**
     * 下载导入模板
     * 
     * @param regesterType
     * @param response
     */
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response);
    /**
     * 导入excel
     * 
     * @param wb
     */
    public String importExcel(HSSFWorkbook wb);

}
