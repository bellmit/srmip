package com.kingtake.project.service.reportmaterial;

import java.io.Serializable;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.CommonService;

import com.kingtake.project.entity.reportmaterial.TBAppraisalReportMaterialEntity;

public interface TBAppraisalReportMaterialServiceI extends CommonService {

  public <T> void delete(T entity);

  public <T> Serializable save(T entity);

  public <T> void saveOrUpdate(T entity);

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBAppraisalReportMaterialEntity t);

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBAppraisalReportMaterialEntity t);

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBAppraisalReportMaterialEntity t);

    /**
     * 鉴定材料审核
     * 
     * @param id
     */
    public AjaxJson doAudit(String id);
}
