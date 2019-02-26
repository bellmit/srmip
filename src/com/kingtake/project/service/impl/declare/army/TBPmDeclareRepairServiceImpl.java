package com.kingtake.project.service.impl.declare.army;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingtake.common.constant.SrmipConstants;
import com.kingtake.project.entity.declare.army.TBPmDeclareRepairEntity;
import com.kingtake.project.entity.manage.TPmProjectEntity;
import com.kingtake.project.service.declare.army.TBPmDeclareRepairServiceI;

@Service("tBPmDeclareRepairService")
@Transactional
public class TBPmDeclareRepairServiceImpl extends CommonServiceImpl implements TBPmDeclareRepairServiceI {

	
 	@Override
    public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TBPmDeclareRepairEntity)entity);
 	}
 	
 	@Override
    public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TBPmDeclareRepairEntity)entity);
 		return t;
 	}
 	
 	@Override
    public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TBPmDeclareRepairEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doAddSql(TBPmDeclareRepairEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doUpdateSql(TBPmDeclareRepairEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	@Override
    public boolean doDelSql(TBPmDeclareRepairEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TBPmDeclareRepairEntity t){
 		sql  = sql.replace("#{military_demand}",String.valueOf(t.getMilitaryDemand()));
 		sql  = sql.replace("#{application_prospect}",String.valueOf(t.getApplicationProspect()));
 		sql  = sql.replace("#{technical_research}",String.valueOf(t.getTechnicalResearch()));
 		sql  = sql.replace("#{advanced_degree}",String.valueOf(t.getAdvancedDegree()));
 		sql  = sql.replace("#{technical_index}",String.valueOf(t.getTechnicalIndex()));
 		sql  = sql.replace("#{scheduling}",String.valueOf(t.getScheduling()));
 		sql  = sql.replace("#{risk_assessment}",String.valueOf(t.getRiskAssessment()));
 		sql  = sql.replace("#{result_form}",String.valueOf(t.getResultForm()));
 		sql  = sql.replace("#{bearing_facility}",String.valueOf(t.getBearingFacility()));
 		sql  = sql.replace("#{past_situation}",String.valueOf(t.getPastSituation()));
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{t_p_id}",String.valueOf(t.getTpId()));
 		sql  = sql.replace("#{project_name}",String.valueOf(t.getProjectName()));
 		sql  = sql.replace("#{project_type_id}",String.valueOf(t.getProjectType()));
 		sql  = sql.replace("#{project_category}",String.valueOf(t.getProjectCategory()));
 		sql  = sql.replace("#{apply_dept_id}",String.valueOf(t.getApplyDeptId()));
 		sql  = sql.replace("#{apply_dept_name}",String.valueOf(t.getApplyDeptName()));
 		sql  = sql.replace("#{apply_time}",String.valueOf(t.getApplyTime()));
 		sql  = sql.replace("#{project_manager_id}",String.valueOf(t.getProjectManagerId()));
 		sql  = sql.replace("#{project_manager_name}",String.valueOf(t.getProjectManagerName()));
 		sql  = sql.replace("#{project_introduce}",String.valueOf(t.getProjectIntroduce()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	@Override
	public TBPmDeclareRepairEntity init(String projectId) {
		/**
		 *  申报书信息
		 */
		TBPmDeclareRepairEntity research = new TBPmDeclareRepairEntity();
		// 项目信息
		TPmProjectEntity project = commonDao.get(TPmProjectEntity.class, projectId);
		research.setTpId(projectId);
		research.setProjectType(project.getProjectType());
		research.setProjectName(project.getProjectName());
        research.setBpmStatus(SrmipConstants.INITIALIZED);//流程状态初始化为未提交
        // 项目负责人
 		research.setProjectManagerId(project.getProjectManagerId());
 		research.setProjectManagerName(project.getProjectManager());
		// 编报单位=当前登录人单位
		TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
		if(depart != null){
			research.setApplyDeptId(depart.getId());
			research.setApplyDeptName(depart.getDepartname());
		}
		// 填报日期=当前日期
		research.setApplyTime(new Date());
		
        //		commonDao.save(research);
        //		
        //		// 将项目人员信息添加到t_pm_declare_member
        //		List<TPmProjectMemberEntity> members = commonDao.findByProperty(
        //				TPmProjectMemberEntity.class, "project.id", projectId);
        //		for(TPmProjectMemberEntity member : members){
        //			TPmDeclareMemberEntity m = new TPmDeclareMemberEntity();
        //			m.setProjDeclareId(research.getId());
        //			m.setUserid(member.getUserId());
        //			m.setName(member.getName());
        //			m.setSex(member.getSex());
        //			m.setBirthday(member.getBirthday());
        //			m.setDegree(member.getDegree());
        //			m.setPosition(member.getPosition());
        //			m.setTaskDivide(member.getTaskDivide());
        //			m.setSuperUnit(member.getSuperUnitId());
        //			m.setProjectManager(member.getProjectManager());
        //			commonDao.save(m);
        //		}
		
		return research;
	}

    @Override
    public void deleteAddAttach(TBPmDeclareRepairEntity t) {
        this.delAttachementByBid(t.getAttachmentCode());
        this.commonDao.delete(t);
        
    }
}