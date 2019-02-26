package com.kingtake.project.service.impl.appraisalmeeting;
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

import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingDepartEntity;
import com.kingtake.project.entity.appraisalmeeting.TBAppraisalMeetingEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.appraisalmeeting.TBAppraisalMeetingDepartServiceI;
import com.kingtake.project.service.manage.ProjectListServiceI;

@Service("tBAppraisalMeetingDepartService")
@Transactional
public class TBAppraisalMeetingDepartServiceImpl extends CommonServiceImpl implements TBAppraisalMeetingDepartServiceI,
        ProjectListServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBAppraisalMeetingDepartEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBAppraisalMeetingDepartEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBAppraisalMeetingDepartEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TBAppraisalMeetingDepartEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TBAppraisalMeetingDepartEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TBAppraisalMeetingDepartEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBAppraisalMeetingDepartEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{departname}",String.valueOf(t.getDepartname()));
 		sql  = sql.replace("#{quota}",String.valueOf(t.getQuota()));
 		sql  = sql.replace("#{meeting_id}",String.valueOf(t.getMeetingId()));
 		sql  = sql.replace("#{member_type}",String.valueOf(t.getMemberType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

    @Override
    public void getProjectGrid(DataGrid dataGrid, TPmProjectEntity tPmProject, HttpServletRequest request) {

    }

    @Override
    public int getAuditCount(Map<String, String> param) {
        CriteriaQuery cq = new CriteriaQuery(TBAppraisalMeetingEntity.class);
        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("checkUserid", user.getId());
        //cq.eq("checkDepartid", user.getCurrentDepart().getId());
        cq.eq("meetingStatus", "1");
        cq.add();
        List<TBAppraisalMeetingEntity> meetingList = this.commonDao.getListByCriteriaQuery(cq, true);
        return meetingList.size();
    }
}