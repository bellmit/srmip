package com.kingtake.expert.service.reviewmain;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.expert.entity.reviewmain.TErReviewMainEntity;

public interface TErReviewMainServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TErReviewMainEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TErReviewMainEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TErReviewMainEntity t);

    /**
     * 保存专家评审信息
     * 
     * @param request
     */
    public TErReviewMainEntity saveReviewMainInfo(HttpServletRequest request);

    /**
     * 删除评审
     * 
     * @param tErReviewMain
     */
    public void deleteReviewMain(TErReviewMainEntity tErReviewMain);

    /**
     * 专家评审
     * 
     * @return
     */
    public Workbook export();
}
