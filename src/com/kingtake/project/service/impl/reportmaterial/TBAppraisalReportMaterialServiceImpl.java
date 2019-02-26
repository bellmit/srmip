package com.kingtake.project.service.impl.reportmaterial;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.appraisal.TBAppraisalProjectEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.reportmaterial.TBAppraisalReportMaterialEntity;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.reportmaterial.TBAppraisalReportMaterialServiceI;

@Service("tBAppraisalReportMaterialService")
@Transactional
public class TBAppraisalReportMaterialServiceImpl extends CommonServiceImpl implements
        TBAppraisalReportMaterialServiceI, ProjectListServiceI {

  public <T> void delete(T entity) {
    super.delete(entity);
    // 执行删除操作配置的sql增强
    this.doDelSql((TBAppraisalReportMaterialEntity) entity);
  }

  public <T> Serializable save(T entity) {
    Serializable t = super.save(entity);
    // 执行新增操作配置的sql增强
    this.doAddSql((TBAppraisalReportMaterialEntity) entity);
    return t;
  }

  public <T> void saveOrUpdate(T entity) {
    super.saveOrUpdate(entity);
    // 执行更新操作配置的sql增强
    this.doUpdateSql((TBAppraisalReportMaterialEntity) entity);
  }

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  public boolean doAddSql(TBAppraisalReportMaterialEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  public boolean doUpdateSql(TBAppraisalReportMaterialEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  public boolean doDelSql(TBAppraisalReportMaterialEntity t) {
    return true;
  }

  /**
   * 替换sql中的变量
   * 
   * @param sql
   * @return
   */
  public String replaceVal(String sql, TBAppraisalReportMaterialEntity t) {
    sql = sql.replace("#{id}", String.valueOf(t.getId()));
    sql = sql.replace("#{level_evaluation}", String.valueOf(t.getLevelEvaluation()));
    sql = sql.replace("#{approve_date}", String.valueOf(t.getApproveDate()));
    sql = sql.replace("#{certificate_year}", String.valueOf(t.getCertificateYear()));
    sql = sql.replace("#{certificate_from_unit}", String.valueOf(t.getCertificateFromUnit()));
    sql = sql.replace("#{certificate_number}", String.valueOf(t.getCertificateNumber()));
    sql = sql.replace("#{certificate_receiptor}", String.valueOf(t.getCertificateReceiptor()));
    sql = sql.replace("#{receiptor_receive_date}", String.valueOf(t.getReceiptorReceiveDate()));
    sql = sql.replace("#{check_flag}", String.valueOf(t.getCheckFlag()));
    sql = sql.replace("#{check_userid}", String.valueOf(t.getCheckUserid()));
    sql = sql.replace("#{check_username}", String.valueOf(t.getCheckUsername()));
    sql = sql.replace("#{check_date}", String.valueOf(t.getCheckDate()));
    sql = sql.replace("#{msg_text}", String.valueOf(t.getMsgText()));
    sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
    sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
    sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
    sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
    sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
    sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
    sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
    return sql;
  }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalReportMaterialEntity.class);
        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("checkUserid", user.getId());
        cq.eq("checkDepartid", user.getCurrentDepart().getId());
        cq.or(Restrictions.eq("checkFlag", "1"), Restrictions.eq("checkFlag", "3"));
        cq.add();
        List<TBAppraisalReportMaterialEntity> materialList = this.commonDao.getListByCriteriaQuery(cq, true);
        return materialList.size();
    }

    @Override
    public AjaxJson doAudit(String id) {
        AjaxJson j = new AjaxJson();
        String message = "操作成功！";
        try {
            if (StringUtil.isNotEmpty(id)) {
                TBAppraisalReportMaterialEntity reportMaterial = commonDao.get(
                        TBAppraisalReportMaterialEntity.class, id);
                if ("1".equals(reportMaterial.getCheckFlag())) {//如果是已提交，则变成已审核
                    reportMaterial.setCheckFlag("2");
                } 
                commonDao.updateEntitie(reportMaterial);
            }
        } catch (Exception e) {
            message = "操作失败！";
            j.setSuccess(false);
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }
}