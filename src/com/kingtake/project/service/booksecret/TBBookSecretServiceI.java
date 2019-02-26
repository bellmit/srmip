package com.kingtake.project.service.booksecret;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.booksecret.TBBookSecretEntity;

public interface TBBookSecretServiceI extends CommonService{
	
 	@Override
    public <T> void delete(T entity);
 	
 	@Override
    public <T> Serializable save(T entity);
 	
 	@Override
    public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBBookSecretEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBBookSecretEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBBookSecretEntity t);    /**
     * 删除业务数据同时删除附件
     */
    public void deleteAddAttach(TBBookSecretEntity t);
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
