package com.kingtake.project.service.impl.reportreq;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.workflow.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.reportreq.TBPmReportReqEntity;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.reportreq.TBPmReportReqServiceI;

@Service("tBPmReportReqService")
@Transactional
public class TBPmReportReqServiceImpl extends CommonServiceImpl implements TBPmReportReqServiceI, ProjectListServiceI {

    @Autowired
    private ActivitiService activitiService;

 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBPmReportReqEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBPmReportReqEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBPmReportReqEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBPmReportReqEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBPmReportReqEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBPmReportReqEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBPmReportReqEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{project_id}",String.valueOf(t.getProjectId()));
 		sql  = sql.replace("#{req_num}",String.valueOf(t.getReqNum()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{research_req}",String.valueOf(t.getResearchReq()));
 		sql  = sql.replace("#{begin_date}",String.valueOf(t.getBeginDate()));
 		sql  = sql.replace("#{end_date}",String.valueOf(t.getEndDate()));
 		sql  = sql.replace("#{report_unit}",String.valueOf(t.getReportUnit()));
 		sql  = sql.replace("#{manage_unit}",String.valueOf(t.getManageUnit()));
 		sql  = sql.replace("#{fee_req}",String.valueOf(t.getFeeReq()));
 		sql  = sql.replace("#{applicantor}",String.valueOf(t.getApplicantor()));
 		sql  = sql.replace("#{bpm_status}",String.valueOf(t.getBpmStatus()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        String businessCode = param.get("key");
        return activitiService.getAuditCount(businessCode);
    }

    @Override
    public void deleteAddAttach(TBPmReportReqEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}