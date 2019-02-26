package com.kingtake.project.service.impl.checkreport;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.project.entity.checkreport.TPmCheckReportEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.checkreport.TPmCheckReportServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tPmCheckReportService")
@Transactional
public class TPmCheckReportServiceImpl extends CommonServiceImpl implements TPmCheckReportServiceI, ProjectListServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TPmCheckReportEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TPmCheckReportEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TPmCheckReportEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TPmCheckReportEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TPmCheckReportEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TPmCheckReportEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TPmCheckReportEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql = sql.replace("#{t_p_id}", String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{report_title}",String.valueOf(t.getReportTitle()));
 		sql  = sql.replace("#{year_start}",String.valueOf(t.getYearStart()));
 		sql  = sql.replace("#{year_end}",String.valueOf(t.getYearEnd()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{check_status}",String.valueOf(t.getCheckStatus()));
 		sql  = sql.replace("#{check_suggest}",String.valueOf(t.getCheckSuggest()));
 		sql  = sql.replace("#{check_userid}",String.valueOf(t.getCheckUserid()));
 		sql  = sql.replace("#{check_username}",String.valueOf(t.getCheckUsername()));
 		sql  = sql.replace("#{check_date}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void doAudit(TPmCheckReportEntity tPmCheckReport) {

    }

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(TPmCheckReportEntity.class);
        cq.eq("checkUserid", user.getId());
        cq.eq("checkUserDeptId", user.getCurrentDepart().getId());
        cq.or(Restrictions.eq("checkStatus", "1"), Restrictions.eq("checkStatus", "4"));//审核状态为1 已提交
        cq.add();
        List<TPmCheckReportEntity> incomeApplyList = this.commonDao.getListByCriteriaQuery(cq, false);
        return incomeApplyList.size();
    }

    @Override
    public void deleteAddAttach(TPmCheckReportEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
    }
}