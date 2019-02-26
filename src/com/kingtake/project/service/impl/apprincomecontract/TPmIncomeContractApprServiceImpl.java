package com.kingtake.project.service.impl.apprincomecontract;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.apprincomecontract.TPmIncomeContractApprEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appr.ApprFlowServiceI;
import com.kingtake.project.service.apprincomecontract.TPmIncomeContractApprServiceI;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.impl.appr.ApprServiceImpl;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmIncomeContractApprService")
@Transactional
public class TPmIncomeContractApprServiceImpl extends ApprServiceImpl implements TPmIncomeContractApprServiceI,
        ProjectListServiceI,ApprFlowServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmIncomeContractApprEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmIncomeContractApprEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmIncomeContractApprEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmIncomeContractApprEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmIncomeContractApprEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmIncomeContractApprEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmIncomeContractApprEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getProject().getId()));
 		sql  = sql.replace("#{apply_unit}",String.valueOf(t.getApplyUnit()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{contract_name}",String.valueOf(t.getContractName()));
 		sql  = sql.replace("#{approval_unit}",String.valueOf(t.getApprovalUnit()));
 		sql  = sql.replace("#{the_third}",String.valueOf(t.getTheContractThird()));
 		sql  = sql.replace("#{start_time}",String.valueOf(t.getStartTime()));
 		sql  = sql.replace("#{end_time}",String.valueOf(t.getEndTime()));
 		sql  = sql.replace("#{total_funds}",String.valueOf(t.getTotalFunds()));
 		sql  = sql.replace("#{contract_type}",String.valueOf(t.getContractType()));
 		sql  = sql.replace("#{coor_unit_funds_info}",String.valueOf(t.getCoorUnitFundsInfo()));
 		sql  = sql.replace("#{technology_flag}",String.valueOf(t.getTechnologyFlag()));
 		sql  = sql.replace("#{quality_cetify_flag}",String.valueOf(t.getQualityCetifyFlag()));
 		sql  = sql.replace("#{technology_require}",String.valueOf(t.getTechnologyRequire()));
 		sql  = sql.replace("#{schedule_require}",String.valueOf(t.getScheduleRequire()));
 		sql  = sql.replace("#{quality_require}",String.valueOf(t.getQualityRequire()));
 		sql  = sql.replace("#{funds_require}",String.valueOf(t.getFundsRequire()));
 		sql  = sql.replace("#{special_require}",String.valueOf(t.getSpecialRequire()));
 		sql  = sql.replace("#{other_require}",String.valueOf(t.getOtherRequire()));
 		sql  = sql.replace("#{regulation_require}",String.valueOf(t.getRegulationRequire()));
 		sql  = sql.replace("#{addition_require}",String.valueOf(t.getAdditionRequire()));
 		sql  = sql.replace("#{use_require}",String.valueOf(t.getUseRequire()));
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
    	
    	TSUser user = ResourceUtil.getSessionUserName();
        String sql = "SELECT COUNT(0)FROM T_PM_INCOME_CONTRACT_APPR APPR, T_PM_APPR_SEND_LOGS SEND, T_PM_APPR_RECEIVE_LOGS RECEIVE, T_PM_FORM_CATEGORY EXT "
        		+ "WHERE APPR.ID = SEND.APPR_ID AND SEND.ID = RECEIVE.SEND_ID AND RECEIVE.SUGGESTION_TYPE = EXT.ID "
        		+ "AND RECEIVE.RECEIVE_USERID = '"+user.getId()+"'  AND RECEIVE.OPERATE_STATUS =0  "
        		+ "AND RECEIVE.VALID_FLAG =1 ORDER BY SEND.OPERATE_DATE DESC, RECEIVE.OPERATE_TIME DESC";
        
        return this.commonDao.getCountForJdbc(sql).intValue();
        //return tPmApprLogsService.getAuditCount(ApprovalConstant.APPR_TYPE_INCOME);
    }


    @Override
    public void updateFinishFlag(String id) {
        if (StringUtil.isNotEmpty(id)) {
            //操作：完成流程
            TPmIncomeContractApprEntity t = commonDao.get(TPmIncomeContractApprEntity.class, id);
            if (ApprovalConstant.APPRSTATUS_SEND.equals(t.getFinishFlag())) {
                TSUser user = ResourceUtil.getSessionUserName();
                //操作：完成流程
                t.setFinishFlag(ApprovalConstant.APPRSTATUS_FINISH);
                t.setFinishTime(new Date());
                t.setFinishUserid(user.getId());
                t.setFinishUsername(user.getRealName());
                commonDao.saveOrUpdate(t);
            } 
        } 
    }

    @Override
    public void deleteAddAttach(TPmIncomeContractApprEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }

    @Override
    public void doBack(String id) {
        TPmIncomeContractApprEntity t = commonDao.get(TPmIncomeContractApprEntity.class, id);
        //将审批状态修改为被驳回
        t.setFinishFlag(ApprovalConstant.APPRSTATUS_REBUT);
        this.saveOrUpdate(t);
        
    }

    /**
     * 发送成功，修改审核状态
     */
    @Override
    public void doPass(String id) {
        TPmIncomeContractApprEntity entity = this.get(TPmIncomeContractApprEntity.class, id);
        if (entity != null) {
            if (ApprovalConstant.APPRSTATUS_UNSEND.equals(entity.getFinishFlag())
                    || ApprovalConstant.APPRSTATUS_REBUT.equals(entity.getFinishFlag())) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_SEND);
              this.saveOrUpdate(entity);
            }
        }
        
    }

    /**
     * 撤回，修改审核状态
     */
    @Override
    public String getAppName(String id) {
        String appName = "";
        TPmIncomeContractApprEntity t = commonDao.get(TPmIncomeContractApprEntity.class, id);
        if(t!=null){
            appName = t.getContractName();
        }
        return appName;
    }

    @Override
    public void doCancel(String id) {
        TPmIncomeContractApprEntity entity = this.get(TPmIncomeContractApprEntity.class, id);
        if (entity != null) {
              entity.setFinishFlag(ApprovalConstant.APPRSTATUS_UNSEND);
              this.saveOrUpdate(entity);
        }
    }

}