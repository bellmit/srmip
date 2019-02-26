package com.kingtake.project.service.impl.payapply;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.ApprovalConstant;
import com.kingtake.project.entity.contractnodecheck.TPmOutContractNodeCheckEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.entity.payapply.TPmPayApplyEntity;
import com.kingtake.project.service.apprlog.TPmApprLogsServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;
import com.kingtake.project.service.payapply.TPmPayApplyServiceI;

@Service("tPmPayApplyService")
@Transactional
public class TPmPayApplyServiceImpl extends CommonServiceImpl implements TPmPayApplyServiceI, ProjectListServiceI {

    @Autowired
    private TPmApprLogsServiceI tPmApprLogsService;
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmPayApplyEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmPayApplyEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmPayApplyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TPmPayApplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TPmPayApplyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TPmPayApplyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmPayApplyEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{devdepart_name}",String.valueOf(t.getDevdepartName()));
 		sql  = sql.replace("#{project_manager_name}",String.valueOf(t.getProjectManagerName()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{accounting_code}",String.valueOf(t.getAccountingCode()));
 		sql  = sql.replace("#{project_code}",String.valueOf(t.getProjectCode()));
 		sql  = sql.replace("#{contract_name}",String.valueOf(t.getContractName()));
 		sql  = sql.replace("#{contract_code}",String.valueOf(t.getContractCode()));
 		sql  = sql.replace("#{contract_unitnameb}",String.valueOf(t.getContractUnitnameb()));
 		sql  = sql.replace("#{contract_start_time}",String.valueOf(t.getContractStartTime()));
 		sql  = sql.replace("#{contract_end_time}",String.valueOf(t.getContractEndTime()));
 		sql  = sql.replace("#{contract_total_amount}",String.valueOf(t.getContractTotalAmount()));
        sql = sql.replace("#{contract_paid_amouont}", String.valueOf(t.getContractPaidAmount()));
 		sql  = sql.replace("#{current_pay_amount}",String.valueOf(t.getCurrentPayAmount()));
 		sql  = sql.replace("#{pay_demand}",String.valueOf(t.getPayDemand()));
 		sql  = sql.replace("#{execute_state}",String.valueOf(t.getExecuteState()));
 		sql  = sql.replace("#{project_team_suggestion}",String.valueOf(t.getProjectTeamSuggestion()));
 		sql  = sql.replace("#{contract_node_id}",String.valueOf(t.getContractNodeId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmOutContractNodeCheckEntity.class);
        cq.eq("payFlag", "0");//待审核
        cq.eq("jhcshrId", user.getId());
        cq.add();
        List<TPmOutContractNodeCheckEntity> result = this.commonDao.getListByCriteriaQuery(cq, false);
        return result.size();
    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {
        // TODO Auto-generated method stub

    }
}