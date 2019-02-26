package com.kingtake.project.service.impl.finish;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.common.constant.ProjectConstant;
import com.kingtake.project.entity.finish.TPmFinishApplyEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.finish.TPmFinishApplyServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmFinishApplyService")
@Transactional
public class TPmFinishApplyServiceImpl extends CommonServiceImpl implements TPmFinishApplyServiceI, ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;

  @Override
public <T> void delete(T entity) {
    super.delete(entity);
    // 执行删除操作配置的sql增强
    this.doDelSql((TPmFinishApplyEntity) entity);
  }

  @Override
public <T> Serializable save(T entity) {
    Serializable t = super.save(entity);
    // 执行新增操作配置的sql增强
    this.doAddSql((TPmFinishApplyEntity) entity);
    return t;
  }

  @Override
public <T> void saveOrUpdate(T entity) {
    super.saveOrUpdate(entity);
    // 执行更新操作配置的sql增强
    this.doUpdateSql((TPmFinishApplyEntity) entity);
  }

  /**
   * 默认按钮-sql增强-新增操作
   * 
   * @param id
   * @return
   */
  @Override
public boolean doAddSql(TPmFinishApplyEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-更新操作
   * 
   * @param id
   * @return
   */
  @Override
public boolean doUpdateSql(TPmFinishApplyEntity t) {
    return true;
  }

  /**
   * 默认按钮-sql增强-删除操作
   * 
   * @param id
   * @return
   */
  @Override
public boolean doDelSql(TPmFinishApplyEntity t) {
    return true;
  }

  /**
   * 替换sql中的变量
   * 
   * @param sql
   * @return
   */
  public String replaceVal(String sql, TPmFinishApplyEntity t) {
    sql = sql.replace("#{id}", String.valueOf(t.getId()));
    sql = sql.replace("#{project_id}", String.valueOf(t.getProjectId()));
    sql = sql.replace("#{finish_date}", String.valueOf(t.getFinishDate()));
    sql = sql.replace("#{project_name}", String.valueOf(t.getProjectName()));
    sql = sql.replace("#{source_unit}", String.valueOf(t.getSourceUnit()));
    sql = sql.replace("#{begin_year}", String.valueOf(t.getBeginYear()));
    sql = sql.replace("#{end_year}", String.valueOf(t.getEndYear()));
    sql = sql.replace("#{fee_type}", String.valueOf(t.getFeeType()));
    sql = sql.replace("#{developer_depart_id}", String.valueOf(t.getDeveloperDepartId()));
    sql = sql.replace("#{developer_depart_name}", String.valueOf(t.getDeveloperDepartName()));
    sql = sql.replace("#{project_manager_id}", String.valueOf(t.getProjectManagerId()));
    sql = sql.replace("#{project_manager}", String.valueOf(t.getProjectManager()));
    sql = sql.replace("#{all_fee}", String.valueOf(t.getAllFee()));
    sql = sql.replace("#{extra_fee}", String.valueOf(t.getExtraFee()));
    sql = sql.replace("#{project_finish_info}", String.valueOf(t.getProjectFinishInfo()));
    sql = sql.replace("#{extra_fee_suggestion}", String.valueOf(t.getExtraFeeSuggestion()));
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
    public int getAuditCount(Map<String, String> param) {
        return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_FINISH_APPLY);
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            // 操作：完成流程
            TPmFinishApplyEntity t = commonDao.get(TPmFinishApplyEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getAuditStatus())) {
                TSUser user = ResourceUtil.getSessionUserName();
                // 操作：完成流程
                t.setAuditStatus(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserId(user.getId());
                t.setFinishUserName(user.getRealName());
                commonDao.saveOrUpdate(t);
                TPmProjectEntity project = t.getProject();
                if (project != null) {
                    project.setProjectStatus(ProjectConstant.PROJECT_STATUS_EVALUEATED);
                    this.commonDao.updateEntitie(project);
                }
            }
        }
    }

    

    @Override
    public void deleteAddAttach(TPmFinishApplyEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void doBack(String id) {
        TPmFinishApplyEntity t = commonDao.get(TPmFinishApplyEntity.class, id);
        //将审批状态修改为被驳回
        t.setAuditStatus(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    @Override
    public void doPass(String id) {
        TPmFinishApplyEntity entity = this.get(TPmFinishApplyEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getAuditStatus())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getAuditStatus())) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmFinishApplyEntity t = commonDao.get(TPmFinishApplyEntity.class, id);
        if(t!=null){
            appName = t.getProjectName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmFinishApplyEntity entity = this.get(TPmFinishApplyEntity.class, id);
        if (entity != null) {
              entity.setAuditStatus(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

}